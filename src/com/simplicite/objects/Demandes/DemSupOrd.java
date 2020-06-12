package com.simplicite.objects.Demandes;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.commons.Demandes.DemCommon;

/**
 * Business object DemSupOrd
 */
public class DemSupOrd extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean isCreateEnable() {
		if(getParentObject() != null && getParentObject().getName().equals("DemOrder") && !getParentObject().getFieldValue("demOrdStatus").equals("PENDING")){
			return false;
		}
		return getParentObject() != null && !getParentObject().getName().equals("DemSupply");
	}
	
	@Override
	public boolean isUpdateEnable(String[] row) {
		return isCreateEnable();
	}
	
	@Override
	public void initCreate() {
		boolean isParam = getGrant().getParameter("placeorder_demSupRef").isEmpty(); 
		Grant g = getGrant();
		if(!isParam){
			double quantityStock = 0;
			ObjectDB demSup = getGrant().getTmpObject("DemSupply");
			ObjectDB demReqSup = getGrant().getTmpObject("DemReqSup");
			synchronized(demReqSup){
				demReqSup.resetFilters();
				demReqSup.setFieldFilter("demReqsupReqId", getGrant().getParameter("placeorder_demReqId"));
				List<String[]> listReqSup = demReqSup.search();
				for(int i = 0; i < listReqSup.size(); i++){
					demReqSup.setValues(listReqSup.get(i));
					String supRef = demReqSup.getFieldValue("demReqsupSupId.demSupReference");
					setFieldValue("demSupordSupId.demSupReference", supRef);
					synchronized(demSup){
						demSup.resetFilters();
						demSup.setFieldFilter("demSupReference", supRef);
						List<String[]> sups = demSup.search();
						demSup.setValues(sups.get(0));
						quantityStock = Tool.parseDouble(demSup.getFieldValue("demSupStockQuantity"));
						double quantityReq = Tool.parseDouble(demReqSup.getFieldValue("demReqsupQuantityRequired"));
						double quantityOrdered = quantityReq - quantityStock;
						if(quantityOrdered < 0)
							quantityOrdered = 0;
						setFieldValue("demSupordQuantityOrdered", quantityOrdered);
						setFieldValue("demSupordSupId", demSup.getRowId());
						setFieldValue("demSupordSupId.demSupName", demSup.getFieldValue("demSupName"));
						setFieldValue("demSupordSupId.demSupSprId.demSprName", demSup.getFieldValue("demSupSprId.demSprName"));
					}
				}
			}
			setFieldValue("demSupordOrdId.demOrdReference", DemCommon.getNumero(g, "dem_ord_reference", "dem_order",  "ORD"));
		}
	}
	
	@Override
	public void initUpdate() {
		ObjectDB demOrd = getGrant().getTmpObject("DemOrder");
		synchronized(demOrd){
			demOrd.select(getFieldValue("demSupordOrdId"));
			if(!demOrd.getFieldValue("demOrdStatus").equals("PENDING")){
				getField("demSupordSupId").setUpdatable(false);
				getField("demSupordOrdId").setUpdatable(false);
				getField("demSupordQuantityOrdered").setUpdatable(false);
			}
		}
	}

	@Override
	public List<String> preValidate() {
		List<String> msgs = new ArrayList<>();
		Grant g = getGrant();
		boolean isParam = g.getParameter("placeorder_demSupRef") == null;
		g.removeParameter("placeorder_demSupRef");
		if(!isParam){
			String demOrdRowId = createOrderAndReqOrd();
			updateRequestStatus();
			double quantiteStockInitial = 0;
			double quantiteReq = 0;
			//Récupère les ReqSup pour une demande donnée
			ObjectDB demReqSup = g.getTmpObject("DemReqSup");
			synchronized(demReqSup){
				demReqSup.resetFilters();
				demReqSup.setFieldFilter("demReqsupReqId", g.getParameter("placeorder_demReqId"));
				List<String[]> listReqSup = demReqSup.search();
				for(int i = 0; i < listReqSup.size(); i++){
					demReqSup.setValues(listReqSup.get(i));
					ObjectDB demSupply = g.getTmpObject("DemSupply");
					synchronized(demSupply){
						//Récupère les fournitures pour mettre à jour leur stock
						String supId = demReqSup.getFieldValue("demReqsupSupId");
						demSupply.resetFilters();
						demSupply.select(supId);
						quantiteReq =  Tool.parseDouble(demReqSup.getFieldValue("demReqsupQuantityRequired"));
						quantiteStockInitial = Tool.parseDouble(demSupply.getFieldValue("demSupStockQuantity"));
						double quantiteStock = Tool.parseDouble(demSupply.getFieldValue("demSupStockQuantity")) - quantiteReq;
						if(quantiteStock < 0)
							quantiteStock = 0;
						demSupply.setFieldValue("demSupStockQuantity", quantiteStock);
						demSupply.update();
						//Récupère l'id du fournisseur et créer SprReq
						ObjectDB demSprReq = g.getTmpObject("DemSprReq");
						synchronized(demSprReq){
							demSprReq.setFieldValue("demSprreqReqId", g.getParameter("placeorder_demReqId"));
							g.removeParameter("placeorde_demReqId");
							demSprReq.setFieldValue("demSprreqSprId", demSupply.getFieldValue("demSupSprId"));
							demSprReq.create();
						}
						//Set les infos de SupOrd pour pouvoir le créer
						double quantiteOrdered = quantiteReq - quantiteStockInitial;
						if(quantiteOrdered < 0)
							quantiteOrdered = 0;
						setFieldValue("demSupordQuantityOrdered", quantiteOrdered);
						setFieldValue("demSupordOrdId.demOrdReference", DemCommon.getNumero(g, "dem_ord_reference", "dem_order", "ORD"));
						setFieldValue("demSupordOrdId", demOrdRowId);
						setFieldValue("demSupordSupId.demSupSprId.demSprName", demSupply.getFieldValue("demSupSprId.demSprName"));
						setFieldValue("demSupordSupId.demSupPrice", demSupply.getFieldValue("demSupPrice"));
						setFieldValue("demSupordSupId", demReqSup.getFieldValue("demReqsupSupId"));
						setFieldValue("demSupordSupId.demSupName", demReqSup.getFieldValue("demReqsupSupId.demSupName"));
						create();
					}
				}
			}
		}
		return msgs;
	}
	
	//Création d'une commande et de la demande/commande
	public String createOrderAndReqOrd(){
		String demOrdRowId;
		ObjectDB demOrd = getGrant().getTmpObject("DemOrder");
		synchronized(demOrd){
			demOrd.setFieldValue("demOrdReference", DemCommon.getNumero(getGrant(), "dem_ord_reference", "dem_order",  "ORD"));
			demOrd.setFieldValue("demOrdOrderDate", Tool.getCurrentDate());
			demOrd.setFieldValue("demOrdStatus", "PENDING");
			demOrd.create();
			demOrdRowId = demOrd.getRowId();
		}
		ObjectDB demReqOrd = getGrant().getTmpObject("DemReqOrd");
		synchronized(demReqOrd){
			demReqOrd.setFieldValue("demReqordOrdId", demOrdRowId);
			demReqOrd.create();
		}
		return demOrdRowId;
	}
	
	//Changer le statut de la demande en Processed
	public void updateRequestStatus(){
		ObjectDB demRequest = getGrant().getTmpObject("DemRequest");
		synchronized(demRequest){
			demRequest.select(getGrant().getParameter("placeorder_demReqId"));
			demRequest.setFieldValue("demReqStatus", "PROCESSED");
			demRequest.update();
		}
	}
}
