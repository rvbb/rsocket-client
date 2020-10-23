package com.rvbb.b2b.rsocket.client.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.rvbb.b2b.rsocket.client.entity.CustomerEntity;
import com.rvbb.b2b.rsocket.client.exception.NotFoundException;
import com.rvbb.b2b.rsocket.client.dto.Customer;
import com.rvbb.b2b.rsocket.client.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	
	@Autowired 
	private ICustomerRepository repository;

	public CustomerEntity insert(Customer customer) {
		return repository.save(toEntity(customer));
	}

	public CustomerEntity update(Customer customer, Long id) {
		CustomerEntity entity = getCustomerById(id);
		entity.setName(customer.getName());
		entity.setAge(customer.getAge());
		entity.setPhone(customer.getPhone());
		return repository.save(entity);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}	

	public List<CustomerEntity> list() {
		return (List<CustomerEntity>) repository.findAll();
	}

	public CustomerEntity getCustomerById(long id) {
		Optional<CustomerEntity> optionalCustomer = repository.findById(id);
		return optionalCustomer.orElseThrow(() -> new NotFoundException("Customer with id: " + id + " could not discovered"));
	}

	private CustomerEntity toEntity(Customer customer) {
		CustomerEntity entity = new CustomerEntity();
		entity.setName(customer.getName());
		entity.setAge(customer.getAge());
		entity.setPhone(customer.getPhone());
		entity.setCreated(new Date());
		
		return entity;
	}

}
