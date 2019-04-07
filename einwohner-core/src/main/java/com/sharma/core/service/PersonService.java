package com.sharma.core.service;

import com.sharma.data.resource.*;

import java.util.UUID;

public interface PersonService {
    CreatePersonResponse createPerson(CreatePersonRequest createPersonRequest);
    UpdatePersonResponse updatePerson(UpdatePersonRequest updatePersonRequest);
    GetPersonResponse findPersonById(UUID personId);
    void deletePersonById(UUID personId);
}
