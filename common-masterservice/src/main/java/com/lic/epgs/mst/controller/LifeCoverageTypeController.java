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

import com.lic.epgs.mst.entity.LifeCoverageTypeMst;
import com.lic.epgs.mst.exceptionhandling.LifeCoverageTypeException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.LifeCoverageTypeService;

@RestController
@CrossOrigin("*")
public class LifeCoverageTypeController {

	@Autowired
	private LifeCoverageTypeService lifecoveragetypeservice;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(LifeCoverageTypeController.class);

	  @GetMapping("/LifeCoverageType")
	public List<LifeCoverageTypeMst> getAllLifeCoverageType()
			throws ResourceNotFoundException, LifeCoverageTypeException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<LifeCoverageTypeMst> gender = lifecoveragetypeservice.getAllLifeCoverageType();

			if (gender.isEmpty()) {
				logger.debug("inside modeofeixtcontroller getAllLifeCoverageType() method");
				logger.info("lifecoveragetype list is empty ");
				throw new ResourceNotFoundException("lifecoveragetype not found");
			}
			return gender;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new LifeCoverageTypeException("internal server error");
		}
	}

	@GetMapping("/LifeCoverageType/{id}")
	public ResponseEntity<LifeCoverageTypeMst> getLifeCoverageTypeById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(lifecoveragetypeservice.getLifeCoverageTypeById(id));
	}

	@GetMapping("/LifeCoverageTypeByCode/{code}")
	public ResponseEntity<LifeCoverageTypeMst> getLifeCoverageTypeByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(lifecoveragetypeservice.getLifeCoverageTypeByCode(code));

	}
}
