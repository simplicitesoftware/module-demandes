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
				//Cancelled
				item.getAccessibleStates().remove(8);
				//Requestfutherinfo
				item.getAccessibleStates().remove(4);
				//Rejectedbyman
				item.getAccessibleStates().remove(3);
				//Pending
				item.getAccessibleStates().remove(1);
				//draft
				item.getAccessibleStates().remove(0);
			}
			if(item.getObjectName().equals("DemRental")){
				//RejectedbyAdm
				item.getAccessibleStates().remove(6);
				//Processing
				item.getAccessibleStates().remove(5);
				//Requestfutherinfo
				item.getAccessibleStates().remove(4);
			}
		}
	}
}
