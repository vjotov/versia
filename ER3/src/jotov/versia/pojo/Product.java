package jotov.versia.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Product {
	private int productId;
	private String projectName;
	private List<Release> realeses = new ArrayList<Release>();

	public Product() {
		super();
	}

	public Product(String projectName) {
		super();
		this.projectName = projectName;
		Release zeroRelease = new Release("Zero Release");
		this.realeses.add(zeroRelease);
		zeroRelease.setProduct(this);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
		return "Product (" + this.getProductId() + ") " + this.getProjectName();
	}

}
