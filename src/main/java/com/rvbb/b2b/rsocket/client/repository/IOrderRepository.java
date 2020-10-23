package com.rvbb.b2b.rsocket.client.repository;

import com.rvbb.b2b.rsocket.client.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends CrudRepository<OrderEntity, Long> {
}
