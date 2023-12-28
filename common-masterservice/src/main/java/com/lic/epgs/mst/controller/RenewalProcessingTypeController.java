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
import com.lic.epgs.mst.entity.RenewalProcessingType;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.RenewalProcessingTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.RenewalProcessingTypeService;

@RestController
@CrossOrigin("*")
public class RenewalProcessingTypeController {

	@Autowired
	private RenewalProcessingTypeService renewalService;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(RenewalProcessingTypeController.class);

	 @GetMapping("/renewals")
	public List<RenewalProcessingType> getAllRenewalType()
			throws ResourceNotFoundException, RenewalProcessingTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<RenewalProcessingType> renewals = renewalService.getAllRenewal();

			if (renewals.isEmpty()) {
				logger.debug("inside renewalController getAllRenewalType() method");
				logger.info("Renewal Processing Type list is empty ");
				throw new ResourceNotFoundException("Renewal Processing Type not found");
			}
			return renewals;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new RenewalProcessingTypeServiceException("internal server error");
		}
	}

	@GetMapping("/renewals/{id}")
	public ResponseEntity<RenewalProcessingType> getRenewalById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Renewal Processing Type search by id");
		return ResponseEntity.ok().body(renewalService.getRenewalById(id));
	}

	@GetMapping("/renewals/code/{code}")
	public ResponseEntity<RenewalProcessingType> getRenewalByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Renewal Processing Type search by code");
		return ResponseEntity.ok().body(renewalService.getRenewalByCode(code));
	}

}
