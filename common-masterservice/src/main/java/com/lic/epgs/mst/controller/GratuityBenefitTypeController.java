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

import com.lic.epgs.mst.entity.GratuityTypeMst;
import com.lic.epgs.mst.exceptionhandling.GratuityBenefitTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.GratuityBenefitTypeService;

@RestController
@CrossOrigin("*")
public class GratuityBenefitTypeController {

	@Autowired
	private GratuityBenefitTypeService gratuitybenefittypetservice;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(GratuityBenefitTypeController.class);

	 @GetMapping("/gratuity")
	public List<GratuityTypeMst> getAllGratuityBenefitType()
			throws ResourceNotFoundException, GratuityBenefitTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<GratuityTypeMst> gratuity = gratuitybenefittypetservice.getAllGratuityBenefitType();

			if (gratuity.isEmpty()) {
				logger.debug("inside gratuitybenefittypecontroller getAllGratuityBenefitType() method");
				logger.info("gratuity type list is empty ");
				throw new ResourceNotFoundException("gratuity not found");
			}
			return gratuity;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new GratuityBenefitTypeServiceException("internal server error");
		}
	}

	@GetMapping("/gratuity/{id}")
	public ResponseEntity<GratuityTypeMst> getGratuityBenefitTypeById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(gratuitybenefittypetservice.getGratuityBenefitTypeById(id));
	}

	
	@GetMapping("/gratuityByCode/{code}")
	public ResponseEntity<GratuityTypeMst> getGratuityBenefitTypeByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(gratuitybenefittypetservice.getGratuityBenefitTypeByCode(code));

	}

}
