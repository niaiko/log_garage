package mg.cnaps.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

//import java.sql.Date;
//import java.util.List;
import mg.cnaps.models.DmdRepMod;


public interface DmdRepService extends CRUDService<DmdRepMod> {
	 long seqDemande();
	 public void UpdateEtatDmdreparation(int iddemande,int idetat,String observation);
	 
	Page<DmdRepMod> findAll(int page,int etat);
	List<DmdRepMod> listeinstance();
	List<DmdRepMod> listevalide();
	List<DmdRepMod> listeencours();
	List<DmdRepMod> listeacheve(Pageable pageable);
	List<DmdRepMod> listeannule(Pageable pageable);
	
	void updateobservationgarage(String observation,Integer iddemande);
}
