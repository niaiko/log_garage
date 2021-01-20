package mg.cnaps.controleur;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.models.TypeRepMod;
import mg.cnaps.repository.TypeRepRepository;
import mg.cnaps.services.TypeRepService;

@RestController
@CrossOrigin
public class TypeRepControleur {
	ObjectMapper om = new ObjectMapper();
	String resultat;

	private static final Logger log = LoggerFactory.getLogger(TypeRepControleur.class);

	@Autowired
	TypeRepService service;

	@Autowired
	TypeRepRepository repository;

	@PostMapping(value = "/listealltyperep/{id}")
	public ResponseEntity<Object> listealltyperepReq(@PathVariable int id) {
		try {
			log.info("pageliste: " + id);
			List<TypeRepMod> liste = service.getAll(id);
			log.info("listecou: " + om.writeValueAsString(liste));
			return new ResponseEntity<Object>(liste, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/listeTypeAll")
	public ResponseEntity<Object> l() {
		try {
			List<TypeRepMod> liste = repository.findAll();
			log.info("listecou: " + om.writeValueAsString(liste));
			return new ResponseEntity<Object>(liste, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/nbrpagetyperep")
	public ResponseEntity<Object> nbrpagetyperepReq() {
		try {
			int page = service.nombrepage();
			resultat = om.writeValueAsString(page);
			return new ResponseEntity<Object>(resultat, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/ajouttyperep")
	public ResponseEntity<Object> ajouttyperepReq(@RequestBody TypeRepMod typeRepMod) {
		try {
			log.info("insert: " + typeRepMod);
			service.save(typeRepMod);
			return new ResponseEntity<Object>(typeRepMod, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/supprtyperep/{idtype}")
	public ResponseEntity<Object> supprtyperepReq(@PathVariable int idtype) {
		try {
			TypeRepMod mod = new TypeRepMod();
			mod = service.getById(idtype);
			if (mod.getIdtyperep() != 0) {
				service.delete(mod);
			}
			return new ResponseEntity<Object>(resultat, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}

//	@PostMapping(value = "/RechercheTypeRep")
//	public ResponseEntity<Object> RechercheTypeRepreq(@RequestBody TypeRepMod param) {
//		try {
//			log.info("nbrligne: " + resultat);
//			List<TypeRepMod> acces = service.rechercheTypeRep(param.getLibellerep());
//			resultat = om.writeValueAsString(acces);
//			return new ResponseEntity<Object>(resultat, HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
//		}
//	}

	@PostMapping(value = "/findlibbyidtyperep/{libelle}")
	public ResponseEntity<Object> findlibbyidtyperepReq(@PathVariable String libelle) {
		try {
			log.info("recherche" + libelle);
			List<TypeRepMod> access = service.getTypeRepByLib(libelle);
			resultat = om.writeValueAsString(access);
			log.info("res: " + resultat);
			return new ResponseEntity<Object>(resultat, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e, HttpStatus.BAD_REQUEST);
		}
	}
}
