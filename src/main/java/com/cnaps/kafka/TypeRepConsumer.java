package com.cnaps.kafka;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.models.TypeRepMod;
import mg.cnaps.services.TypeRepService;

@Component
public class TypeRepConsumer {

	ObjectMapper om = new ObjectMapper();
	String resultat;

	private static final Logger log = LoggerFactory.getLogger(TypeRepConsumer.class);
	@Autowired
	Producer producer;

	@Autowired
	TypeRepService service;
	

	@KafkaListener(topics = "listealltyperepReq")
	public void liste(ConsumerRecord<?, ?> record) throws Exception {
		try {
			log.info("pageliste: "+record.value().toString());
			List<TypeRepMod> liste = service.getAll(Integer.parseInt(record.value().toString()));
			log.info("listecou: "+om.writeValueAsString(liste));
			producer.send(record.key().toString(), "listealltyperepRes", om.writeValueAsString(liste));
		} catch (Exception e) {
			producer.send(record.key().toString(), "listealltyperepRes", e.getMessage());
		}
	}
	
	@KafkaListener(topics = "nbrpagetyperepReq")
	public void nombredepage(ConsumerRecord<?, ?> record) throws Exception {
		try {
			int page = service.nombrepage();
			resultat = om.writeValueAsString(page);
			producer.send(record.key().toString(), "nbrpagetyperepRes", resultat);
		} catch (Exception e) {
			producer.send(record.key().toString(), "nbrpagetyperepRes", e.getMessage());
		}
	}
	
	@KafkaListener(topics = "ajouttyperepReq")
	public void ajout(ConsumerRecord<?, ?> record) throws Exception {
		try {
			log.info("insert: "+record.value().toString());
			service.save(om.readValue(record.value().toString(), TypeRepMod.class));
			producer.send(record.key().toString(), "ajouttyperepRes", "{\"success\":true}");
		} catch (Exception e) {
			producer.send(record.key().toString(), "ajouttyperepRes", e.getMessage());
		}
	}

	@KafkaListener(topics = "supprtyperepReq")
	public void suppr(ConsumerRecord<?, ?> record) throws Exception {
		try {
			service.delete(om.readValue(record.value().toString(), TypeRepMod.class));
			producer.send(record.key().toString(), "supprtyperepRes", "succes");
		} catch (Exception e) {
			producer.send(record.key().toString(), "supprtyperepRes", e.getMessage());
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
	
	@KafkaListener(topics = "findlibbyidtyperepReq")
	public void findLibByIdTypeRep(ConsumerRecord<?, ?> record) throws Exception {
		try {
			log.info("recherche" + record.value().toString());
			List<TypeRepMod> access = service.getTypeRepByLib(record.value().toString());
			resultat = om.writeValueAsString(access);
			log.info("res: "+resultat);
			producer.send(record.key().toString(), "findlibbyidtyperepRes", resultat);
		}
		catch(Exception e) {
			e.printStackTrace();
			producer.send(record.key().toString(), "findlibbyidtyperepRes", e.getMessage());
		}
	}
	
}
