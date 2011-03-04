package jotov.versia.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="VERSIONED_OBJECT")
public class VObject {
	private int VObjectId;
	private List<ObjectVersion> versions = new ArrayList<ObjectVersion>();
	//private List<VersionSelector> localVersions = new ArrayList<VersionSelector>();
	
	public VObject(Workspace workspace, String name, String datum) {
		super();
		ObjectVersion firstVersion = new ObjectVersion(this, 1, name, datum);
		this.versions.add(firstVersion);
		
		//VersionSelector versionSelector = new VersionSelector(workspace, this.versions.get(1));
		//this.localVersions.add(versionSelector);
	}

	@Id
	@Column(name="VO_ID")
	public int getVObjectId() {
		return VObjectId;
	}

	public void setVObjectId(int vObjectId) {
		VObjectId = vObjectId;
	}
	@OneToMany(fetch=FetchType.LAZY, mappedBy="vobject")
	public List<ObjectVersion> getVersions() {
		return versions;
	}

	public void setVersions(List<ObjectVersion> versions) {
		this.versions = versions;
	}
/*	//TODO - annotations
	public List<VersionSelector> getLocalVersions() {
		return localVersions;
	}

	public void setLocalVersions(List<VersionSelector> localVersions) {
		this.localVersions = localVersions;
	}*/
	
}
