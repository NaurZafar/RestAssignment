package com.mark.rest.assignment.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "ORDER")
public class OrderEntity {

    @Id
    @Column(name = "REFRENCE_ID")
    private long referenceId;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "ORDER_DATE")
    private Date orderDate;

    @Column(name = "ORDER_DESC")
    private String orderDesc;

    @Column(name = "QUANTITY")
    private int quantity;

}
