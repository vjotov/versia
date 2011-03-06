package jotov.versia.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class VersionSelector {
	private Workspace workspace;
	private ObjectVersion objectVersion;

	public VersionSelector() {
		super();
	}

	public VersionSelector(Workspace workspace, ObjectVersion objectVersion) {
		super();
		this.objectVersion = objectVersion;
		this.workspace = workspace;
	}

	@Id
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH })
	public Workspace getWorkspace() {
		return workspace;
	}

	public void setWorkspace(Workspace workspace) {
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
