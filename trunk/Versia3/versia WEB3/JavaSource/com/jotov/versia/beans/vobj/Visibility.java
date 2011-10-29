package com.jotov.versia.beans.vobj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jotov.versia.orm.VObjectVersion;

public class Visibility {
	private HashMap<VObjectVersion, ArrayList<VisibilityEnum>> vvector = new HashMap<VObjectVersion, ArrayList<VisibilityEnum>>();

	public void addObject(VObjectVersion obj, VisibilityEnum vis) {
		if (vvector.containsKey(obj)) {
			ArrayList<VisibilityEnum> vector = vvector.get(obj);
			vector.add(vis);
			vvector.put(obj, vector);
		} else {
			ArrayList<VisibilityEnum> vector = new ArrayList<VisibilityEnum>();
			vector.add(vis);
			vvector.put(obj, vector);
		}
	}

	public void addObjects(ArrayList<VObjectVersion> local, VisibilityEnum ve) {
		for (VObjectVersion obj : local) {
			this.addObject(obj, ve);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<VObjectVersion> getVisibleVersions(){
		return (List<VObjectVersion>) vvector.keySet();
	}
	public ArrayList<VisibilityEnum> getVisibilityVector(VObjectVersion key){
		return vvector.get(key);
	}
}
