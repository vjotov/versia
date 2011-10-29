package com.jotov.versia.orm;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import org.apache.openjpa.persistence.ExternalValues;
import org.apache.openjpa.persistence.Type;

@Entity
public class VObject {
	private int VObjectId;
	private boolean WorkItem = false;
	private List<VObjectVersion> versions = new ArrayList<VObjectVersion>();
	private List<WorkItemAttachement> attachedAsWorkItem = new ArrayList<WorkItemAttachement>();

	public VObject() {
		super();
	}

	public static VObject createVObject(String name, String datum,
			WSpace workspace, List<VObjectVersion> precedors) {

		VObject newObject = new VObject();
		VObjectVersion.createVersion(newObject, name, datum, workspace,
				precedors, null);
		return newObject;
	}

	public VObject(WSpace workspace, String name, String datum) {
		super();
		VObjectVersion firstVersion = new VObjectVersion(this, 1, name, datum,
				workspace);
		this.versions.add(firstVersion);
	}

	@Id
	@GeneratedValue(generator = "ObjectSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ObjectSeq", sequenceName = "SQ_VOJBECT")
	public int getVObjectId() {
		return VObjectId;
	}

	public void setVObjectId(int vObjectId) {
		VObjectId = vObjectId;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "vobject", cascade = {
			CascadeType.PERSIST, CascadeType.REFRESH })
	public List<VObjectVersion> getVersions() {
		return versions;
	}

	public void setVersions(List<VObjectVersion> versions) {
		this.versions = versions;
	}

	public void addVersion(VObjectVersion version) {
		this.versions.add(version);
	}

	@ExternalValues({"true=TRUE", "false=FALSE"}) 
	@Type(String.class)
	public boolean isWorkItem() {
		return WorkItem;
	}

	public void setWorkItem(boolean worItem) {
		WorkItem = worItem;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "workitem")
	public List<WorkItemAttachement> getAttachedAsWorkItem() {
		return attachedAsWorkItem;
	}

	public void setAttachedAsWorkItem(
			List<WorkItemAttachement> attachedAsWorkItem) {
		this.attachedAsWorkItem = attachedAsWorkItem;
	}

	public void addAtachment(WorkItemAttachement workItemAttachement) {
		this.getAttachedAsWorkItem().add(workItemAttachement);
	}

}
