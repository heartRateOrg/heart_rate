package com.sergtm.repository;

import com.sergtm.entities.Occasion;
import com.sergtm.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OccasionRepository extends JpaRepository<Occasion, Long> {
    @Modifying
    @Query("delete from Occasion o where o.person = :person")
    void deleteByPerson(Person person);
}
