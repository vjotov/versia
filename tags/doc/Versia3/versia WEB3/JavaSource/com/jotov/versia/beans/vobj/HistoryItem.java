package com.jotov.versia.beans.vobj;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.jotov.versia.orm.Cause;
import com.jotov.versia.orm.VersionArc;

public class HistoryItem {
	private VersionArc va;
	private List<CauseItem> causeItems = new ArrayList<CauseItem>();
	
	public HistoryItem(VersionArc va) {
		super();
		this.va = va;
		List<Cause> causes = va.getCauses();
		
		for (Cause c : causes) {
			causeItems.add(new CauseItem(c));
		}
	}
	public int getArcID(){
		return va.getArcId();
	}
	public int getVObjectID() {
		return va.getTarget().getVobject().getVObjectId();
	}
	public int getPrecedorID() {
		return va.getPrecedor().getGlobalVPId();
	}
	public int getPrecedorVersionNumber() {
		return va.getPrecedor().getVersionNumber();
	}
	public int getTargetID() {
		return va.getTarget().getGlobalVPId();
	}
	public int getTargetVersionNumber() {
		return va.getTarget().getVersionNumber();
	}
	public String getUser() {
		return va.getUser().getUserName();
	}
	public String getDate() {
		DateFormat dateFormatter;
		dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale("en","US"));
		return dateFormatter.format(va.getArcDate());
	}
	public VersionArc getVa() {
		return va;
	}
	public void setVa(VersionArc va) {
		this.va = va;
	}
	public List<CauseItem> getCauseItems() {
		return causeItems;
	}
	public void setCauseItems(List<CauseItem> causeItems) {
		this.causeItems = causeItems;
	}
}
