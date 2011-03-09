package jotov.versia.pojo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
// @Table(name="WORKSPACE")
public class WSpace {
	private int wSpaceId;
	private String wSpaceName;
	private UserProfile user;
	private Release release;
	private WSpace ancestorWSpace;
	private List<WSpace> offspringWSpaces = new ArrayList<WSpace>();
	private List<ObjectVersion> localVersions = new ArrayList<ObjectVersion>();
	// private List<ObjectVersionExtended> visibleObjects = new
	// ArrayList<VersionSelectorExtended>();
	private List<WItem> wItems = new ArrayList<WItem>();

	public WSpace() {
		super();
	}

	public WSpace(String workspaceName) {
		super();
		this.wSpaceName = workspaceName;
	}

	// static public List<ObjectVersionExtended> getVisibleObjects(Workspace
	// localWorkspace) {
	// localWorkspace.get
	// }

	@Id
	// @Column(name="WS_ID")
	public int getWorkspaceId() {
		return wSpaceId;
	}

	public void setWorkspaceId(int workspaceId) {
		this.wSpaceId = workspaceId;
	}

	// @Column(name="WS_NAME", length=20,nullable=false)
	public String getWorkspaceName() {
		return wSpaceName;
	}

	public void setWorkspaceName(String workspaceName) {
		this.wSpaceName = workspaceName;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name="USER_ID")
	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
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
	public WSpace getAncestorWorkspace() {
		return ancestorWSpace;
	}

	public void setAncestorWorkspace(WSpace ancestorWorkspace) {
		this.ancestorWSpace = ancestorWorkspace;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ancestorWorkspace")
	public List<WSpace> getOffspringWorkspaces() {
		return offspringWSpaces;
	}

	public void setOffspringWorkspaces(List<WSpace> offspringWorkspaces) {
		offspringWSpaces = offspringWorkspaces;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "workspaces")
	public List<ObjectVersion> getLocalVersions() {
		return localVersions;
	}

	public void setLocalVersions(List<ObjectVersion> localVersions) {
		this.localVersions = localVersions;
	}

	public void addLocalVersion(ObjectVersion objectVersion) {
		List<ObjectVersion> LVer = getLocalVersions();
		if (!LVer.contains(objectVersion))
			LVer.add(objectVersion);
	}

	public void removeLocalVersion(ObjectVersion objectVersion) {
		List<ObjectVersion> LVer = getLocalVersions();
		if (LVer.contains(objectVersion))
			LVer.remove(objectVersion);
	}

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "attWs")
	public List<WItem> getWItems() {
		return wItems;
	}

	public void setWItems(List<WItem> workitems) {
		this.wItems = workitems;
	}

	public void attachWorkitem(WItem workitem) {
		List<WItem> workitems = this.getWItems();
		if (!workitems.contains(workitem)) {
			workitems.add(workitem);
			workitem.attachToWorkspace(this);
		}
	}

	public void detachWorkitem(WItem workitem) {
		List<WItem> workitems = this.getWItems();
		if (workitems.contains(workitem)) {
			workitems.remove(workitem);
			workitem.dettachFromWorkspace(this);
		}
	}

	@Override
	public String toString() {
		StringBuffer returnString = new StringBuffer("Workspace ("
				+ this.getWorkspaceId() + ")-" + this.getWorkspaceName()
				+ "\t(Ancestor WS ID = ");
		WSpace ancestor = this.getAncestorWorkspace();
		if (ancestor != null)
			returnString.append(ancestor.getWorkspaceId());
		else
			returnString.append("null");
		returnString.append("; number of Offspring WSs = "
				+ this.getOffspringWorkspaces().size() + ")");
		return returnString.toString();
	}

}
