package mg.cnaps.services;

import java.io.Serializable;
//import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import mg.cnaps.models.ProspectionMod;
import mg.cnaps.repository.ProspectionRepository;
@Service
public class ProspectionServiceImpl implements ProspectionService{
	
	public static int max=50;
	
	@Autowired
	ProspectionRepository repository;

	@Override
	public void save(ProspectionMod cou) {
		repository.save(cou);
		
	}

	@Override
	public ProspectionMod getById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProspectionMod> getAll(int page) {
		return (repository.findAll(new PageRequest(page-1,max))).getContent();
	}

	@Override
	public void delete(ProspectionMod entity) {
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

//	@Override
//	public List<CsLogMod> getByDestinataireDateCou(String destinataire, Date dateInsert) {
//		return repository.getByDestinataireDateCou(destinataire, dateInsert);
//	}

}
