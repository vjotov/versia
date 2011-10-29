package com.jotov.versia.beans;

import javax.persistence.EntityTransaction;

import com.jotov.versia.orm.UserProfile;

public class LoginBean extends aDBbean {
	private String name = "admin";
	private String password = "admin";
	private UserSessionBean session;

	public LoginBean() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String CheckValidUser() {
		return dbean.executeQuery(this);
	}

	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
	}

	@Override
	public String executeQuery() {
		String return_srt;

		/*
		 * .createQuery("select up from UserProfile up where up.userName = '" +
		 * this.name + "' and up.password = '" + this.password + "' ");
		 */
		UserProfile user = em.find(UserProfile.class, 1);

		if (user == null) {
			EntityTransaction trx = em.getTransaction();
			trx.begin();
			user = new UserProfile();
			user.setUserId(1);
			user.setUserName("admin");
			em.persist(user);

			trx.commit();

		}
		session.setUserProfile(user);
		// if (query.getResultList().size() == 1)
		return_srt = "success";
		// else
		// return_srt = "fail";
		return return_srt;
	}
}
