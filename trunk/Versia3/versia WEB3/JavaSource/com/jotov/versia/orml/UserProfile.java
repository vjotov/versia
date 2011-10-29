package com.jotov.versia.orml;

import java.util.ArrayList;
import java.util.List;

//import javax.persistence.Column;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class UserProfile {
	private int userId;
	private String userName;
	private String password;
	private List<Permitions> permitions = new ArrayList<Permitions>();
	private List<WSpace> openedWorkspaces = new ArrayList<WSpace>();

	public UserProfile() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {
			CascadeType.ALL })
	public List<Permitions> getPermitions() {
		return permitions;
	}

	public void setPermitions(List<Permitions> permitions) {
		this.permitions = permitions;
	}

/*	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	public List<WSpace> getOpenedWorkspaces() {
		return openedWorkspaces;
	}

	public void setOpenedWorkspaces(List<WSpace> openedWorkspaces) {
		this.openedWorkspaces = openedWorkspaces;
	}*/

/*	public void addOpenWorkspace(WSpace workspace) {
		List<WSpace> ws_ls = getOpenedWorkspaces();
		ws_ls.add(workspace);
		workspace.setUser(this);
	}

	public void removeOpenWorkspace(WSpace workspace) {
		List<WSpace> ws_ls = getOpenedWorkspaces();
		if (ws_ls.contains(workspace))
			ws_ls.remove(workspace);
		workspace.setUser(null);
	}*/

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User: " + getUserName();
	}

}
