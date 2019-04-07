package com.sharma.orm.repository;

import com.sharma.orm.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface PersonRepository extends CrudRepository<PersonEntity, UUID> {
}
