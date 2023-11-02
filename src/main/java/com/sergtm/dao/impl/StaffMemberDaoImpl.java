package com.sergtm.dao.impl;

import com.sergtm.dao.IStaffMemberDao;
import com.sergtm.entities.Person;
import com.sergtm.entities.StaffMember;
import com.sergtm.entities.User;
import com.sergtm.repository.StaffMemberRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.Query;
import java.util.Optional;

@Repository
public class StaffMemberDaoImpl implements IStaffMemberDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Resource
    private StaffMemberRepository staffMemberRepository;

    @Override
    public Optional<StaffMember> getByUser(User user) {
        String sql = "FROM StaffMember s WHERE s.user.id = :user_id";
        Query query = sessionFactory.getCurrentSession().createQuery(sql);
        query.setParameter("user_id", user.getId());

        return query.getResultList().stream().findFirst();
    }

    @Override
    @Transactional
    public void save(StaffMember staffMember) {
        sessionFactory.getCurrentSession().save(staffMember);
    }

    @Override
    public void deleteByPerson(Person person) {
        staffMemberRepository.deleteByPerson(person);
    }
}
