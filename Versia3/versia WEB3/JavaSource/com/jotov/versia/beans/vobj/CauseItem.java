package com.jotov.versia.beans.vobj;

import com.jotov.versia.orm.Cause;

public class CauseItem {
	private Cause causeEntity;

	public CauseItem(Cause cause) {
		super();
		this.causeEntity = cause;
	}

	public int getCauseId() {
		return causeEntity.getCauseId();
	}

	public int getVoID() {
		return causeEntity.getCause().getVobject().getVObjectId();
	}

	public int getVersionNumber() {
		return causeEntity.getCause().getVersionNumber();
	}

	public String getObjectName() {
		return causeEntity.getCause().getObjectName();
	}
	
}
