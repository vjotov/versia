package jotov.versia.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RELEASES")
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
	@Column(name = "RELEASE_ID")
	public int getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(int releaseId) {
		this.releaseId = releaseId;
	}

	@Column(name = "RELEASE_NAME", length = 30, nullable = false)
	public String getReleaseName() {
		return releaseName;
	}

	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "MASTER_WORKSPACE")
	public Workspace getMasterWorkspace() {
		return masterWorkspace;
	}

	public void setMasterWorkspace(Workspace masterWorkspace) {
		this.masterWorkspace = masterWorkspace;
	}

	@OneToMany(fetch = FetchType.LAZY)
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
