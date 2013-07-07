package com.jotov.versia.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.Query;
import com.jotov.versia.orm.Product;
import com.jotov.versia.orm.Release;
import com.jotov.versia.orm.UserProfile;
import com.jotov.versia.orm.WSpace;

public class ProductReleaseWorkspaceBean extends aDBbean {
	private UserSessionBean session;
	private ArrayList<Product> products = new ArrayList<Product>();
	private ArrayList<Release> releases = new ArrayList<Release>();
	private ArrayList<WSpace> workspaces = new ArrayList<WSpace>();

	public ProductReleaseWorkspaceBean() {
		super();
	}

	@PostConstruct
	public void init() {
		dbean.executeQuery(this, 1);
	}

	@Override
	public String executeQuery(int mode) throws Exception {
		switch (mode) {
		case 1:
			return loadProducts();
		case 2:
			return loadWorkspaces();
		case 3:
			return openWorkspace();
		default:
			throw new Exception(
					"com.jotov.versia.beans.ProductReleaseWorkspaceBean/executeQuery - unexmected query mode: "
							+ mode);
		}
	}

	private String openWorkspace() {
		if (canOpen()) {
			UserProfile user = session.getUserProfile();
			em.getTransaction().begin();
			session.getWorkspace().setOpenedByUser(user);
			em.getTransaction().commit();
			session.getOpenWsRegistry().register(session.getWorkspace());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private String loadProducts() {
		products.clear();
		Query query = em.createQuery("select p from Product p");
		List<Product> result = query.getResultList();
		products.addAll(result);

		return null;
	}

	@SuppressWarnings("unchecked")
	private String loadWorkspaces() {
		if (session.getRelease() != null) {
			workspaces.clear();
			Query query = em
					.createQuery("select w from WSpace w where w.release=:rel ORDER BY w.wSpaceId");
			query.setParameter("rel", session.getRelease());
			List<WSpace> result = query.getResultList();
			workspaces.addAll(result);
		}
		return null;
	}

	// GETTERS & SETTERS

	private boolean canOpen() {
		WSpace workspace = session.getWorkspace();
		return Object.class.isInstance(workspace)
				&& (workspace.getOpenedByUser() == null || workspace
						.getOpenedByUser().equals(session.getUserProfile()));
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public ArrayList<Release> getReleases() {
		if (session.getProduct() != null) {
			releases = (ArrayList<Release>) session.getProduct().getRealeses();
		}
		return releases;
	}

	public ArrayList<WSpace> getWorkspaces() {
		if (session.getRelease() != null) {
			dbean.executeQuery(this, 2);
		}
		return workspaces;
	}

	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
	}

	public void setSelectedProduct(Product selectedProduct) {
		if (selectedProduct.equals(session.getProduct()))
			return;
		session.setProduct(selectedProduct);
		session.setRelease(null);
		session.setWorkspace(null);
	}

	public void setSelectedRelease(Release selectedRelease) {
		if (selectedRelease.equals(session.getRelease()))
			return;
		session.setRelease(selectedRelease);
		session.setWorkspace(null);
	}

	public void setSelectedWorkspace(WSpace selectedWorkspace) {
		UserProfile user = selectedWorkspace.getOpenedByUser();
		if (user == null || user.equals(session.getUserProfile()))
			session.setWorkspace(selectedWorkspace);
	}

	public boolean getCanOpen() {
		if (canOpen())
			return false;
		else
			return true;
	}

	public String openWorkSpace() {
		if (Object.class.isInstance(session.getWorkspace())) {
			dbean.executeQuery(this, 3);
			return "open";
		} else
			return null;
	}

}
