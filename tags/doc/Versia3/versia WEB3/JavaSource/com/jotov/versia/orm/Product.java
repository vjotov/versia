package com.jotov.versia.orm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Product {
	private int productId;
	private String productName;

	private List<Release> realeses = new ArrayList<Release>();

	public static List<Object> createProduct(String productName, EntityManager em) {
		ArrayList<Object> result = new ArrayList<Object>();

		Product newProduct = new Product(productName);
		result.add(newProduct);
		
		result.addAll(Release.createRelease(newProduct, "Zero Release", null, em));
		return result;
	}

	public Product() {
		super();
	}

	private Product(String productName) {
		super();
		this.productName = productName;
		/*
		 * Release zeroRelease = new Release("Zero Release");
		 * this.realeses.add(zeroRelease); zeroRelease.setProduct(this);
		 */
	}

	@Id
	@GeneratedValue(generator="ProductSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ProductSeq", sequenceName="SQ_PRODUCT")
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@OneToMany(mappedBy = "product", cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	public List<Release> getRealeses() {
		return realeses;
	}

	public void setRealeses(List<Release> realeses) {
		this.realeses = realeses;
	}

	@Override
	public String toString() {
		return "("+this.getProductId() + ") " + this.getProductName();
	}
	// public void addRelease(Release release) {
	// List<Release> releases = this.getRealeses();
	// releases.add(release);
	// }

	public void addRelease(Release newRelease) {
		realeses.add(newRelease);
		
	}

}
