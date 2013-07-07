package com.jotov.versia.beans.vobj;

import java.util.ArrayList;
import java.util.List;

import com.jotov.versia.orm.Cause;
import com.jotov.versia.orm.VObjectVersion;
import com.jotov.versia.orm.VersionArc;

public class PrecedorItem {
	private VObjectVersion precedor;
	// private VersionArc arc;
	private List<CauseItem> causes = new ArrayList<CauseItem>();

	public PrecedorItem(VObjectVersion precedor, VersionArc arc) {
		super();
		this.precedor = precedor;
		// this.arc = arc;
		List<Cause> localCauses = arc.getCauses();

		for (Cause c : localCauses) {
			causes.add(new CauseItem(c));
		}
	}

	public int getVObjectID() {
		return precedor.getVobject().getVObjectId();
	}

	public int getVersionNumber() {
		return precedor.getVersionNumber();
	}

	public String getName() {
		return precedor.getObjectName();
	}

	public String getDatum() {
		return precedor.getObjectDatum();
	}

	public VObjectVersion getPrecedor() {
		return precedor;
	}

	public void setPrecedor(VObjectVersion precedor) {
		this.precedor = precedor;
	}

	public List<CauseItem> getCauses() {
		return causes;
	}

	public void setCauses(List<CauseItem> causes) {
		this.causes = causes;
	}

}
