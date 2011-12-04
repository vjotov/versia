package com.jotov.versia.beans.vobj;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jotov.versia.orm.VComposer;
import com.jotov.versia.orm.VObject;
import com.jotov.versia.orm.VObjectVersion;
import com.jotov.versia.orm.WSpace;
import com.jotov.versia.orm.WorkItemAttachement;

public class VItem {
	private WSpace workspace;
	private VObject vObject;
	private VObjectVersion voVersion;
	private Integer voID;
	private Integer versionNumber;
	private String voName;
	private String voDatum;
	private Boolean workitemFlag;
	private Boolean deletedFlag;
	private Boolean attachedWIFlag;
	private Boolean ancestorFlag;
	private Boolean ancestorAWI;
	private Integer ancestorVersionNumber;
	private String ancestorVOName;
	private String ancestorVODatum;
	private List<VObjectVersion> subVObjectVersions;
	private List<VSubItem> subObjects;

	private List<VisibilityEnum> visibility = new ArrayList<VisibilityEnum>();

	public VItem() {
	}

	@SuppressWarnings("unchecked")
	public VItem(VObjectVersion vov, WSpace ws, EntityManager em) {
		workspace = ws;
		voVersion = vov;
		vObject = vov.getVobject();
		voID = vObject.getVObjectId();
		versionNumber = vov.getVersionNumber();
		voName = vov.getObjectName();
		workitemFlag = new Boolean(vObject.isWorkItem());
		deletedFlag = new Boolean(vov.getDeleteFlag() != 0);

		if (workitemFlag) {
			visibility.add(VisibilityEnum.WI);
			Query q = em.createQuery("SELECT COUNT(w) FROM WorkItemAttachement w WHERE w.workitem = :obj AND w.workspace = :wspace");
			q.setParameter("obj", vObject);
			q.setParameter("wspace", ws);

			List<Long> a = q.getResultList();
			attachedWIFlag = (a.size() > 0 && a.get(0) > 0);
		} else
			attachedWIFlag = false;
		if (attachedWIFlag)
			visibility.add(VisibilityEnum.A);
		Query q = em
				.createQuery("SELECT v FROM VObjectVersion v WHERE v.workspace.lv < :lvParameter AND v.workspace.rv >:rvParameter ORDER BY v.workspace.lv DESC");
		q.setParameter("lvParameter", ws.getLv());
		q.setParameter("rvParameter", ws.getRv());

		List<VObjectVersion> ancestorVOVs = (List<VObjectVersion>) q
				.getResultList();
		if (ancestorVOVs.size() > 0) {
			ancestorFlag = true;
			VObjectVersion ancestorVersion = ancestorVOVs.get(0);
			ancestorVersionNumber = ancestorVersion.getVersionNumber();
			ancestorVOName = ancestorVersion.getObjectName();
			ancestorVODatum = ancestorVersion.getObjectDatum();
		} else {
			ancestorFlag = false;
			ancestorVersionNumber = 0;
			ancestorVOName = "N/A";
			ancestorVODatum = "N/A";

		}

		if (Object.class.isInstance(ws.getAncestorWorkspace())) {
			Query q2 = em.createQuery("SELECT COUNT(w) FROM WorkItemAttachement w WHERE w.workitem = :obj AND w.workspace = :wspace");
			q2.setParameter("obj", vObject);
			q2.setParameter("wspace", ws.getAncestorWorkspace());

			List<Long> a = q2.getResultList();
			ancestorAWI = (a.size() > 0 && a.get(0) > 0);
		} else
			ancestorAWI = false;
	}

	public WSpace getWorkspace() {
		return workspace;
	}

	public void setWorkspace(WSpace workspace) {
		this.workspace = workspace;
	}

	public VObject getvObject() {
		return vObject;
	}

	public void setvObject(VObject vObject) {
		this.vObject = vObject;
	}

	public VObjectVersion getVoVersion() {
		return voVersion;
	}

	public void setVoVersion(VObjectVersion voVersion) {
		this.voVersion = voVersion;
	}

	public Integer getVoID() {
		return voID;
	}

	public void setVoID(Integer voID) {
		this.voID = voID;
	}

	public Integer getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getVoName() {
		return voName;
	}

	public void setVoName(String voName) {
		this.voName = voName;
	}

	public String getVoDatum() {
		return voDatum;
	}

	public void setVoDatum(String voDatum) {
		this.voDatum = voDatum;
	}

	public Boolean isWorkitem() {
		return workitemFlag;
	}

	public Boolean isDeleted() {
		return deletedFlag;
	}

	public void setWorkitem(Boolean workitemFlag) {
		this.workitemFlag = workitemFlag;
	}

	public Boolean isAttachedWI() {
		if (!Object.class.isInstance(attachedWIFlag))
			calculateAttachedWIFlag();
		return attachedWIFlag;

	}

	private void calculateAttachedWIFlag() {
		List<WorkItemAttachement> wias = workspace.getAttachedWorkItems();
		for (WorkItemAttachement wia : wias) {
			if (wia.getWorkitem().equals(vObject)) {
				this.attachedWIFlag = new Boolean(true);
				break;
			}
		}
		if (!Object.class.isInstance(this.attachedWIFlag))
			this.attachedWIFlag = new Boolean(false);
	}

	public Boolean hasAncestor() {
		return ancestorFlag;
	}

	public Integer getAncestorVersionNumber() {
		return ancestorVersionNumber;
	}

	public String getAncestorVOName() {
		return ancestorVOName;
	}

	public String getAncestorVODatum() {
		return ancestorVODatum;
	}

	public List<VObjectVersion> getSubVObjectVersions() {
		if (subVObjectVersions == null) {
			subVObjectVersions = new ArrayList<VObjectVersion>();
			subObjects = new ArrayList<VSubItem>();

			List<VComposer> vcLS = voVersion.getSubObjects();
			for (VComposer vc : vcLS) {
				VObjectVersion svo = vc.getSubObject();
				subVObjectVersions.add(vc.getSubObject());
				subObjects.add(new VSubItem(svo.getVobject().getVObjectId(),
						svo.getVersionNumber(), svo.getObjectName()));
			}
		}
		return subVObjectVersions;
	}

	public void setSubVObjectVersions(List<VObjectVersion> subVObjectVersions) {
		this.subVObjectVersions = subVObjectVersions;
	}

	public List<VisibilityEnum> getVisibility() {
		return visibility;
	}

	public void setVisibility(List<VisibilityEnum> visibility) {
		this.visibility = visibility;
	}

	public void addVisibility(VisibilityEnum v) {
		visibility.add(v);
	}

	public List<VSubItem> getSubObjects() {
		if (subObjects == null) {
			subVObjectVersions = new ArrayList<VObjectVersion>();
			subObjects = new ArrayList<VSubItem>();

			List<VComposer> vcLS = voVersion.getSubObjects();
			for (VComposer vc : vcLS) {
				VObjectVersion svo = vc.getSubObject();
				subVObjectVersions.add(vc.getSubObject());
				subObjects.add(new VSubItem(svo.getVobject().getVObjectId(),
						svo.getVersionNumber(), svo.getObjectName()));
			}
		}
		return subObjects;
	}

	public void setSubObjects(List<VSubItem> subObjects) {
		this.subObjects = subObjects;
	}

	public String getImage() {
		if (getSubObjects().size() == 0)
			return "img/tree_empty.gif";
		else
			return "img/tree_expand.gif";
	}

	public Boolean isAncestorAWI() {
		return ancestorAWI;
	}

}
