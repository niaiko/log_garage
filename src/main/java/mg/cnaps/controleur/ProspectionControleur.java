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

import mg.cnaps.models.ProspectionMod;
import mg.cnaps.repository.ProspectionRepository;
import mg.cnaps.services.ProspectionService;

@RestController
@CrossOrigin
public class ProspectionControleur {

	ObjectMapper om = new ObjectMapper();
	String resultat;

	private static final Logger log = LoggerFactory.getLogger(ProspectionControleur.class);

	@Autowired
	ProspectionService service;

	@Autowired
	ProspectionRepository repository;

	@PostMapping(value = "/listeallprospec/{id}")
	public ResponseEntity<Object> listeallprospecReq(@PathVariable int id) {
		try {
			List<ProspectionMod> liste = service.getAll(id);
			log.info("listecou: " + om.writeValueAsString(liste));
			return new ResponseEntity<Object>(liste, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/ajoutprospec")
	public ResponseEntity<Object> ajoutprospecReq(@RequestBody ProspectionMod record) {
		try {
			service.save(record);
			return new ResponseEntity<Object>(record, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}
}
