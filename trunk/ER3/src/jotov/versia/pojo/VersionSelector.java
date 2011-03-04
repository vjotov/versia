package jotov.versia.pojo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="WS_VO_SWLWCTOR")
public class VersionSelector {
	private Workspace workspace;
	private ObjectVersion objectVersion;
	
	public VersionSelector(Workspace workspace, ObjectVersion objectVersion) {
		super();
		this.objectVersion = objectVersion;
		this.workspace = workspace;
	}
	@Id
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="WS_ID")
	public Workspace getWorkspace() {
		return workspace;
	}
	public void setWorkspace(Workspace workspace) {
		this.workspace = workspace;
	}
	
	@Id
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumns(value={@JoinColumn(name="VO_ID"),@JoinColumn(name="VP_ID")}) //TODO - to check this join
	public ObjectVersion getObjectVersion() {
		return objectVersion;
	}
	public void setObjectVersion(ObjectVersion objectVersion) {
		this.objectVersion = objectVersion;
	}
	
}
