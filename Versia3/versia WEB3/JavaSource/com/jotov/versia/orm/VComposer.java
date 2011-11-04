package com.jotov.versia.orm;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class VComposer {
	private VObjectVersion superObject;
	private VObjectVersion subObject;

	public VComposer() {

	}
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	public VObjectVersion getSuperObject() {
		return superObject;
	}

	public void setSuperObject(VObjectVersion superObject) {
		this.superObject = superObject;
	}
	
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	public VObjectVersion getSubObject() {
		return subObject;
	}

	public void setSubObject(VObjectVersion subObject) {
		this.subObject = subObject;
	}
	
	
}
