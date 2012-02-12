package com.jotov.versia.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jotov.versia.beans.vobj.VisibilityEnum;
import com.jotov.versia.orm.VComposer;
import com.jotov.versia.orm.VObject;
import com.jotov.versia.orm.VObjectVersion;
import com.jotov.versia.orm.WSpace;

public class OpenWSRegistry {

	private HashMap<WSpace, List<VObjectVersion>> visibleVersion;
	private HashMap<vwPair, List<VObjectVersion>> subObjectVersions;
	private HashMap<vwPair, VObjectVersion> superObject;
	private HashMap<vwPair, VObjectVersion> ancestorVisibleVersion;
	private HashMap<vwPair, List<VisibilityEnum>> visibilityFlags;

	private static OpenWSRegistry registry;

	private OpenWSRegistry() {
		visibleVersion = new HashMap<WSpace, List<VObjectVersion>>();
		subObjectVersions = new HashMap<vwPair, List<VObjectVersion>>();
		superObject = new HashMap<vwPair, VObjectVersion>();
		ancestorVisibleVersion = new HashMap<vwPair, VObjectVersion>();
		visibilityFlags = new HashMap<vwPair, List<VisibilityEnum>>();
	}
	
	public static OpenWSRegistry getSingleton() {
		if (registry == null)
			registry = new OpenWSRegistry();
		return registry;
	}

	public synchronized List<VObjectVersion> getLocalVersions(
			WSpace wspace) {

		if (Object.class.isInstance(wspace) && isWorkspaceLoaded(wspace)
				&& registry.visibleVersion.containsKey(wspace)) {
			return registry.visibleVersion.get(wspace);
		}
		return null;
	}

	public synchronized List<VObjectVersion> getSubObjects(WSpace ws,
			VObject superObj) {

		vwPair key = new vwPair(ws, superObj);

		if (Object.class.isInstance(ws) && Object.class.isInstance(superObj)
				&& isWorkspaceLoaded(ws)
				&& registry.subObjectVersions.containsKey(key)) {
			return registry.subObjectVersions.get(key);
		}
		return null;
	}

	public synchronized List<VObjectVersion> getSubObjects(WSpace ws,
			VObjectVersion superObjv) {
		return getSubObjects(ws, superObjv.getVobject());
	}

	public synchronized VObjectVersion getSuperObject(WSpace ws,
			VObject Obj) {

		vwPair key = new vwPair(ws, Obj);

		if (Object.class.isInstance(ws) && Object.class.isInstance(Obj)
				&& isWorkspaceLoaded(ws)
				&& registry.superObject.containsKey(key)) {
			return registry.superObject.get(key);
		}
		return null;
	}

	public synchronized VObjectVersion getSuperObject(WSpace ws,
			VObjectVersion ObjV) {
		return getSuperObject(ws, ObjV.getVobject());
	}

	public synchronized  VObjectVersion getAncestorVisibleVersion(
			WSpace ws, VObject Obj) {

		vwPair key = new vwPair(ws, Obj);

		if (Object.class.isInstance(ws) && Object.class.isInstance(Obj)
				&& isWorkspaceLoaded(ws)
				&& registry.ancestorVisibleVersion.containsKey(key)) {
			return registry.ancestorVisibleVersion.get(key);
		}
		return null;
	}

	public synchronized VObjectVersion getAncestorVisibleVersion(
			WSpace ws, VObjectVersion ObjV) {
		return getAncestorVisibleVersion(ws, ObjV.getVobject());
	}

	public synchronized  List<VisibilityEnum> getVisibilityFlag(
			WSpace ws, VObject Obj) {

		vwPair key = new vwPair(ws, Obj);

		if (Object.class.isInstance(ws) && Object.class.isInstance(Obj)
				&& isWorkspaceLoaded(ws)
				&& registry.visibilityFlags.containsKey(key)) {
			return registry.visibilityFlags.get(key);
		}
		return null;
	}

	public synchronized  List<VisibilityEnum> getVisibilityFlag(
			WSpace ws, VObjectVersion ObjV) {
		return getVisibilityFlag(ws, ObjV.getVobject());
	}

	public synchronized boolean isWorkspaceLoaded(WSpace wspace) {

		if (Object.class.isInstance(wspace)
				&& registry.visibleVersion.containsKey(wspace)) {
			return true;
		}
		return false;
	}

	public synchronized void register(WSpace wspace) {
		if (Object.class.isInstance(wspace) && !isWorkspaceLoaded(wspace)) {
			loadLocalNAncestorVisibleVersions(wspace);
			loadSubObjects(wspace);
			loadSuperObjects(wspace);

		}
	}

	public synchronized  void unregister(WSpace wspace) {
		if (Object.class.isInstance(wspace) && isWorkspaceLoaded(wspace)) {
			List<VObjectVersion> vovLs = getLocalVersions(wspace);
			for (VObjectVersion vov : vovLs) {
				vwPair key = new vwPair(wspace, vov.getVobject());
				registry.ancestorVisibleVersion.remove(key);
				registry.subObjectVersions.remove(key);
				registry.superObject.remove(key);
			}
			registry.visibleVersion.remove(wspace);
		}
	}

	public void refresh(WSpace workspace) {
		unregister(workspace);
		register(workspace);
	}

	private void loadLocalNAncestorVisibleVersions(WSpace wspace) {
		HashMap<VObject, VObjectVersion> localVersions = new HashMap<VObject, VObjectVersion>();
		HashMap<VObject, VObjectVersion> ancestorVersions = new HashMap<VObject, VObjectVersion>();
		HashMap<VObject, List<VisibilityEnum>> visibilityAccumulator = new HashMap<VObject, List<VisibilityEnum>>();
		doLoadVersions(wspace, localVersions, ancestorVersions, false,
				VisibilityEnum.L, visibilityAccumulator);

		registry.visibleVersion.put(wspace, new ArrayList<VObjectVersion>(
				localVersions.values()));

		for (Map.Entry<VObject, VObjectVersion> entry : ancestorVersions
				.entrySet()) {
			VObject obj = entry.getKey();
			VObjectVersion vov = entry.getValue();

			vwPair key = new vwPair(wspace, obj);
			registry.ancestorVisibleVersion.put(key, vov);
		}
		

		// visibility flag
		for (VObject obj : localVersions.keySet()) {
			vwPair key = new vwPair(wspace, obj);
			registry.visibilityFlags.put(key, visibilityAccumulator.get(obj));
		}
	}

	private void doLoadVersions(WSpace wspace,
			HashMap<VObject, VObjectVersion> localVersions,
			HashMap<VObject, VObjectVersion> ancestorVersions,
			boolean ancestorVersionsFlag, VisibilityEnum visibilityFlag,
			HashMap<VObject, List<VisibilityEnum>> visibilityAccumulator) {

		if (wspace.getAncestorWorkspace() == null) {
			visibilityFlag = VisibilityEnum.R;
		}

		List<VObjectVersion> ws_local = wspace.getLocalVersions();
		for (VObjectVersion vov : ws_local) {
			VObject obj = vov.getVobject();
			if (localVersions.containsKey(obj)) {
				if (ancestorVersionsFlag && !ancestorVersions.containsKey(obj)) {
					ancestorVersions.put(obj, vov);
				}
			} else {
				localVersions.put(obj, vov);
			}
			if (vov.getDeleteFlag() == 0) {
				addVisibilityFlag(obj, visibilityFlag, visibilityAccumulator);
			} else {
				addVisibilityFlag(obj, VisibilityEnum.D, visibilityAccumulator);
			}
		}
		if (ancestorVersionsFlag == false)
			ancestorVersionsFlag = true;

		if (Object.class.isInstance(wspace.getAncestorWorkspace())) {
			doLoadVersions(wspace.getAncestorWorkspace(), localVersions, ancestorVersions,
					ancestorVersionsFlag, VisibilityEnum.P,
					visibilityAccumulator);
		}
	}

	private void addVisibilityFlag(VObject obj,
			VisibilityEnum visibilityFlag,
			HashMap<VObject, List<VisibilityEnum>> visibilityAccumulator) {

		List<VisibilityEnum> visibilityList;
		if (!visibilityAccumulator.containsKey(obj)) {
			visibilityList = new ArrayList<VisibilityEnum>();
			visibilityAccumulator.put(obj, visibilityList);
		} else {
			visibilityList = visibilityAccumulator.get(obj);
		}

		if (!visibilityList.contains(visibilityList)) {
			visibilityList.add(visibilityFlag);
		}
	}

	private void loadSubObjects(WSpace wspace) {
		List<VObjectVersion> vovLs = getLocalVersions(wspace);
		for (VObjectVersion vov : vovLs) {
			List<VComposer> comLs = vov.getSubObjects();
			ArrayList<VObjectVersion> subObjectList = new ArrayList<VObjectVersion>();
			if (Object.class.isInstance(comLs) && comLs.size() != 0) {
				for (VComposer vc : comLs) {
					subObjectList.add(vc.getSubObject());
				}
			}
			vwPair key = new vwPair(wspace, vov.getVobject());
			registry.subObjectVersions.put(key, subObjectList);
		}
	}

	private void loadSuperObjects(WSpace wspace) {
		List<VObjectVersion> vovLs = getLocalVersions(wspace);
		for (VObjectVersion vov : vovLs) {
			List<VComposer> comLs = vov.getSuperObjects();
			VObjectVersion superObject = null;
			if (Object.class.isInstance(comLs) && comLs.size() != 0) {
				for (VComposer vc : comLs) {
					VObjectVersion superObj = vc.getSuperObject();
					if (vovLs.contains(superObj)) {
						superObject = superObj;
						break;
					}
				}
			}
			vwPair key = new vwPair(wspace, vov.getVobject());
			registry.superObject.put(key, superObject);
		}
	}
}
