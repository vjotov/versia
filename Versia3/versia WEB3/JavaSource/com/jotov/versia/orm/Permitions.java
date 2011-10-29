package com.jotov.versia.orm;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Permitions {
	private Actions action;
	private UserProfile user;
	private int granted;

	@ManyToOne(cascade=CascadeType.PERSIST)
	public Actions getAction() {
		return action;
	}

	public void setAction(Actions action) {
		this.action = action;
	}
	@ManyToOne(cascade=CascadeType.PERSIST)
	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}

	public int getGranted() {
		return granted;
	}

	public void setGranted(int granted) {
		if (granted != 1)
			granted = 0;
		this.granted = granted;
	}
}
