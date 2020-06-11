package com.simplicite.objects.Demandes;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.commons.Demandes.DemCommon;

/**
 * Business object DemLocation
 */
public class DemRental extends DemRequest {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void postLoad() {
		if(getGrant().hasResponsibility("DEM_USERS")){
			setSearchSpec("t.created_by='user' and t.dem_req_type='RENTAL'");
		}
		if(getGrant().hasResponsibility("DEM_MANAGERS")){
			setSearchSpec("t.created_by= 'manager' and t.dem_req_type = 'RENTAL' or t.created_by='user' and t.dem_req_type='RENTAL'");
		}
	}
	
	@Override
	public void initCreate() {
		setFieldValue("demReqType", "RENTAL");
		setFieldValue("demReqReference", DemCommon.getNumero(getGrant(), "dem_req_reference", "dem_request", "REQ"));
		setFieldValue("demReqRequestDate", Tool.getCurrentDate());
		setUpdatableFields();
	}
	
	@Override
	public void initUpdate() {
		if((("PENDING").equals(getFieldValue("demReqStatus")) && getFieldValue("demReqFutherInformation").equals(""))){
			setUpdatableFields();
		}
		if(!("REQUESTFUTHERINFO").equals(getFieldValue("demReqStatus"))){
			getField("demReqFutherInformation").setUpdatable(false);
		}
	}
	@Override
	public void setUpdatableFields(){
		getField("demReqTitle").setUpdatable(true);
		getField("demReqReason").setUpdatable(true);
		getField("demReqSupplyType").setUpdatable(true);
		getField("demRenStartDate").setUpdatable(true);
		getField("demRenEndDate").setUpdatable(true);
	}

	@Override
	public String preSave() {
		if(getContext().isCreate() || getContext().isCopy()){
			setFieldValue("demReqReference", DemCommon.getNumero(getGrant(), "dem_req_reference", "dem_request", "REQ"));
		}
		if(!getFieldValue("demReqRejectedReasonManager").equals("") && getGrant().hasResponsibility("DEM_MANAGERS") ||
		!getFieldValue("demReqRejectedReasonManager").equals("") && getGrant().hasResponsibility("DEM_GROUP"))
			setFieldValue("demReqStatus", "REJECTEDMAN");
		return null;
	}
	@Override
	public void setRejectedReason(Map<String, String> params) {
		ObjectField rejectedReason;
		if(getGrant().hasResponsibility("DEM_MANAGERS")|| getGrant().hasResponsibility("DEM_GROUP")){
			rejectedReason = getField("demReqRejectedReasonManager");
			rejectedReason.setValue(params.get("demRejectedReasonManager"));
			save();
		}
	}

}
