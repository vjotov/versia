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
//import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Release {
	private int releaseId;
	private String releaseName;
	private Product product;
	private Workspace masterWorkspace;
	private List<Workspace> workspaces = new ArrayList<Workspace>();

	public Release() {
		super();
	}

	public Release(String releaseName) {
		super();
		this.releaseName = releaseName;
		Workspace masterWorkspace = new Workspace("Master workspace");
		masterWorkspace.setRelease(this);
		this.masterWorkspace = masterWorkspace;

		// VObject generalWorkitemObject = new VObject(masterWorkspace,
		// "General Workitem", "General Workitem Datum");
		// Workitem generalWorkitem = new Workitem(generalWorkitemObject,
		// masterWorkspace);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH })
	public Workspace getMasterWorkspace() {
		return masterWorkspace;
	}

	public void setMasterWorkspace(Workspace masterWorkspace) {
		this.masterWorkspace = masterWorkspace;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "release", cascade = {
			CascadeType.PERSIST, CascadeType.REFRESH })
	public List<Workspace> getWorkspaces() {
		return workspaces;
	}

	public void setWorkspaces(List<Workspace> workspaces) {
		this.workspaces = workspaces;
	}

	@Override
	public String toString() {
		return "Release (" + this.getReleaseId() + ") " + this.getReleaseName();
	}

}
