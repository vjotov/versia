package com.jotov.versia.beans.vobj;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import com.jotov.versia.beans.UserSessionBean;
import com.jotov.versia.beans.aDBbean;
import com.jotov.versia.orm.VObject;
import com.jotov.versia.orm.VObjectVersion;

public class ObjectHistoryBean extends aDBbean {
	private UserSessionBean session;
	private List<VOVHistoryItem> vovHistoryItem = new ArrayList<VOVHistoryItem>();
	private VOVHistoryItem selectedVOVHistoryItem;
	private PrecedorItem selectedPrecedorItem;

	public ObjectHistoryBean() {
	}

	public String showWorkspace() {
		vovHistoryItem = null;
		selectedVOVHistoryItem = null;
		selectedPrecedorItem = null;
		return "show_workspace";
	}

	public UserSessionBean getSession() {
		return session;
	}

	public void setSession(UserSessionBean session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String executeQuery() {
		VObject obj = session.getSelectedVersion().getVobject();
		Query q = em.createNamedQuery("vovGetAllVersions");
		q.setParameter("obj", obj);
		List<VObjectVersion> res = (List<VObjectVersion>) q.getResultList();
		for (VObjectVersion vov : res) {
			vovHistoryItem.add(new VOVHistoryItem(vov));

		}
		return null;
	}

	public List<VOVHistoryItem> getVovHistoryItem() {
		if (vovHistoryItem == null)
			vovHistoryItem = new ArrayList<VOVHistoryItem>();
		if (vovHistoryItem.size() == 0)
			dbean.executeQuery(this);

		return vovHistoryItem;
	}

	public void setSelectedVOVHistoryItem(VOVHistoryItem selectedVOVHistoryItem) {
		this.selectedVOVHistoryItem = selectedVOVHistoryItem;
		this.selectedPrecedorItem = null;
	}

	public VOVHistoryItem getSelectedVOVHistoryItem() {
		return selectedVOVHistoryItem;
	}

	public PrecedorItem getSelectedPrecedorItem() {
		return selectedPrecedorItem;
	}

	public void setSelectedPrecedorItem(PrecedorItem selectedPrecedorItem) {
		this.selectedPrecedorItem = selectedPrecedorItem;
	}

}
