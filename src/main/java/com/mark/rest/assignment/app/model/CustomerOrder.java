package com.mark.rest.assignment.app.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomerOrder {

    private long referenceId;
    private String customerId;
    private Date orderDate;
    private String orderDesc;
    private int quantity;

}
