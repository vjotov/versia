package com.jotov.versia.orm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class ReleaseArc {

	private Release sourceRelease;
	private Release targetRelease;

	public static List<ReleaseArc> createArcs(List<Release> srcReleases, Release target) {
		List<ReleaseArc> result = new ArrayList<ReleaseArc>();
		if (srcReleases == null)
			return result;
		for(Release source: srcReleases){
			result.add(new ReleaseArc(source, target));
		}
		return result;
	}

	public ReleaseArc() {
		super();
	}

	private ReleaseArc(Release sourceRelease, Release targetRelease) {
		super();
		this.sourceRelease = sourceRelease;
		this.targetRelease = targetRelease;
	}

	public Release getSourceRelease() {
		return sourceRelease;
	}

	public void setSourceRelease(Release sourceRelease) {
		this.sourceRelease = sourceRelease;
	}

	public Release getTargetRelease() {
		return targetRelease;
	}

	public void setTargetRelease(Release targetRelease) {
		this.targetRelease = targetRelease;
	}

}
