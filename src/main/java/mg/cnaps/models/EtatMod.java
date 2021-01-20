package mg.cnaps.models;


//import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "etat")
public class EtatMod{
	
	
	@Id 
	@GeneratedValue(strategy= GenerationType.AUTO )
	@Column(name = "id_etat", length = 100, nullable = false )
	private int idetat;
	
	@Column(name = "libelle")
	private String libelle;

	 

	
	public int getIdetat() {
		return idetat;
	}

	public void setIdetat(int idetat) {
		this.idetat = idetat;
	}
 

	 
	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
}
