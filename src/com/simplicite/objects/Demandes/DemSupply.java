package com.simplicite.objects.Demandes;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.commons.Demandes.DemCommon;

/**
 * Business object DemSupply
 */
public class DemSupply extends ObjectDB {
	private static final long serialVersionUID = 1L;

	@Override
	public void initCreate() {
		setFieldValue("demSupReference", DemCommon.getNumero(getGrant(), "dem_sup_reference", "dem_supply", "SUP"));
		setFieldValue("demSupStockQuantity", 0);
	}
	
	@Override
	public boolean isCreateEnable() {
		return getParentObject()!= null;
	}
	
	@Override
	public String preSave() {
		if(getContext().isCreate() || getContext().isCopy()){
			setFieldValue("demSupReference", DemCommon.getNumero(getGrant(), "dem_sup_reference", "dem_supply", "SUP"));
		}
		return null;
	}
}
