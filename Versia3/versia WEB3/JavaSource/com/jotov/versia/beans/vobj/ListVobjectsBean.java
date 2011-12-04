package com.jotov.versia.beans.vobj;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.richfaces.component.UIScrollableDataTable;
import org.richfaces.model.selection.SimpleSelection;

import com.jotov.versia.beans.EditVObjectBean;
import com.jotov.versia.beans.UserSessionBean;
import com.jotov.versia.beans.aDBbean;
import com.jotov.versia.orm.VObjectVersion;

public class ListVobjectsBean extends aDBbean {
	private UserSessionBean session;
	private SimpleSelection selection = new SimpleSelection();
	private UIScrollableDataTable table;
	private ArrayList<VItem> selectedItems;
	private VObjectVersion selectedVersion;
	private int selectedRow;

	private EditVObjectBean evoBean;

	public String Save() {
		System.out.println("Save");
		return null;
	}

	public String selectVov() {
		selectedVersion = selectedItems.get(selectedRow).getVoVersion();
		return null;
	}

	public String takeSelection() {
		selectedItems = new ArrayList<VItem>();
		Iterator<Object> iterator = getSelection().getKeys();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			table.setRowKey(key);
			if (table.isRowAvailable()) {
				getSelectedItems().add((VItem) table.getRowData());
			}
		}
		VObjectVersion vov = selectedItems.get(0).getVoVersion();
		session.setSelectedVersion(vov);
		return null;
	}

	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
	}

	public List<VItem> getVisibleItems() {
		// return VisibileItemsExtractor.buildVersions(session.getWorkspace());
		if (session.getVItemShell().isReady() == false) {
			dbean.executeQuery(session.getVItemShell());
		}
		return session.getVItemShell().getVItems();
	}


	public VItem getSelectedItem() {
		return selectedItems.get(0);
	}

	public SimpleSelection getSelection() {
		return selection;
	}

	public void setSelection(SimpleSelection selection) {
		this.selection = selection;
	}

	public ArrayList<VItem> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(ArrayList<VItem> selectedItems) {
		this.selectedItems = selectedItems;
	}

	public UIScrollableDataTable getTable() {
		return table;
	}

	public void setTable(UIScrollableDataTable table) {
		this.table = table;
	}

	public VObjectVersion getSelectedVersion() {
		return selectedVersion;
	}

	public void setSelectedVersion(VObjectVersion selectedVersion) {
		this.selectedVersion = selectedVersion;
	}

	public int getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(int selectedRow) {
		this.selectedRow = selectedRow;
	}

	public String showHistory() {
		return "show_history";
	}

	public String showDistribution() {
		return "show_distribution";
	}

	public List<VSubItem> getSubobjects() {
		if (Object.class.isInstance(this.selectedItems)
				&& this.selectedItems.size() > 0)

			return this.selectedItems.get(0).getSubObjects();
		else
			return new ArrayList<VSubItem>();
	}

	public EditVObjectBean getEvoBean() {
		return evoBean;
	}

	public void setEvoBean(EditVObjectBean evoBean) {
		this.evoBean = evoBean;
	}

}
