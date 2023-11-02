package com.sergtm.repository;

import com.sergtm.entities.HeartRate;
import com.sergtm.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartRateRepository extends JpaRepository<HeartRate, Long>  {
    @Modifying
    @Query("delete from HeartRate hr where hr.person = :person")
    void deleteByPerson(Person person);
}
