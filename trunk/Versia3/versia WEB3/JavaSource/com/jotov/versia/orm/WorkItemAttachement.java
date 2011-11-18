package com.jotov.versia.orm;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
@NamedQueries({
    @NamedQuery(name="wiaByWSnVO",
        query="SELECT COUNT(w) FROM WorkItemAttachement w WHERE w.workitem = :object AND w.workspace = :wspace")
})
public class WorkItemAttachement {
	private int WIAID;
	private VObject workitem;
	private WSpace workspace;

	public WorkItemAttachement() {
	}

	public WorkItemAttachement(WSpace workspace, VObject vo) {
		this.workitem = vo;
		this.workspace = workspace;
	}

	@Id
	@GeneratedValue(generator = "WorkItemAttSeq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "WorkItemAttSeq", sequenceName = "SQ_WORKITEMATT")
	public int getWIAID() {
		return WIAID;
	}

	public void setWIAID(int wIA) {
		WIAID = wIA;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public VObject getWorkitem() {
		return workitem;
	}

	public void setWorkitem(VObject workitem) {
		this.workitem = workitem;
	}

	@Transient
	public int getVoId() {
		return this.getWorkitem().getVObjectId();
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public WSpace getWorkspace() {
		return workspace;
	}

	public void setWorkspace(WSpace workspace) {
		this.workspace = workspace;
	}

	public static WorkItemAttachement createWorkItemAttachement(
			WSpace workspace, VObject vo) {
		WorkItemAttachement wia = new WorkItemAttachement(workspace, vo);
		workspace.addAttachedWorkItem(wia);
		return wia;
	}

}
