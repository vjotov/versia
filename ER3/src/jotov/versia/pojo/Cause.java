package jotov.versia.pojo;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="CAUSE")
public class Cause {
	private VersionArc arc;
	private ObjectVersion initiator;
	
	
	public VersionArc getArc() {
		return arc;
	}
	public void setArc(VersionArc arc) {
		this.arc = arc;
	}
	public ObjectVersion getInitiator() {
		return initiator;
	}
	public void setInitiator(ObjectVersion initiator) {
		this.initiator = initiator;
	}	
}
