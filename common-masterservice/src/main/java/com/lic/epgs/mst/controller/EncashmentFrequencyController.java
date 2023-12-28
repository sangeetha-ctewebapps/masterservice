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

import com.lic.epgs.mst.entity.EncashmentFrequencyMaster;
import com.lic.epgs.mst.exceptionhandling.EncashmentFrequencyServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.EncashmentFrequencyService;

@RestController
@CrossOrigin("*")
public class EncashmentFrequencyController {

	private static final Logger logger = LoggerFactory.getLogger(EncashmentFrequencyController.class);

	@Autowired
	private EncashmentFrequencyService encashmentFrequencyService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/encashmentFrequency")
	public List<EncashmentFrequencyMaster> getAllEncashmentFrequency()
			throws ResourceNotFoundException, EncashmentFrequencyServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<EncashmentFrequencyMaster> encashmentFrequency = encashmentFrequencyService
					.getAllEncashmentFrequency();

			if (encashmentFrequency.isEmpty()) {
				logger.debug("inside encashment Frequency controller getAllAccountFrequency() method");
				logger.info("encashment Frequency list is empty ");
				throw new ResourceNotFoundException("encashment Frequency not found");
			}
			return encashmentFrequency;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new EncashmentFrequencyServiceException("internal server error");
		}
	}

	@GetMapping("/encashmentFrequency/{id}")
	public ResponseEntity<EncashmentFrequencyMaster> getEncashmentFrequencyById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(encashmentFrequencyService.getEncashmentFrequencyById(id));

	}

	@GetMapping("/encashmentFrequencyByCode/{code}")
	public ResponseEntity<EncashmentFrequencyMaster> getEncashmentFrequencyByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(encashmentFrequencyService.getEncashmentFrequencyByCode(code));

	}

}
