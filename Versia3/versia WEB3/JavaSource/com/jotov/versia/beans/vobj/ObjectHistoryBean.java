package com.jotov.versia.beans.vobj;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.richfaces.component.UIScrollableDataTable;
import org.richfaces.model.selection.SimpleSelection;

import com.jotov.versia.beans.UserSessionBean;
import com.jotov.versia.beans.aDBbean;
import com.jotov.versia.orm.VObject;
import com.jotov.versia.orm.VObjectVersion;
import com.jotov.versia.orm.VersionArc;

public class ObjectHistoryBean extends aDBbean {
	private UserSessionBean session;
	private List<HistoryItem> historyItems = new ArrayList<HistoryItem>();
	private ArrayList<HistoryItem> selectedItems;
	private UIScrollableDataTable table;
	private SimpleSelection selection = new SimpleSelection();
	private HistoryItem selectedItem;
	private int selectedRow;

	public ObjectHistoryBean() {
	}

	public String takeSelection() {
		selectedItems = new ArrayList<HistoryItem>();
		Iterator<Object> iterator = getSelection().getKeys();
		while (iterator.hasNext()) {
			Object key = iterator.next();
			table.setRowKey(key);
			if (table.isRowAvailable()) {
				getSelectedItems().add((HistoryItem) table.getRowData());
			}
		}
		selectedItem = selectedItems.get(0);
		return null;
	}

	public String showWorkspace() {
		historyItems = null;
		selectedItem = null;
		return "show_workspace";
	}

	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
	}

	public List<HistoryItem> getHistoryItems() {
		if (historyItems == null || historyItems.size() == 0) {
			historyItems = new ArrayList<HistoryItem>();
			dbean.executeQuery(this);
		}
		return historyItems;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String executeQuery() {
		VObject obj = session.getSelectedVersion().getVobject();
		Query q = em.createNamedQuery("vovGetAllVersions");
		q.setParameter("obj", obj);
		List<VObjectVersion> res = (List<VObjectVersion>) q.getResultList();
		for (VObjectVersion vov : res) {
			List<VersionArc> precedors = vov.getPrecetorsArc();
			for (VersionArc arc : precedors) {
				historyItems.add(new HistoryItem(arc));
			}
		}
		// TODO implement
		return null;
	}

	public UIScrollableDataTable getTable() {
		return table;
	}

	public void setTable(UIScrollableDataTable table) {
		this.table = table;
	}

	public SimpleSelection getSelection() {
		return selection;
	}

	public void setSelection(SimpleSelection selection) {
		this.selection = selection;
	}

	public int getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(int selectedRow) {
		this.selectedRow = selectedRow;
	}

	public HistoryItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(HistoryItem selectedItem) {
		this.selectedItem = selectedItem;
	}

	public ArrayList<HistoryItem> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(ArrayList<HistoryItem> selectedItems) {
		this.selectedItems = selectedItems;
	}

}
