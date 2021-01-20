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
@Table(name = "prospection")
public class ProspectionMod{
	
	
	@Id
	@SequenceGenerator(name="seq-gen",sequenceName="MY_SEQ_GEN", initialValue=205, allocationSize=12)
	@GeneratedValue(strategy= GenerationType.IDENTITY, generator="seq-gen")
	@Column(name = "id_prospection", length = 100, nullable = false )
	private Integer idprospection;
	
	@Column(name = "id_demande_rep")
	private int iddmdrep;
	
	@Column(name = "nom_piece")
	private String nompiece;
	
	@Column(name = "etat")
	private boolean etat;
	
	@Column(name = "reference")
	private String reference;
	
	@Column(name = "date_prospect")
	private Date dateprospec;

	public Integer getIdprospection() {
		return idprospection;
	}

	public void setIdprospection(Integer idprospection) {
		this.idprospection = idprospection;
	}

	public int getIddmdrep() {
		return iddmdrep;
	}

	public void setIddmdrep(int iddmdrep) {
		this.iddmdrep = iddmdrep;
	}

	public String getNompiece() {
		return nompiece;
	}

	public void setNompiece(String nompiece) {
		this.nompiece = nompiece;
	}

	public boolean isEtat() {
		return etat;
	}

	public void setEtat(boolean etat) {
		this.etat = etat;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Date getDateprospec() {
		return dateprospec;
	}

	public void setDateprospec(Date dateprospec) {
		this.dateprospec = dateprospec;
	}
	
}
