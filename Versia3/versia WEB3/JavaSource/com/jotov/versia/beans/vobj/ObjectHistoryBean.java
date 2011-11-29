package com.jotov.versia.beans.vobj;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.richfaces.component.UIScrollableDataTable;

import com.jotov.versia.beans.UserSessionBean;
import com.jotov.versia.beans.aDBbean;
import com.jotov.versia.orm.VObject;
import com.jotov.versia.orm.VObjectVersion;
import com.jotov.versia.orm.VersionArc;

public class ObjectHistoryBean extends aDBbean {
	private UserSessionBean session;
	private List<HistoryItem> historyItems = new ArrayList<HistoryItem>();
	private UIScrollableDataTable table;
	private HistoryItem selection = null;

	public ObjectHistoryBean() {
	}

	public String showWorkspace() {
		historyItems = null;
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
			// VObjectVersion selectedVersion = session.getSelectedVersion();
			// List<VersionArc> precedors = selectedVersion.getPrecetorsArc();
			// for (VersionArc arc : precedors) {
			// historyItems.add(new HistoryItem(arc));
			// }
		}
		return historyItems;
	}

	public UIScrollableDataTable getTable() {
		return table;
	}

	public void setTable(UIScrollableDataTable table) {
		this.table = table;
	}

	public HistoryItem getSelection() {
		return selection;
	}

	public void setSelection(HistoryItem selection) {
		this.selection = selection;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String executeQuery() {
		 VObject obj = session.getSelectedVersion().getVobject();
		Query q = em.createNamedQuery("vovGetAllVersions");
		q.setParameter("obj", obj);
		List<VObjectVersion> res = (List<VObjectVersion>) q.getResultList();
		for (VObjectVersion vov: res) {
			List<VersionArc> precedors = vov.getPrecetorsArc();
			for (VersionArc arc : precedors) {
				 historyItems.add(new HistoryItem(arc));
			}
		}
		//TODO implement
		return null;
	}

}
