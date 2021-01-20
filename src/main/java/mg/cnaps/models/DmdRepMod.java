package mg.cnaps.models;

import java.util.Date;

//import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "demande_reparation")
public class DmdRepMod{
	
	
	@Id
	@SequenceGenerator(name="seq-gen",sequenceName="MY_SEQ_GEN", initialValue=1, allocationSize=12)
	@GeneratedValue(strategy= GenerationType.IDENTITY, generator="seq-gen")
	@Column(name = "id_demande_rep", length = 100, nullable = false )
	private int iddmdrep;
	
	@Column(name = "id_type_rep")
	private int idtyperep;
	
	@Column(name = "id_statue_rep")
	private int idetat;
	
	@Column(name = "observation")
	private String observation;
	
	@Column(name = "id_vehicule")
	private int idvehicule;
	
	@Column(name = "date_dmd_garage")
	private Date dateDmdGarage;
	
	@Column(name = "expediteur_dmd")
	private String expediteurDmd;
	
	@Column(name = "reference_rep")
	private String referenceRep;
	
	@Column(name = "mat_vehicule")
	private String matrvehicule;
	
	@Column(name = "kilometrage")
	private int kilometrage;

	public int getKilometrage() {
		return kilometrage;
	}

	public void setKilometrage(int kilometrage) {
		this.kilometrage = kilometrage;
	}

	public int getIddmdrep() {
		return iddmdrep;
	}

	public void setIddmdrep(int iddmdrep) {
		this.iddmdrep = iddmdrep;
	}

	public int getIdtyperep() {
		return idtyperep;
	}

	public void setIdtyperep(int idtyperep) {
		this.idtyperep = idtyperep;
	}

 

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public int getIdvehicule() {
		return idvehicule;
	}

	public void setIdvehicule(int idvehicule) {
		this.idvehicule = idvehicule;
	}

	public Date getDateDmdGarage() {
		return dateDmdGarage;
	}

	public void setDateDmdGarage(Date dateDmdGarage) {
		this.dateDmdGarage = dateDmdGarage;
	}

	public String getExpediteurDmd() {
		return expediteurDmd;
	}

	public void setExpediteurDmd(String expediteurDmd) {
		this.expediteurDmd = expediteurDmd;
	}

	public String getReferenceRep() {
		return referenceRep;
	}

	public void setReferenceRep(String referenceRep) {
		this.referenceRep = referenceRep;
	}

	public String getMatrvehicule() {
		return matrvehicule;
	}

	public void setMatrvehicule(String matrvehicule) {
		this.matrvehicule = matrvehicule;
	}

	public int getIdetat() {
		return idetat;
	}

	public void setIdetat(int idetat) {
		this.idetat = idetat;
	}

	
	
}
