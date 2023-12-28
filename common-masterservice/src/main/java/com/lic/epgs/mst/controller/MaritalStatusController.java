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

import com.lic.epgs.mst.entity.MaritalStatus;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.MaritalStatusServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.MaritalStatusService;

@RestController
@CrossOrigin("*")
public class MaritalStatusController {

	@Autowired
	private MaritalStatusService maritalService;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(MaritalStatusController.class);

	 @GetMapping("/marital")
	public List<MaritalStatus> getAllMaritalStatus() throws ResourceNotFoundException, MaritalStatusServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<MaritalStatus> maritals = maritalService.getAllMarital();

			if (maritals.isEmpty()) {
				logger.debug("inside maritalController getAllMaritalStatus() method");
				logger.info("marital status list is empty ");
				throw new ResourceNotFoundException("marital status not found");
			}
			return maritals;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new MaritalStatusServiceException("internal server error");
		}
	}

	@GetMapping("/marital/{id}")
	public ResponseEntity<MaritalStatus> getMaritalById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("MaritalStatus search by id");
		return ResponseEntity.ok().body(maritalService.getMaritalById(id));
	}

	@GetMapping("/marital/code/{code}")
	public ResponseEntity<MaritalStatus> getMaritalByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("MaritalStatus search by code");
		return ResponseEntity.ok().body(maritalService.getMaritalByCode(code));
	}

}
