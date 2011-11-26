package com.jotov.versia.beans.vobj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.jotov.versia.beans.aDBbean;
import com.jotov.versia.orm.VObject;
import com.jotov.versia.orm.VObjectVersion;
import com.jotov.versia.orm.WSpace;

public class VItemShell extends aDBbean {
	private WSpace workspace;
	private List<VItem> VItems;
	private HashMap<VObjectVersion, VItem> vovVItemMap = new HashMap<VObjectVersion, VItem>();
	private boolean ready = false;

	public VItemShell(WSpace ws) {
		workspace = ws;
	}

	@Override
	public String executeQuery() {
		// construction
		HashMap<VObject, VItem> resultMAP = new HashMap<VObject, VItem>();
		HashMap<VObject, VItem> voMap = reqursiveCalculation(resultMAP,
				workspace, VisibilityEnum.LOCAL);
		VItems = new ArrayList<VItem>(voMap.values());
		populateMap();
		ready = true;
		return null;
	}
	public VItem getItemByVOV(VObjectVersion vov) {
		if (vovVItemMap.containsKey(vov))
			return vovVItemMap.get(vov);
		return null;
	}

	private void populateMap() {
		for (VItem vi:VItems){
			vovVItemMap.put(vi.getVoVersion(), vi);
		}
	}

	private HashMap<VObject, VItem> reqursiveCalculation(
			HashMap<VObject, VItem> viMAP, WSpace ws, VisibilityEnum vis) {

		if (ws.getAncestorWorkspace() == null) {
			vis = VisibilityEnum.RELEASE;
		}

		List<VObjectVersion> vovLS = ws.getLocalVersions();
		addVitem(viMAP, vovLS, vis);

		if (vis == VisibilityEnum.RELEASE)
			return viMAP;
		else
			return reqursiveCalculation(viMAP, ws.getAncestorWorkspace(),
					VisibilityEnum.PARENT);
	}

	private void addVitem(HashMap<VObject, VItem> viMAP,
			List<VObjectVersion> vovLS, VisibilityEnum vis) {
		for (VObjectVersion vov : vovLS) {
			VObject vobj = vov.getVobject();
			if (viMAP.containsKey(vobj)) {
				// add visibility to an existent vitem
				VItem vitem = viMAP.get(vobj);
				if (vov.getDeleteFlag() == 0)
					vitem.addVisibility(vis);
				else
					vitem.addVisibility(VisibilityEnum.DELETED);
			} else {
				// create new vitem and add to map
				VItem vitem = new VItem(vov, workspace, em);
				if (vov.getDeleteFlag() == 0)
					vitem.addVisibility(vis);
				else
					vitem.addVisibility(VisibilityEnum.DELETED);
				viMAP.put(vobj, vitem);
			}
		}
	}

	public WSpace getWorkspace() {
		return workspace;
	}

	public void setWorkspace(WSpace workspace) {
		this.workspace = workspace;
	}

	public List<VItem> getVItems() {
		return VItems;
	}

	public void setVItems(List<VItem> vItems) {
		VItems = vItems;
	}

	public boolean isReady() {
		return ready;
	}

}
