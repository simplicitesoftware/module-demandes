package com.simplicite.objects.Demandes;

import java.util.*;

import com.simplicite.util.*;
import com.simplicite.util.exceptions.*;
import com.simplicite.util.tools.*;

/**
 * Business object DemUser
 */
public class DemUser extends ObjectDB {
	private static final long serialVersionUID = 1L;
	private static final String ROW_MODULE_ID = "row_module_id";
	@Override
	public void postLoad() {
		setMenuStates(false); 
		//Fields Visibility
		getField(ROW_MODULE_ID).setVisibility(ObjectField.VIS_HIDDEN); 
		getField("usr_home_id").setVisibility(ObjectField.VIS_HIDDEN); 
		getField("usr_lang").setVisibility(ObjectField.VIS_HIDDEN); 
		getField("usr_active").setVisibility(ObjectField.VIS_HIDDEN); 
		
		//Default value
		setFieldValue("usr_lang","FRA"); 
	}
	
	@Override
	public List<String> preValidate() {
		List<String> msgs = new ArrayList<>();
		setFieldValue("usr_active","1");
		setFieldValue(ROW_MODULE_ID,ModuleDB.getModuleId​("DemandesUsers",true));
		return msgs;
	}
	
	@Override
	public String postSave() {
		createResp(getGrant(),getRowId(),getFieldValue("demDemuserRole"));
		return null;
	}
	
	private void createResp(Grant g, String userId, String role){
		String objname ="Responsability";
		boolean oldcrud[] =g.changeAccess(objname,true,true,true,true);
		ObjectDB resp = g.getTmpObject(objname);
		synchronized(resp.getLock()){
			try{
				resp.getTool().getForCreate();
				resp.setFieldValue(ROW_MODULE_ID,ModuleDB.getModuleId​("DemandesUsers",true));
				resp.setFieldValue("rsp_login_id", userId);
				resp.setFieldValue("rsp_start_dt", Tool.getCurrentDate(-1));
				switch(role){
					case "A" : 
						resp.setFieldValue("rsp_group_id", GroupDB.getGroupId("DEM_ADMINISTRATOR"));
						break;
					case "M" : 
						resp.setFieldValue("rsp_group_id", GroupDB.getGroupId("DEM_MANAGER"));
						break; 
					case "U" : 
						resp.setFieldValue("rsp_group_id", GroupDB.getGroupId("DEM_USER"));
						break; 
					default:
	                    break;
				}
				resp.getTool().validateAndCreate();
			}catch(Exception e){
				AppLog.error("Error creating responsability", e, getGrant());
			}finally{
				g.changeAccess(objname,oldcrud);	
			}
			
		}
		
	}
}
