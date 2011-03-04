package jotov.versia.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="VERSIONED_PRIMITIVE")
public class ObjectVersion {
	private VObject vobject;
	private int versionID;
	private String objectName;
	private String objectDatum; 
	
	public ObjectVersion(VObject vobject, int versionNumber, String name, String datum) {
		super();
		this.vobject = vobject;
		this.versionID = versionNumber;
		this.objectName = name;
		this.objectDatum = datum;		
	}
	@Id
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="VO_ID")
	public VObject getVobject() {
		return vobject;
	}
	public void setVobject(VObject vobject) {
		this.vobject = vobject;
	}
	
	@Id
	@Column(name="VP_ID")
	public int getVersionID() {
		return versionID;
	}
	public void setVersionID(int versionID) {
		this.versionID = versionID;
	}
	@Column	(name="OBJECT_NAME", length=20)
	public String getName() {
		return objectName;
	}
	public void setName(String name) {
		this.objectName = name;
	}
	@Column(name="DATUM")
	public String getDatum() {
		return objectDatum;
	}
	public void setDatum(String datum) {
		this.objectDatum = datum;
	}
}
