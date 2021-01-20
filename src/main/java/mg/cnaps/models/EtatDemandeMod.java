package mg.cnaps.models;

import java.sql.Date;

//import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "etat_demande")
public class EtatDemandeMod{
	
	
	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="seq-gen")
	@SequenceGenerator(name="seq-gen",sequenceName="seq_etat_demande", initialValue=1, allocationSize=12)
	@Column(name = "id_etat_dmd", length = 100, nullable = false )
	
	private Integer idEtatDmd;
	
	@Column(name = "id_etat")
	private Integer idEtat;

	@Column(name = "id_demande_rep")
	private Integer idDmdRep;
	
	@Column(name = "date_validation")
	private Date date;
	
	@Column(name = "niveau")
	private String niveau;
	
	@Column(name = "matricule_vehi")
	private String matrVehi;

	public String getMatrVehi() {
		return matrVehi;
	}

	public void setMatrVehi(String matrVehi) {
		this.matrVehi = matrVehi;
	}

	public Integer getIdEtatDmd() {
		return idEtatDmd;
	}

	public void setIdEtatDmd(Integer idEtatDmd) {
		this.idEtatDmd = idEtatDmd;
	}

	public Integer getIdEtat() {
		return idEtat;
	}

	public void setIdEtat(Integer idEtat) {
		this.idEtat = idEtat;
	}

	public Integer getIdDmdRep() {
		return idDmdRep;
	}

	public void setIdDmdRep(Integer idDmdRep) {
		this.idDmdRep = idDmdRep;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}
	
}
