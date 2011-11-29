package com.jotov.versia.beans.vobj;

import com.jotov.versia.orm.Cause;

public class CauseItem {
	private Cause cause;

	public CauseItem(Cause cause) {
		super();
		this.cause = cause;
	}

	public int getCauseId() {
		return cause.getCauseId();
	}

	public int getVoID() {
		return cause.getCause().getVobject().getVObjectId();
	}

	public int getVersionNumber() {
		return cause.getCause().getVersionNumber();
	}

	public String getObjectName() {
		return cause.getCause().getObjectName();
	}
	
}
