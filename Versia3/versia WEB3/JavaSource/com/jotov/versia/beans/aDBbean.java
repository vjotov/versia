package com.jotov.versia.beans;

import javax.persistence.EntityManager;

public abstract class aDBbean implements iExecuteQuery {

	protected EntityManager em;
	protected DBConnectBean dbean;

	public aDBbean() {
		super();
	}

	public DBConnectBean getDbean() {
		return dbean;
	}

	public void setDbean(DBConnectBean dbean) {
		this.dbean = dbean;
	}

	@Override
	public void setEntityManager(EntityManager em) {
		this.em = em;

	}

	@Override
	public void resetEntityManager() {
		em = null;

	}

	@Override
	public String executeQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeQuery(int mode) {
		// TODO Auto-generated method stub
		return null;
	}

}