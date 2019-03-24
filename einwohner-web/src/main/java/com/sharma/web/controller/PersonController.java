package com.sharma.web.controller;

import com.sharma.core.service.PersonService;
import com.sharma.data.resource.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @PostMapping(path = "/person",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CreatePersonResponse personErstellen(@Valid @RequestBody CreatePersonRequest createPersonRequest){
        logger.debug("Inside personErstellen");
        return personService.createPerson(createPersonRequest);
    }

    @DeleteMapping(path = "/person/{personId}")
    public void personLoeschen(@PathVariable("personId") String personId){
        personService.deletePersonById(Long.parseLong(personId));
        logger.info("Person mit Id {} deleted", personId);
    }

    @PutMapping(path = "/person/{personId}")
    @ResponseBody
    public UpdatePersonResponse personBearbeiten(@PathVariable(name = "personId") String personId, @Valid @RequestBody UpdatePersonRequest updatePersonRequest){
        logger.info("Updating person with Id {}", personId);
        return personService.updatePerson(updatePersonRequest);
    }

    @GetMapping(path = "/person/{personId}")
    public GetPersonResponse personSuchen(@PathVariable("personId") String personId){
        GetPersonResponse getPersonResponse = personService.findPersonById(Long.parseLong(personId));
        logger.debug("Person mit Id {} gefunden", personId);
        return getPersonResponse;
    }


}
