package mg.cnaps.controleur;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.models.StatueRepMod;
import mg.cnaps.repository.StatueRepRepository;
import mg.cnaps.services.StatueRepService;

@RestController
@CrossOrigin
public class StatueRepControleur {
	ObjectMapper om = new ObjectMapper();
	String resultat;

	private static final Logger log = LoggerFactory.getLogger(StatueRepControleur.class);

	@Autowired
	StatueRepService service;
	@Autowired
	StatueRepRepository repository;

	@PostMapping(value = "/listeallstatuerep/{id}")
	public ResponseEntity<Object> listeallstatuerepReq(@PathVariable int id) {
		try {
			List<StatueRepMod> liste = service.getAll(id);
			log.info("listecou: " + om.writeValueAsString(liste));
			return new ResponseEntity<Object>(liste, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/ajoutstatuerep")
	public ResponseEntity<Object> ajoutstatuerepReq(@RequestBody StatueRepMod repMod) {
		try {
			log.info("insert: " + repMod);
			service.save(repMod);
			return new ResponseEntity<Object>(repMod, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/modifierstatusrep")
	public ResponseEntity<Object> modifierstatusrepReq(@RequestBody StatueRepMod repMod) {
		try {
			log.info("update: " + repMod);
			service.save(repMod);
			return new ResponseEntity<Object>(repMod, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

}
