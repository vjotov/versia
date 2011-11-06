package com.jotov.versia.beans.vobj;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.richfaces.component.UIScrollableDataTable;
import org.richfaces.model.selection.SimpleSelection;
import com.jotov.versia.beans.UserSessionBean;
import com.jotov.versia.beans.aDBbean;
import com.jotov.versia.orm.VObjectVersion;

public class ListVobjectsBean extends aDBbean {
	private UserSessionBean session;
	private SimpleSelection selection = new SimpleSelection();
	private UIScrollableDataTable table;
	private ArrayList<VisibleItems> selectedItems;
	private VObjectVersion selectedVersion;
	private int selectedRow;

	public String Save() {
		System.out.println("Save");
		return null;
	}

	public String selectVov() {
		selectedVersion = selectedItems.get(selectedRow).getVov();
		return null;
	}

	public String takeSelection() {
		selectedItems = new ArrayList<VisibleItems>();
		Iterator<Object> iterator = getSelection().getKeys();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			table.setRowKey(key);
			if (table.isRowAvailable()) {
				getSelectedItems().add((VisibleItems) table.getRowData());
			}
		}
		VObjectVersion vov = selectedItems.get(0).getVov();
		session.setSelectedVersion(vov);
		return null;
	}

	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
	}

	public List<VisibleItems> getVisibleItems() {
		return VisibileItemsExtractor.buildVersions(session.getWorkspace());
	}

	public VisibleItems getSelectedItem() {
		return selectedItems.get(0);
	}

	public SimpleSelection getSelection() {
		return selection;
	}

	public void setSelection(SimpleSelection selection) {
		this.selection = selection;
	}

	public ArrayList<VisibleItems> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(ArrayList<VisibleItems> selectedItems) {
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

	public List<VisibleSubItem> getSubobjects() {
		if (Object.class.isInstance(this.selectedItems)
				&& this.selectedItems.size() > 0)

			return this.selectedItems.get(0).getSubobjects();
		else
			return new ArrayList<VisibleSubItem>();
	}
}
