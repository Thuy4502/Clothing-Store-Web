package com.ptithcm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="promotion")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="promotion_id")
    private Long promotionId;

    @Column(name="promotion_name")
    private String promotionName;

    @Column(name="status")
    private String status;

    @ManyToOne
    @JoinColumn(name="staff_id")
    private Staff staff;

}
