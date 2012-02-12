package com.jotov.versia.beans;

import java.util.ArrayList;
import java.util.List;

import com.jotov.versia.util.OpenWSRegistry;

public class ApplicationBean {
	private List<iSessionAdaptor> sessionAdaptors = new ArrayList<iSessionAdaptor>();
	private OpenWSRegistry openWsRegistry = OpenWSRegistry.getSingleton();

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

	public OpenWSRegistry getOpenWsRegistry() {
		return openWsRegistry;
	}
}
