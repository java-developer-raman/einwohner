package com.sharma.core.service;

import com.sharma.data.exception.DataNotFoundException;
import com.sharma.data.resource.*;
import com.sharma.orm.entity.PersonEntity;
import com.sharma.orm.repository.PersonRepository;
import com.sharma.shared.collaborator.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);


    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private Transformer transformer;


    @Override
    @Transactional
    public CreatePersonResponse createPerson(CreatePersonRequest createPersonRequest) {
        PersonEntity personEntity = personRepository.save(transformer.transform(createPersonRequest, PersonEntity.class));
        return transformer.transform(personEntity, CreatePersonResponse.class);
    }

    @Override
    @Transactional
    public UpdatePersonResponse updatePerson(UpdatePersonRequest updatePersonRequest) {
        Optional<PersonEntity> personEntityOptional = personRepository.findById(updatePersonRequest.getId());
        if(personEntityOptional.isPresent()){
            logger.info("Updating Person with Id {}", updatePersonRequest.getId());
            PersonEntity personEntity = personEntityOptional.get();
            personEntity.setEmail(updatePersonRequest.getEmail());
            personEntity.setHandyNummer(updatePersonRequest.getHandyNummer());
            personEntity.setTelefonNummer(updatePersonRequest.getTelefonNummer());
            personEntity.setNachName(updatePersonRequest.getNachName());
            personEntity.setVorName(updatePersonRequest.getVorName());
            return transformer.transform(personRepository.save(personEntity), UpdatePersonResponse.class);
        }
        throw new DataNotFoundException("No Person found with id : " + updatePersonRequest.getId());
    }

    @Override
    @Transactional
    public GetPersonResponse findPersonById(UUID personId) {
        PersonEntity personEntity = personRepository.findById(personId).orElseThrow(() -> new DataNotFoundException("No Person Found with Id " + personId));
        return transformer.transform(personEntity, GetPersonResponse.class);
    }

    @Override
    @Transactional
    public void deletePersonById(UUID personId) {
        personRepository.deleteById(personId);
        logger.info("Person with Id {} deleted.", personId);
    }
}
