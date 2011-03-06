package jotov.versia.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Workitem {
	private int workitemId;
	private VObject vObject;
	private List<Workspace> attachedToWorkspaces = new ArrayList<Workspace>();

	public Workitem() {
		super();
	}

	public Workitem(VObject vobject) {
		super();
		this.vObject = vobject;
	}

	public Workitem(VObject vobject, Workspace workspace) {
		this(vobject);
		this.attachToWorkspace(workspace);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getWorkitemId() {
		return workitemId;
	}

	public void setWorkitemId(int workitemId) {
		this.workitemId = workitemId;
	}

	@OneToOne(fetch = FetchType.EAGER)
	public VObject getVObject() {
		return vObject;
	}

	public void setVObject(VObject vObject) {
		this.vObject = vObject;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	public List<Workspace> getAttachedToWorkspaces() {
		return attachedToWorkspaces;
	}

	public void setAttachedToWorkspaces(List<Workspace> attachedToWorkspaces) {
		this.attachedToWorkspaces = attachedToWorkspaces;
	}

	public void attachToWorkspace(Workspace workspace) {
		List<Workspace> ws_ls = this.getAttachedToWorkspaces();
		if (!ws_ls.contains(workspace)) {
			ws_ls.add(workspace);
			workspace.attachWorkitem(this);
		}
	}

	public void dettachFromWorkspace(Workspace workspace) {
		List<Workspace> ws_ls = this.getAttachedToWorkspaces();
		if (ws_ls.contains(workspace)) {
			ws_ls.remove(workspace);
			workspace.detachWorkitem(this);
		}
	}
}
