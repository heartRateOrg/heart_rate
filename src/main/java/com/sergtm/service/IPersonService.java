package com.sergtm.service;

import com.sergtm.entities.Person;

import java.util.Collection;

public interface IPersonService {
    boolean deletePerson(Long id);
    Person addPerson(String firstName, String secondName);
    Collection<Person> findAll();
    Collection<Person> getByUser(String userName);
    Person getByName(String firstName, String secondName);
	Person findByIdOrThrowException(Long personId);
}
