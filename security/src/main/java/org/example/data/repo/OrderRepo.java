package org.example.data.repo;

import org.example.data.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {

    Iterable<Order> findAllByCustomerId(long customerId);
}
