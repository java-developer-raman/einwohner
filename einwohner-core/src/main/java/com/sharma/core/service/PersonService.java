package com.sharma.core.service;

import com.sharma.data.resource.*;

public interface PersonService {
    CreatePersonResponse createPerson(CreatePersonRequest createPersonRequest);
    UpdatePersonResponse updatePerson(UpdatePersonRequest updatePersonRequest);
    GetPersonResponse findPersonById(Long personId);
    void deletePersonById(Long personId);

}
