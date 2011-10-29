package com.jotov.versia.beans;

import javax.persistence.Query;
import com.jotov.versia.orm.Product;

public class OpenProductBean extends aDBbean {
	private int productId;
	private UserSessionBean session;
	Product selectedProduct = null;

	public OpenProductBean() {
	};

	public void OpenProduct() {
		dbean.executeQuery(this);
		session.setProduct(selectedProduct);
//		session.doOpenReleases();
	}

	@Override
	public String executeQuery() {
		Query query = em
				.createQuery("select p from Product p where p.productId=:prod");
		query.setParameter("prod", productId);
		selectedProduct = (Product) query.getSingleResult();

		return null;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
	}
}
