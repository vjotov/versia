package com.jotov.versia.orm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.jotov.versia.beans.UserSessionBean;

@Entity
@NamedQueries({ @NamedQuery(name = "vovGetAllVersions", query = "SELECT v FROM VObjectVersion v WHERE v.vobject = :obj ") })
public class VObjectVersion implements Cloneable {
	private int globalVPId;
	private VObject vobject;
	private int versionNumber;
	private String objectName;
	private String objectDatum;
	private WSpace workspace;
	private int deleteFlag = 0;
	private List<VersionArc> precetorsArc = new ArrayList<VersionArc>();
	private List<VComposer> subObjects;
	private List<VComposer> superObjects;

	// private List<VObjectVersion> subObjects = new
	// ArrayList<VObjectVersion>();

	public VObjectVersion() {
		super();
	}

	public static VObjectVersion createVersion(VObject vObject, String name,
			String datum, WSpace workspace, List<VObjectVersion> precedors,
			UserSessionBean session) {

		int versionNumber = getNextVersionNumber(vObject);

		VObjectVersion newVersion = new VObjectVersion(vObject, versionNumber,
				name, datum, workspace);
		vObject.addVersion(newVersion);
		workspace.addLocalVersion(newVersion);
		if (Object.class.isInstance(precedors))
			for (VObjectVersion pr : precedors) {
				if (pr.getWorkspace() == workspace) {
					workspace.removeLocalVersion(pr);
					pr.setWorkspace(null);
				}

				VersionArc va = VersionArc.createArcs(newVersion, pr,
						workspace, session);
				newVersion.addPrecetorsArc(va);
			}
		return newVersion;
	}

	public static VObjectVersion markDeleteVersion(WSpace selectedWorkspace,
			VObjectVersion selectedVersion, UserSessionBean session) {

		ArrayList<VObjectVersion> precedors = new ArrayList<VObjectVersion>();
		precedors.add(selectedVersion);
		VObjectVersion deletedVersion = createVersion(
				selectedVersion.getVobject(), selectedVersion.objectName, null,
				selectedWorkspace, precedors, session);
		for (VersionArc pa : deletedVersion.precetorsArc) {
			pa.setNotes("Delete");
		}
		deletedVersion.setDeleteFlag(1);
		return deletedVersion;
	}

	public static VObjectVersion newSuperObjectVersion(
			VObjectVersion superObjectOldVer,
			VObjectVersion changedSubObjectVersion, UserSessionBean session)
			throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		List<VObjectVersion> precedors = new ArrayList<VObjectVersion>();
		precedors.add(superObjectOldVer);
		WSpace ws = changedSubObjectVersion.getWorkspace();

		VObjectVersion newSuperObjectVersion = createVersion(
				superObjectOldVer.getVobject(), superObjectOldVer.objectName,
				superObjectOldVer.objectDatum, ws, precedors, session);

		copySubObjects(superObjectOldVer, newSuperObjectVersion,
				changedSubObjectVersion);

		return newSuperObjectVersion;
	}

	private static void copySubObjects(VObjectVersion superObjectOldVer,
			VObjectVersion newSuperObjectVersion,
			VObjectVersion changedSubObjectVersion) {
		// TODO Auto-generated method stub

	}

	private static int getNextVersionNumber(VObject vObject) {
		int versionNumber = -1;
		List<VObjectVersion> prevVers = vObject.getVersions();

		for (VObjectVersion vo : prevVers) {
			if (versionNumber < vo.getVersionNumber())
				versionNumber = vo.getVersionNumber();
		}
		versionNumber++;

		return versionNumber;
	}

	public VObjectVersion(VObject vobject, int versionNumber, String name,
			String datum, WSpace workspace) {
		super();
		this.vobject = vobject;
		this.versionNumber = versionNumber;
		this.objectName = name;
		this.objectDatum = datum;
		this.workspace = workspace;
	}

	@Id
	@GeneratedValue(generator = "VersionGIDSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "VersionGIDSeq", sequenceName = "SQ_VERSION_GID")
	@Column(name = "GID")
	public int getGlobalVPId() {
		return globalVPId;
	}

	public void setGlobalVPId(int globalVPId) {
		this.globalVPId = globalVPId;
	}

	@ManyToOne
	public VObject getVobject() {
		return vobject;
	}

	public void setVobject(VObject vobject) {
		this.vobject = vobject;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(int versionID) {
		this.versionNumber = versionID;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	@Basic(fetch = FetchType.LAZY, optional = true)
	@Lob
	public String getObjectDatum() {
		return objectDatum;
	}

	public void setObjectDatum(String objectDatum) {
		this.objectDatum = objectDatum;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH }, optional = true)
	public WSpace getWorkspace() {
		return workspace;
	}

	public void setWorkspace(WSpace workspaces) {
		this.workspace = workspaces;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH }, mappedBy = "target")
	public List<VersionArc> getPrecetorsArc() {
		return precetorsArc;
	}

	public void setPrecetorsArc(List<VersionArc> precetorsArc) {
		this.precetorsArc = precetorsArc;
	}

	public void addPrecetorsArc(VersionArc va) {
		precetorsArc.add(va);
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	@OneToMany(mappedBy = "superObject", fetch = FetchType.LAZY)
	public List<VComposer> getSubObjects() {
		return subObjects;
	}

	@Transient
	public List<Integer> getSubObgectGIDs() {
		List<Integer> result = new ArrayList<Integer>();
		for (VComposer vc : subObjects) {
			result.add(vc.getSubObject().getGlobalVPId());
		}
		return result;
	}

	public void setSubObjects(List<VComposer> subObjects) {
		this.subObjects = subObjects;
	}

	@OneToMany(mappedBy = "subObject", fetch = FetchType.LAZY)
	public List<VComposer> getSuperObjects() {
		return superObjects;
	}

	public void setSuperObjects(List<VComposer> superObject) {
		this.superObjects = superObject;
	}

	/*
	 * @Transient public int getSuperObgectGID() { return
	 * superObjects.getSuperObject().getGlobalVPId(); }
	 */

	@Transient
	public HashMap<WSpace, VObjectVersion> getSuperObgectWS_VOVs() {
		HashMap<WSpace, VObjectVersion> result = new HashMap<WSpace, VObjectVersion>();
		for (VComposer vc : superObjects) {
			WSpace WS = vc.getSuperObject().getWorkspace();
			if (Object.class.isInstance(WS))
				result.put(WS, vc.getSuperObject());
		}
		return result;
	}

	@Override
	public String toString() {
		return this.getVobject().getVObjectId() + "v" + this.getVersionNumber()
				+ "(" + this.getGlobalVPId() + ") - " + this.getObjectName();
	}

	@Transient
	public VObjectVersion getAncestorVersion() {
		WSpace aws = workspace.getAncestorWorkspace();
		if (Object.class.isInstance(aws)) {
			List<VObjectVersion> vovList = aws.getLocalVersions();
			for (VObjectVersion vov : vovList) {
				if (vov.getVobject().equals(vobject)) {
					return vov;
				}
			}
		}
		return null;
	}

	@Transient
	public VObjectVersion getSuperObject() {
		HashMap<WSpace, VObjectVersion> ws_SuperVOVs = getSuperObgectWS_VOVs();
		if (ws_SuperVOVs.size() > 0)
			return getSuperObject(ws_SuperVOVs, workspace);
		else
			return null;
	}

	@Transient
	public VObjectVersion getAncestorSuperObjectVersion() {
		HashMap<WSpace, VObjectVersion> ws_SuperVOVs = getSuperObgectWS_VOVs();
		if (ws_SuperVOVs.size() > 0)
			return getSuperObject(ws_SuperVOVs,
					workspace.getAncestorWorkspace());

		return null;
	}

	@Transient
	private VObjectVersion getSuperObject(
			HashMap<WSpace, VObjectVersion> ws_SuperVOVs, WSpace selectedWS) {
		if (Object.class.isInstance(selectedWS)) {
			if (ws_SuperVOVs.containsKey(selectedWS))
				return ws_SuperVOVs.get(selectedWS);
			else
				return getSuperObject(ws_SuperVOVs,
						selectedWS.getAncestorWorkspace());
		} else
			return null;
	}

	@Transient
	public VObjectVersion getLocalSuperObgect() {
		HashMap<WSpace, VObjectVersion> ws_SuperVOVs = getSuperObgectWS_VOVs();
		if (ws_SuperVOVs.containsKey(workspace))
			return ws_SuperVOVs.get(workspace);
		else
			return null;
	}

}
