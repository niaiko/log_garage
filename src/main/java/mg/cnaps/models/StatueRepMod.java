package mg.cnaps.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "statue_reparation")
public class StatueRepMod{
	
	
	@Id
	@SequenceGenerator(name="seq-gen",sequenceName="MY_SEQ_GEN", initialValue=205, allocationSize=12)
	@GeneratedValue(strategy= GenerationType.IDENTITY, generator="seq-gen")
	@Column(name = "id_statue_rep", length = 100, nullable = false )
	private int idstatuerep;
	
	@Column(name = "statut")
	private String statue;
	
	@Column(name = "date_statue_deb")
	private Date datestatuedeb;
	
	@Column(name = "date_statue_fin")
	private Date datestatuefin;
	
	@Column(name = "statue_apres_rep")
	private String statueapresrep;

	public int getIdstatuerep() {
		return idstatuerep;
	}

	public void setIdstatuerep(int idstatuerep) {
		this.idstatuerep = idstatuerep;
	}

	public String getStatue() {
		return statue;
	}

	public void setStatue(String statue) {
		this.statue = statue;
	}

	public Date getDatestatuedeb() {
		return datestatuedeb;
	}

	public void setDatestatuedeb(Date datestatuedeb) {
		this.datestatuedeb = datestatuedeb;
	}

	public Date getDatestatuefin() {
		return datestatuefin;
	}

	public void setDatestatuefin(Date datestatuefin) {
		this.datestatuefin = datestatuefin;
	}

	public String getStatueapresrep() {
		return statueapresrep;
	}

	public void setStatueapresrep(String statueapresrep) {
		this.statueapresrep = statueapresrep;
	}
}
