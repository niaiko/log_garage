package com.cnaps.kafka;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.models.StatueRepMod;
import mg.cnaps.services.StatueRepService;

@Component
public class StatueRepConsumer {

	ObjectMapper om = new ObjectMapper();
	String resultat;

	private static final Logger log = LoggerFactory.getLogger(StatueRepConsumer.class);
	@Autowired
	Producer producer;

	@Autowired
	StatueRepService service;
	

	@KafkaListener(topics = "listeallstatuerepReq")
	public void liste(ConsumerRecord<?, ?> record) throws Exception {
		try {
			log.info("pageliste: "+record.value().toString());
			List<StatueRepMod> liste = service.getAll(Integer.parseInt(record.value().toString()));
			log.info("listestatus: "+om.writeValueAsString(liste));
			producer.send(record.key().toString(), "listeallstatuerepRes", om.writeValueAsString(liste));
		} catch (Exception e) {
			producer.send(record.key().toString(), "listeallstatuerepRes", e.getMessage());
			
		}
	}
	
	@KafkaListener(topics = "nbrpagestatuerepReq")
	public void nombredepage(ConsumerRecord<?, ?> record) throws Exception {
		try {
			int page = service.nombrepage();
			resultat = om.writeValueAsString(page);
			producer.send(record.key().toString(), "nbrpagestatuerepRes", resultat);
		} catch (Exception e) {
			producer.send(record.key().toString(), "nbrpagestatuerepRes", e.getMessage());
		}
	}
	
	@KafkaListener(topics = "ajoutstatuerepReq")
	public void ajout(ConsumerRecord<?, ?> record) throws Exception {
		try {
			log.info("insert: "+record.value().toString());
			service.save(om.readValue(record.value().toString(), StatueRepMod.class));
			producer.send(record.key().toString(), "ajoutstatuerepRes", "{\"success\":true}");
		} catch (Exception e) {
			producer.send(record.key().toString(), "ajoutstatuerepRes", e.getMessage());
		}
	}
	
	@KafkaListener(topics = "modifierstatusrepReq")
	public void modifier(ConsumerRecord<?, ?> record) throws Exception {
		try {
			log.info("update: " + record.value().toString());
			service.save(om.readValue(record.value().toString(), StatueRepMod.class));
			producer.send(record.key().toString(), "modifierstatusrepRes", "{\"success\":true}");
		}
		catch(Exception e) {
			e.printStackTrace();
			producer.send(record.key().toString(), "modifierstatusrepRes", e.getMessage());
		}
	}

	@KafkaListener(topics = "supprstatuerepReq")
	public void suppr(ConsumerRecord<?, ?> record) throws Exception {
		try {
			service.delete(om.readValue(record.value().toString(), StatueRepMod.class));
			producer.send(record.key().toString(), "supprstatuerepRes", "succes");
		} catch (Exception e) {
			producer.send(record.key().toString(), "supprstatuerepRes", e.getMessage());
		}
	}
	
//	@KafkaListener(topics = "findbyrechcslogReq")
//	public void findbydestinatairedatecou(ConsumerRecord<?, ?> record) {
//		try {
////			log.info("rech1: "+record.value().toString());
//			CsLogMod param = om.readValue(record.value().toString(), CsLogMod.class);
//			log.info("rech1: "+param.getDestinataire());
//			log.info("rech2: "+param.getDateInsert());
//			List<CsLogMod> acces = service.getByDestinataireDateCou(param.getDestinataire(),param.getDateInsert());
//			resultat = om.writeValueAsString(acces);
//			log.info("nbrligne: "+resultat);
//			producer.send(record.key().toString(), "findbyrechcslogRes", resultat);
//		} catch (Exception e) {
//			producer.send(record.key().toString(), "findbyrechcslogRes", e.getMessage());
//		}
//	}
}
