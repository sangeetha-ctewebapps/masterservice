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

import com.lic.epgs.mst.entity.IndustryType;
import com.lic.epgs.mst.exceptionhandling.IndustryTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.IndustryTypeService;

@RestController
@CrossOrigin("*")
public class IndustryTypeController {

	@Autowired
	private IndustryTypeService industryService;

	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(IndustryTypeController.class);

	 @GetMapping("/industry")
	public List<IndustryType> getAllIndustryType() throws ResourceNotFoundException, IndustryTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<IndustryType> industry = industryService.getAllIndustryType();

			if (industry.isEmpty()) {
				logger.debug("inside industrytype controller getAllIndustryType() method");
				logger.info("industry type list is empty ");
				throw new ResourceNotFoundException("industry type not found");
			}
			return industry;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new IndustryTypeServiceException("internal server error");
		}
	}

	@GetMapping("/industry/{id}")
	public ResponseEntity<IndustryType> getIndustryTypeById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Industry Type search by id");
		return ResponseEntity.ok().body(industryService.getIndustryTypeById(id));
	}

	@GetMapping("/industry/code/{code}")
	public ResponseEntity<IndustryType> getIndustryTypeByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Industry Type search by code");
		return ResponseEntity.ok().body(industryService.getIndustryTypeByCode(code));
	}
}