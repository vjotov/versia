package jotov.versia.pojo;

import javax.persistence.CascadeType;
//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
//import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Permitions {
	private Actions action;
	private User user;
	private int granted;

	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
	public Actions getAction() {
		return action;
	}

	public void setAction(Actions action) {
		this.action = action;
	}
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
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
