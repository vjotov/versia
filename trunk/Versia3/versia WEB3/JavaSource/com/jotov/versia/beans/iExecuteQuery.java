package com.jotov.versia.beans;

import javax.persistence.EntityManager;

public interface iExecuteQuery {

	String executeQuery();

	String executeQuery(int mode) throws Exception;

	void setEntityManager(EntityManager em);

	void resetEntityManager();

}
