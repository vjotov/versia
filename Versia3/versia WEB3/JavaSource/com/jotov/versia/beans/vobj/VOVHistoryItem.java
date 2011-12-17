package com.jotov.versia.beans.vobj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.jotov.versia.orm.VObjectVersion;
import com.jotov.versia.orm.VersionArc;

public class VOVHistoryItem {
	private VObjectVersion version;
	private List<PrecedorItem> precedors = new ArrayList<PrecedorItem>();

	public VOVHistoryItem(VObjectVersion version) {
		super();
		this.version = version;
		List<VersionArc> precedorsArcs = version.getPrecetorsArc();
		for (VersionArc arc : precedorsArcs) {
			VObjectVersion precedeVersion = arc.getPrecedor();
			precedors.add( new PrecedorItem(precedeVersion, arc));
		}
	}

	public int getVObjectID() {
		return version.getVobject().getVObjectId();
	}

	public int getVersionNumber() {
		return version.getVersionNumber();
	}

	public String getName() {
		return version.getObjectName();
	}

	public String getDatum() {
		return version.getObjectDatum();
	}

	public VObjectVersion getVersion() {
		return version;
	}

	public void setVersion(VObjectVersion version) {
		this.version = version;
	}

	public List<PrecedorItem> getPrecedors() {
		return (List<PrecedorItem>) precedors;
	}

}
