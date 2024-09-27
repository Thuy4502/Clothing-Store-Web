package com.ptithcm.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StaffInfoRequest { // Đổi tên từ customerId thành staffId
    private String firstName;
    private String lastName;
    private String gender;
    private String idNumber;
    private String email;
    private String phoneNumber;
    private String address;  // Thêm @Column cho address nếu cần
    private String taxId;
    private LocalDate hiredDate;
    private LocalDate dateOfBirth;
}
