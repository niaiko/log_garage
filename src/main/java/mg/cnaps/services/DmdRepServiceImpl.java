package mg.cnaps.services;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mg.cnaps.models.DmdRepMod;
import mg.cnaps.repository.DmdRepRepository;
@Service
public class DmdRepServiceImpl implements DmdRepService{
	
	public static int max=50;
	
	@Autowired
	DmdRepRepository repository;

	@Override
	public void save(DmdRepMod cou) {
		repository.save(cou);
		
	}

	@Override
	public DmdRepMod getById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<DmdRepMod> findAll(int page,int etat) {
		return  repository.findByIdetat(new PageRequest(page,max),etat)  ;
	}

	@Override
	public void delete(DmdRepMod entity) {
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
	public void UpdateEtatDmdreparation(int iddemande, int idetat, String observation) {
		// TODO Auto-generated method stub
		repository.updateetatdmd(iddemande, idetat,observation);
	}

	@Override
	public List<DmdRepMod> getAll(int page) {
		// TODO Auto-generated method stub
		return null;
	};

	@Override
	public List<DmdRepMod> listeinstance() {
		// TODO Auto-generated method stub
		return repository.listeinstance();
	}
	
	@Override
	public List<DmdRepMod> listevalide() {
		// TODO Auto-generated method stub
		return repository.listevalide();
	}
	@Override
	public List<DmdRepMod> listeencours() {
		// TODO Auto-generated method stub
		return repository.listeencours();
	}
	@Override
	public List<DmdRepMod> listeacheve(Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.listeacheve(pageable);
	}
	@Override
	public List<DmdRepMod> listeannule(Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.listeannule(pageable);
	}
//	@Override
//	public List<CsLogMod> getByDestinataireDateCou(String destinataire, Date dateInsert) {
//		return repository.getByDestinataireDateCou(destinataire, dateInsert);
//	}

	@Override
	public void updateobservationgarage(String observation, Integer iddemande) {
		// TODO Auto-generated method stub
		repository.updateobservationgarage( observation, iddemande);
	}

}
