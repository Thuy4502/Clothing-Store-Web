package com.ptithcm.service;


import com.ptithcm.exception.UserException;
import com.ptithcm.model.Staff;
import com.ptithcm.request.StaffInfoRequest;

import java.text.ParseException;


public interface StaffService {

    Staff findStaffByUserId(Long user_id);

    Staff findStaffById(Long staff_id) throws UserException;

    Staff createStaff(Staff staff);

    Staff updateStaff(StaffInfoRequest staff, Long staff_id) throws UserException, ParseException;

}

