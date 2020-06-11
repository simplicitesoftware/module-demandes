package com.simplicite.objects.Demandes;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Business object DemSprReq
 */
public class DemSprReq extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String[] getTargetObject(String rowId, String[] row) {
		if("1".equals(getParameter("_UI_EDIT_TEMPLATE_"))) // Template editor
			return null;
		if(rowId.equals(ObjectField.DEFAULT_ROW_ID)) 
			return null;
		if(getParentObject() == null)
			return null;
		if(row == null && (rowId.equals(getRowId()) || select(rowId)))
			row = getValues();
		if(row == null)
			return null;
		if(getParentObject().getName().equals("DemSupplier"))
			return new String[]{"DemRequest", "the_ajax_DemRequest", row[getFieldIndex("demSprreqReqId")] };
		if(getParentObject().getName().equals("DemRequest"))
			return new String[]{ "DemSupplier", "the_ajax_DemSupplier", row[getFieldIndex("demSprreqSprId")] };
		return null;
	}
	
	@Override
	public boolean isCreateEnable() {
		return false;
	}
	
}
