package jotov.versia.pojo;

import java.util.ArrayList;
import java.util.List;

//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

//import javax.persistence.Table;

@Entity
// @Table(name="WORKSPACE")
public class Workspace {
	private int workspaceId;
	private String workspaceName;
	private User user;
	private Release release;
	private Workspace ancestorWorkspace;
	private List<Workspace> OffspringWorkspaces = new ArrayList<Workspace>();
	private List<VersionSelector> versionSelectors = new ArrayList<VersionSelector>();
	// private List<ObjectVersionExtended> visibleObjects = new
	// ArrayList<VersionSelectorExtended>();
	private List<Workitem> workitems = new ArrayList<Workitem>();

	public Workspace() {
		super();
	}

	public Workspace(String workspaceName) {
		super();
		this.workspaceName = workspaceName;
	}

	// static public List<ObjectVersionExtended> getVisibleObjects(Workspace
	// localWorkspace) {
	// localWorkspace.get
	// }

	@Id
	// @Column(name="WS_ID")
	public int getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(int workspaceId) {
		this.workspaceId = workspaceId;
	}

	// @Column(name="WS_NAME", length=20,nullable=false)
	public String getWorkspaceName() {
		return workspaceName;
	}

	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name="USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name="RELEASE_ID")
	public Release getRelease() {
		return release;
	}

	public void setRelease(Release release) {
		this.release = release;
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "ANCESTOR_WS_ID")
	public Workspace getAncestorWorkspace() {
		return ancestorWorkspace;
	}

	public void setAncestorWorkspace(Workspace ancestorWorkspace) {
		this.ancestorWorkspace = ancestorWorkspace;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ancestorWorkspace")
	public List<Workspace> getOffspringWorkspaces() {
		return OffspringWorkspaces;
	}

	public void setOffspringWorkspaces(List<Workspace> offspringWorkspaces) {
		OffspringWorkspaces = offspringWorkspaces;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "workspace")
	public List<VersionSelector> getVersionSelectors() {
		return versionSelectors;
	}

	public void setVersionSelectors(List<VersionSelector> versionSelectors) {
		this.versionSelectors = versionSelectors;
	}

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "attachedToWorkspaces")
	public List<Workitem> getWorkitems() {
		return workitems;
	}

	public void setWorkitems(List<Workitem> workitems) {
		this.workitems = workitems;
	}

	public void attachWorkitem(Workitem workitem) {
		List<Workitem> workitems = this.getWorkitems();
		if (!workitems.contains(workitem)) {
			workitems.add(workitem);
			workitem.attachToWorkspace(this);
		}
	}
	public void detachWorkitem(Workitem workitem){
		List<Workitem> workitems = this.getWorkitems();
		if(workitems.contains(workitem)) {
			workitems.remove(workitem);
			workitem.dettachFromWorkspace(this);
		}
	}

	@Override
	public String toString() {
		StringBuffer returnString = new StringBuffer("Workspace ("
				+ this.getWorkspaceId() + ")-" + this.getWorkspaceName()
				+ "\t(Ancestor WS ID = ");
		Workspace ancestor = this.getAncestorWorkspace();
		if (ancestor != null)
			returnString.append(ancestor.getWorkspaceId());
		else
			returnString.append("null");
		returnString.append("; number of Offspring WSs = "
				+ this.getOffspringWorkspaces().size() + ")");
		return returnString.toString();
	}

}
