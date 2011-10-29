package com.jotov.versia.beans.vobj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jotov.versia.orm.VObject;
import com.jotov.versia.orm.VObjectVersion;
import com.jotov.versia.orm.WSpace;

public class VisibileItemsExtractor {
	// TODO optimisation of Visibility load

	static public ArrayList<VisibleItems> buildVersions(WSpace workspace) {
		HashMap<VObject, VisibleItems> voMap = constructVisibility(workspace,
				new HashMap<VObject, VisibleItems>(), VisibilityEnum.LOCAL);
		return new ArrayList<VisibleItems>(voMap.values());
	}

	static public ArrayList<VisibleItems> getAvailableWorkitems(WSpace workspace) {
		
		ArrayList<VisibleItems> voMap = buildVersions(workspace);
		ArrayList<VisibleItems> forRemove = new ArrayList<VisibleItems>();
		
		// leaving only potential workitems
		for (VisibleItems item : voMap) {
			if (!item.getVo().isWorkItem())
				forRemove.add(item);
		}
		voMap.removeAll(forRemove);
		return voMap;
	}

	static private HashMap<VObject, VisibleItems> constructVisibility(
			WSpace workspace, HashMap<VObject, VisibleItems> map,
			VisibilityEnum ve) {
		if (workspace.getAncestorWorkspace() == null)
			ve = VisibilityEnum.RELEASE;

		ArrayList<VObjectVersion> local = (ArrayList<VObjectVersion>) workspace
				.getLocalVersions();
		addObjects(map, local, ve);

		if (ve != VisibilityEnum.RELEASE)
			return constructVisibility(workspace.getAncestorWorkspace(), map,
					VisibilityEnum.PARENT);
		else
			return map;
	}

	private static void addObjects(HashMap<VObject, VisibleItems> map,
			ArrayList<VObjectVersion> local, VisibilityEnum ve) {
		// TODO Auto-generated method stub
		for (VObjectVersion vov : local) {
			VObject vobj = vov.getVobject();
			if (map.containsKey(vobj)) {
				VisibleItems vi = map.get(vobj);
				vi.addVvector(ve);
			} else {
				VisibleItems vi = new VisibleItems(vobj, vov, ve);
				map.put(vobj, vi);
			}
		}

	}

	public static List<VisibleItems> buildWorkItems(WSpace workspace) {
		buildVersions(workspace);
		return null;
	}
}
