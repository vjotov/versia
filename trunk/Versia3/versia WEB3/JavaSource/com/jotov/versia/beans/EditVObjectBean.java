package com.jotov.versia.beans;

import java.util.ArrayList;

import javax.persistence.EntityTransaction;

import com.jotov.versia.orm.VObject;
import com.jotov.versia.orm.VObjectVersion;
import com.jotov.versia.orm.VersionArc;
import com.jotov.versia.orm.WSpace;
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
		if (Object.class.isInstance(aws)
				&& !Object.class.isInstance(aws.getOpenedByUser())
				&& ws.getLocalVersions().contains(localVOV)) {
			VObjectVersion ancestorVOV = localVOV.getAncestorVersion();
			if (Object.class.isInstance(ancestorVOV)) {
				em.getTransaction().begin();
				ancestorVOV.setWorkspace(null);
				aws.removeLocalVersion(ancestorVOV);

				localVOV.setWorkspace(aws);
				localVOV.addPrecetorsArc(VersionArc.createArcs(localVOV,
						ancestorVOV, aws, session.getUserProfile()));
				ws.removeLocalVersion(localVOV);
				aws.addLocalVersion(localVOV);
				em.getTransaction().commit();
			} else {
				em.getTransaction().begin();
				localVOV.setWorkspace(aws);
				ws.removeLocalVersion(localVOV);
				aws.addLocalVersion(localVOV);
				em.getTransaction().commit();
			}

		}
		return null;
	}

	private synchronized String rollbackVersion() {
		WSpace ws = session.getWorkspace();
		VObjectVersion localVOV = session.getSelectedVersion();

		em.getTransaction().begin();
		ws.removeLocalVersion(localVOV);
		localVOV.setWorkspace(null);
		em.persist(localVOV);
		em.getTransaction().commit();
		return null;
	}

	private String deleteVersion() {
		em.getTransaction().begin();
		VObjectVersion deletedVersion = VObjectVersion.markDeleteVersion(
				session.getWorkspace(), session.getSelectedVersion(), session.getUserProfile());
		em.persist(session.getSelectedVersion());
		em.persist(deletedVersion);
		em.getTransaction().commit();
		return null;
	}
	
	// TODO: attached WI should be editable
	// TODO: Deleted object should be editable

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
		System.out.println("editVObjectBean.RollBack()/0");
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

}
