package com.simplicite.objects.Demandes;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.commons.Demandes.DemCommon;

/**
 * Business object DemSprReq
 */
public class DemSprReq extends ObjectDB {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String[] getTargetObject(String rowId, String[] row) {
		if(!DemCommon.verifTargetObject(rowId, row, getParentObject(), this).equals(null))
			return DemCommon.getTargetObjectCom(row, "DemSupplier", "DemRequest", "demSprreqSprId", "demSprreqReqId", getParentObject(), this);
		return null;
	}

	@Override
	public boolean isCreateEnable() {
		return false;
	}
	
}
