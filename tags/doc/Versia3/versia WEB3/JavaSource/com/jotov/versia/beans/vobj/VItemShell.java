package com.jotov.versia.beans.vobj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.jotov.versia.beans.ApplicationBean;
import com.jotov.versia.beans.aDBbean;
import com.jotov.versia.orm.VObjectVersion;
import com.jotov.versia.orm.WSpace;

public class VItemShell extends aDBbean {
	private WSpace workspace;
	private List<VItem> VItems;
	private HashMap<VObjectVersion, VItem> vovVItemMap = new HashMap<VObjectVersion, VItem>();
	private boolean ready = false;
	private ApplicationBean application;

	public VItemShell(WSpace ws, ApplicationBean app) {
		workspace = ws;
		application = app;
	}

	@Override
	public String executeQuery() {
		// construction
		VItems = new ArrayList<VItem>();

		List<VObjectVersion> visibleLocalVOVs = application.getOpenWsRegistry().getLocalVersions(workspace);
		for(VObjectVersion vov: visibleLocalVOVs) {
			List<VisibilityEnum> visibilityFlags = application.getOpenWsRegistry().getVisibilityFlag(workspace, vov);
			VItem vitem = new VItem(vov, workspace, em, application);
			vitem.setVisibility(visibilityFlags);
			VItems.add(vitem);
		}
		
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
