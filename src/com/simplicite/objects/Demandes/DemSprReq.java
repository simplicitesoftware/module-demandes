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
		return DemCommon.getTargetObjectCom(rowId, row, "DemSupplier", "DemRequest", "demSprreqSprId", "demSprreqReqId", getParentObject(), this);
	}

	@Override
	public boolean isCreateEnable() {
		return false;
	}
	
}
