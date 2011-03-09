package jotov.versia;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import jotov.versia.pojo.ObjectVersion;
import jotov.versia.pojo.WSpace;

@Entity
public class VersionSelector {
	private WSpace workspace;
	private ObjectVersion objectVersion;

	public VersionSelector() {
		super();
	}

	public VersionSelector(WSpace workspace, ObjectVersion objectVersion) {
		super();
		this.objectVersion = objectVersion;
		this.workspace = workspace;
	}

	@Id
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH })
	public WSpace getWorkspace() {
		return workspace;
	}

	public void setWorkspace(WSpace workspace) {
		this.workspace = workspace;
	}

	@Id
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH })
	public ObjectVersion getObjectVersion() {
		return objectVersion;
	}

	public void setObjectVersion(ObjectVersion objectVersion) {
		this.objectVersion = objectVersion;
	}

}
