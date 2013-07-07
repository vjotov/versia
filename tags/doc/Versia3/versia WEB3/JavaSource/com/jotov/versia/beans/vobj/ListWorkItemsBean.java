package com.jotov.versia.beans.vobj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import com.jotov.versia.beans.UserSessionBean;
import com.jotov.versia.beans.aDBbean;
import com.jotov.versia.orm.VObject;
import com.jotov.versia.orm.WSpace;
import com.jotov.versia.orm.WorkItemAttachement;

public class ListWorkItemsBean extends aDBbean {
	private UserSessionBean session;
	private WSpace workspace;
	private List<SelectItem> options = new ArrayList<SelectItem>();
	private List<Integer> selected = new ArrayList<Integer>();

	public ListWorkItemsBean() {
		super();
	}

	@PostConstruct
	public void init() {
		// loading available workitems
		if (session.getVItemShell() == null
				&& session.getVItemShell().isReady() == false) {
			dbean.executeQuery(session.getVItemShell());
		}
		this.workspace = session.getWorkspace();

		List<VItem> vitems = session.getVItemShell().getVItems();

		options.clear();
		selected.clear();

		for (VItem vi : vitems) {
			if (vi.isWorkitem())
				if (!vi.isDeleted())
					options.add(new SelectItem(vi.getVoID(), vi.getVoName()));
			if (vi.isAttachedWI())
				selected.add(vi.getVoID());

		}
	}

	@Override
	public String executeQuery(int mode) {
		switch (mode) {
		case 1:
			SaveWorkItemConfiguration();
			break;
		}
		return null;
	};

	private void SaveWorkItemConfiguration() {
		List<WorkItemAttachement> itemsWIA = workspace.getAttachedWorkItems();
		HashMap<Integer, WorkItemAttachement> attached = new HashMap<Integer, WorkItemAttachement>();
		// preparing map of voID->workitem attachment
		for (WorkItemAttachement itemWIA : itemsWIA) {
			attached.put(itemWIA.getVoId(), itemWIA);
		}

		// This array will contain only the remained VoIDs after WIA removal
		ArrayList<Integer> attachedVoID = new ArrayList<Integer>();

		em.getTransaction().begin();
		// removing missing workitem attachments
		for (Integer key : attached.keySet()) {
			if (!selected.contains(key)) {
				RemoveAttachment(attached.get(key));
			} else
				attachedVoID.add(key);
		}
		// adding new workitem attachments
		for (int selectedItem : selected) {
			if (!attachedVoID.contains(selectedItem)) {
				CreateAttachment(selectedItem);
			}
		}

		em.getTransaction().commit();
	}

	private void RemoveAttachment(WorkItemAttachement itemWIA) {
		workspace.removeWorkItemAttachement(itemWIA);
		em.remove(itemWIA);
		em.persist(workspace);
		em.flush();
	}

	private void CreateAttachment(Integer selectedItem) {
		VObject vo = em.find(VObject.class, selectedItem);
		WorkItemAttachement wia = WorkItemAttachement
				.createWorkItemAttachement(workspace, vo);
		em.persist(wia);
		em.persist(workspace);
	}

	public String Update() {
		dbean.executeQuery(this, 1);
		session.getApp().NotifyCleanAll();
		return null;
	}

	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
	}

	public List<SelectItem> getOptions() {
		if (this.workspace != session.getWorkspace()) {
			this.init();
		}
		return options;
	}

	public void setOptions(List<SelectItem> options) {
		this.options = options;
	}

	public List<Integer> getSelected() {
		if (this.workspace != session.getWorkspace()) {
			this.init();
		}
		return selected;
	}

	public void setSelected(List<Integer> selected) {
		this.selected = selected;
	}

	public String ManageWorkitem() {
		return "manage_workitem";
	}

	public String ManageWorkspace() {
		return "manage_workspace";
	}
}
