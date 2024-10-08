package com.ptithcm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="staff_id")
    private Long staffId;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="gender")
    private String gender;

    @Column(name="id_number")
    private String idNumber;

    @Column(name="email")
    private String email;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="address")
    private String address;

    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name="tax_id")
    private String taxId;

    @Column(name="hired_date")
    private LocalDate hiredDate;

    @Column
    private Long user_id;

    @OneToOne
    @JoinColumn(name="user_id",insertable = false,updatable = false)
    private User user;
    @JsonIgnore
    @OneToMany(mappedBy = "createdBy")
    private List<Brand> brands;
    @JsonIgnore
    @OneToMany(mappedBy = "createdBy")
    private List<Category> categories;
    @JsonIgnore
    @OneToMany(mappedBy = "createdBy")
    private List<Product> products;
    @JsonIgnore
    @OneToMany(mappedBy = "acceptedBy")
    private List<Order> ordersAccepted;

    @JsonIgnore
    @OneToMany(mappedBy = "staff_bill")
    private List<Bill> bills;
}
