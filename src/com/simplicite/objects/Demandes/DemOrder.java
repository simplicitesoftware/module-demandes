package com.simplicite.objects.Demandes;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;
import com.simplicite.commons.Demandes.DemCommon;

/**
 * Business object DemOrder
 */
public class DemOrder extends ObjectDB {
	private static final long serialVersionUID = 1L;
/*	@Override
	public void initCreate() {
		setFieldValue("demOrdReference",  DemCommon.getNumero(getGrant(), "dem_ord_reference", "dem_order", "ORD"));
		setFieldValue("demOrdOrderDate", Tool.getCurrentDate());
		setFieldValue("demOrdStatus", "PENDING");
	}

	@Override
	public void initUpdate() {
		double quantiteTotal = 0;
		double prixTotal = 0;
		ObjectDB demSupOrd = getGrant().getTmpObject("DemSupOrd");
		synchronized(demSupOrd){
			demSupOrd.resetFilters();
			demSupOrd.setFieldFilter("demSupordOrdId", getCurrentRowId());
			List<String[]> listSup = demSupOrd.search();
			for(int i = 0 ; i < listSup.size(); i++){
				demSupOrd.setValues(listSup.get(i));
				double quantiteOrdered = Tool.parseDouble(demSupOrd.getFieldValue("demSupordQuantityOrdered"));
				quantiteTotal += quantiteOrdered;
				prixTotal += quantiteOrdered * Tool.parseDouble(demSupOrd.getFieldValue("demSupordSupId.demSupPrice"));
			}
		}
		setFieldValue("demOrdQuantityTotal", quantiteTotal);
		setFieldValue("demOrdPriceTotal", prixTotal);
		if(!("PENDING").equals(getFieldValue("demOrdStatus"))){
			getField("demOrdReference").setUpdatable(false);
			getField("demOrdQuantityTotal").setUpdatable(false);
			getField("demOrdPriceTotal").setUpdatable(false);
			getField("demOrdOrderDate").setUpdatable(false);
		}
	}
	
	@Override
	public String preSave() {
		if(getContext().isCreate() || getContext().isCopy()){
			setFieldValue("demOrdReference",  DemCommon.getNumero(getGrant(), "dem_ord_reference", "dem_order", "ORD"));
		}
		return null;
	}
	*/
}
