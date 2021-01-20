package com.cnaps.kafka;


import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.models.EtatDemandeMod;
import mg.cnaps.models.EtatDemandeParam;
import mg.cnaps.services.DmdRepService;
import mg.cnaps.services.EtatDemandeService;
import mg.cnaps.utils.DateUtil;


@Component
public class EtatDemandeConsumer {

	ObjectMapper om = new ObjectMapper();
	String resultat;

	private static final Logger log = LoggerFactory.getLogger(EtatDemandeConsumer.class);
	@Autowired
	Producer producer;
	
	@Autowired
	EtatDemandeService service;
	
	 
	@Autowired
	DmdRepService  dmdservice;
	
	@KafkaListener(topics = "listealletatdmdgarageReq")
	public void liste(ConsumerRecord<?, ?> record) throws Exception {
		try {
			log.info("Reference: " + record.value().toString());
			int id = Integer.valueOf(record.value().toString());
			List<EtatDemandeMod> liste = service.findalleatadmd(new PageRequest(Integer.parseInt(record.value().toString()), 10));
			log.info("listecou: " + om.writeValueAsString(liste));
			producer.send(record.key().toString(), "listealletatdmdgarageRes", om.writeValueAsString(liste));
		} catch (Exception e) {
			e.printStackTrace();
			producer.send(record.key().toString(), "listealletatdmdgarageRes", e.getMessage());
		}
	}
	
	@KafkaListener(topics = "RechetatdmdgarageReq")
	public void Rechetatdmdgarage(ConsumerRecord<?, ?> record) throws Exception {
		try {
			log.info("Reference: " + record.value().toString());
			EtatDemandeMod param = om.readValue(record.value().toString(), EtatDemandeMod.class);
			List<EtatDemandeMod> liste = service.rechetatdmdgarage(param.getIdEtat(),param.getDate(), param.getMatrVehi());
			log.info("listecou: " + om.writeValueAsString(liste));
			producer.send(record.key().toString(), "RechetatdmdgarageRes", om.writeValueAsString(liste));
		} catch (Exception e) {
			e.printStackTrace();
			producer.send(record.key().toString(), "RechetatdmdgarageRes", e.getMessage());
		}
	}

	@KafkaListener(topics = "UpdatedemandeStatusreq")
	public void updateetatdmd(ConsumerRecord<?, ?> record) throws Exception {
		try {
			log.info("insert: "+record.value().toString());
			EtatDemandeParam param = om.readValue(record.value().toString(), EtatDemandeParam.class);
			dmdservice.UpdateEtatDmdreparation(param.getIdDemande(),param.getIdEtat(), param.getObservation());
			EtatDemandeMod etatDemande = new EtatDemandeMod();
			etatDemande.setIdEtat(param.getIdEtat());
			etatDemande.setIdDmdRep(param.getIdDemande());
			etatDemande.setDate(DateUtil.sqlDateNow());
			etatDemande.setNiveau(param.getObservation());
			etatDemande.setMatrVehi(param.getMatricule());
			service.save(etatDemande);
			dmdservice.updateobservationgarage(param.getObservation(),param.getIdDemande());
			producer.send(record.key().toString(), "UpdatedemandeStatusres", "{\"success\":true}");
		} catch (Exception e) {
			e.printStackTrace();
			producer.send(record.key().toString(), "UpdatedemandeStatusres", e.getMessage());
		}
	}


	
}
