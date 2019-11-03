package com.mark.rest.assignment.app.service;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.mark.rest.assignment.app.entity.OrderEntity;
import com.mark.rest.assignment.app.model.CustomerOrder;
import com.mark.rest.assignment.app.repo.OrderRepository;
import com.mark.rest.assignment.app.util.ApplicationUtil;

@RunWith(SpringRunner.class)
public class CustomerOrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CustomerOrderServiceImpl customerOrderServiceImpl;

    @Test
    public void shouldCreateCustomerOrder() {
        OrderEntity entity = buildSingleOrderEntity().get();

        when(orderRepository.save(entity)).thenReturn(entity);

        when(orderRepository.getNextReferenceId()).thenReturn(65432L);

        long referenceId = customerOrderServiceImpl
                .saveCustomerOrder(buildSingleOrder());

        assertNotNull(referenceId);
        assertEquals(entity.getReferenceId(), referenceId);

    }

    @Test
    public void shouldGetCustomerOrderByReferenceId() {
        when(orderRepository.findById(65432L))
                .thenReturn(buildSingleOrderEntity());

        CustomerOrder actualOrder = customerOrderServiceImpl
                .getOrderByReferenceId(65432L);

        assertNotNull(actualOrder);
        assertEquals(buildSingleOrder(), actualOrder);

    }

    @Test
    public void shouldGetAllOrdersDetails() {
        List<CustomerOrder> expectedOrderList = buildCustomerOrderList();

        when(orderRepository.findAll()).thenReturn(buildOrderEntityList());

        List<CustomerOrder> actualOrderList = customerOrderServiceImpl
                .getAllOrderDetails();

        assertNotNull(actualOrderList);
        assertThat(actualOrderList.size(), Is.is(2));
        assertThat(actualOrderList, containsInAnyOrder(expectedOrderList.get(0),
                expectedOrderList.get(1)));
    }

    @Test
    public void shouldUpdateCustomerOrder() {

        when(orderRepository.existsById(65432L)).thenReturn(true);

        doNothing().when(orderRepository).updateCustomerOrder(42, 65432L);

        boolean status = customerOrderServiceImpl.updateCustomerOrder(65432L,
                42);

        assertEquals(true, status);

    }

    private List<CustomerOrder> buildCustomerOrderList() {
        List<CustomerOrder> customerOrders = new ArrayList<CustomerOrder>();

        CustomerOrder order1 = CustomerOrder.builder().referenceId(65432L)
                .customerId("C255")
                .orderDate(ApplicationUtil
                        .convertLocalDateToDate(LocalDate.of(2019, 10, 22)))
                .orderDesc("First Order Created").quantity(20).build();

        CustomerOrder order2 = CustomerOrder.builder().referenceId(65433L)
                .customerId("C254")
                .orderDate(ApplicationUtil
                        .convertLocalDateToDate(LocalDate.of(2019, 10, 12)))
                .orderDesc("First Order Created").quantity(42).build();

        customerOrders.add(order1);
        customerOrders.add(order2);

        return customerOrders;
    }

    private List<OrderEntity> buildOrderEntityList() {

        List<OrderEntity> orderEntities = new ArrayList<OrderEntity>();

        OrderEntity entity1 = OrderEntity.builder().referenceId(65432L)
                .customerId("C255")
                .orderDate(ApplicationUtil
                        .convertLocalDateToDate(LocalDate.of(2019, 10, 22)))
                .orderDesc("First Order Created").quantity(20).build();

        OrderEntity entity2 = OrderEntity.builder().referenceId(65433L)
                .customerId("C254")
                .orderDate(ApplicationUtil
                        .convertLocalDateToDate(LocalDate.of(2019, 10, 12)))
                .orderDesc("First Order Created").quantity(42).build();

        orderEntities.add(entity1);
        orderEntities.add(entity2);
        return orderEntities;

    }

    private CustomerOrder buildSingleOrder() {
        return CustomerOrder.builder().referenceId(65432L).customerId("C255")
                .orderDate(ApplicationUtil
                        .convertLocalDateToDate(LocalDate.of(2019, 10, 22)))
                .orderDesc("First Order Created").quantity(20).build();

    }

    private Optional<OrderEntity> buildSingleOrderEntity() {
        return Optional.of(OrderEntity.builder().referenceId(65432L)
                .customerId("C255")
                .orderDate(ApplicationUtil
                        .convertLocalDateToDate(LocalDate.of(2019, 10, 22)))
                .orderDesc("First Order Created").quantity(20).build());

    }

}
