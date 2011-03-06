package jotov.versia.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Cause {
	private int causeId;
	private VersionArc arc;
	private ObjectVersion initiator;

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

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	public VersionArc getArc() {
		return arc;
	}

	public void setArc(VersionArc arc) {
		this.arc = arc;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	public ObjectVersion getInitiator() {
		return initiator;
	}

	public void setInitiator(ObjectVersion initiator) {
		this.initiator = initiator;
	}
}
