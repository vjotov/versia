package com.jotov.versia.orml;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class VOComposer {
	private VObjectVersion superObject;
	private List<VObjectVersion> subObjects = new ArrayList<VObjectVersion>();

	public VOComposer() {
		super();
	}

	@Id
	@OneToOne
	public VObjectVersion getSuperObject() {
		return superObject;
	}

	public void setSuperObject(VObjectVersion superObject) {
		this.superObject = superObject;
	}

	@OneToMany(fetch = FetchType.EAGER)
	public List<VObjectVersion> getSubObjects() {
		return subObjects;
	}

	public void setSubObjects(List<VObjectVersion> subObjects) {
		this.subObjects = subObjects;
	}

	public boolean add(VObjectVersion e) {
		return getSubObjects().add(e);
	}

	public boolean remove(Object o) {
		return getSubObjects().remove(o);
	}

	public int size() {
		return getSubObjects().size();
	}

	public boolean contains(VObjectVersion o) {
		return getSubObjects().contains(o);
	}

}
