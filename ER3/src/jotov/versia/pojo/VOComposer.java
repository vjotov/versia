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
		return getSubObjects().add(e);
	}

	public boolean isEmpty() {
		return getSubObjects().isEmpty();
	}

	public boolean remove(Object o) {
		return getSubObjects().remove(o);
	}

	public int size() {
		return getSubObjects().size();
	}

	public boolean contains(ObjectVersion o) {
		return getSubObjects().contains(o);
	}

}
