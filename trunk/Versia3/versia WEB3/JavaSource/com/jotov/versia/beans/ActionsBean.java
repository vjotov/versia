package com.jotov.versia.beans;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import com.jotov.versia.orm.Actions;

public class ActionsBean extends aDBbean {
	private ArrayList<Actions> actions;
	private Actions selectedAction;
	private Actions newAction = new Actions();

	public void createAction() {
		dbean.executeQuery(this, 2);
	}

	public void saveAction() {
		dbean.executeQuery(this, 3);
	}

	public ActionsBean() {

	}

	@Override
	public String executeQuery(int mode) {
		switch (mode) {
		case 1:
			return loadActions();
		case 2:
			return newAction();
		case 3:
			return updateAction();
		default:
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private String loadActions() {
		Query query = em.createQuery("select a from Actions a");
		List<Actions> result = query.getResultList();
		actions.addAll(result);

		return null;
	}

	private String newAction() {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		em.persist(newAction);
		trx.commit();
		newAction = new Actions();
		return null;
	}

	private String updateAction() {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		em.persist(selectedAction);
		trx.commit();
		return null;
	}

	// GETTERS & SETTERS
	public ArrayList<Actions> getActions() {
		if (actions == null) {
			actions = new ArrayList<Actions>();
			dbean.executeQuery(this, 1);
		}
		return actions;
	}

	public void setActions(ArrayList<Actions> actions) {
		this.actions = actions;
	}

	public Actions getSelectedAction() {
		return selectedAction;
	}

	public void setSelectedAction(Actions selectedAction) {
		this.selectedAction = selectedAction;
	}

	public Actions getNewAction() {
		return newAction;
	}

	public void setNewAction(Actions newAction) {
		this.newAction = newAction;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String executeQuery() {
		Query query = em.createQuery("select a from Actions a");
		List<Actions> result = query.getResultList();
		actions.addAll(result);

		return null;
	}

}
