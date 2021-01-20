package mg.cnaps.services;

import java.io.Serializable;
//import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import mg.cnaps.models.EtatMod;
import mg.cnaps.repository.EtatRepository;
@Service
public class EtatServiceImpl implements EtatService{
	
	public static int max=50;
	
	@Autowired
	EtatRepository repository;

	@Override
	public void save(EtatMod cou) {
		repository.save(cou);
		
	}

	@Override
	public EtatMod getById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EtatMod> getAll(int page) {
		return (repository.findAll(new PageRequest(page-1,max))).getContent();
	}

	@Override
	public void delete(EtatMod entity) {
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

}
