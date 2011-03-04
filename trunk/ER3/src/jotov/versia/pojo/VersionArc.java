package jotov.versia.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinColumns;
//import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="VERSION_GRAPH")
public class VersionArc {
	private int arcId;
	private User user;
	private ObjectVersion source;
	private ObjectVersion target;
	private List<ObjectVersion> causes = new ArrayList<ObjectVersion>(); //TODO: relation of initiators
	

	public int getArcId() {
		return arcId;
	}
	public void setArcId(int arcId) {
		this.arcId = arcId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public ObjectVersion getSource() {
		return source;
	}
	public void setSource(ObjectVersion source) {
		this.source = source;
	}
	public ObjectVersion getTarget() {
		return target;
	}
	public void setTarget(ObjectVersion target) {
		this.target = target;
	}
	@ManyToMany
//	@JoinTable(name="CAUSE", 
//			joinColumns=@JoinColumn(name="VERSION_ARC_ID", referencedColumnName="ARC_ID"),
//			inverseJoinColumns=@JoinColumns(value={
//				@JoinColumn(name="CAUSE_VO_ID", referencedColumnName="VO_ID"),
//				@JoinColumn(name="CAUSE_VP_ID", referencedColumnName="VP_ID")})
//		)
	public List<ObjectVersion> getCauses() {
		return causes;
	}
	public void setCause(List<ObjectVersion> causes) {
		this.causes = causes;
	}	
}
