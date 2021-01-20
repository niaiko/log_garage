package mg.cnaps.models;

//import java.sql.Date;


public class EtatDemandeParam{
	
	private Integer idDemande;
	private Integer idEtat;
	private String observation;
	private String matricule;
	
	public String getMatricule() {
		return matricule;
	}
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}
	public Integer getIdDemande() {
		return idDemande;
	}
	public void setIdDemande(Integer idDemande) {
		this.idDemande = idDemande;
	}
	public Integer getIdEtat() {
		return idEtat;
	}
	public void setIdEtat(Integer idEtat) {
		this.idEtat = idEtat;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	
	
	
	
}
