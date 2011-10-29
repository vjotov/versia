package com.jotov.versia.orml;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinColumns;
//import javax.persistence.JoinTable;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
//import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class VersionArc {
	private int arcId;
	private UserProfile user;
	private VObjectVersion precedor;
	private VObjectVersion target;
	private List<Cause> causes = new ArrayList<Cause>(); // TODO: relation of
															// initiators

	public VersionArc() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getArcId() {
		return arcId;
	}

	public void setArcId(int arcId) {
		this.arcId = arcId;
	}

	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}

	@ManyToOne
	public VObjectVersion getPrecedor() {
		return precedor;
	}

	public void setPrecedor(VObjectVersion precedor) {
		this.precedor = precedor;
	}

	@ManyToOne
	public VObjectVersion getTarget() {
		return target;
	}

	public void setTarget(VObjectVersion target) {
		this.target = target;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arc")
	public List<Cause> getCauses() {
		return causes;
	}

	public void setCauses(List<Cause> causes) {
		this.causes = causes;
	}
}
