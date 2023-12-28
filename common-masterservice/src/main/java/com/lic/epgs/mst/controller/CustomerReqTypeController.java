package com.lic.epgs.mst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.CustomerReqType;
import com.lic.epgs.mst.exceptionhandling.CustomerReqTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.CustomerReqTypeService;

@RestController
@CrossOrigin("*")
public class CustomerReqTypeController {

	@Autowired
	private CustomerReqTypeService customerService;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(CustomerReqTypeController.class);

	@GetMapping("/customer")
	 public List<CustomerReqType> getAllCustomerReqTypes()
			throws ResourceNotFoundException, CustomerReqTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<CustomerReqType> customers = customerService.getAllCustomer();

			if (customers.isEmpty()) {
				logger.debug("inside customerController getAllCustomer() method");
				logger.info("customer required type list is empty ");
				throw new ResourceNotFoundException("customer equired type not found");
			}
			return customers;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new CustomerReqTypeServiceException("internal server error");
		}
	}

	@GetMapping("/customer/{id}")
	public ResponseEntity<CustomerReqType> getCustomerById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Customer Req Type search by id");
		return ResponseEntity.ok().body(customerService.getCustomerById(id));

	}

	@GetMapping("/customer/code/{code}")
	public ResponseEntity<CustomerReqType> getCustomerByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Customer Req Type search by Code");
		return ResponseEntity.ok().body(customerService.getCustomerByCode(code));
	}

}
