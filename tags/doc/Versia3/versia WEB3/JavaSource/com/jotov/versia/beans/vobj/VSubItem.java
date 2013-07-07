package com.jotov.versia.beans.vobj;

public class VSubItem {
	private int voID;
	private int verionNumber;
	private String voName;

	public VSubItem(int voID, int verionNumber, String objectName) {
		super();
		this.voID = voID;
		this.verionNumber = verionNumber;
		this.voName = objectName;
	}

	public int getVoID() {
		return voID;
	}

	public void setVoID(int voID) {
		this.voID = voID;
	}

	public int getVerionNumber() {
		return verionNumber;
	}

	public void setVerionNumber(int verionNumber) {
		this.verionNumber = verionNumber;
	}

	public String getVoName() {
		return voName;
	}

	public void setVoName(String voName) {
		this.voName = voName;
	}

}
