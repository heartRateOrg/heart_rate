package com.sergtm.entities;

import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Sergey on 16.07.2017.
 */
@Entity
@Table(name = "PERSON")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person implements IEntity{
    @Id
    @SequenceGenerator(name = "PERSON_SEQ", sequenceName = "PERSON_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "PERSON_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "SECOND_NAME")
    private String secondName;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "CITY")
    private String city;

    @Column(name = "BIRTHDATE")
    private LocalDateTime birthdate;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "MOBILE_PHONE")
    private String mobilePhone;

    @Column(name = "EMAIL")
    private String email;

    public String getName() {
        final String middleName = getMiddleName();
        return String.format("%s%s, %s", getSecondName(), 
                StringUtils.isEmpty(middleName) ? "" : " " + middleName, getFirstName());
    }

    public static Person createPerson(String firstName, String secondName){
        Person person = new Person();
        person.setFirstName(firstName);
        person.setSecondName(secondName);
        return person;
    }
}
