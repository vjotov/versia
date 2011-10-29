package com.jotov.versia.beans;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBConnectBean {
	private EntityManagerFactory factory;
	private EntityManager em;

	public DBConnectBean() {
		super();
		factory = Persistence.createEntityManagerFactory("versia_er3");
		try {
			em = factory.createEntityManager();
		} catch (Exception ex) {
			System.out.println("constructor 0 " + ex.getMessage());
			ex.printStackTrace();
			System.out.println("constructor 00 ");
		}//*/
	}

	public EntityManager getEntityManager() {
		return em;
	}

	synchronized public String executeQuery(iExecuteQuery eq) {
		String result = "";
		try {
			eq.setEntityManager(em);
			result = eq.executeQuery();
			eq.resetEntityManager();
		} catch (Exception e) {
			System.err.println("DBConnectBean executeQuery/1 " + e.getMessage());
			System.err.println(e.getStackTrace());
		}
		return result;
	}
	synchronized public String executeQuery(iExecuteQuery eq, int mode) {
		String result = "";
		try {
			eq.setEntityManager(em);
			result = eq.executeQuery(mode);
			eq.resetEntityManager();
		} catch (Exception e) {
			if(em.getTransaction().isActive())
				em.getTransaction().rollback();
			System.err.println("DBConnectBean executeQuery/2 " + e.getMessage());
			e.printStackTrace(System.err);
		}
		return result;
	}

	@Override
	protected void finalize() throws Throwable {
		factory.close();
		super.finalize();
	}
}
