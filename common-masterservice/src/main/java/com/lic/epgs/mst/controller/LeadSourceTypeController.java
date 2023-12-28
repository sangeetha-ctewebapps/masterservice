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

import com.lic.epgs.mst.entity.LeadSourceTypeMaster;
import com.lic.epgs.mst.exceptionhandling.LeadSourceTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.LeadSourceTypeService;

@RestController
@CrossOrigin("*")
public class LeadSourceTypeController {

	private static final Logger logger = LoggerFactory.getLogger(LeadSourceTypeController.class);

	@Autowired
	private LeadSourceTypeService leadSourceTypeService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/leadSourceType")
	public List<LeadSourceTypeMaster> getAllLeadSourceType()
			throws ResourceNotFoundException, LeadSourceTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<LeadSourceTypeMaster> leadSourceType = leadSourceTypeService.getAllLeadSourceType();

			if (leadSourceType.isEmpty()) {
				logger.debug("inside lead source type controller getAllLeadSourceType() method");
				logger.info("lead source type list is empty ");
				throw new ResourceNotFoundException("lead source type not found");
			}
			return leadSourceType;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new LeadSourceTypeServiceException("internal server error");
		}
	}

	@GetMapping("/leadSourceType/{id}")
	public ResponseEntity<LeadSourceTypeMaster> getLeadSourceTypeById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(leadSourceTypeService.getLeadSourceTypeById(id));

	}

	@GetMapping("/leadSourceTypeByCode/{code}")
	public ResponseEntity<LeadSourceTypeMaster> getLeadSourceTypeByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(leadSourceTypeService.getLeadSourceTypeByCode(code));

	}

}
