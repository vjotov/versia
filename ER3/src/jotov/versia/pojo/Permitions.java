package jotov.versia.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PERMITIONS")
public class Permitions {
	private Actions action;
	private User user;
	private int granted;

	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
	@Column(name="ACTION_ID")
	public Actions getAction() {
		return action;
	}

	public void setAction(Actions action) {
		this.action = action;
	}
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JoinColumn(name="USER_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "GRANTED")
	public int getGranted() {
		return granted;
	}

	public void setGranted(int granted) {
		if (granted != 1)
			granted = 0;
		this.granted = granted;
	}
}
