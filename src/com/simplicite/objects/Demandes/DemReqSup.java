 package com.simplicite.objects.Demandes;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.commons.Demandes.DemCommon;

/**
 * Business object DemReqSup
 */
public class DemReqSup extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void postLoad() {
		if(getGrant().hasResponsibility("DEM_USER")){
			setSearchSpec("t.created_by = 'user'");
		}
		if(getGrant().hasResponsibility("DEM_MANAGER")){
			setSearchSpec("t.created_by= 'manager' or t.created_by='user'");
		}
		getField("demReqsupReqId.demReqReference").setUpdatable(false);
	}	
	
	@Override
	public boolean isCreateEnable() {
		ObjectDB parentObject = getParentObject();
		if(parentObject != null && parentObject.getName().equals("DemRequest") 
			&& ("PENDING").equals(parentObject.getFieldValue("demReqStatus"))){
			return parentObject.getFieldValue("demReqFutherInformation").isEmpty();
		}
		if(parentObject != null && parentObject.getName().equals("DemRequest") && ("PENDING").equals(parentObject.getFieldValue("demReqStatus"))  || parentObject != null && parentObject.getName().equals("DemRequest") && ("DRAFT").equals(parentObject.getFieldValue("demReqStatus")))
			return true;
		if(parentObject != null && parentObject.getName().equals("DemRental") && ("PENDING").equals(parentObject.getFieldValue("demReqStatus")) || parentObject != null && parentObject.getName().equals("DemRental") && ("DRAFT").equals(parentObject.getFieldValue("demReqStatus")))
			return true;
		return parentObject != null && !parentObject.getName().equals("DemSupply");
	}
	
	@Override
	public boolean isUpdateEnable(String[] row) {
		return isCreateEnable();
	}
	
	@Override
	public boolean isDeleteEnable(String[] row) {
		return isCreateEnable();
	}

	@Override
	public void initUpdate() {
		initCreate();
	}
	
	@Override
	public void initCreate() {
		if(getParentObject() != null && getParentObject().getName().equals("DemRequest") && ("PENDING").equals(getParentObject().getFieldValue("demReqStatus")) || getParentObject() != null && getParentObject().getName().equals("DemRequest") && ("DRAFT").equals(getParentObject().getFieldValue("demReqStatus"))){
			getField("demReqsupQuantityRequired").setUpdatable(true);
		}
		if(getParentObject() != null && getParentObject().getName().equals("DemRental")){
			getField("demReqsupQuantityRequired").setUpdatable(true);
		}
	}
		
	@Override
	public boolean isActionEnable(String[] row, String action) { 
	if(action.equals("DEM_GETSTOCK") && getParentObject() != null && getParentObject().getName().equals("DemRequest") 
		|| action.equals("DEM_ORDER") && getParentObject() != null && getParentObject().getName().equals("DemRequest"))
			{
				return getParentObject().getFieldValue("demReqStatus").equals("PROCESSING");
			} else if (action.equals("DEM_ORDER") && getParentObject() != null 
			|| action.equals("DEM_GETSTOCK") && getParentObject() != null) {
				return getParentObject().getName().equals("DemRequest");
			} else {
				return true;
			}
	}
	
	@Override
	public String postUpdate() {
		boolean isRequestComplete = true;
		if(getParentObject() != null && getParentObject().getName().equals("DemRequest")){
			resetFilters();
			setFieldFilter("demReqsupReqId",getParentObject().getRowId());
			List<String[]> reqSupList = search();
			for(int i = 0; i < reqSupList.size(); i++){
				setValues(reqSupList.get(i));
				if(!getFieldValue("demReqsupQuantityRequired").equals("0"))
					isRequestComplete = false;
				ObjectDB demSprReq = getGrant().getTmpObject("DemSprReq");
				synchronized(demSprReq){
					demSprReq.setFieldValue("demSprreqReqId", getFieldValue("demReqsupReqId"));
					demSprReq.setFieldValue("demSprreqSprId", getFieldValue("demReqsupSupId.demSupSprId"));
					demSprReq.create();
				}
			}
			if(isRequestComplete){
				getParentObject().setFieldValue("demReqStatus", "CLOSED");
				getParentObject().update();
			}
		}
		return null;
	}
	
	public void setOrderQuantity(Map<String, String> params){
		List<String> selectedIds = getSelectedIds();
		for(String id : selectedIds){
			select(id);
			int quantiteRequired = Tool.parseInt(getFieldValue("demReqsupQuantityRequired"));
			int paramOrder = Tool.parseInt(params.get("demOrderQuantity"));
			int quantiteStock = 0;
			setFieldValue("demReqsupQuantityOrdered", paramOrder);
			ObjectDB demSup = getGrant().getTmpObject("DemSupply");
			synchronized(demSup){
				demSup.select(getFieldValue("demReqsupSupId"));
				quantiteStock = Tool.parseInt(demSup.getFieldValue("demSupStockQuantity"));
				quantiteStock = paramOrder - quantiteRequired + quantiteStock;
				demSup.setFieldValue("demSupStockQuantity", quantiteStock);
				demSup.update();
			}
			quantiteRequired = quantiteRequired - paramOrder;
			if(quantiteRequired < 0)
				quantiteRequired = 0;
			setFieldValue("demReqsupQuantityRequired", quantiteRequired);
			if(!getFieldValue("demReqsupOrderChoice").isEmpty())
				setFieldValue("demReqsupOrderChoice", "ORDER;DECREASESTOCK");
			else
				setFieldValue("demReqsupOrderChoice", "ORDER");
			update();
		}
	}
	
	public void decreaseStock(){
		List<String> selectedIds = getSelectedIds();
		int quantiteRequired = 0;
		int quantiteInit = 0;
		for(String id : selectedIds){
			select(id);
			ObjectDB demSup = getGrant().getTmpObject("DemSupply");
			synchronized(demSup){
				demSup.select(getFieldValue("demReqsupSupId"));
				quantiteRequired = Tool.parseInt(getFieldValue("demReqsupQuantityRequired"));
				quantiteInit = Tool.parseInt(demSup.getFieldValue("demSupStockQuantity"));
				int quantiteStock = quantiteInit - quantiteRequired;
				quantiteRequired = quantiteRequired - quantiteInit;
				if(quantiteStock < 0){
					quantiteStock = 0;
				}
				if(quantiteRequired < 0){
					quantiteRequired = 0;
				}
				demSup.setFieldValue("demSupStockQuantity", quantiteStock);
				demSup.update();
			}
			setFieldValue("demReqsupQuantityRequired", quantiteRequired);
			if(quantiteInit != 0){
				setFieldValue("demReqsupOrderChoice", "DECREASESTOCK");
				setFieldValue("demReqsupQuantityOrdered", "0");
				update();
			}
		}
	}
	
	@Override
	public String[] getTargetObject(String rowId, String[] row) {
		if(DemCommon.verifTargetObject(rowId, row, getParentObject(), this) != null)
			return DemCommon.getTargetObjectCom(row, "DemSupply", "DemRequest", "demReqsupSupId", "demReqsupReqId", getParentObject(), this);
		return null;
	}
}
