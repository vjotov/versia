package com.jotov.versia.orml;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class WSpace {
	private int wSpaceId;
	private String wSpaceName;
	private UserProfile user;
	private ReleaseORML release;
	private WSpace ancestorWSpace;
	private List<WSpace> offspringWSpaces = new ArrayList<WSpace>();
	private List<VObjectVersion> localVersions = new ArrayList<VObjectVersion>();
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


	@Id
	public int getWSpaceId() {
		return wSpaceId;
	}

	public void setWSpaceId(int workspaceId) {
		this.wSpaceId = workspaceId;
	}

	public String getWSpaceName() {
		return wSpaceName;
	}

	public void setWSpaceName(String workspaceName) {
		this.wSpaceName = workspaceName;
	}

	@ManyToOne
	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}

	@ManyToOne
	public ReleaseORML getRelease() {
		return release;
	}

	public void setRelease(ReleaseORML release) {
		this.release = release;
	}

	@ManyToOne(optional = true)
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
	public List<VObjectVersion> getLocalVersions() {
		return localVersions;
	}

	public void setLocalVersions(List<VObjectVersion> localVersions) {
		this.localVersions = localVersions;
	}

	public void addLocalVersion(VObjectVersion objectVersion) {
		List<VObjectVersion> LVer = getLocalVersions();
		if (!LVer.contains(objectVersion))
			LVer.add(objectVersion);
	}

	public void removeLocalVersion(VObjectVersion objectVersion) {
		List<VObjectVersion> LVer = getLocalVersions();
		if (LVer.contains(objectVersion))
			LVer.remove(objectVersion);
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "attWs")
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
				+ this.getWSpaceId() + ")-" + this.getWSpaceName()
				+ "\t(Ancestor WS ID = ");
		WSpace ancestor = this.getAncestorWorkspace();
		if (ancestor != null)
			returnString.append(ancestor.getWSpaceId());
		else
			returnString.append("null");
		returnString.append("; number of Offspring WSs = "
				+ this.getOffspringWorkspaces().size() + ")");
		return returnString.toString();
	}

}
