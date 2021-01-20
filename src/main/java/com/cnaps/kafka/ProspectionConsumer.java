package com.cnaps.kafka;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import mg.cnaps.models.ProspectionMod;
import mg.cnaps.services.ProspectionService;

@Component
public class ProspectionConsumer {

	ObjectMapper om = new ObjectMapper();
	String resultat;

	private static final Logger log = LoggerFactory.getLogger(ProspectionConsumer.class);
	@Autowired
	Producer producer;

	@Autowired
	ProspectionService service;
	

	@KafkaListener(topics = "listeallprospecReq")
	public void liste(ConsumerRecord<?, ?> record) throws Exception {
		try {
			log.info("pageliste: "+record.value().toString());
			List<ProspectionMod> liste = service.getAll(Integer.parseInt(record.value().toString()));
			log.info("listecou: "+om.writeValueAsString(liste));
			producer.send(record.key().toString(), "listeallprospecRes", om.writeValueAsString(liste));
		} catch (Exception e) {
			producer.send(record.key().toString(), "listeallprospecRes", e.getMessage());
		}
	}
	
	@KafkaListener(topics = "nbrpageprospecReq")
	public void nombredepage(ConsumerRecord<?, ?> record) throws Exception {
		try {
			int page = service.nombrepage();
			resultat = om.writeValueAsString(page);
			producer.send(record.key().toString(), "nbrpageprospecRes", resultat);
		} catch (Exception e) {
			producer.send(record.key().toString(), "nbrpageprospecRes", e.getMessage());
		}
	}
	
	@KafkaListener(topics = "ajoutprospecReq")
	public void ajout(ConsumerRecord<?, ?> record) throws Exception {
		try {
			log.info("insert: "+record.value().toString());
			//ProspectionMod test = om.readValue(record.value().toString(), ProspectionMod.class);
			//log.info(om.writeValueAsString(test));
			//log.info(test.getNompiece());
			service.save(om.readValue(record.value().toString(), ProspectionMod.class));
			producer.send(record.key().toString(), "ajoutprospecRes", "{\"success\":true}");
		} catch (Exception e) {
			e.printStackTrace();
			producer.send(record.key().toString(), "ajoutprospecRes", e.getMessage());
		}
	}

	@KafkaListener(topics = "supprprospecReq")
	public void suppr(ConsumerRecord<?, ?> record) throws Exception {
		try {
			service.delete(om.readValue(record.value().toString(), ProspectionMod.class));
			producer.send(record.key().toString(), "supprprospecRes", "succes");
		} catch (Exception e) {
			producer.send(record.key().toString(), "supprprospecRes", e.getMessage());
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
