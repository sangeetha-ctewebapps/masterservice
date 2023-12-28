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

import com.lic.epgs.mst.entity.CustomerType;
import com.lic.epgs.mst.exceptionhandling.CustomerTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.CustomerTypeService;

@RestController
@CrossOrigin("*")
public class CustomerTypeController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerTypeController.class);

	@Autowired
	private CustomerTypeService customertypeService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/CustomerType")
	public List<CustomerType> getAllCustomerType() throws ResourceNotFoundException, CustomerTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<CustomerType> customertype = customertypeService.getAllCustomer();

			if (customertype.isEmpty()) {
				logger.debug("inside Customer Type controller getAllCustomerType() method");
				logger.info("CustomerType list is empty ");
				throw new ResourceNotFoundException("CustomerType not found");
			}
			return customertype;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new CustomerTypeServiceException("internal server error");
		}
	}

	@GetMapping("/CustomerType/{id}")
	public ResponseEntity<CustomerType> getCustomerTypeById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(customertypeService.getCustomerById(id));

	}

	@GetMapping("/CustomerTypeByCode/{code}")
	public ResponseEntity<CustomerType> getCustomerTypeByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(customertypeService.getCustomerByCode(code));

	}

}
