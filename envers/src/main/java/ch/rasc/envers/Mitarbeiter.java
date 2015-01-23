package ch.rasc.envers;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;

@Entity
@Audited
public class Mitarbeiter {
	@Id
	@GeneratedValue
	private int id;

	private String name;

	private String vorname;

	private String strasse;

	private String ort;

	@ManyToOne
	private Firma firma;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return this.vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getStrasse() {
		return this.strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getOrt() {
		return this.ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public Firma getFirma() {
		return this.firma;
	}

	public void setFirma(Firma firma) {
		this.firma = firma;
	}

}