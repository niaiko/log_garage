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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.models.EtatMod;
import mg.cnaps.services.EtatService;

@RestController
@CrossOrigin
public class EtatControleur {

	ObjectMapper om = new ObjectMapper();
	String resultat;

	private static final Logger log = LoggerFactory.getLogger(EtatControleur.class);

	@Autowired
	EtatService service;

	@PostMapping(value = "/listealletatgarage/{id}")
	public ResponseEntity<Object> listealletatgarageReq(@PathVariable int id) {
		try {
			List<EtatMod> liste = service.getAll(id);
			log.info("listecou: " + om.writeValueAsString(liste));
			return new ResponseEntity<Object>(liste, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

}
