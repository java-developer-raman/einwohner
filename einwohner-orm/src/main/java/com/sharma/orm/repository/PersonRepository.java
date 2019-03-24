package com.sharma.orm.repository;

import com.sharma.orm.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonRepository extends CrudRepository<PersonEntity, Long> {
}
