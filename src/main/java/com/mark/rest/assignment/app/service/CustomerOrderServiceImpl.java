package com.mark.rest.assignment.app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mark.rest.assignment.app.entity.OrderEntity;
import com.mark.rest.assignment.app.model.CustomerOrder;
import com.mark.rest.assignment.app.repo.OrderRepository;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public long saveCustomerOrder(CustomerOrder order) {
        OrderEntity orderEntity = orderRepository.save(buildOrderEntity(order));
        return orderEntity.getReferenceId();
    }

    @Override
    public CustomerOrder getOrderByReferenceId(long referenceId) {
        Optional<OrderEntity> orderEntity = orderRepository
                .findById(referenceId);

        return orderEntity.isPresent() ? buildCustomerOrder(orderEntity.get())
                : new CustomerOrder();

    }

    @Override
    public List<CustomerOrder> getAllOrderDetails() {
        return orderRepository.findAll().stream().map(this::buildCustomerOrder)
                .collect(Collectors.toList());
    }

    private OrderEntity buildOrderEntity(CustomerOrder order) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(order, orderEntity);
        orderEntity.setReferenceId(orderRepository.getNextReferenceId());
        return orderEntity;
    }

    private CustomerOrder buildCustomerOrder(OrderEntity orderEntity) {
        CustomerOrder order = new CustomerOrder();
        BeanUtils.copyProperties(orderEntity, order);
        return order;

    }

    @Override
    @Transactional
    public boolean updateCustomerOrder(long referenceId, int quantity) {
        boolean status = false;
        if (isCustomerOrderExist(referenceId)) {
            orderRepository.updateCustomerOrder(quantity, referenceId);
            status = true;
        }
        return status;
    }

    @Override
    public boolean isCustomerOrderExist(long referenceId) {
        return orderRepository.existsById(referenceId);

    }

}
