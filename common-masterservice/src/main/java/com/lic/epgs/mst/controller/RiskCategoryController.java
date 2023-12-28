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

import com.lic.epgs.mst.entity.RiskCategoryMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.RiskCategoryServiceException;
import com.lic.epgs.mst.service.RiskCategoryService;

@RestController
@CrossOrigin("*")
public class RiskCategoryController {

	private static final Logger logger = LoggerFactory.getLogger(RiskCategoryController.class);

	@Autowired
	private RiskCategoryService riskCategoryService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/riskcategory")
	public List<RiskCategoryMaster> getAllRiskCategory()
			throws ResourceNotFoundException, RiskCategoryServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<RiskCategoryMaster> riskCategory = riskCategoryService.getAllRiskCategory();

			if (riskCategory.isEmpty()) {
				logger.debug("inside risk category controller getAllZone() method");
				logger.info("risk category list is empty ");
				throw new ResourceNotFoundException("risk category not found");
			}
			return riskCategory;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new RiskCategoryServiceException("internal server error");
		}
	}

	@GetMapping("/riskcategory/{id}")
	public ResponseEntity<RiskCategoryMaster> getRiskCategoryById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(riskCategoryService.getRiskCategoryById(id));

	}

	@GetMapping("/riskcategoryByCode/{code}")
	public ResponseEntity<RiskCategoryMaster> getgetRiskCategoryByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(riskCategoryService.getRiskCategoryByCode(code));

	}

}
