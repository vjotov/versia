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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof vwPair))
			return false;
		vwPair a = (vwPair) obj;
		return (this.vobj.equals(a.vobj) && this.wspace.equals(a.wspace));
	}

	@Override
	public int hashCode() {
		return wspace.getWSpaceId() + vobj.getVObjectId() * 10000;
	}

	@Override
	public String toString() {
		return wspace.getAncestorWSId()+"/"+vobj.getVObjectId();
	}
}