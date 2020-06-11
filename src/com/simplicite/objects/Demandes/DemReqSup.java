package com.simplicite.objects.Demandes;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

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
			&& ("PENDING").equals(parentObject.getFieldValue("demReqStatus")) 
			&& parentObject.getFieldValue("demReqFutherInformation") != ""){
			return false;
		}
		if(parentObject != null && parentObject.getName().equals("DemRequest"))
			return ("PENDING").equals(parentObject.getFieldValue("demReqStatus"));
		if(parentObject != null && parentObject.getName().equals("DemSupply"))
			return false;
		if(parentObject != null && parentObject.getName().equals("DemRental"))
			return ("PENDING").equals(parentObject.getFieldValue("demReqStatus"));
		return true;
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
		if(getParentObject() != null && getParentObject().getName().equals("DemRequest")){
			getField("demReqsupQuantityRequired").setUpdatable(("PENDING").equals(getParentObject().getFieldValue("demReqStatus")));
		}
		if(getParentObject() != null && getParentObject().getName().equals("DemRental")){
			getField("demReqsupQuantityRequired").setUpdatable(true);
		}
	}
		
	@Override
	public boolean isActionEnable(String[] row, String action) {
		if(action.equals("DemRequestOrder")){
			String statut = getGrant().getParameter("demreqstat");
			return statut.equals("PROCESSING");	
		}
		return true;
	}
		
	public String createOrder(){
		try {
			ObjectDB demReqOrd = getGrant().getTmpObject("DemReqOrd");
			synchronized(demReqOrd){
				demReqOrd.setFieldValue("demReqordReqId", getFieldValue("demReqsupReqId"));
			}
			getGrant().setParameter("placeorder_demReqId", getFieldValue("demReqsupReqId"));
			getGrant().setParameter("placeorder_demSupRef", getFieldValue("demReqsupSupId.demSupReference"));
			return "REDIRECT:"+HTMLTool.getFormURL("DemSupOrd","the_ajax_demSupOrd", ObjectField.DEFAULT_ROW_ID, "nav=new&action=create");
		}
		catch(Exception e) {
			return e.getMessage()+"#ERROR";
		}
	} 
	
}
