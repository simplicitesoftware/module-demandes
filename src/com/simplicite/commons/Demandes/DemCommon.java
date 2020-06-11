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
	
}
