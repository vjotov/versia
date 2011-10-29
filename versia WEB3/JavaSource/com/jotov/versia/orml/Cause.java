package com.jotov.versia.orml;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Cause {
	private int causeId;
	private VersionArc arc;
	private VObjectVersion cause;

	public Cause() {
		super();
	}

	@Id
	public int getCauseId() {
		return causeId;
	}

	public void setCauseId(int causeId) {
		this.causeId = causeId;
	}

	@ManyToOne(cascade = CascadeType.PERSIST)
	public VersionArc getArc() {
		return arc;
	}

	public void setArc(VersionArc arc) {
		this.arc = arc;
	}

	@ManyToOne(cascade = CascadeType.PERSIST)
	public VObjectVersion getCause() {
		return cause;
	}

	public void setCause(VObjectVersion initiator) {
		this.cause = initiator;
	}
}
