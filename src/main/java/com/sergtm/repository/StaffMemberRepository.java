package com.sergtm.repository;

import com.sergtm.entities.Person;
import com.sergtm.entities.StaffMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffMemberRepository extends JpaRepository<StaffMember, Long> {
    @Modifying
    @Query("delete from StaffMember sm where sm.person = :person")
    void deleteByPerson(Person person);
}
