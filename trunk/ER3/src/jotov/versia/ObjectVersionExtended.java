package jotov.versia;

import jotov.versia.pojo.ObjectVersion;
import jotov.versia.pojo.VObject;

public class ObjectVersionExtended {
	private ObjectVersion objectVetsion;
	//TODO - visibility vector
	public ObjectVersionExtended() {
		super();
	}

	public ObjectVersionExtended(VObject vobject, int versionNumber,
			String name, String datum) {
		objectVetsion = new ObjectVersion(vobject, versionNumber, name, datum);
	}
	
	public ObjectVersion getObjectVetsion() {
		return objectVetsion;
	}

	public void setObjectVetsion(ObjectVersion objectVetsion) {
		this.objectVetsion = objectVetsion;
	}
}
