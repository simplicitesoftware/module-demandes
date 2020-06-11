package com.simplicite.commons.Demandes;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Shared code DemCommon
 */
public class DemCommon implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Génération numéro 20REQ00533
	 */
	public static String getNumero(Grant g, String sqlRef, String sqlTable, String refTroisCaracteres){
		long num = 1;
		String year = Tool.getCurrentYear().substring(2);
		String lastNum =g.simpleQuery("select "+ sqlRef + " from " + sqlTable + " order by " + sqlRef + " desc fetch first 1 row only");
		
		// Si aucun résultat (premier dossier) ou année courante => Incrément ; Sinon, défaut = 1
		if(!Tool.isEmpty(lastNum) && lastNum.substring(0,2).equals(year))
			num = Long.valueOf(lastNum.substring(5))+1;
		return Tool.format(year+ refTroisCaracteres + "%05d", num);
	}
	
	public static String[] verifTargetObject(String rowId, String[] row, ObjectDB parentObject, ObjectDB obj){
		if("1".equals(obj.getParameter("_UI_EDIT_TEMPLATE_"))) // Template editor
			return null;
		if(rowId.equals(ObjectField.DEFAULT_ROW_ID)) 
			return null;
		if(parentObject == null)
			return null;
		if(row == null && (rowId.equals(obj.getRowId()) || obj.select(rowId)))
			row = obj.getValues();
		if(row == null)
			return null;
		return row;
	}

	public static String[] getTargetObjectCom(String[] row, String parentObjectName, String targetObjectName, String idTargetObject, String idParentObject, ObjectDB parentObject, ObjectDB obj) {
		if(parentObject.getName().equals(parentObjectName))
			return new String[]{targetObjectName, "the_ajax_" + targetObjectName, row[obj.getFieldIndex(idParentObject)] };
		if(parentObject.getName().equals(targetObjectName))
			return new String[]{ parentObjectName, "the_ajax_" + parentObjectName, row[obj.getFieldIndex(idTargetObject)] };
		return null;
	}
	
}
