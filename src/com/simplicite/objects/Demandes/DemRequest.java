package com.simplicite.objects.Demandes;

import java.util.*;

import com.simplicite.util.*;
import com.simplicite.util.exceptions.*;
import com.simplicite.util.tools.*;

/**
 * Business object DemRequest
 */
public class DemRequest extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void postLoad() {
		Grant g = getGrant();
		if (g.hasResponsibility("DEM_USER"))
			setSearchSpec(getSearchSpec() +" and t.dem_req_dem_user_id=" + g.getUserId()); 
	}
	
	@Override
	public boolean isCreateEnable() {
		return !isMainInstance() || isHomeInstance();
	}
	
	@Override
	public String[] getTargetObject(String rowId, String[] row) {
		if (isCopied())
			rowId = getCopyId();
		else if (rowId.equals(ObjectField.DEFAULT_ROW_ID))
			return null;
		if (row==null && (rowId.equals(getRowId()) || select(rowId)))
			row = getValues();
		String target = null;
		if (row!=null) {
			String type = row[getFieldIndex("demReqType")];
			if (type.equals("PURCHASE")) target = "DemPurchase";
			else if (type.equals("RENTAL")) target = "DemRental";
			
		}
		if (target==null) return null; // no redirection
		String t[] = new String[3];
		t[0] = target; // target object
		t[1] = "the_ajax_"+target; // main target instance
		t[2] = rowId; // target id
		return t;
	}
}
