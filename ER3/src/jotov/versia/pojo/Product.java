package jotov.versia.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PRODUCT")
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
	@Column(name="PRODUCT_ID")
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	@Column(name="PRODUCT_NAME", nullable=false)
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	@OneToMany(mappedBy="product", cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	public List<Release> getRealeses() {
		return realeses;
	}
	public void setRealeses(List<Release> realeses) {
		this.realeses = realeses;
	}
	@Override
	public String toString() {
		return "Product ("+this.getProductId()+") "+this.getProjectName();
	}
	
}
