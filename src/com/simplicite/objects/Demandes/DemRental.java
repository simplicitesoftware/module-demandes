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
	public void preSelect(String rowId, boolean copy) {
		if(Tool.diffDatetime(Tool.getCurrentDateTime(), getFieldValue("demRenStartDate")) <= 0){
			DemCommon.setStockQuantityRental(this, getGrant());
		}
		if(Tool.diffDatetime(Tool.getCurrentDateTime(), getFieldValue("demRenEndDate")) <= 0){
			setFieldValue("demReqStatus", "CLOSED");
			ObjectDB demReqSup = getGrant().getTmpObject("DemReqSup");
			synchronized(demReqSup){
				demReqSup.resetFilters();
				demReqSup.setFieldFilter("demReqsupReqId", rowId);
				List<String[]> listReqSup = demReqSup.search();
				for(int i = 0; i < listReqSup.size(); i++){
					demReqSup.setValues(listReqSup.get(i));
					int quantiteRequired = Tool.parseInt(demReqSup.getFieldValue("demReqsupQuantityRequired"));
					ObjectDB demSup = getGrant().getTmpObject("DemSupply");
					synchronized(demSup){
						demSup.select(demReqSup.getFieldValue("demReqsupSupId"));
						int quantiteStock = Tool.parseInt(demSup.getFieldValue("demSupStockQuantity"));
						demSup.setFieldValue("demSupStockQuantity", quantiteStock + quantiteRequired );
						demSup.update();
					}
				}
				update();
			}
		}
	}
	
	@Override
	public void initCreate() {
		setFieldValue("demReqType", "RENTAL");
		setFieldValue("demReqReference", DemCommon.getNumero(getGrant(), "dem_req_reference", "dem_request", "REQ"));
		setFieldValue("demReqRequestDate", Tool.getCurrentDate());
		DemCommon.setUpdatableFieldsRental(this);
	}
	
	@Override
	public void initUpdate() {
		if(("PENDING").equals(getFieldValue("demReqStatus")) && getFieldValue("demReqFutherInformation").isEmpty()){
			DemCommon.setUpdatableFieldsRental(this);
		}
		if(("DRAFT").equals(getFieldValue("demReqStatus")))
			DemCommon.setUpdatableFieldsRental(this);
		if(!("REQUESTFUTHERINFO").equals(getFieldValue("demReqStatus"))){
			getField("demReqFutherInformation").setUpdatable(false);
		}
	}
	
	@Override
	public String preSave() {
		if(getContext().isCreate() || getContext().isCopy()){
			setFieldValue("demReqReference", DemCommon.getNumero(getGrant(), "dem_req_reference", "dem_request", "REQ"));
		}
		if(!getFieldValue("demReqRejectedReasonManager").isEmpty() && getGrant().hasResponsibility("DEM_MANAGERS") ||
		!getFieldValue("demReqRejectedReasonManager").isEmpty() && getGrant().hasResponsibility("DEM_GROUP"))
			setFieldValue("demReqStatus", "REJECTEDMAN");
		return null;
	}
	
	@Override
	public void setRejectedReason(Map<String, String> params) { 
		if(getGrant().hasResponsibility("DEM_MANAGERS")|| getGrant().hasResponsibility("DEM_GROUP")){
			ObjectField rejectedReason = getField("demReqRejectedReasonManager");
			rejectedReason.setValue(params.get("demRejectedReasonManager"));
			save();
		}
	}
	
	public String setStockQuantity(){
		setFieldValue("demReqStatus", "VALIDATED");
		update();
		if(Tool.diffDatetime(Tool.getCurrentDateTime(), getFieldValue("demRenStartDate")) <= 0){
			return DemCommon.setStockQuantityRental(this, getGrant());		
		}
		return null;
	}

}
