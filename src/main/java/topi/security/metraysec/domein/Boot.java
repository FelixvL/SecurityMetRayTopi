package topi.security.metraysec.domein;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Boot {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	long id;

	String naam;
	String kapitein;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNaam() {
		return naam;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	public String getKapitein() {
		return kapitein;
	}
	public void setKapitein(String kapitein) {
		this.kapitein = kapitein;
	}
	
	
}
