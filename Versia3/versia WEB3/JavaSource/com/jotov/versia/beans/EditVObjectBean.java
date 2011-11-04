package com.jotov.versia.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.jotov.versia.beans.vobj.VisibileItemsExtractor;
import com.jotov.versia.beans.vobj.VisibleItems;
import com.jotov.versia.orm.VComposer;
import com.jotov.versia.orm.VObject;
import com.jotov.versia.orm.VObjectVersion;
import com.jotov.versia.orm.VersionArc;
import com.jotov.versia.orm.WSpace;
import com.jotov.versia.orm.WorkItemAttachement;
import com.sun.swing.internal.plaf.synth.resources.synth;

public class EditVObjectBean extends aDBbean {
	// private VObject vo;
	private String NewName;
	private String NewData;
	private Boolean isWorkItem;
	private UserSessionBean session;

	@Override
	public String executeQuery(int mode) {
		switch (mode) {
		case 1:
			return createNewVersion();
		case 2:
			return publishVersion();
		case 3:
			return rollbackVersion();
		case 4:
			return deleteVersion();
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

	@SuppressWarnings("unchecked")
	private void publishVersion(VObjectVersion pVOV, WSpace ws,
			WSpace ancestorWS) {
		// Separate method for recursive publication of sub-objects of a
		// composed object
		
		if (!pVOV.getWorkspace().equals(ws))
			// not local object => nothing to publicate
			return;
		
		VObjectVersion ancestorVOV = pVOV.getAncestorVersion();
		if (Object.class.isInstance(ancestorVOV)) {

			ancestorVOV.setWorkspace(null);
			ancestorWS.removeLocalVersion(ancestorVOV);

			pVOV.setWorkspace(ancestorWS);
			pVOV.addPrecetorsArc(VersionArc.createArcs(pVOV, ancestorVOV,
					ancestorWS, session.getUserProfile()));
			ws.removeLocalVersion(pVOV);
			ancestorWS.addLocalVersion(pVOV);

		} else {
			pVOV.setWorkspace(ancestorWS);
			ws.removeLocalVersion(pVOV);
			ancestorWS.addLocalVersion(pVOV);
		}

		Query query = em
				.createQuery("SELECT c FROM VComposer c WHERE c.superObject = :super");
		query.setParameter("super", pVOV);
		List<VComposer> vcs = query.getResultList();
		for (VComposer vc : vcs) {
			publishVersion(vc.getSubObject(), ws, ancestorWS);
		}
	}

	private synchronized String rollbackVersion() {
		WSpace ws = session.getWorkspace();
		VObjectVersion localVOV = session.getSelectedVersion();
		if (localVOV.getWorkspace().equals(ws)) {
			// RollBack of local versions only
			em.getTransaction().begin();
			ws.removeLocalVersion(localVOV);
			localVOV.setWorkspace(null);
			em.persist(localVOV);
			em.getTransaction().commit();
		}
		return null;
	}

	private String deleteVersion() {
		em.getTransaction().begin();
		VObjectVersion deletedVersion = VObjectVersion.markDeleteVersion(
				session.getWorkspace(), session.getSelectedVersion(),
				session.getUserProfile());
		em.persist(session.getSelectedVersion());
		em.persist(deletedVersion);
		em.getTransaction().commit();
		return null;
	}

	private String createNewVersion() {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		VObjectVersion oldVer = session.getSelectedVersion();
		VObject vo = oldVer.getVobject();
		if (!oldVer.getObjectName().equals(NewName)
				|| !oldVer.getObjectDatum().equals(NewData)) {
			ArrayList<VObjectVersion> precedors = new ArrayList<VObjectVersion>();
			precedors.add(oldVer);

			VObjectVersion nv = VObjectVersion.createVersion(vo, NewName,
					NewData, session.getWorkspace(), precedors,
					session.getUserProfile());

			vo.setWorkItem(isWorkItem);
			oldVer.setWorkspace(null);
			em.persist(nv);
			em.persist(oldVer);
		}
		if (vo.isWorkItem() != this.isWorkItem) {
			vo.setWorkItem(isWorkItem);
			em.persist(vo);
		}

		trx.commit();
		return null;
	}

	public void Save() {
		System.out.println("editVObjectBean.Save()/0");
		dbean.executeQuery(this, 1);
		session.setSelectedVersion(null);
		isWorkItem = null;
		NewData = null;
		NewName = null;
	}

	public void Publish() {
		System.out.println("editVObjectBean.Publish()/0");
		dbean.executeQuery(this, 2);
		session.setSelectedVersion(null);
		isWorkItem = null;
		NewData = null;
		NewName = null;
	}

	public void RollBack() {
		System.out.println("editVObjectBean.RollBack()/0");
		dbean.executeQuery(this, 3);
		session.setSelectedVersion(null);
		isWorkItem = null;
		NewData = null;
		NewName = null;
	}

	public void Delete() {
		System.out.println("editVObjectBean.Delete()/0");
		dbean.executeQuery(this, 4);
		session.setSelectedVersion(null);
		isWorkItem = null;
		NewData = null;
		NewName = null;
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

	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
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
		VObject vo = vov.getVobject();

		if (vov.getDeleteFlag() == 1)
			// Deleted object are read only
			return true;
		else if (vo.isWorkItem()) {
			// attached WI are read only
			List<WorkItemAttachement> wia_ls = session.getWorkspace()
					.getAttachedWorkItems();
			for (WorkItemAttachement wia : wia_ls) {
				if (vo.equals(wia.getWorkitem()))
					return true;
			}
		}
		return false;
	}
}
