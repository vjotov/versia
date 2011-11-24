package com.jotov.versia.beans;

import java.util.ArrayList;
import java.util.List;

public class ApplicationBean {
	private List<iSessionAdaptor> sessionAdaptors = new ArrayList<iSessionAdaptor>();

	public void Subscribe(iSessionAdaptor sa) {
		sessionAdaptors.add(sa);
	}

	public void NotifyCleanAll() {
		List<iSessionAdaptor> forRemove = new ArrayList<iSessionAdaptor>();
		for (iSessionAdaptor sa : sessionAdaptors) {
			if (Object.class.isInstance(sa))
				sa.executeClean();
			else
				forRemove.add(sa);
		}
		sessionAdaptors.removeAll(forRemove);
	}
}
