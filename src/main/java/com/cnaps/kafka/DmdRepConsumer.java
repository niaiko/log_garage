package com.cnaps.kafka;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.models.DmdRepMod;
import mg.cnaps.models.EtatDemandeMod;
import mg.cnaps.models.EtatDemandeParam;
import mg.cnaps.models.ReferenceParam;
import mg.cnaps.models.StatueRepMod;
import mg.cnaps.pagination.PageParam;
import mg.cnaps.pagination.PageResponse;
import mg.cnaps.services.DmdRepService;
import mg.cnaps.services.EtatDemandeService;
import mg.cnaps.services.StatueRepService;
import mg.cnaps.utils.DateUtil;
import mg.cnaps.utils.ReferenceUtil;

@Component
public class DmdRepConsumer {

	ObjectMapper om = new ObjectMapper();
	String resultat;

	private static final Logger log = LoggerFactory.getLogger(DmdRepConsumer.class);
	@Autowired
	Producer producer;

	@Autowired
	DmdRepService service;
	

	@Autowired
	EtatDemandeService service2;
	
	@Autowired
	StatueRepService service3;

	@KafkaListener(topics = "listealldmdrepReq")
	public void liste(ConsumerRecord<?, ?> record) throws Exception {
		try {
			log.info("Reference: " + record.value().toString());
			PageParam param = om.readValue(record.value().toString(), PageParam.class);
			Page<DmdRepMod> liste = service.findAll(param.getPage(),param.getEtat());
			log.info("listecou: " + om.writeValueAsString(new PageResponse<>(liste.getContent(), liste.getTotalPages())));
			producer.send(record.key().toString(), "listealldmdrepRes", om.writeValueAsString(liste));
		} catch (Exception e) {
			e.printStackTrace();
			producer.send(record.key().toString(), "listealldmdrepRes", e.getMessage());
		}
	}

	@KafkaListener(topics = "referenceDmdRepReq")
	public void demandeReference(ConsumerRecord<?, ?> record) throws Exception {
		try {
			log.info("Reference: " + record.value().toString());
			ReferenceParam param = om.readValue(record.value().toString(), ReferenceParam.class);
			resultat = ReferenceUtil.getReferenceDemande(param.getPrestation(), service.seqDemande(), param.getDr());
			producer.send(record.key().toString(), "referenceDmdRepRes", om.writeValueAsString(resultat));
		} catch (Exception e) {
			producer.send(record.key().toString(), "referenceDmdRepRes", e.getMessage());
		}
	}

	@KafkaListener(topics = "nbrpagedmdrepReq")
	public void nombredepage(ConsumerRecord<?, ?> record) throws Exception {
		try {
			int page = service.nombrepage();
			resultat = om.writeValueAsString(page);
			producer.send(record.key().toString(), "nbrpagedmdrepRes", resultat);
		} catch (Exception e) {
			producer.send(record.key().toString(), "nbrpagedmdrepRes", e.getMessage());
		}
	}

	@KafkaListener(topics = "ajoutdmdrepReq")
	public void ajout(ConsumerRecord<?, ?> record) throws Exception {
		try {
			log.info(" donnée : "+ record.value().toString());
			DmdRepMod dmdRep = om.readValue(record.value().toString(), DmdRepMod.class);
//			DmdRepMod dmd = new DmdRepMod();
//			StatueRepMod statue = new StatueRepMod();
//			statue.setStatue("Demande en instance");
//			statue.setDatestatuedeb(DateUtil.sqlDateNow());
//			service3.save(statue);
			
//			dmd.setIdetat();
			service.save(dmdRep);
//			log.info("VITA io");
			EtatDemandeMod etatDemande = new EtatDemandeMod();
			etatDemande.setIdEtat(1);
			etatDemande.setIdDmdRep(Integer.valueOf(dmdRep.getIddmdrep()));
			etatDemande.setDate(DateUtil.sqlDateNow());
			etatDemande.setNiveau("Demande en instance"); 
			etatDemande.setMatrVehi(dmdRep.getMatrvehicule());
			service2.save(etatDemande);
			producer.send(record.key().toString(), "ajoutdmdrepRes", "{\"success\":true}");
		} catch (Exception e) {
			e.printStackTrace();
			producer.send(record.key().toString(), "ajoutdmdrepRes", e.getMessage());
		}
	}

	@KafkaListener(topics = "findstoryreq")
	public void findhistorique(ConsumerRecord<?, ?> record) throws Exception {
		try {
			String idmd  = record.value().toString();
			List<EtatDemandeMod> historique=service2.Gethistobyiddemande(Integer.parseInt(idmd));
			resultat = om.writeValueAsString(historique);
			producer.send(record.key().toString(), "findstoryres",resultat);
		} catch (Exception e) {
			e.printStackTrace();
			producer.send(record.key().toString(), "findstoryres", e.getMessage());
		}
	}
	
	@KafkaListener(topics = "listeinstancereq")
	public void listeinstance(ConsumerRecord<?, ?> record) throws Exception {
		try {
			List<DmdRepMod> historique=service.listeinstance();
			resultat = om.writeValueAsString(historique);
			producer.send(record.key().toString(), "listeinstanceres",resultat);
		} catch (Exception e) {
			e.printStackTrace();
			producer.send(record.key().toString(), "listeinstanceres", e.getMessage());
		}
	}
	
	@KafkaListener(topics = "listevalidereq")
	public void listevalide(ConsumerRecord<?, ?> record) throws Exception {
		try {
			List<DmdRepMod> historique=service.listevalide();
			resultat = om.writeValueAsString(historique);
			producer.send(record.key().toString(), "listevalideres",resultat);
		} catch (Exception e) {
			e.printStackTrace();
			producer.send(record.key().toString(), "listevalideres", e.getMessage());
		}
	}
	
	@KafkaListener(topics = "listeencoursreq")
	public void listeencours(ConsumerRecord<?, ?> record) throws Exception {
		try {
			List<DmdRepMod> historique=service.listeencours();
			resultat = om.writeValueAsString(historique);
			producer.send(record.key().toString(), "listeencoursres",resultat);
		} catch (Exception e) {
			e.printStackTrace();
			producer.send(record.key().toString(), "listeencoursres", e.getMessage());
		}
	}
	
	@KafkaListener(topics = "listeachevereq")
	public void listeacheve(ConsumerRecord<?, ?> record) throws Exception {
		try {
			List<DmdRepMod> historique=service.listeacheve(new PageRequest(Integer.parseInt(record.value().toString()), 10));
			resultat = om.writeValueAsString(historique);
			producer.send(record.key().toString(), "listeacheveres",resultat);
		} catch (Exception e) {
			e.printStackTrace();
			producer.send(record.key().toString(), "listeacheveres", e.getMessage());
		}
	}
	
	@KafkaListener(topics = "listeannulereq")
	public void listeannule(ConsumerRecord<?, ?> record) throws Exception {
		try {
			List<DmdRepMod> historique=service.listeannule(new PageRequest(Integer.parseInt(record.value().toString()), 10));
			resultat = om.writeValueAsString(historique);
			producer.send(record.key().toString(), "listeannuleres",resultat);
		} catch (Exception e) {
			e.printStackTrace();
			producer.send(record.key().toString(), "listeannuleres", e.getMessage());
		}
	}
	
	@KafkaListener(topics = "supprdmdrepReq")
	public void suppr(ConsumerRecord<?, ?> record) throws Exception {
		try {
			service.delete(om.readValue(record.value().toString(), DmdRepMod.class));
			producer.send(record.key().toString(), "supprdmdrepRes", "succes");
		} catch (Exception e) {
			producer.send(record.key().toString(), "supprdmdrepRes", e.getMessage());
		}
	}

	@KafkaListener(topics = "ValidationStatusreparationreq")
	public void validationdmd(ConsumerRecord<?, ?> record) throws Exception {
		try {
			log.info("insert: "+record.value().toString());
			EtatDemandeParam param = om.readValue(record.value().toString(), EtatDemandeParam.class);
			service.UpdateEtatDmdreparation(param.getIdDemande(),2,param.getObservation());
			EtatDemandeMod etatDemande = new EtatDemandeMod();
			etatDemande.setIdEtat(2);
			etatDemande.setIdDmdRep(param.getIdDemande());
			etatDemande.setDate(DateUtil.sqlDateNow());
			etatDemande.setNiveau(param.getObservation());
			etatDemande.setMatrVehi(param.getMatricule());
			service2.save(etatDemande);
			producer.send(record.key().toString(), "ValidationStatusreparationres", "{\"success\":true}");
		} catch (Exception e) {
			e.printStackTrace();
			producer.send(record.key().toString(), "ValidationStatusreparationres", e.getMessage());
		}
	
	}
}
