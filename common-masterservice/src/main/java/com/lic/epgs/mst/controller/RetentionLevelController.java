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

import com.lic.epgs.mst.entity.RetentionLevel;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.RetentionLevelServiceException;
import com.lic.epgs.mst.service.RetentionLevelService;

@RestController
@CrossOrigin("*")
public class RetentionLevelController {

	@Autowired
	private RetentionLevelService retentionService;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(RetentionLevelController.class);

	 @GetMapping("/retentions")
	public List<RetentionLevel> getAllRetention() throws ResourceNotFoundException, RetentionLevelServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<RetentionLevel> retentions = retentionService.getAllRetention();

			if (retentions.isEmpty()) {
				logger.debug("inside RetentionLevelController getAllRetention() method");
				logger.info("Retention Level list is empty ");
				throw new ResourceNotFoundException("Retention Level list is not found");
			}
			return retentions;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new RetentionLevelServiceException("internal server error");
		}
	}

	@GetMapping("/retentions/{id}")
	public ResponseEntity<RetentionLevel> getRetentionById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Retention Level search by id");
		return ResponseEntity.ok().body(retentionService.getRetentionById(id));
	}

	@GetMapping("/retentions/code/{code}")
	public ResponseEntity<RetentionLevel> getRetentionByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Retention Level search by code");
		return ResponseEntity.ok().body(retentionService.getRetentionByCode(code));
	}
}


