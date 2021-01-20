package mg.cnaps.services;

import java.io.Serializable;
import java.util.Date;
//import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mg.cnaps.models.EtatDemandeMod;
import mg.cnaps.repository.EtatDemandeRepository;
@Service
public class EtatDemandeServiceImpl implements EtatDemandeService{
	
	public static int max=50;
	
	@Autowired
	EtatDemandeRepository repository;

	@Override
	public void save(EtatDemandeMod cou) {
		repository.save(cou);
		
	}

	@Override
	public EtatDemandeMod getById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EtatDemandeMod> getAll(int page) {
		return (repository.findAll(new PageRequest(page-1,max))).getContent();
	}

	@Override
	public void delete(EtatDemandeMod entity) {
		repository.delete(entity);
		
	}

	@Override
	public int nombrepage() {
		return (int)(repository.count()/max)+1;
	}

	@Override
	public long seqDemande() {
		return repository.seqDemande();
	}

	@Override
	public List<EtatDemandeMod> Gethistobyiddemande(int iddemande) {
		// TODO Auto-generated method stub
		return repository.findByIdDmdRep(iddemande);
	}
	
	
	@Override
	public List<EtatDemandeMod> rechetatdmdgarage(int idetat, Date datevalidation,String matrVehi) {
		// TODO Auto-generated method stub
		return repository.rechetatdmdgarage(idetat, datevalidation,matrVehi);
	}

	@Override
	public List<EtatDemandeMod> findalleatadmd(Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findalleatadmd( pageable);
	}
	

//	@Override
//	public List<CsLogMod> getByDestinataireDateCou(String destinataire, Date dateInsert) {
//		return repository.getByDestinataireDateCou(destinataire, dateInsert);
//	}

}
