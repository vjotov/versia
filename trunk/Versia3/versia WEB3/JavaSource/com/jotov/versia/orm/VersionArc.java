package com.jotov.versia.orm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.jotov.versia.beans.UserSessionBean;

@Entity
public class VersionArc {
	private int arcId;
	private UserProfile user;
	private VObjectVersion precedor;
	private VObjectVersion target;
	private List<Cause> causes = new ArrayList<Cause>();
	private Date arcDate = new Date();
	
	public VersionArc() {
		super();
	}
	
	public static VersionArc createArcs(VObjectVersion target,
			VObjectVersion precedor, WSpace workspace, UserSessionBean session) {
		
		VersionArc nva = new VersionArc(session.getUserProfile(),precedor,target);
		List<Cause> causes = Cause.createCauses(nva,workspace, session);
		nva.setCauses(causes);
		return nva;
	}

	public VersionArc(UserProfile user, VObjectVersion precedor,
			VObjectVersion target) {
		super();
		this.user = user;
		this.precedor = precedor;
		this.target = target;
	}

	@Id
	@GeneratedValue(generator="ArcSeq", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ArcSeq", sequenceName="SQ_ARC")
	public int getArcId() {
		return arcId;
	}

	public void setArcId(int arcId) {
		this.arcId = arcId;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}

	@ManyToOne(optional=true, fetch=FetchType.LAZY, cascade={CascadeType.PERSIST,CascadeType.REFRESH})
	public VObjectVersion getPrecedor() {
		return precedor;
	}

	public void setPrecedor(VObjectVersion precedor) {
		this.precedor = precedor;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST,CascadeType.REFRESH})
	public VObjectVersion getTarget() {
		return target;
	}

	public void setTarget(VObjectVersion target) {
		this.target = target;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arc", cascade={CascadeType.PERSIST,CascadeType.REFRESH})
	public List<Cause> getCauses() {
		return causes;
	}

	public void setCauses(List<Cause> causes) {
		this.causes = causes;
	}

	public Date getArcDate() {
		return arcDate;
	}

	public void setArcDate(Date arcDate) {
		this.arcDate = arcDate;
	}


}
