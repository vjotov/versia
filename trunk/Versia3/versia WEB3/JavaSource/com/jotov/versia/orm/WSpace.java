package com.jotov.versia.orm;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class WSpace {
	private int wSpaceId;
	private String wSpaceName;
	private UserProfile openedByUser;
	private UserProfile publicationByUser;
	private Release release;
	private Release releaseMaster;
	private WSpace ancestorWSpace;
	private List<WSpace> offspringWSpaces = new ArrayList<WSpace>();
	private List<VObjectVersion> localVersions = new ArrayList<VObjectVersion>();
	private List<WorkItemAttachement> attachedWorkItems = new ArrayList<WorkItemAttachement>();

	// private List<ObjectVersionExtended> visibleObjects = new
	// ArrayList<VersionSelectorExtended>();
	// private List<WItem> wItems = new ArrayList<WItem>();

	public WSpace() {
		super();
	}

	public static WSpace createWorkspace(String workspaceName,
			WSpace ancestorWorkspace, Release rel) {
		WSpace ws = new WSpace(workspaceName, ancestorWorkspace, rel);

		return ws;
	}

	public static WSpace createMasterWorkspace(Release rel) {
		WSpace result = createWorkspace("Zero Workspace", null, rel);
		result.setReleaseMaster(rel);
		// TODO to create initial workitem
		return result;
	}

	private WSpace(String workspaceName, WSpace ancestorWorkspace, Release rel) {
		super();
		this.wSpaceName = workspaceName;
		this.ancestorWSpace = ancestorWorkspace;
		if (ancestorWorkspace == null)
			this.release = rel;
		else
			this.release = ancestorWorkspace.getRelease();
	}

	@Id
	@GeneratedValue(generator = "WorkspaceSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "WorkspaceSeq", sequenceName = "SQ_WORKSPACE")
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

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	public Release getRelease() {
		return release;
	}

	public void setRelease(Release release) {
		this.release = release;
	}

	@ManyToOne(optional = true)
	public WSpace getAncestorWorkspace() {
		return ancestorWSpace;
	}

	public void setAncestorWorkspace(WSpace ancestorWorkspace) {
		this.ancestorWSpace = ancestorWorkspace;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ancestorWorkspace", cascade = {
			CascadeType.PERSIST, CascadeType.REFRESH })
	public List<WSpace> getOffspringWorkspaces() {
		return offspringWSpaces;
	}

	public void setOffspringWorkspaces(List<WSpace> offspringWorkspaces) {
		offspringWSpaces = offspringWorkspaces;
	}

	@OneToOne(fetch = FetchType.LAZY, optional = true, cascade = {
			CascadeType.REFRESH, CascadeType.PERSIST })
	public UserProfile getOpenedByUser() {
		return openedByUser;
	}

	public void setOpenedByUser(UserProfile openedByUser) {
		this.openedByUser = openedByUser;
	}

	@OneToOne(fetch = FetchType.LAZY, optional = true, cascade = {
			CascadeType.REFRESH, CascadeType.PERSIST })
	public UserProfile getPublicationByUser() {
		return publicationByUser;
	}

	public void setPublicationByUser(UserProfile publicationByUser) {
		this.publicationByUser = publicationByUser;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH }, mappedBy = "workspace")
	public List<VObjectVersion> getLocalVersions() {
		return localVersions;
	}

	public void setLocalVersions(List<VObjectVersion> localVersions) {
		this.localVersions = localVersions;
	}

	public void addLocalVersion(VObjectVersion localVersion) {
		this.localVersions.add(localVersion);
	}

	public void removeLocalVersion(VObjectVersion pr) {
		this.localVersions.remove(pr);
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	public Release getReleaseMaster() {
		return releaseMaster;
	}

	public void setReleaseMaster(Release release_master) {
		this.releaseMaster = release_master;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "workspace")
	public List<WorkItemAttachement> getAttachedWorkItems() {
		return attachedWorkItems;
	}

	public void setAttachedWorkItems(List<WorkItemAttachement> attachedWorkItems) {
		this.attachedWorkItems = attachedWorkItems;
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
		return returnString.toString();
	}

	public void addAttachedWorkItem(WorkItemAttachement workItemAttachement) {
		this.getAttachedWorkItems().add(workItemAttachement);		
	}

	public void removeWorkItemAttachement(WorkItemAttachement workItemAttachement) {
		attachedWorkItems.remove(workItemAttachement);
	}

}
