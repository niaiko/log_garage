package mg.cnaps.models;

//import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "type_reparation")
public class TypeRepMod{
	
	
	@Id
	@SequenceGenerator(name="seq-gen",sequenceName="MY_SEQ_GEN", initialValue=205, allocationSize=12)
	@GeneratedValue(strategy= GenerationType.IDENTITY, generator="seq-gen")
	@Column(name = "id_type_rep", length = 100, nullable = false )
	private int idtyperep;
	
	@Column(name = "libelle")
	private String libellerep;

	public int getIdtyperep() {
		return idtyperep;
	}

	public void setIdtyperep(int idtyperep) {
		this.idtyperep = idtyperep;
	}

	public String getLibellerep() {
		return libellerep;
	}

	public void setLibellerep(String libellerep) {
		this.libellerep = libellerep;
	}
	
}
