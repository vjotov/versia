package com.jotov.versia.orm;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import com.jotov.versia.beans.UserSessionBean;
import com.jotov.versia.beans.vobj.VItem;


@Entity
public class Cause {
	private int causeId;
	private VersionArc arc;
	private VObjectVersion cause;

	public static List<Cause> createCauses(VersionArc nva, WSpace workspace,
			UserSessionBean session) {

		List<WorkItemAttachement> wiaList = workspace.getAttachedWorkItems();
		if (wiaList.size() == 0)
			return null;

		List<VItem> vitems = session.getVItemShell().getVItems();
		ArrayList<Cause> causeList = new ArrayList<Cause>();
		for (VItem vi : vitems) {
			if (vi.isWorkitem() && vi.isAttachedWI())
				causeList.add(new Cause(nva, vi.getVoVersion()));
		}

		return causeList;
	}

	public Cause() {
		super();
	}

	public Cause(VersionArc arc, VObjectVersion cause) {
		super();
		this.arc = arc;
		this.cause = cause;
	}

	@Id
	@GeneratedValue(generator = "CauseSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "CauseSeq", sequenceName = "SQ_CAUSE")
	public int getCauseId() {
		return causeId;
	}

	public void setCauseId(int causeId) {
		this.causeId = causeId;
	}

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	public VersionArc getArc() {
		return arc;
	}

	public void setArc(VersionArc arc) {
		this.arc = arc;
	}

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	public VObjectVersion getCause() {
		return cause;
	}

	public void setCause(VObjectVersion initiator) {
		this.cause = initiator;
	}

}
