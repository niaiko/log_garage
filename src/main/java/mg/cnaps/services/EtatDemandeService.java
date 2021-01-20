package mg.cnaps.services;



import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;

import mg.cnaps.models.EtatDemandeMod;


public interface EtatDemandeService extends CRUDService<EtatDemandeMod> {
	 long seqDemande();
	
	 List<EtatDemandeMod> Gethistobyiddemande(int iddemande);
	 List<EtatDemandeMod> rechetatdmdgarage(int idetat, Date datevalidation,String matrVehi);
	 List<EtatDemandeMod> findalleatadmd(Pageable pageable);
}
