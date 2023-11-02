package com.sergtm.repository;

import com.sergtm.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository  extends JpaRepository<Person, Long> {
    @Modifying
    @Query("delete from Person p where p.id = :personId")
    void deleteByPerson(Long personId);
}
