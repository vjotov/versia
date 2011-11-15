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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;


@Entity
public class Release {
	private int releaseId;
	private String releaseName;
	private Product product;
	private List<WSpace> workspaces = new ArrayList<WSpace>();
	private WSpace masterWorkspace;

	public static List<Object> createRelease(Product product,
			String releaseName, List<Release> sourceReleases, EntityManager em) {
		List<Object> result = new ArrayList<Object>();

		Release newRelease = new Release(product, releaseName);
		result.add(newRelease);

		result.addAll(ReleaseArc.createArcs(sourceReleases, newRelease));
		WSpace master = WSpace.createMasterWorkspace(newRelease, em);
		newRelease.setMasterWorkspace(master);
		return result;
	}
	@OneToOne(fetch=FetchType.LAZY, mappedBy="releaseMaster", cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
	public WSpace getMasterWorkspace(){
		return masterWorkspace;
	}
	public void setMasterWorkspace(WSpace master) {
		this.masterWorkspace = master;
	}

	public Release() {
		super();
	}

	private Release(Product product, String releaseName) {
		super();
		this.product = product;
		this.releaseName = releaseName;
	}

	@Id
	@GeneratedValue(generator="ReleaseSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ReleaseSeq", sequenceName="SQ_RELEASE")
	public int getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(int releaseId) {
		this.releaseId = releaseId;
	}

	public String getReleaseName() {
		return releaseName;
	}

	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "(" + this.getReleaseId() + ") " + this.getReleaseName();
	}
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REFRESH}, fetch=FetchType.LAZY, mappedBy="release")
	public List<WSpace> getWorkspaces() {
		return workspaces;
	}

	public void setWorkspaces(List<WSpace> workspaces) {
		this.workspaces = workspaces;
	}

}
