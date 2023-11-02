package com.sergtm.service.impl;

import com.sergtm.dao.IPersonDao;
import com.sergtm.entities.Person;
import com.sergtm.repository.PersonRepository;
import com.sergtm.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements IPersonService {
    private static final Logger LOG = LogManager.getLogger(PersonServiceImpl.class);

    private static final String CAN_NOT_FIND_PERSON_BY_PERSON_ID_MESSAGE = "Can't find person by person id = %s";

    @Autowired
    private IPersonDao personDao;
    @Resource
    private IStaffMemberService staffMemberService;
    @Resource
    private IWeightService weightService;
    @Resource
    private IHeartRateService heartRateService;
    @Resource
    private IOccasionService occasionService;
    @Resource
    private PersonRepository personRepository;

    @Override
    @Transactional
    public boolean deletePerson(Long id) {
        Person person = findByIdOrThrowException(id);
        return deletePersonRelatedData(person);
    }

    private boolean deletePersonRelatedData(Person person) {
        try {
            Stream.of(staffMemberService, weightService, heartRateService, occasionService)
                    .forEach(service -> service.deleteByPerson(person));
            personRepository.deleteByPerson(person.getId());
            return true;
        } catch (HibernateException ex) {
            LOG.debug(ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    @Transactional
    public Person addPerson(String firstName, String secondName) {
        Person person = createPerson(firstName, secondName);
        personDao.savePerson(person);
        return person;
    }

    @Override
    public Collection<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Collection<Person> getByUser(String userName) {
        return personDao.getByUser(userName);
    }

    @Override
    //TODO: rewrite
    public Person getByName(String firstName, String secondName) {
        return personDao.getPersonByName(firstName, secondName).get(0);
    }

    private Person createPerson(String firstName, String secondName) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setSecondName(secondName);
        return person;
    }

    @Override
    public Person findByIdOrThrowException(Long personId) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new IllegalArgumentException(String.format(CAN_NOT_FIND_PERSON_BY_PERSON_ID_MESSAGE, personId)));
    }
}
