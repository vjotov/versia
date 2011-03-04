package jotov.versia.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "ACTIONS")
public class Actions {
	private int actionId;
	private String actionName;
	private List<Permitions> permitions = new ArrayList<Permitions>();

	@Id
	@Column(name = "ACTION_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public int getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	@Column(name = "ACTION_NAME", length = 30)
	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	@OneToMany(mappedBy = "action", fetch = FetchType.EAGER)
	public List<Permitions> getPermitions() {
		return permitions;
	}

	public void setPermitions(List<Permitions> permitions) {
		this.permitions = permitions;
	}

}
