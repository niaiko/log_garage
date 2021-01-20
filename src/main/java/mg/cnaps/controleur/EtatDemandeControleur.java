package mg.cnaps.controleur;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.models.EtatDemandeMod;
import mg.cnaps.models.EtatDemandeParam;
import mg.cnaps.services.DmdRepService;
import mg.cnaps.services.EtatDemandeService;
import mg.cnaps.utils.DateUtil;

@RestController
@CrossOrigin
public class EtatDemandeControleur {

	ObjectMapper om = new ObjectMapper();
	String resultat;

	private static final Logger log = LoggerFactory.getLogger(EtatDemandeControleur.class);

	@Autowired
	EtatDemandeService service;

	@Autowired
	DmdRepService dmdservice;

	@GetMapping(value = "/listealletatdmdgarage/{page}")
	public ResponseEntity<Object> listealletatdmdgarageReq(@PathVariable int page) {
		try {
			List<EtatDemandeMod> liste = service.findalleatadmd(new PageRequest(page, 10));
			log.info("listecou: " + om.writeValueAsString(liste));
			return new ResponseEntity<Object>(liste, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/Rechetatdmdgarage")
	public ResponseEntity<Object> RechetatdmdgarageReq(@RequestBody EtatDemandeMod param) {
		try {
			List<EtatDemandeMod> liste = service.rechetatdmdgarage(param.getIdEtat(), param.getDate(),
					param.getMatrVehi());
			log.info("listecou: " + om.writeValueAsString(liste));
			return new ResponseEntity<Object>(liste, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/UpdatedemandeStatus")
	public ResponseEntity<Object> UpdatedemandeStatus(@RequestBody EtatDemandeParam param) {
		try {
			dmdservice.UpdateEtatDmdreparation(param.getIdDemande(), param.getIdEtat(), param.getObservation());
			EtatDemandeMod etatDemande = new EtatDemandeMod();
			etatDemande.setIdEtat(param.getIdEtat());
			etatDemande.setIdDmdRep(param.getIdDemande());
			etatDemande.setDate(DateUtil.sqlDateNow());
			etatDemande.setNiveau(param.getObservation());
			etatDemande.setMatrVehi(param.getMatricule());
			service.save(etatDemande);
			dmdservice.updateobservationgarage(param.getObservation(), param.getIdDemande());
			return new ResponseEntity<Object>(param, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
