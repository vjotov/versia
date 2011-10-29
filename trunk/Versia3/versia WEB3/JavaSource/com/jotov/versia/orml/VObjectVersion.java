package com.jotov.versia.orml;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class VObjectVersion {
	private int globalVPId;
	private VObject vobject;
	private int versionNumber;
	private String objectName;
	private String objectDatum;
	private List<WSpace> workspaces = new ArrayList<WSpace>();

	// private List<Cause> causes = new ArrayList<Cause>();

	public VObjectVersion() {
		super();
	}

	public VObjectVersion(VObject vobject, int versionNumber, String name,
			String datum, WSpace workspace) {
		super();
		this.vobject = vobject;
		this.versionNumber = versionNumber;
		this.objectName = name;
		this.objectDatum = datum;
		this.workspaces.add(workspace);
	}

	public VObjectVersion(VObject vobject, int versionNumber, String name,
			String datum) {
		super();
		this.vobject = vobject;
		this.versionNumber = versionNumber;
		this.objectName = name;
		this.objectDatum = datum;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "GID")
	public int getGlobalVPId() {
		return globalVPId;
	}

	public void setGlobalVPId(int globalVPId) {
		this.globalVPId = globalVPId;
	}

	@ManyToOne
	public VObject getVobject() {
		return vobject;
	}

	public void setVobject(VObject vobject) {
		this.vobject = vobject;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(int versionID) {
		this.versionNumber = versionID;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	@Basic(fetch = FetchType.LAZY)
	@Lob
	public String getObjectDatum() {
		return objectDatum;
	}

	public void setObjectDatum(String objectDatum) {
		this.objectDatum = objectDatum;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	public List<WSpace> getWorkspaces() {
		return workspaces;
	}

	public void setWorkspaces(List<WSpace> workspaces) {
		this.workspaces = workspaces;
	}

	@Override
	public String toString() {
		return this.getVobject().getVObjectId() + "v" + this.getVersionNumber()
				+ "(" + this.getGlobalVPId() + ") - " + this.getObjectName();
	}
}
