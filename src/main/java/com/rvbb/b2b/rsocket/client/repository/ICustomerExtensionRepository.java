package com.rvbb.b2b.rsocket.client.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerExtensionRepository{
	String getSomeComplexityData();
}
