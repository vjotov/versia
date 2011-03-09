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
public class WItem {
	private int wItemId;
	private VObject vObject;
	private List<WSpace> attWs = new ArrayList<WSpace>();

	public WItem() {
		super();
	}

	public WItem(VObject vobject) {
		super();
		this.vObject = vobject;
	}

/*	public WItem(VObject vobject, WSpace workspace) {
		this(vobject);
		this.setAttWs(workspace);
	}*/

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getWorkitemId() {
		return wItemId;
	}

	public void setWorkitemId(int workitemId) {
		this.wItemId = workitemId;
	}

	@OneToOne(fetch = FetchType.EAGER)
	public VObject getVObject() {
		return vObject;
	}

	public void setVObject(VObject vObject) {
		this.vObject = vObject;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	public List<WSpace> getAttWs() {
		return attWs;
	}

	public void setAttWs(List<WSpace> attachedToWorkspaces) {
		this.attWs = attachedToWorkspaces;
	}

	public void attachToWorkspace(WSpace workspace) {
		List<WSpace> ws_ls = this.getAttWs();
		if (!ws_ls.contains(workspace)) {
			ws_ls.add(workspace);
			workspace.attachWorkitem(this);
		}
	}

	public void dettachFromWorkspace(WSpace workspace) {
		List<WSpace> ws_ls = this.getAttWs();
		if (ws_ls.contains(workspace)) {
			ws_ls.remove(workspace);
			workspace.detachWorkitem(this);
		}
	}
}
