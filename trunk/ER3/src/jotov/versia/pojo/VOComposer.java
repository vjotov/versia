package jotov.versia.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class VOComposer {
	private ObjectVersion superObject;
	private List<ObjectVersion> subObjects = new ArrayList<ObjectVersion>();

	public VOComposer() {
		super();
	}

	@Id
	@OneToOne(fetch = FetchType.EAGER)
	public ObjectVersion getSuperObject() {
		return superObject;
	}

	public void setSuperObject(ObjectVersion superObject) {
		this.superObject = superObject;
	}

	@OneToMany(fetch = FetchType.EAGER)
	public List<ObjectVersion> getSubObjects() {
		return subObjects;
	}

	public void setSubObjects(List<ObjectVersion> subObjects) {
		this.subObjects = subObjects;
	}

	public boolean add(ObjectVersion e) {
		return subObjects.add(e);
	}

	public boolean isEmpty() {
		return subObjects.isEmpty();
	}

	public boolean remove(Object o) {
		return subObjects.remove(o);
	}

	public int size() {
		return subObjects.size();
	}

	public boolean contains(ObjectVersion o) {
		return subObjects.contains(o);
	}

}
