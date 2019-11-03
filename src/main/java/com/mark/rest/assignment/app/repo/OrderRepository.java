package com.mark.rest.assignment.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mark.rest.assignment.app.entity.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(value = "SELECT NEXT VALUE FOR reference_id_seq", nativeQuery = true)
    Long getNextReferenceId();

    @Modifying
    @Query("update OrderEntity set quantity=:quantity WHERE referenceId=:referId")
    public void updateCustomerOrder(@Param("") int quantity, @Param("referId") long referId);

}
