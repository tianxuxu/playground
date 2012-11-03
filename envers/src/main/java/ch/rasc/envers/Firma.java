package ch.rasc.envers;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.envers.Audited;

@Entity
public class Firma {
	@Id
	@GeneratedValue
	private int id;

	private String name;

	@Audited
	private String strasse;

	private String ort;

	@OneToMany(mappedBy = "firma", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Mitarbeiter> mitarbeiter;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public Set<Mitarbeiter> getMitarbeiter() {
		return mitarbeiter;
	}

	public void setMitarbeiter(Set<Mitarbeiter> mitarbeiter) {
		this.mitarbeiter = mitarbeiter;
	}

}