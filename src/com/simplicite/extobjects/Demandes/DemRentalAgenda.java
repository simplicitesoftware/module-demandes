package com.simplicite.extobjects.Demandes;

import java.util.*;
import com.simplicite.util.*;
import com.simplicite.util.tools.*;

/**
 * External object DemLocationAgenda
 */
public class DemRentalAgenda extends com.simplicite.webapp.web.ResponsiveExternalObject {
	private static final long serialVersionUID = 1L;

	@Override
	public String getRenderStatement(Parameters params) {
		return "$ui.loadCalendar(function() { " + super.getRenderStatement(params) + " });";
	}
}
