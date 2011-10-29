package com.jotov.versia.beans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import com.jotov.versia.orm.Product;

public class ProductBean extends aDBbean {
	private UserSessionBean session;
	private ArrayList<Product> products = new ArrayList<Product>();;
	private int selectedRow;
	//private Product selectedProduct;
	private Set<Integer> rowsToUpdate = new HashSet<Integer>();
	private String newProductName;

	@Override
	public String executeQuery(int mode) {
		switch (mode) {
		case 1:
			return loadProducts();
		case 2:
			return newProduct();
		case 3:
			return saveProduct();
		default:
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private String loadProducts() {
		products.clear();
		Query query = em.createQuery("select p from Product p");
		List<Product> result = query.getResultList();
		products.addAll(result);

		return null;
	}

	private String newProduct() {
		EntityTransaction trx = em.getTransaction();
		trx.begin();

		List<Object> listForPersist = Product.createProduct(newProductName);
		for (Object obj : listForPersist) {
			em.persist(obj);
		}
		trx.commit();

		newProductName = new String();
		return null;
	}

	private String saveProduct() {
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		Product selectedProduct = this.products.get(selectedRow);
		em.persist(selectedProduct);
		trx.commit();

		rowsToUpdate.add(selectedRow);
		return null;
	}

	public void createProduct() {
		dbean.executeQuery(this, 2);
	}

	public void updateProduct() {
		dbean.executeQuery(this, 3);
	}

//	public void openProduct() {
//		Product selectedProduct = this.products.get(selectedRow);
//		session.setProduct(selectedProduct);
//		session.doOpenReleases();
//	}
	
	public String openProduct() {
		Product selectedProduct = this.products.get(selectedRow);
		session.setProduct(selectedProduct);
		return "open_release";
	}

	// GETTERS & SETTERS

	public ArrayList<Product> getProducts() {
		products.clear();
		dbean.executeQuery(this, 1);
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public String getNewProductName() {
		return newProductName;
	}

	public void setNewProductName(String newProductName) {
		this.newProductName = newProductName;
	}

	public int getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(int selectedRow) {
		this.selectedRow = selectedRow;
	}

	public Set<Integer> getRowsToUpdate() {
		return rowsToUpdate;
	}

	public void setRowsToUpdate(Set<Integer> rowsToUpdate) {
		this.rowsToUpdate = rowsToUpdate;
	}

	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
	}

/*	public Product getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}*/

}
