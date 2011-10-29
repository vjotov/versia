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
	// private List<VisibleItems> attachedWorkitems;
	// private List<VisibleItems> availableWorkitems;
	private WSpace workspace;
	private List<SelectItem> options = new ArrayList<SelectItem>();
	private List<Integer> selected = new ArrayList<Integer>();

	public ListWorkItemsBean() {
		super();
	}

	@PostConstruct
	public void init() {
		// loading available workitems
		this.workspace = session.getWorkspace();
		ArrayList<VisibleItems> available = VisibileItemsExtractor
				.getAvailableWorkitems(this.workspace);
		options.clear();

		for (VisibleItems item : available) {
			options.add(new SelectItem(item.getObjectId(), item.getObjectName()));
		}

		// loading of attached workitems
		List<WorkItemAttachement> itemsWIA = this.workspace
				.getAttachedWorkItems();
		selected.clear();
		for (WorkItemAttachement item : itemsWIA) {
			selected.add(item.getVoId());
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
		// preparing map of void->workitem attachment
		for (WorkItemAttachement itemWIA : itemsWIA) {
			attached.put(itemWIA.getVoId(), itemWIA);
		}
		
		// This array will contain only the remained VoIDs after WIA removal
		ArrayList<Integer> attachedVoID = new ArrayList<Integer>();
		
		em.getTransaction().begin();
		//removing missing workitem attachments
		for (Integer key : attached.keySet()) {
			if (!selected.contains(key)) {
				RemoveAttachment(attached.get(key));
			} else
				attachedVoID.add(key);
		}
		//adding new workitem attachments
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
		// TODO Auto-generated method stub
		VObject vo = em.find(VObject.class, selectedItem);
		WorkItemAttachement wia = WorkItemAttachement
				.createWorkItemAttachement(workspace, vo);
		em.persist(wia);
		em.persist(workspace);
	}

	public String Update() {
		System.out.println("ListWorkItemsBean/Update()");
		dbean.executeQuery(this, 1);
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

}
