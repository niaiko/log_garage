package com.cnaps.kafka;


import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.models.EtatMod;
import mg.cnaps.services.EtatService;

@Component
public class EtatConsumer {

	ObjectMapper om = new ObjectMapper();
	String resultat;

	private static final Logger log = LoggerFactory.getLogger(EtatConsumer.class);
	@Autowired
	Producer producer;

	@Autowired
	EtatService service;
	
	@KafkaListener(topics = "listealletatgarageReq")
	public void liste(ConsumerRecord<?, ?> record) throws Exception {
		try {
			log.info("Reference: " + record.value().toString());
			int id = Integer.valueOf(record.value().toString());
			List<EtatMod> liste = service.getAll(id);
			log.info("listecou: " + om.writeValueAsString(liste));
			producer.send(record.key().toString(), "listealletatgarageRes", om.writeValueAsString(liste));
		} catch (Exception e) {
			e.printStackTrace();
			producer.send(record.key().toString(), "listealletatgarageRes", e.getMessage());
		}
	}

	@KafkaListener(topics = "ajoutetatReq")
	public void ajout(ConsumerRecord<?, ?> record) throws Exception {
		try {
			log.info("insert: "+record.value().toString());
			/*EtatDemandeParam param = om.readValue(record.value().toString(), EtatDemandeParam.class);
			
			EtatDemandeMod etatDemande = new EtatDemandeMod();
			etatDemande.setIdDmdRep(param.getIdDemande());
			etatDemande.setIdEtat(2);
			etatDemande.setDate(DateUtil.sqlDateNow());
			etatDemande.setNiveau("u");
			service.save(etatDemande);*/
			
			producer.send(record.key().toString(), "ajoutetatRes", "{\"success\":true}");
		} catch (Exception e) {
			e.printStackTrace();
			producer.send(record.key().toString(), "ajoutetatRes", e.getMessage());
		}
	}
	
}
