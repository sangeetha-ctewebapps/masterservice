package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.CustomerTypeController;
import com.lic.epgs.mst.entity.CustomerType;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.CustomerTypeRepository;

@Service
@Transactional
public class CustomerTypeServiceImpl implements CustomerTypeService {

	@Autowired
	private CustomerTypeRepository customerRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(CustomerTypeController.class);

	@Override
	public List<CustomerType> getAllCustomer() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get CustomerType list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return customerRepository.findAll();
	}

	@Override
	public CustomerType getCustomerById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<CustomerType> customerDb = this.customerRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for CustomerType By Id: " + id);
		if (customerDb.isPresent()) {
			logger.info("CustomerType is  found with id: " + id);
			return customerDb.get();
		} else {
			throw new ResourceNotFoundException("CustomerType not found with id: " + id);
		}
	}

	@Override
	public CustomerType getCustomerByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<CustomerType> customerDb = this.customerRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for CustomerType By code: " + code);
		if (customerDb.isPresent()) {
			logger.info("CustomerType is  found with code: " + code);
			return customerDb.get();
		} else {
			throw new ResourceNotFoundException("CustomerType not found with code: " + code);
		}
	}

}
