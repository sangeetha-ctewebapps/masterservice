package com.lic.epgs.mst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.CategoryForGstNonApplicabilityMst;
import com.lic.epgs.mst.exceptionhandling.CategoryForGstNonApplicabilityException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.CategoryForGstNonApplicabilityService;

@RestController
public class CategoryForGstNonApplicabilityController {

	private static final Logger logger = LoggerFactory.getLogger(CategoryForGstNonApplicabilityController.class);

	@Autowired
	private CategoryForGstNonApplicabilityService categoryforgstnonapplicabilityService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/Category")
	public List<CategoryForGstNonApplicabilityMst> getAllCategoryForGstNonApplicability() throws ResourceNotFoundException, CategoryForGstNonApplicabilityException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<CategoryForGstNonApplicabilityMst> categoryforgstnonapplicability = categoryforgstnonapplicabilityService.getAllCategoryForGstNonApplicability();

			if (categoryforgstnonapplicability.isEmpty()) {
				logger.debug("inside Category For Gst Non Applicability controller getAllCategoryForGstNonApplicability() method");
				logger.info("CategoryForGstNonApplicability list is empty ");
				throw new ResourceNotFoundException("CategoryForGstNonApplicability not found");
			}
			return categoryforgstnonapplicability;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new CategoryForGstNonApplicabilityException("internal server error");
		}
	}

	@GetMapping("/Category/{id}")
	public ResponseEntity<CategoryForGstNonApplicabilityMst> getCategoryForGstNonApplicabilityById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(categoryforgstnonapplicabilityService.getCategoryForGstNonApplicabilityById(id));

	}

	@GetMapping("/CategoryByCode/{code}")
	public ResponseEntity<CategoryForGstNonApplicabilityMst> getCategoryForGstNonApplicabilityByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(categoryforgstnonapplicabilityService.getCategoryForGstNonApplicabilityByCode(code));

	}

}
