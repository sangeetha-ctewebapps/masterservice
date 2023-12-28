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

import com.lic.epgs.mst.entity.CoverageType;
import com.lic.epgs.mst.exceptionhandling.CoverageTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.CoverageTypeService;

@RestController
@CrossOrigin("*")
public class CoverageTypeController {
	@Autowired
	private CoverageTypeService coveragetypeservice;
	String className = this.getClass().getSimpleName();

	 private static final Logger logger = LoggerFactory.getLogger(CoverageTypeController.class);

	@GetMapping("/coverageType")
	public List<CoverageType> getAllCoverageType() throws ResourceNotFoundException, CoverageTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		try {
			List<CoverageType> coveragees = coveragetypeservice.getAllCoverageType();
			if (coveragees.isEmpty()) {
				logger.debug("Getting All Coverage Type");
				throw new ResourceNotFoundException("Coverage Type is not found");
			}
			return coveragees;
		} catch (Exception ex) {
			throw new CoverageTypeServiceException("Coverage type is not found");
		}
	}

	@GetMapping("/coverageTypeById/{id}")
	public ResponseEntity<CoverageType> getCoverageTypeById(@PathVariable Long id) throws CoverageTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new coverage Type By Id");
			return ResponseEntity.ok().body(coveragetypeservice.getCoverageTypeById(id));
		} catch (Exception e) {
			logger.error("Coverage type is not found with ID : Invalid Data", e);
			throw new CoverageTypeServiceException("Coverage type is not found with ID" + id);
		}
	}

	@GetMapping("/coverageTypeByCode/{code}")
	public ResponseEntity<CoverageType> getCoverageTypeByCode(@PathVariable String code)
			throws CoverageTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new coverage Type By code");
			return ResponseEntity.ok().body(coveragetypeservice.findByCoverageCode(code));
		} catch (Exception e) {
			logger.error("Coverage type is not found with code : Invalid Data", e);
			throw new CoverageTypeServiceException("Coverage type is not found with code" + code);
		}
	}
}
