package com.simplicite.objects.Demandes;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.commons.Demandes.DemCommon;

/**
 * Business object DemReqOrd
 */
public class DemReqOrd extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean isCreateEnable() {
		if(getParentObject() != null && getParentObject().getName().equals("DemOrder") && !getParentObject().getFieldValue("demOrdStatus").equals("PENDING"))
			return false;
		return	getParentObject() != null && !getParentObject().getName().equals("DemRequest");
	}
	
	@Override
	public boolean isUpdateEnable(String[] row) {
		return isCreateEnable();
	}
	
	@Override
	public void initUpdate() {
		ObjectDB demOrd = getGrant().getTmpObject("DemOrder");
		synchronized(demOrd){
			demOrd.select(getFieldValue("demReqordOrdId"));
			if(!demOrd.getFieldValue("demOrdStatus").equals("PENDING")){
				getField("demReqordReqId").setUpdatable(false);
				getField("demReqordOrdId").setUpdatable(false);
			}
		}
	}
	
	@Override
	public String[] getTargetObject(String rowId, String[] row) {
		if(DemCommon.verifTargetObject(rowId, row, getParentObject(), this) != null)
			return DemCommon.getTargetObjectCom(row, "DemRequest", "DemOrder", "demReqordReqId", "demReqordOrdId", getParentObject(), this);
		return null;
	}

	@Override
	public String postSave() {
		//Récupérer la liste des supplies à commander pour le request sélectionné
		//ensuite créer supOrd
		String demSupQuantityStock;
		ObjectDB demReqSup = getGrant().getTmpObject("DemReqSup");
		synchronized(demReqSup){
			demReqSup.resetFilters();
			demReqSup.setFieldFilter("demReqsupReqId", getFieldValue("demReqordReqId"));
			List<String[]> listSup = demReqSup.search();
			for(int i = 0; i < listSup.size(); i++){
				demReqSup.setValues(listSup.get(i)); 
				ObjectDB demSup = getGrant().getTmpObject("DemSupply");
				synchronized(demSup){
					demSup.select(demReqSup.getFieldValue("demReqsupSupId"));
					demSupQuantityStock = demSup.getFieldValue("demSupStockQuantity");
					double quantiteStock = Tool.parseDouble(demSupQuantityStock) - Tool.parseDouble(demReqSup.getFieldValue("demReqsupQuantityRequired"));
					if(quantiteStock < 0)
						quantiteStock = 0;
					demSup.setFieldValue("demSupStockQuantity", quantiteStock);
					demSup.update();
					//Récupère l'id du fournisseur et créer SprReq
					ObjectDB demSprReq = getGrant().getTmpObject("DemSprReq");
					synchronized(demSprReq){
						demSprReq.setFieldValue("demSprreqReqId", demReqSup.getFieldValue("demReqsupReqId"));
						demSprReq.setFieldValue("demSprreqSprId", demSup.getFieldValue("demSupSprId"));
						demSprReq.create();
					}
				}
				ObjectDB demSupOrd = getGrant().getTmpObject("DemSupOrd");
				synchronized(demSupOrd){
					demSupOrd.setFieldValue("demSupordSupId", demReqSup.getFieldValue("demReqsupSupId"));
					demSupOrd.setFieldValue("demSupordOrdId", getFieldValue("demReqordOrdId"));
					double quantiteOrdered = Tool.parseDouble(demReqSup.getFieldValue("demReqsupQuantityRequired")) - Tool.parseDouble(demSupQuantityStock);
					if(quantiteOrdered < 0)
						quantiteOrdered = 0;
					demSupOrd.setFieldValue("demSupordQuantityOrdered", quantiteOrdered);
					demSupOrd.create();
				}
			}
			ObjectDB demRequest = getGrant().getTmpObject("DemRequest");
			synchronized(demRequest){
				demRequest.select(getFieldValue("demReqordReqId"));
				demRequest.setFieldValue("demReqStatus", "PROCESSED");
				demRequest.update();
			}
		}
		return  null;
	}
	
}
