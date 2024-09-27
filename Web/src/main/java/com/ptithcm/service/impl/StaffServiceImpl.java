package com.ptithcm.service.impl;
import com.ptithcm.exception.UserException;
import com.ptithcm.model.Staff;
import com.ptithcm.repository.StaffRepository;
import com.ptithcm.request.StaffInfoRequest;
import com.ptithcm.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;


import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepo;

    @Override
    public Staff findStaffByUserId(Long userId) {
        return  staffRepo.findStaffByUserId(userId);
    }

    @Override
    public Staff findStaffById(Long staff_id) throws UserException {
        Optional<Staff> staff = staffRepo.findById(staff_id);

        if (staff.isPresent()) {
            return staff.get();
        }
        throw new UserException("Staff not found with id " + staff_id);
    }

    @Override
    public Staff createStaff(Staff staff) {
        return staffRepo.save(staff);
    }


    @Override
    public 	Staff updateStaff(StaffInfoRequest staff, Long staff_id) throws UserException, ParseException {
        Staff findStaff = findStaffById(staff_id);
        findStaff.setAddress(staff.getAddress());

        findStaff.setDateOfBirth(staff.getDateOfBirth());
        findStaff.setDateOfBirth(staff.getDateOfBirth());
        findStaff.setEmail(staff.getEmail());
        findStaff.setFirstName(staff.getFirstName());
        findStaff.setLastName(staff.getLastName());
        findStaff.setTaxId(staff.getTaxId());
//        findStaff.setUp(LocalDateTime.now());
        Staff savedStaff = staffRepo.save(findStaff);
        return savedStaff;
    }
}
