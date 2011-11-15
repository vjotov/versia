package com.jotov.versia.orm;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;
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
	private int lv;
	private int rv;

	// private List<ObjectVersionExtended> visibleObjects = new
	// ArrayList<VersionSelectorExtended>();
	// private List<WItem> wItems = new ArrayList<WItem>();

	public WSpace() {
		super();
	}

	public static WSpace createWorkspace(String workspaceName,
			WSpace ancestorWorkspace, Release rel, EntityManager em) {
		WSpace ws;
		if (ancestorWorkspace == null) { // Creation of master workspace
			Query q = em
					.createQuery("SELECT w FROM WSpace w ORDER BY w.rv DESC");
			WSpace w = (WSpace) q.getResultList().get(0);
			ws = new WSpace(workspaceName, ancestorWorkspace, rel,
					w.getRv() + 1, w.getRv() + 2);
		} else { // adding sub-workspace
			int lv = ancestorWorkspace.getRv();
			Query q1 = em
					.createQuery("UPDATE WSpace w SET w.lv = w.lv+2 WHERE w.lv >= :lv_parameter");
			Query q2 = em
					.createQuery("UPDATE WSpace w SET w.rv = w.rv+2 WHERE w.rv >= :lv_parameter");

			q1.setParameter("lv_parameter", lv);
			q2.setParameter("lv_parameter", lv);
			int updated = q1.executeUpdate();
			System.out.println("1) Add offspring WS - Updated " + updated
					+ " LV values of WSpace objects");
			updated = q2.executeUpdate();
			System.out.println("2) Add offspring WS - Updated " + updated
					+ " RV values of WSpace objects");

			ws = new WSpace(workspaceName, ancestorWorkspace, rel, lv, lv + 1);
		}
		return ws;
	}

	public static WSpace createMasterWorkspace(Release rel, EntityManager em) {
		WSpace result = createWorkspace("Zero Workspace", null, rel, em);
		result.setReleaseMaster(rel);
		
		// create initial workitem
		VObject firstVO = VObject.createVObject("First Object", "", result,
				null);
		WorkItemAttachement wia = WorkItemAttachement
				.createWorkItemAttachement(result, firstVO);
		result.addAttachedWorkItem(wia);

		return result;
	}

	public static void removeWorkspace(WSpace ws, EntityManager em) {
		// remove all ancestor workspaces
		for (WSpace offspringWS : ws.getOffspringWorkspaces()) {
			removeWorkspace(offspringWS, em);
		}

		// update RV and LV indexes of other workspaces
		int lv = ws.getLv();
		Query q1 = em
				.createQuery("UPDATE WSpace w SET w.lv = w.lv-2 WHERE w.lv >= :lv_parameter");
		Query q2 = em
				.createQuery("UPDATE WSpace w SET w.rv = w.rv-2 WHERE w.rv >= :lv_parameter");
		q1.setParameter("lv_parameter", lv);
		q2.setParameter("lv_parameter", lv);
		int updated = q1.executeUpdate();
		System.out.println("1) Remove WS - Updated " + updated
				+ " LV values of WSpace objects");
		updated = q2.executeUpdate();
		System.out.println("2) Remove WS - Updated " + updated
				+ " RV values of WSpace objects");

		// remove workitem attachments
		for (WorkItemAttachement wia : ws.getAttachedWorkItems()) {
			em.remove(wia);
		}
		ws.setAttachedWorkItems(null);

		// detach local Versioned Objects from workspace
		for (VObjectVersion vov : ws.getLocalVersions()) {
			vov.setWorkspace(null);
		}
		ws.setLocalVersions(null);

		// remove workspace
		em.remove(ws);
	}

	private WSpace(String workspaceName, WSpace ancestorWorkspace, Release rel,
			int lv, int rv) {
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

	public void removeWorkItemAttachement(
			WorkItemAttachement workItemAttachement) {
		attachedWorkItems.remove(workItemAttachement);
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lV) {
		lv = lV;
	}

	public int getRv() {
		return rv;
	}

	public void setRv(int rV) {
		rv = rV;
	}

}
