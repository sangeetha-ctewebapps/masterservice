package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.CustomerReqTypeController;
import com.lic.epgs.mst.entity.CustomerReqType;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.CustomerReqTypeRepo;

@Service
@Transactional
public class CustomerReqTypeServiceImpl implements CustomerReqTypeService {
	@Autowired
	CustomerReqTypeRepo customerRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(CustomerReqTypeController.class);

	@Override
	//@Cacheable()
	public List<CustomerReqType> getAllCustomer() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return customerRepository.findAll();
	}

	@Override
	public CustomerReqType getCustomerById(long customerId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<CustomerReqType> customerDb = this.customerRepository.findById(customerId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for customer By Id" + customerId);
		if (customerDb.isPresent()) {
			logger.info("customer type is not found with id" + customerId);
			return customerDb.get();
		} else {
			throw new ResourceNotFoundException("customer not found with id:" + customerId);
		}
	}

	@Override
	public CustomerReqType getCustomerByCode(String customerCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<CustomerReqType> customerDb = this.customerRepository.findByCustomerCode(customerCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for customer By Code" + customerCode);
		if (customerDb.isPresent()) {
			logger.info("customer type is not found with code" + customerCode);
			return customerDb.get();
		} else {
			throw new ResourceNotFoundException("customer not found with code:" + customerCode);
		}
	}
}
