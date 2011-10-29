package com.jotov.versia.beans.vobj;

import java.util.ArrayList;

import com.jotov.versia.orm.VObject;
import com.jotov.versia.orm.VObjectVersion;

public class VisibleItems {
	private VObject vo;
	private VObjectVersion vov;
	private ArrayList<VisibilityEnum> vvector = new ArrayList<VisibilityEnum>();

	public VisibleItems(VObject vobj, VObjectVersion vov2, VisibilityEnum ve) {
		this.vo = vobj;
		this.vov = vov2;
		this.vvector.add(ve);
	}

	public int getObjectId() {
		return vo.getVObjectId();
	}	
	public int getVerionNumber() {
		return vov.getVersionNumber();
	}

	public String getObjectName() {
		return vov.getObjectName();
	}

	public VObjectVersion getVov() {
		return vov;
	}

	public void setVov(VObjectVersion vov) {
		this.vov = vov;
	}

	public ArrayList<VisibilityEnum> getVvector() {
		return vvector;
	}

	public void setVvector(ArrayList<VisibilityEnum> vvector) {
		this.vvector = vvector;
	}

	public void addVvector(VisibilityEnum ve) {
		if (this.vvector.indexOf(ve) == -1) {
			this.vvector.add(ve);
		}
	}

	public VObject getVo() {
		return vo;
	}

	public void setVo(VObject vo) {
		this.vo = vo;
	}

}
