package com.sergtm.service;

import com.sergtm.entities.StaffMember;
import com.sergtm.entities.User;

public interface IStaffMemberService extends IDeletableByPersonService {
    StaffMember add(int userId, int personId);
    StaffMember getByUser(User user);
    StaffMember addStaffMemberByUsernameAndPersonName(String username, String firstName, String lastName);
    StaffMember addPatientForStaffMember(String username, String firstName, String lastName);
}
