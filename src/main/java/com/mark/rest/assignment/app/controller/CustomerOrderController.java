package com.mark.rest.assignment.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mark.rest.assignment.app.model.CustomerOrder;
import com.mark.rest.assignment.app.service.CustomerOrderServiceImpl;

@RestController
@RequestMapping("/orders")
public class CustomerOrderController {

    @Autowired
    private CustomerOrderServiceImpl custOrderService;

    @PostMapping
    public ResponseEntity<Long> createCustomerOrder(
            @RequestBody CustomerOrder order) {
        long referenceId = custOrderService.saveCustomerOrder(order);
        return new ResponseEntity<Long>(referenceId, HttpStatus.CREATED);
    }

    @GetMapping("/{referenceId}")
    public ResponseEntity<?> getCustomerOrder(
            @PathVariable String referenceId) {
        CustomerOrder custOrder = custOrderService
                .getOrderByReferenceId(Long.parseLong(referenceId));
        if (custOrder.getReferenceId() == 0) {
            return new ResponseEntity<CustomerOrder>(custOrder,
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CustomerOrder>(custOrder, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomerOrders() {
        List<CustomerOrder> customerOrders = custOrderService
                .getAllOrderDetails();
        if (customerOrders.isEmpty()) {
            return new ResponseEntity<List<CustomerOrder>>(customerOrders,
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<CustomerOrder>>(customerOrders,
                HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<String> updateCustomerOrder(
            @RequestParam(value = "referenceId",
                    required = false) String referenceId,
            @RequestParam(value = "quantity",
                    required = false) String quantity) {
        if (custOrderService.updateCustomerOrder(Long.parseLong(referenceId),
                Integer.parseInt(quantity))) {
            return new ResponseEntity<String>(
                    "Customer Order Succesfully Updated for ReferenceId: "
                            + referenceId,
                    HttpStatus.OK);
        }
        return new ResponseEntity<String>(
                "Customer Order failed to update for ReferenceId: "
                        + referenceId,
                HttpStatus.NOT_FOUND);

    }

}
