package com.sharma.core.service;

import com.sharma.core.dto.PersonDto;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService{

    @Override
    public PersonDto erstelltPerson(PersonDto personDto){
        return personDto;
    }

}
