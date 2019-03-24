package com.sharma.core.service;

import com.sharma.core.collaborator.Transformer;
import com.sharma.data.resource.*;
import com.sharma.orm.entity.PersonEntity;
import com.sharma.orm.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonServiceImpl implements PersonService {

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
        PersonEntity personEntity = personRepository.save(transformer.transform(updatePersonRequest, PersonEntity.class));
        return transformer.transform(personEntity, UpdatePersonResponse.class);
    }

    @Override
    @Transactional
    public GetPersonResponse findPersonById(Long personId) {
        PersonEntity personEntity = personRepository.findById(personId).orElse(new PersonEntity());
        return transformer.transform(personEntity, GetPersonResponse.class);
    }

    @Override
    @Transactional
    public void deletePersonById(Long personId) {
        personRepository.deleteById(personId);
    }
}
