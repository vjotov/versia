package jotov.versia.pojo;

import java.util.ArrayList;
import java.util.List;

//import javax.persistence.Column;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
//import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ObjectVersion {
	private int globalVPId;
	private VObject vobject;
	private int versionNumber;
	private String objectName;
	private String objectDatum;
	private List<VersionSelector> localVersionSelector = new ArrayList<VersionSelector>();

	// private List<Cause> causes = new ArrayList<Cause>();

	public ObjectVersion() {
		super();
	}

	public ObjectVersion(VObject vobject, int versionNumber, String name,
			String datum) {
		super();
		this.vobject = vobject;
		this.versionNumber = versionNumber;
		this.objectName = name;
		this.objectDatum = datum;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getGlobalVPId() {
		return globalVPId;
	}

	public void setGlobalVPId(int globalVPId) {
		this.globalVPId = globalVPId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
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

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "objectVersion")
	public List<VersionSelector> getLocalVersionSelector() {
		return localVersionSelector;
	}

	public void setLocalVersionSelector(
			List<VersionSelector> localVersionSelector) {
		this.localVersionSelector = localVersionSelector;
	}

	/*
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "initiator") public
	 * List<Cause> getCauses() { return causes; }
	 * 
	 * public void setCauses(List<Cause> causes) { this.causes = causes; }
	 */

	@Override
	public String toString() {
		return this.getVobject().getVObjectId() + "v" + this.getVersionNumber()
				+ "(" + this.getGlobalVPId() + ") - " + this.getObjectName();
	}
}
