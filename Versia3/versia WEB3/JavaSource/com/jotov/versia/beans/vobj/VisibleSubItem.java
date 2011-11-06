package com.jotov.versia.beans.vobj;

public class VisibleSubItem {
	private int objectId;
	private int verionNumber;
	private String objectName;

	public VisibleSubItem(int objectId, int verionNumber, String objectName) {
		super();
		this.objectId = objectId;
		this.verionNumber = verionNumber;
		this.objectName = objectName;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public int getVerionNumber() {
		return verionNumber;
	}

	public void setVerionNumber(int verionNumber) {
		this.verionNumber = verionNumber;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
}
