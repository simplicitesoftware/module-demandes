package com.simplicite.commons.Demandes;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * Grant Hooks
 */
public class GrantHooks extends GrantHooksInterface {
	
	public static void postLoadGrant(Grant g) {
		MenuItem menu = g.getMenu();
		List<MenuItem> menuDemandes = menu.findChildren("DemDomain");
		for(int i = 1; i < menuDemandes.size(); i++){
			MenuItem item = menuDemandes.get(i);
			if(item.getObjectName().equals("DemRequest") && g.hasResponsibility("DEM_ADMINISTRATOR")){
				item.getAccessibleStates().remove(8);
				item.getAccessibleStates().remove(3);
				item.getAccessibleStates().remove(2);
				item.getAccessibleStates().remove(0);
			}
			if(item.getObjectName().equals("DemRental")){
				for(int j = 6; j >= 3; j--){
					item.getAccessibleStates().remove(j);
				}
			}
		}
	}
}
