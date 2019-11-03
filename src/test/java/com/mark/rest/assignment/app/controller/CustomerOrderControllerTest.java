package com.mark.rest.assignment.app.controller;

import static com.mark.rest.assignment.app.util.ApplicationUtil.convertObjectToJsonString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mark.rest.assignment.app.model.CustomerOrder;
import com.mark.rest.assignment.app.service.CustomerOrderServiceImpl;
import com.mark.rest.assignment.app.util.ApplicationUtil;

@RunWith(SpringRunner.class)
public class CustomerOrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerOrderServiceImpl customerOrderService;

    @InjectMocks
    private CustomerOrderController customerOrderController;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerOrderController)
                .build();
    }

    @Test
    public void shouldCreateCustomerOrder_WhenAPICalled() throws Exception {
        CustomerOrder order = buildSingleOrder();

        when(customerOrderService.saveCustomerOrder(order)).thenReturn(65432L);

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(order)))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
        assertNotNull(response.getContentAsString());
    }

    @Test
    public void shouldGetAllCustomerOrders_whenAPICalled() throws Exception {

        when(customerOrderService.getAllOrderDetails())
                .thenReturn(buildCustomerOrderList());

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertNotNull(response.getContentAsString());

    }

    @Test
    public void shouldGetCustomerByReferenceId_WhenAPICalled()
            throws Exception {

        when(customerOrderService.getOrderByReferenceId(65432L))
                .thenReturn(buildSingleOrder());

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/orders/65432")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertNotNull(response.getContentAsString());

    }

    @Test
    public void shouldUpdateCustomerOder_WhenAPICalled() throws Exception {
        when(customerOrderService.updateCustomerOrder(65432L, 42))
                .thenReturn(true);

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.put("/orders")
                        .param("referenceId", "65432").param("quantity", "42")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertNotNull(response.getContentAsString());

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

    private CustomerOrder buildSingleOrder() {
        return CustomerOrder.builder().referenceId(65432L).customerId("C255")
                .orderDate(ApplicationUtil
                        .convertLocalDateToDate(LocalDate.of(2019, 10, 22)))
                .orderDesc("First Order Created").quantity(20).build();

    }

}
