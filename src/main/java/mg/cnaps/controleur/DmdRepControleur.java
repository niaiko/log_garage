package mg.cnaps.controleur;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.models.DmdRepMod;
import mg.cnaps.models.EtatDemandeMod;
import mg.cnaps.models.EtatDemandeParam;
import mg.cnaps.models.ParamDmdRep;
import mg.cnaps.models.ReferenceParam;
import mg.cnaps.pagination.PageParam;
import mg.cnaps.pagination.PageResponse;
import mg.cnaps.repository.DmdRepRepository;
import mg.cnaps.services.DmdRepService;
import mg.cnaps.services.EtatDemandeService;
import mg.cnaps.services.StatueRepService;
import mg.cnaps.utils.DateUtil;
import mg.cnaps.utils.ReferenceUtil;

@RestController
@CrossOrigin
public class DmdRepControleur {

	ObjectMapper om = new ObjectMapper();
	String resultat;

	private static final Logger log = LoggerFactory.getLogger(DmdRepControleur.class);
	@Autowired
	DmdRepService service;
	@Autowired
	DmdRepRepository repository;
	@Autowired
	EtatDemandeService service2;

	@Autowired
	StatueRepService service3;

	@PostMapping(value = "/listealldmdrep")
	public ResponseEntity<Object> listealldmdrepReq(@RequestBody PageParam param) {
		try {
			log.info("Reference: " + param);
			Page<DmdRepMod> liste = service.findAll(param.getPage(), param.getEtat());
			log.info("listecou: "
					+ om.writeValueAsString(new PageResponse<>(liste.getContent(), liste.getTotalPages())));
			return new ResponseEntity<Object>(liste, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/referenceDmdRep")
	public ResponseEntity<Object> referenceDmdRepReq(@RequestBody ReferenceParam param) {
		try {
			log.info("Reference: " + param);
			resultat = ReferenceUtil.getReferenceDemande(param.getPrestation(), service.seqDemande(), param.getDr());
			return new ResponseEntity<Object>(resultat, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/ajoutdmdrep")
	public ResponseEntity<Object> ajoutdmdrepReq(@RequestBody DmdRepMod dmdRep) {
		try {
			log.info(" donnée : " + dmdRep);
			service.save(dmdRep);
			EtatDemandeMod etatDemande = new EtatDemandeMod();
			etatDemande.setIdEtat(1);
			etatDemande.setIdDmdRep(Integer.valueOf(dmdRep.getIddmdrep()));
			etatDemande.setDate(DateUtil.sqlDateNow());
			etatDemande.setNiveau("Demande en instance");
			etatDemande.setMatrVehi(dmdRep.getMatrvehicule());
			service2.save(etatDemande);
			return new ResponseEntity<Object>(dmdRep, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/findstory")
	public ResponseEntity<Object> findstoryreq(@RequestParam int idmd) {
		try {
			List<EtatDemandeMod> historique = service2.Gethistobyiddemande(idmd);
			resultat = om.writeValueAsString(historique);
			return new ResponseEntity<Object>(resultat, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/listeinstance")
	public ResponseEntity<Object> listeinstancereq(@RequestBody ParamDmdRep p) {
		try {
			Page<DmdRepMod> historique = repository.listeinstancePage(new PageRequest(p.getPage() - 1, 10));
			p.setL(historique.getContent());
			p.setNbPage(historique.getTotalPages());
			resultat = om.writeValueAsString(p);
			return new ResponseEntity<Object>(resultat, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/listevalide")
	public ResponseEntity<Object> listevalidereq() {
		try {
			List<DmdRepMod> historique = service.listevalide();
			resultat = om.writeValueAsString(historique);
			return new ResponseEntity<Object>(resultat, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/listeencours")
	public ResponseEntity<Object> listeencoursreq() {
		try {
			List<DmdRepMod> historique = service.listeencours();
			resultat = om.writeValueAsString(historique);
			return new ResponseEntity<Object>(resultat, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/listeacheve/{page}")
	public ResponseEntity<Object> listeachevereq(@PathVariable int page) {
		try {
			List<DmdRepMod> historique = service.listeacheve(new PageRequest(page, 10));
			resultat = om.writeValueAsString(historique);
			return new ResponseEntity<Object>(resultat, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/listeannule/{page}")
	public ResponseEntity<Object> listeannulereq(@PathVariable int page) {
		try {
			List<DmdRepMod> historique = service.listeannule(new PageRequest(page, 10));
			resultat = om.writeValueAsString(historique);
			return new ResponseEntity<Object>(resultat, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/supprdmdrep/{id}")
	public ResponseEntity<Object> supprdmdrepReq(@PathVariable int id) {
		try {
			DmdRepMod record = new DmdRepMod();
			record = service.getById(id);
			if (record.getIddmdrep() != 0) {
				service.delete(record);
			}
			return new ResponseEntity<Object>(resultat, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/ValidationStatusreparation")
	public ResponseEntity<Object> ValidationStatusreparationreq(@RequestBody EtatDemandeParam param) {
		try {
			log.info("insert: " + param);
			service.UpdateEtatDmdreparation(param.getIdDemande(), 2, param.getObservation());
			EtatDemandeMod etatDemande = new EtatDemandeMod();
			etatDemande.setIdEtat(2);
			etatDemande.setIdDmdRep(param.getIdDemande());
			etatDemande.setDate(DateUtil.sqlDateNow());
			etatDemande.setNiveau(param.getObservation());
			etatDemande.setMatrVehi(param.getMatricule());
			service2.save(etatDemande);
			return new ResponseEntity<Object>(param, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}
}
