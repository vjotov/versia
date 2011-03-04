package jotov.versia.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="WORKITEM")
public class Workitem {
	private int workitemId;
	private VObject vObject;
	private List<Workspace> attachedToWorkspaces = new ArrayList<Workspace>();
	
	public Workitem(VObject vobject) {
		super();
		this.vObject = vobject;
	}
	public Workitem(VObject vobject, Workspace workspace) {
		this(vobject);
		this.attachToWorkspace(workspace);		
	}
	@Id
	@Column(name="WI_ID")
	public int getWorkitemId() {
		return workitemId;
	}
	public void setWorkitemId(int workitemId) {
		this.workitemId = workitemId;
	}
	
	@OneToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="VO_ID")
	public VObject getvObject() {
		return vObject;
	}
	public void setvObject(VObject vObject) {
		this.vObject = vObject;
	}
	@ManyToMany (mappedBy="workitems", cascade=CascadeType.PERSIST) // TODO
//	@JoinTable(name="WS_WI_SELECTOR",
//        joinColumns=@JoinColumn(name="WI_ID", referencedColumnName="WI_ID"),
//        inverseJoinColumns=@JoinColumn(name="WS_ID", referencedColumnName="WS_ID"))
	public List<Workspace> getAttachedToWorkspaces() {
		return attachedToWorkspaces;
	}
	public void setAttachedToWorkspaces(List<Workspace> attachedToWorkspaces) {
		this.attachedToWorkspaces = attachedToWorkspaces;
	}
	public void attachToWorkspace(Workspace workspace){
		List<Workspace> ws_ls = this.getAttachedToWorkspaces();
		if(!ws_ls.contains(workspace))
			ws_ls.add(workspace);
	}
	public void dettachToWorkspace(Workspace workspace){
		List<Workspace> ws_ls = this.getAttachedToWorkspaces();
		if(ws_ls.contains(workspace))
			ws_ls.remove(workspace);
	}	
}
