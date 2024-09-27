package com.ptithcm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;
    private String username;
    private String password;
    private String email;
    @Column(name="role_id")
    private Long roleId;
    @Column(name="create_at")
    private LocalDateTime createdAt;
    private String status;

    @Column(name="updated_at")
    private LocalDateTime updated_at;

    @Column
    private int point;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "role_id",insertable = false,updatable = false)
    private Role role;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Customer customer;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Staff staff;
}
