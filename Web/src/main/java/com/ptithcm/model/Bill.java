package com.ptithcm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long bill_id;

    @Column
    private LocalDateTime created_at;

    @Column
    private Long created_by;

    @Column
    private Long order_id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "created_by",insertable = false , updatable = false)
    private Staff staff_bill;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "order_id",insertable = false, updatable = false)
    private Order orders;
}
