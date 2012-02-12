package com.jotov.versia.beans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import javax.persistence.EntityTransaction;
import com.jotov.versia.beans.vobj.ListVobjectsBean;
import com.jotov.versia.beans.vobj.VItem;
import com.jotov.versia.orm.VComposer;
import com.jotov.versia.orm.VObject;
import com.jotov.versia.orm.VObjectVersion;
import com.jotov.versia.orm.VersionArc;
import com.jotov.versia.orm.WSpace;

public class EditVObjectBean extends aDBbean {
	// private VObject vo;
	private String NewName;
	private String NewData;
	private Boolean isWorkItem;
	private UserSessionBean session;
	private ListVobjectsBean lvBean;
	private List<SelectItem> options = new ArrayList<SelectItem>();
	private List<Integer> selected = new ArrayList<Integer>();

	@Override
	public synchronized String executeQuery(int mode) {
		switch (mode) {
		case 1:
			return createNewVersion("create");
		case 2:
			return publishVersion();
		case 3:
			return rollbackVersion();
		case 4:
			return createNewVersion("delete");
		default:
			return null;
		}
	}

	private synchronized String publishVersion() {
		WSpace ws = session.getWorkspace();
		WSpace aws = ws.getAncestorWorkspace();
		VObjectVersion localVOV = session.getSelectedVersion();

		if (Object.class.isInstance(aws) && aws.getOpenedByUser() == null
				&& ws.getLocalVersions().contains(localVOV)) {

			em.getTransaction().begin();

			publishVersion(localVOV, ws, aws);

			em.getTransaction().commit();
		}
		return null;
	}

	private synchronized void publishVersion(VObjectVersion pVOV, WSpace ws,
			WSpace ancestorWS) {

		// Separate method for recursive publication of sub-objects of a
		// composed object

		if (!pVOV.getWorkspace().equals(ws))
			// not local object => nothing to publicate
			return;

		VObjectVersion localSuperVOV = pVOV.getLocalSuperObgect();
	
		if (Object.class.isInstance(localSuperVOV))
			publishVersion(localSuperVOV, ws, ancestorWS);

		VObjectVersion ancestorVOV = pVOV.getAncestorVersion();
		if (Object.class.isInstance(ancestorVOV)) {

			ancestorVOV.setWorkspace(null);
			ancestorWS.removeLocalVersion(ancestorVOV);

			pVOV.setWorkspace(ancestorWS);
			VersionArc va = VersionArc.createArcs(pVOV, ancestorVOV,
					ancestorWS, session);
			va.setNotes("publication");
			pVOV.addPrecetorsArc(va);
			ws.removeLocalVersion(pVOV);
			ancestorWS.addLocalVersion(pVOV);

		} else {
			pVOV.setWorkspace(ancestorWS);
			ws.removeLocalVersion(pVOV);
			ancestorWS.addLocalVersion(pVOV);
		}

		List<VComposer> vcs = pVOV.getSubObjects();
		for (VComposer vc : vcs) {
			VObjectVersion subObject = vc.getSubObject();

			// publication only of local subObject versions
			if (subObject.getWorkspace().equals(ws))
				publishVersion(subObject, ws, ancestorWS);
		}
	}

	private synchronized String rollbackVersion() {
		WSpace currentWS = session.getWorkspace();
		VObjectVersion localVOV = session.getSelectedVersion();
		if (localVOV.getWorkspace().equals(currentWS)) {
			// RollBack of local versions only
			em.getTransaction().begin();
			rollbackVersion(currentWS, localVOV);

			em.getTransaction().commit();
		}
		return null;
	}

	private synchronized void rollbackVersion(WSpace selectedWS,
			VObjectVersion vov) {
		selectedWS.removeLocalVersion(vov);
		vov.setWorkspace(null);
		em.persist(vov);

		// process subObjects
		VObjectVersion ancestorVisibleObjectVersion = session.getOpenWsRegistry().getAncestorVisibleVersion(selectedWS, vov);
		if (Object.class.isInstance(ancestorVisibleObjectVersion)) {
			List<VComposer> ancestorComposions = ancestorVisibleObjectVersion
					.getSubObjects();
			for (VComposer vc : ancestorComposions) {
				VObjectVersion ancestorSubVOVersion = vc.getSubObject();
				VObjectVersion localSubVOVersion = getLovalVerion(
						ancestorSubVOVersion, selectedWS);

				// recursive rollback of all sub-objects
				rollbackVersion(selectedWS, localSubVOVersion);
			}
		}
	}

	private VObjectVersion getLovalVerion(VObjectVersion ancestorVOVersion,
			WSpace selectedWS) {
		VObject vObj = ancestorVOVersion.getVobject();
		List<VObjectVersion> localVerions = selectedWS.getLocalVersions();
		for (VObjectVersion lvov : localVerions) {
			if (vObj.equals(lvov.getVobject()))
				return lvov;
		}
		return null;
	}

	private synchronized String createNewVersion(String createParameter) {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		VObjectVersion oldVer = session.getSelectedVersion();
		VObject vo = oldVer.getVobject();
		if (!oldVer.getObjectName().equals(NewName) || isChangedComposition()
				|| !oldVer.getObjectDatum().equals(NewData)) {
			// initialisation
			ArrayList<VObjectVersion> precedors = new ArrayList<VObjectVersion>();
			precedors.add(oldVer);
			WSpace currentWS = session.getWorkspace();
			VObjectVersion newVer;

			// create new version
			if (createParameter.equalsIgnoreCase("create"))
				newVer = VObjectVersion.createVersion(vo, NewName, NewData,
						currentWS, precedors, session);
			else if (createParameter.equalsIgnoreCase("delete"))
				newVer = VObjectVersion.markDeleteVersion(currentWS, oldVer,
						session);
			else {
				trx.rollback();
				return null;
			}

			updateSuperObject(newVer, oldVer);

			// update old verion's woekspace attachment, if needed
			if (currentWS.equals(oldVer.getWorkspace())) {
				oldVer.setWorkspace(null);
				em.persist(oldVer);
			}

			saveNewComposition();
			em.persist(newVer);

		}
		// update workitem settings (they are on object level)
		if (vo.isWorkItem() != this.isWorkItem) {
			// workitem flag doesn't change version
			if (isWorkItem)
				vo.setWorkItem("TRUE");
			else
				vo.setWorkItem("FALSE");
			em.persist(vo);
		}

		trx.commit();
		return null;
	}

	private void updateSuperObject(VObjectVersion newVer, VObjectVersion oldVer) {
		VObjectVersion oldSuperVOV = oldVer.getSuperObject();
		if (Object.class.isInstance(oldSuperVOV)) {
//			VObjectVersion oldSuperObjectVer = superObjComp.getSuperObject();
			WSpace currentWS = session.getWorkspace();

			List<VObjectVersion> precedors = new ArrayList<VObjectVersion>();
			precedors.add(oldSuperVOV);
			VObjectVersion newSuperVOV = VObjectVersion.createVersion(
					oldSuperVOV.getVobject(),
					oldSuperVOV.getObjectName(),
					oldSuperVOV.getObjectDatum(), newVer.getWorkspace(),
					precedors, session);
			if (currentWS.equals(oldSuperVOV.getWorkspace())) {
				oldSuperVOV.setWorkspace(null);
				em.persist(oldSuperVOV);
			}
			copySuperObjectComposition(newSuperVOV, oldSuperVOV,
					newVer, oldVer);

			// recursive update of superObjects
			updateSuperObject(newSuperVOV, oldSuperVOV);
		} else
			return;
	}

	private void copySuperObjectComposition(VObjectVersion newSuperObjectVer,
			VObjectVersion oldSuperObjectVer, VObjectVersion newVer,
			VObjectVersion oldVer) {

		List<VComposer> oldComposers = oldSuperObjectVer.getSubObjects();
		for (VComposer vc : oldComposers) {
			VComposer nvc;
			if (vc.getSubObject().equals(oldVer)) {
				nvc = VComposer.createComposition(newSuperObjectVer, newVer);
			} else {
				nvc = VComposer.createComposition(newSuperObjectVer,
						vc.getSubObject());
			}
			em.persist(nvc);
		}
	}

	private void saveNewComposition() {
		for (int i : selected) {
			VObjectVersion subObject = em.find(VObjectVersion.class, i);
			VComposer newVC = VComposer.createComposition(
					session.getSelectedVersion(), subObject);
			em.persist(newVC);
		}
	}

	private boolean isChangedComposition() {
		VObjectVersion oldVer = session.getSelectedVersion();
		List<Integer> oldCompositionList = oldVer.getSubObgectGIDs();
		if (oldCompositionList.size() != selected.size())
			return true;
		for (int i : oldCompositionList) {
			if (!selected.contains(i))
				return true;
		}
		for (int i : selected) {
			if (!oldCompositionList.contains(i))
				return true;
		}
		return false;
	}

	public void Save() {
		if (this.isReadonly()) {
			System.out
					.println("editVObjectBean.Save()/0 failed - object is read only");
			resetVars();
			return;
		}

		System.out.println("editVObjectBean.Save()/0");
		dbean.executeQuery(this, 1);
		session.setSelectedVersion(null);
		session.executeClean();
		resetVars();
	}

	private void resetVars() {
		isWorkItem = null;
		NewData = null;
		NewName = null;
		options = new ArrayList<SelectItem>();
		selected = new ArrayList<Integer>();
	}

	public void Publish() {
		if (this.isReadonly()) {
			resetVars();
			return;
		}
		System.out.println("editVObjectBean.Publish()/0");
		dbean.executeQuery(this, 2);
		session.setSelectedVersion(null);
		session.getApp().NotifyCleanAll();
		resetVars();
	}

	public void RollBack() {
		if (this.isReadonly()) {
			resetVars();
			return;
		}
		System.out.println("editVObjectBean.RollBack()/0");
		dbean.executeQuery(this, 3);
		session.setSelectedVersion(null);
		session.getApp().NotifyCleanAll();
		resetVars();
	}

	public void Delete() {
		if (this.isReadonly()) {
			resetVars();
			return;
		}
		System.out.println("editVObjectBean.Delete()/0");
		dbean.executeQuery(this, 4);
		session.setSelectedVersion(null);
		session.getApp().NotifyCleanAll();
		resetVars();
	}

	public Integer getVObjectID() {
		VObjectVersion vov = session.getSelectedVersion();
		if (vov == null)
			return null;
		else {
			VObject vo = vov.getVobject();
			return vo.getVObjectId();
		}
	}

	public String getVObjectName() {
		if (NewName == null && session.getSelectedVersion() != null)
			NewName = session.getSelectedVersion().getObjectName();

		return NewName;
	}

	public void setVObjectName(String newName) {
		this.NewName = newName;
	}

	public String getVObjectDatum() {
		if (NewData == null && session.getSelectedVersion() != null)
			NewData = session.getSelectedVersion().getObjectDatum();

		return NewData;
	}

	public void setVObjectDatum(String newDatum) {
		this.NewData = newDatum;
	}

	public Integer getVObjectVersion() {
		VObjectVersion vov = session.getSelectedVersion();
		if (vov == null)
			return null;
		else
			return vov.getVersionNumber();
	}

	public boolean getIsWorkItem() {
		if (isWorkItem == null && session.getSelectedVersion() != null)
			isWorkItem = new Boolean(session.getSelectedVersion().getVobject()
					.isWorkItem());
		else if (isWorkItem == null)
			return false;
		return isWorkItem;
	}

	public void setIsWorkItem(boolean isWorkItem) {
		this.isWorkItem = isWorkItem;
	}

	public boolean isReadonly() {
		VObjectVersion vov = session.getSelectedVersion();
		if (vov == null)
			return false;
		VItem vitem = session.getVItemShell().getItemByVOV(vov);

		if (vitem.isDeleted() || (vitem.isAttachedWI()))
			// Deleted object are read only
			// Attached WI are read only
			return true;
		return false;
	}

	public boolean isRoPublicable() {
		VObjectVersion vov = session.getSelectedVersion();
		if (vov == null)
			return false;
		VItem vitem = session.getVItemShell().getItemByVOV(vov);

		if (vitem.isAttachedWI() || vitem.isAncestorAWI())
			// Attached WI cannot be published
			// and WI attached in ancestor's WS
			return true;
		return false;
	}

	public boolean isRoRollback() {
		VObjectVersion vov = session.getSelectedVersion();
		if (vov == null)
			return false;
		VItem vitem = session.getVItemShell().getItemByVOV(vov);

		if ((vitem.isAttachedWI()))
			// Attached WI cannot be roll-backed
			return true;
		return false;
	}

	public Integer getAncestorVersionNumber() {

		return null;
	}

	public ListVobjectsBean getLvBean() {
		return lvBean;
	}

	public void setLvBean(ListVobjectsBean lvBean) {
		this.lvBean = lvBean;
	}

	public List<SelectItem> getOptions() {
		if (options.size() == 0 && session.getSelectedVersion() != null)
			calculateOptions();
		return options;
	}

	private void calculateOptions() {
		List<VObjectVersion> vovs = getAllVisibleNotSubVersions();
		options = new ArrayList<SelectItem>();
		for (VObjectVersion vov : vovs) {
			options.add(new SelectItem(vov.getGlobalVPId(), vov.getObjectName()));
		}
	}

	private List<VObjectVersion> getAllVisibleNotSubVersions() {
		List<VItem> vItems = session.getVItemShell().getVItems();
		List<VObjectVersion> resultList = new ArrayList<VObjectVersion>();
		for (VItem vItem : vItems) {
			VObjectVersion vov = vItem.getVoVersion();

			if (!vov.equals(session.getSelectedVersion())
					//TODO това долу трябва да се оправи
					&& (vov.getSuperObjects() == null || vov.getSubObjects()
							.equals(session.getSelectedVersion())))
				resultList.add(vov);
		}
		return resultList;
	}

	public void setOptions(List<SelectItem> options) {
		this.options = options;
	}

	public List<Integer> getSelected() {
		if (selected.size() == 0 && session.getSelectedVersion() != null)
			calculateSelected();
		return selected;
	}

	private void calculateSelected() {
		VObjectVersion vov = session.getSelectedVersion();
		List<VComposer> subObjects = vov.getSubObjects();
		selected = new ArrayList<Integer>();
		for (VComposer subObject : subObjects) {
			selected.add(subObject.getSubObject().getGlobalVPId());
		}
	}

	public void setSelected(List<Integer> selected) {
		this.selected = selected;
	}

	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
	}

}
