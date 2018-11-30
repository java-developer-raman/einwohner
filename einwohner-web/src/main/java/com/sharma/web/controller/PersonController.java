package com.sharma.web.controller;

import com.sharma.core.collaborator.Transformer;
import com.sharma.core.dto.PersonDto;
import com.sharma.core.service.PersonService;
import com.sharma.data.resource.PersonRequest;
import com.sharma.data.resource.PersonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private Transformer transformer;
    @Autowired
    private PersonService personService;

    @PostMapping(path = "/person",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PersonResponse erstelltPerson(@Valid @RequestBody PersonRequest personRequest){
        logger.debug("innen erstelltPerson");
        PersonDto personDto = transformer.transform(personRequest, PersonDto.class);
        return transformer.transform(personService.erstelltPerson(personDto), PersonResponse.class);
    }
}
