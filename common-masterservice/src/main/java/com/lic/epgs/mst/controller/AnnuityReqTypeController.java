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

import com.lic.epgs.mst.entity.AnnuityReqType;
import com.lic.epgs.mst.exceptionhandling.AnnuityReqTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.AnnuityReqTypeService;

@RestController
@CrossOrigin("*")
public class AnnuityReqTypeController {

	@Autowired
	private AnnuityReqTypeService annuityService;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(AnnuityReqTypeController.class);

	 @GetMapping("/annuity")
	public List<AnnuityReqType> getAllAnnuityReqTypes()
			throws ResourceNotFoundException, AnnuityReqTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<AnnuityReqType> annutys = annuityService.getAllAnnuity();

			if (annutys.isEmpty()) {
				logger.debug("inside annuity reuired type Controller getAllAnnuity() method");
				logger.info("annuity required type list is empty ");
				throw new ResourceNotFoundException("annuity required type not found");
			}
			return annutys;
		} catch (Exception ex) {
			logger.error(" get All Annuity Req Types  exception occured." + ex.getMessage());
			throw new AnnuityReqTypeServiceException("internal server error");
		}
	}

	@GetMapping("/annuity/{id}")
	public ResponseEntity<AnnuityReqType> getAnnuityById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("annuity required type search by Id");
		return ResponseEntity.ok().body(annuityService.getAnnuityById(id));
	}

	@GetMapping("/annuity/code/{code}")
	public ResponseEntity<AnnuityReqType> getAnnuityByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("annuity required type search by Code");
		return ResponseEntity.ok().body(annuityService.getAnnuityByCode(code));
	}

}
