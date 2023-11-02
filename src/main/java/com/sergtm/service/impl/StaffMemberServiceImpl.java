package com.sergtm.service.impl;

import com.sergtm.dao.IStaffMemberDao;
import com.sergtm.entities.Person;
import com.sergtm.entities.StaffMember;
import com.sergtm.entities.User;
import com.sergtm.service.IPersonService;
import com.sergtm.service.IStaffMemberService;
import com.sergtm.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class StaffMemberServiceImpl implements IStaffMemberService {
    @Resource
    private IStaffMemberDao staffMemberDao;
    @Resource
    private IUserService userService;
    @Resource
    private IPersonService personService;

    @Override
    public StaffMember add(int userId, int personId) {
        return null;
    }

    @Override
    public StaffMember getByUser(User user) {
        Optional<StaffMember> staffMember = staffMemberDao.getByUser(user);
        return staffMember.orElse(null);
    }

    @Override
    @Transactional
    public StaffMember addStaffMemberByUsernameAndPersonName(String username, String firstName, String secondName) {
        User user = userService.findUserByUsername(username);
        Person person = personService.getByName(firstName, secondName);

        StaffMember staffMember = new StaffMember();
        staffMember.setPerson(person);
        staffMember.setUser(user);

        staffMemberDao.save(staffMember);
        return staffMember;
    }

    @Override
    public StaffMember addPatientForStaffMember(String username, String firstName, String lastName) {
        User user = userService.findUserByUsername(username);
        Person person = personService.getByName(firstName, lastName);

        StaffMember staffMember = staffMemberDao.getByUser(user).get();
        staffMember.getPatients().add(person);

        return staffMember;
    }

    @Override
    public void deleteByPerson(Person person) {
        staffMemberDao.deleteByPerson(person);
    }
}
