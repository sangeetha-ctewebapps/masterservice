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

import com.lic.epgs.mst.entity.TypeOfService;
import com.lic.epgs.mst.exceptionhandling.TypeOfServiceServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.TypeOfServiceService;

@RestController
@CrossOrigin("*")
public class TypeOfServiceController {
	@Autowired
	private TypeOfServiceService typeOfServiceService;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(TypeOfServiceController.class);

	 @GetMapping("/TypeOfService")
	public List<TypeOfService> getAllTypeOfService() throws ResourceNotFoundException, TypeOfServiceServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		try {
			List<TypeOfService> customeres = typeOfServiceService.getAllTypeOfService();
			if (customeres.isEmpty()) {
				logger.debug("Getting All Customer Type");
				throw new ResourceNotFoundException("Customer Type is not found");
			}
			return customeres;
		} catch (Exception ex) {
			throw new TypeOfServiceServiceException("Type Of Service is not found");
		}
	}

	@GetMapping("/TypeOfServiceById/{id}")
	public ResponseEntity<TypeOfService> getTypeOfServiceById(@PathVariable Long id) throws TypeOfServiceServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new customer Type By Id");
			return ResponseEntity.ok().body(typeOfServiceService.getTypeOfServiceById(id));
		} catch (Exception e) {
			logger.error("Type Of Service is not found with ID : Invalid Data", e);
			throw new TypeOfServiceServiceException("Type Of Service is not found with ID" + id);
		}
	}

	@GetMapping("/TypeOfServiceByCode/{code}")
	public ResponseEntity<TypeOfService> getTypeOfServiceByCode(@PathVariable String code)
			throws TypeOfServiceServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new customer Type By code");
			return ResponseEntity.ok().body(typeOfServiceService.findByTypeOfServiceCode(code));
		} catch (Exception e) {
			logger.error("Type Of Service is not found with code : Invalid Data", e);
			throw new TypeOfServiceServiceException("Type Of Service is not found with code" + code);
		}
	}
}
