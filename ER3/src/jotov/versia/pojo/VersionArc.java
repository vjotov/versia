package jotov.versia.pojo;

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
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;

@Entity
// @Table(name="VERSION_GRAPH")
public class VersionArc {
	private int arcId;
	private UserProfile user;
	private ObjectVersion objectVersion;
	private ObjectVersion target;
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

	@ManyToOne(fetch = FetchType.LAZY)
	public ObjectVersion getObjectVersion() {
		return objectVersion;
	}

	public void setObjectVersion(ObjectVersion source) {
		this.objectVersion = source;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public ObjectVersion getTarget() {
		return target;
	}

	public void setTarget(ObjectVersion target) {
		this.target = target;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arc")
	public List<Cause> getCauses() {
		return causes;
	}

	public void setCause(List<Cause> causes) {
		this.causes = causes;
	}
}
