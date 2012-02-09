package com.jotov.versia.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.jotov.versia.orm.VComposer;
import com.jotov.versia.orm.VObject;
import com.jotov.versia.orm.VObjectVersion;
import com.jotov.versia.orm.WSpace;

public class OpenWSRegistry {

	private HashMap<WSpace, List<VObjectVersion>> visibleVersion;
	private HashMap<vwPair, List<VObjectVersion>> subObjectVersions;
	private HashMap<vwPair, VObjectVersion> superObject;
	private HashMap<vwPair, VObjectVersion> ancestorVisibleVersion;

	private static OpenWSRegistry registry;

	private OpenWSRegistry() {
		visibleVersion = new HashMap<WSpace, List<VObjectVersion>>();
		subObjectVersions = new HashMap<vwPair, List<VObjectVersion>>();
		superObject = new HashMap<vwPair, VObjectVersion>();
		ancestorVisibleVersion = new HashMap<vwPair, VObjectVersion>();
	}

	public synchronized static List<VObjectVersion> getLocalVersions(
			WSpace wspace) {
		if (registry == null)
			registry = new OpenWSRegistry();

		if (Object.class.isInstance(wspace) && isWorkspaceLoaded(wspace)
				&& registry.visibleVersion.containsKey(wspace)) {
			return registry.visibleVersion.get(wspace);
		}
		return null;
	}

	public synchronized static List<VObjectVersion> getSubObjects(WSpace ws,
			VObject superObj) {
		if (registry == null)
			registry = new OpenWSRegistry();

		vwPair key = new vwPair(ws, superObj);

		if (Object.class.isInstance(ws) && Object.class.isInstance(superObj)
				&& isWorkspaceLoaded(ws)
				&& registry.subObjectVersions.containsKey(key)) {
			return registry.subObjectVersions.get(key);
		}
		return null;
	}

	public synchronized static List<VObjectVersion> getSubObjects(WSpace ws,
			VObjectVersion superObjv) {
		return getSubObjects(ws, superObjv.getVobject());
	}

	public synchronized static VObjectVersion getSuperObject(WSpace ws,
			VObject Obj) {
		if (registry == null)
			registry = new OpenWSRegistry();

		vwPair key = new vwPair(ws, Obj);

		if (Object.class.isInstance(ws) && Object.class.isInstance(Obj)
				&& isWorkspaceLoaded(ws)
				&& registry.superObject.containsKey(key)) {
			return registry.superObject.get(key);
		}
		return null;
	}

	public synchronized static VObjectVersion getSuperObject(WSpace ws,
			VObjectVersion ObjV) {
		return getSuperObject(ws, ObjV.getVobject());
	}

	public synchronized static VObjectVersion getAncestorVisibleVersion(
			WSpace ws, VObject Obj) {
		if (registry == null)
			registry = new OpenWSRegistry();

		vwPair key = new vwPair(ws, Obj);

		if (Object.class.isInstance(ws) && Object.class.isInstance(Obj)
				&& isWorkspaceLoaded(ws)
				&& registry.ancestorVisibleVersion.containsKey(key)) {
			return registry.ancestorVisibleVersion.get(key);
		}
		return null;
	}

	public synchronized static VObjectVersion getAncestorVisibleVersion(
			WSpace ws, VObjectVersion ObjV) {
		return getAncestorVisibleVersion(ws, ObjV.getVobject());
	}

	public synchronized static boolean isWorkspaceLoaded(WSpace wspace) {
		if (registry == null)
			registry = new OpenWSRegistry();

		if (Object.class.isInstance(wspace)
				&& registry.visibleVersion.containsKey(wspace)) {
			return true;
		}
		return false;
	}

	public synchronized static void register(WSpace wspace) {
		if (registry == null)
			registry = new OpenWSRegistry();
		if (Object.class.isInstance(wspace) && !isWorkspaceLoaded(wspace)) {
			loadLocalNAncestorVisibleVersions(wspace);
			loadSubObjects(wspace);
			loadSuperObjects(wspace);

		}
	}

	public synchronized void unregister(WSpace wspace) {
		if (registry == null) 
			registry = new OpenWSRegistry();
			
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

	@SuppressWarnings("null")
	private static void loadLocalNAncestorVisibleVersions(WSpace wspace) {
		HashMap<VObject, VObjectVersion> localVersions = new HashMap<VObject, VObjectVersion>();
		HashMap<VObject, VObjectVersion> ancestorVersions = null;
		doLoadVersions(wspace, localVersions, ancestorVersions);

		registry.visibleVersion.put(wspace, new ArrayList<VObjectVersion>(
				localVersions.values()));

		for (Map.Entry<VObject, VObjectVersion> entry : ancestorVersions
				.entrySet()) {
			VObject obj = entry.getKey();
			VObjectVersion vov = entry.getValue();

			vwPair key = new vwPair(wspace, obj);
			registry.ancestorVisibleVersion.put(key, vov);
		}
	}

	private static void doLoadVersions(WSpace wspace,
			HashMap<VObject, VObjectVersion> localVersions,
			HashMap<VObject, VObjectVersion> ancestorVersions) {
		// TODO Auto-generated method stub
		List<VObjectVersion> ws_local = wspace.getLocalVersions();
		for (VObjectVersion vov : ws_local) {
			VObject obj = vov.getVobject();
			if (localVersions.containsKey(obj)) {
				if (ancestorVersions != null
						&& !ancestorVersions.containsKey(obj)) {
					ancestorVersions.put(obj, vov);
				}
			} else {
				localVersions.put(obj, vov);
			}
		}
		if (ancestorVersions == null)
			ancestorVersions = new HashMap<VObject, VObjectVersion>();

		if (Object.class.isInstance(wspace.getAncestorWorkspace())) {
			doLoadVersions(wspace, localVersions, ancestorVersions);
		}
	}

	private static void loadSubObjects(WSpace wspace) {
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

	private static void loadSuperObjects(WSpace wspace) {
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
