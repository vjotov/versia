package com.jotov.versia.util;

import com.jotov.versia.orm.VObject;
import com.jotov.versia.orm.WSpace;

public class vwPair {
		public vwPair(WSpace wspace, VObject vobj) {
			super();
			this.wspace = wspace;
			this.vobj = vobj;
		}
		public WSpace wspace;
		public VObject vobj;
	}