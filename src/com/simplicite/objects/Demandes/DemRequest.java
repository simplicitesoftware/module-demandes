package com.simplicite.objects.Demandes;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.commons.Demandes.DemCommon;

/**
 * Business object DemRequest
 */
public class DemRequest extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void postLoad() {
		if(getGrant().hasResponsibility("DEM_USERS")){
			setSearchSpec("t.created_by='user' and t.dem_req_type='PURCHASE'");
		}
		if(getGrant().hasResponsibility("DEM_MANAGERS")){
			setSearchSpec("t.created_by= 'manager' and t.dem_req_type = 'PURCHASE' or t.created_by='user' and t.dem_req_type='PURCHASE'");
		}
		if(getGrant().hasResponsibility("DEM_ADMINISTRATOR")){
			setSearchSpec("t.dem_req_status='VALIDATED' and t.dem_req_type='PURCHASE' or t.dem_req_status='PROCESSING' and t.dem_req_type='PURCHASE' or t.dem_req_status = 'PROCESSED' and t.dem_req_type='PURCHASE' or t.dem_req_status='CLOSED' and t.dem_req_type='PURCHASE' or t.dem_req_status = 'REJECTEDADM' and t.dem_req_type='PURCHASE'");
		}
	}
	
	@Override
	public boolean isCreateEnable() {
		if(getGrant().hasResponsibility("DEM_ADMINISTRATOR") && !getFieldValue("demReqStatus").equals("VALIDATED"))
			return false;
		return true;
	}
	
	@Override
	public boolean isUpdateEnable(String[] row) {
		return isCreateEnable();
	}
	
	@Override
	public void initCreate() {
		setFieldValue("demReqType", "PURCHASE");
		setFieldValue("demReqReference", DemCommon.getNumero(getGrant(), "dem_req_reference", "dem_request", "REQ"));
		setFieldValue("demReqRequestDate", Tool.getCurrentDate());
		DemCommon.setUpdatableFieldsRequest(this);
	}
	
	@Override
	public void initUpdate() {
		if((("PENDING").equals(getFieldValue("demReqStatus")) && getFieldValue("demReqFutherInformation").equals(""))){
			DemCommon.setUpdatableFieldsRequest(this);
		}
		if(!("REQUESTFUTHERINFO").equals(getFieldValue("demReqStatus"))){
			getField("demReqFutherInformation").setUpdatable(false);
		}
	}

	@Override
	public void initCopy() {
		initCreate();
	}
	
	@Override
	public void preSelect(String rowId, boolean copy) {
		resetUpdatables();
		getGrant().setParameter("demreqstat", getFieldValue("demReqStatus"));
	}
	
	@Override
	public String preSave() {
		if(!getFieldValue("demReqRejectedReasonAdministrator").equals("") && getGrant().hasResponsibility("DEM_ADMINISTRATOR") || !getFieldValue("demReqRejectedReasonAdministrator").equals("") && getGrant().hasResponsibility("DEM_GROUP"))
			setFieldValue("demReqStatus", "REJECTEDADM");
		if(!getFieldValue("demReqRejectedReasonManager").equals("") && getGrant().hasResponsibility("DEM_MANAGERS") || !getFieldValue("demReqRejectedReasonManager").equals("") && getGrant().hasResponsibility("DEM_GROUP"))
			setFieldValue("demReqStatus", "REJECTEDMAN");
		return null;
	}
	
	public void setRejectedReason(Map<String, String> params) {
		ObjectField rejectedReason;
		if(getGrant().hasResponsibility("DEM_MANAGERS") || getGrant().hasResponsibility("DEM_GROUP") ){
			rejectedReason = getField("demReqRejectedReasonManager");
			rejectedReason.setValue(params.get("demRejectedReasonManager"));
			save();
		}
		if(getGrant().hasResponsibility("DEM_ADMINISTRATOR") || getGrant().hasResponsibility("DEM_GROUP") ){
			rejectedReason = getField("demReqRejectedReasonAdministrator");
			rejectedReason.setValue(params.get("demRejectedReasonAdministrator"));
			save();
		}
	}
}
