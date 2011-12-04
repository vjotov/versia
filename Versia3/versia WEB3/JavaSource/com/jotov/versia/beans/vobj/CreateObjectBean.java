package com.jotov.versia.beans.vobj;

import javax.persistence.EntityTransaction;
import com.jotov.versia.beans.UserSessionBean;
import com.jotov.versia.beans.aDBbean;
import com.jotov.versia.orm.VObject;

public class CreateObjectBean extends aDBbean {
	private String Name;
	private String Datum;
	private UserSessionBean session;

	public CreateObjectBean() {
		super();
	}

	public void CreateObject() {
		dbean.executeQuery(this);
		session.getApp().NotifyCleanAll();
	}

	@Override
	public String executeQuery() {
		EntityTransaction trx = em.getTransaction();
		try {
			trx.begin();

			VObject obj = VObject.createVObject(this.Name, this.Datum,
					session.getWorkspace(), null);
			em.persist(obj);
			trx.commit();
		} catch (Exception e) {
			if(trx.isActive())
				trx.rollback();
			e.printStackTrace(System.err);
		}
		return null;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDatum() {
		return Datum;
	}

	public void setDatum(String datum) {
		Datum = datum;
	}

	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
	}
}
