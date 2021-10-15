package it.anac.segnalazioni.backend.rest;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/ws")

public class SubmissionRestController {

   @Autowired
   private MongoTemplate mongoTemplate;

   @PutMapping("/submission")
   ResponseEntity<?> add(@RequestBody String jsonString) {

       Document doc = Document.parse(jsonString);
       mongoTemplate.insert(doc, "segnalazioni");

       return new ResponseEntity<>(null, HttpStatus.CREATED);
   }

}