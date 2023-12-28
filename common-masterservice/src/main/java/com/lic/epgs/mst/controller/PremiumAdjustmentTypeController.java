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

import com.lic.epgs.mst.entity.PremiumAdjustmentTypeMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.PremiumAdjustmentTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.PremiumAdjustmentTypeService;

@RestController
@CrossOrigin("*")
public class PremiumAdjustmentTypeController {

	private static final Logger logger = LoggerFactory.getLogger(PremiumAdjustmentTypeController.class);

	@Autowired
	private PremiumAdjustmentTypeService premiumAdjustmentTypeService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/premium")
	public List<PremiumAdjustmentTypeMaster> getAllPremium()
			throws ResourceNotFoundException, PremiumAdjustmentTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<PremiumAdjustmentTypeMaster> premium = premiumAdjustmentTypeService.getAllPremium();

			if (premium.isEmpty()) {
				logger.debug("inside  premium controller getAllPremium() method");
				logger.info("premium  list is empty ");
				throw new ResourceNotFoundException("premium not found");
			}
			return premium;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new PremiumAdjustmentTypeServiceException("internal server error");
		}
	}

	@GetMapping("/premium/{id}")
	public ResponseEntity<PremiumAdjustmentTypeMaster> getPremiumById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(premiumAdjustmentTypeService.getPremiumById(id));

	}

	@GetMapping("/premiumByCode/{code}")
	public ResponseEntity<PremiumAdjustmentTypeMaster> getPremiumByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(premiumAdjustmentTypeService.getpremiumByCode(code));

	}

}
