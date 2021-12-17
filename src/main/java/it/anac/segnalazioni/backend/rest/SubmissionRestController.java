package it.anac.segnalazioni.backend.rest;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


@RestController
@RequestMapping(path="/ws")

public class SubmissionRestController {
	
   @Value("${mongo.submussion.collection}")
   private String collezione;

   @Autowired
   private MongoTemplate mongoTemplate;

   @PutMapping("/submission")
   public ResponseEntity<?> add(@RequestBody String jsonString) throws JsonMappingException, JsonProcessingException
   {	   
       Document doc = Document.parse(jsonString);
       mongoTemplate.insert(doc, collezione);
       return new ResponseEntity<>(null, HttpStatus.CREATED);
   }
   

}