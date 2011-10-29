package com.jotov.versia.orm;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class UserProfile {
	private int userId;
	private String userName;
	private String password;
	private List<Permitions> permitions = new ArrayList<Permitions>();
	private WSpace openedWorkspace;
	private WSpace publicationWorkspace;

	public UserProfile() {
		super();
	}

	@Id
	@GeneratedValue(generator="UserSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="UserSeq", sequenceName="SQ_USERPROFILE")	
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

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	public List<Permitions> getPermitions() {
		return permitions;
	}

	public void setPermitions(List<Permitions> permitions) {
		this.permitions = permitions;
	}

	@OneToOne(fetch = FetchType.LAZY, optional = true, cascade = {
			CascadeType.REFRESH, CascadeType.PERSIST }, mappedBy = "openedByUser")
	public WSpace getOpenedWorkspace() {
		return openedWorkspace;
	}

	public void setOpenedWorkspace(WSpace openedWorkspace) {
		if (this.openedWorkspace != null) {
			this.openedWorkspace.setOpenedByUser(null);
		}
		this.openedWorkspace = openedWorkspace;
		if (openedWorkspace != null) {
			this.openedWorkspace.setOpenedByUser(this);
		}
	}

	@OneToOne(fetch = FetchType.LAZY, optional = true, cascade = {
			CascadeType.REFRESH, CascadeType.PERSIST }, mappedBy = "publicationByUser")
	public WSpace getPublicationWorkspace() {
		return publicationWorkspace;
	}

	public void setPublicationWorkspace(WSpace publicationWorkspace) {
		if (this.publicationWorkspace != null) {
			this.publicationWorkspace.setPublicationByUser(null);
		}
		this.publicationWorkspace = publicationWorkspace;
		if (publicationWorkspace != null) {
			this.publicationWorkspace.setPublicationByUser(this);
		}
	}

	/*
	 * @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) public List<WSpace>
	 * getOpenedWorkspaces() { return openedWorkspaces; }
	 * 
	 * public void setOpenedWorkspaces(List<WSpace> openedWorkspaces) {
	 * this.openedWorkspaces = openedWorkspaces; }
	 */

	/*
	 * public void addOpenWorkspace(WSpace workspace) { List<WSpace> ws_ls =
	 * getOpenedWorkspaces(); ws_ls.add(workspace); workspace.setUser(this); }
	 * 
	 * public void removeOpenWorkspace(WSpace workspace) { List<WSpace> ws_ls =
	 * getOpenedWorkspaces(); if (ws_ls.contains(workspace))
	 * ws_ls.remove(workspace); workspace.setUser(null); }
	 */

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
