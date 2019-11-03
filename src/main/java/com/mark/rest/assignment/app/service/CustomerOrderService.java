package com.mark.rest.assignment.app.service;

import java.util.List;

import com.mark.rest.assignment.app.model.CustomerOrder;

public interface CustomerOrderService {

    public long saveCustomerOrder(CustomerOrder order);

    public CustomerOrder getOrderByReferenceId(long referenceId);

    public List<CustomerOrder> getAllOrderDetails();

    public boolean updateCustomerOrder(long referenceId, int quantity);

    public boolean isCustomerOrderExist(long referenceId);

}
