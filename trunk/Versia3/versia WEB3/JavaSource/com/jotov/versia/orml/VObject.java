package com.jotov.versia.orml;

import java.util.ArrayList;
import java.util.List;

//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class VObject {
	private int VObjectId;
	private List<VObjectVersion> versions = new ArrayList<VObjectVersion>();

	public VObject() {
		super();
	}

	public VObject(WSpace workspace, String name, String datum) {
		super();
		VObjectVersion firstVersion = new VObjectVersion(this, 1, name, datum, workspace);
		this.versions.add(firstVersion);
	}

	@Id
	public int getVObjectId() {
		return VObjectId;
	}

	public void setVObjectId(int vObjectId) {
		VObjectId = vObjectId;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "vobject")
	public List<VObjectVersion> getVersions() {
		return versions;
	}

	public void setVersions(List<VObjectVersion> versions) {
		this.versions = versions;
	}

}
