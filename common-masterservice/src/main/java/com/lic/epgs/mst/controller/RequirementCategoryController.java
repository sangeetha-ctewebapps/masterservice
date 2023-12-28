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

import com.lic.epgs.mst.entity.RequirementCategoryMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.RequirementCategoryException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.RequirementCategoryService;

@RestController
@CrossOrigin("*")
public class RequirementCategoryController {

	@Autowired
	private RequirementCategoryService requirementcategoryservice;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(RequirementCategoryController.class);

	 @GetMapping("/requirementcategory")
	public List<RequirementCategoryMst> getAllRequirementCategory()
			throws ResourceNotFoundException, RequirementCategoryException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<RequirementCategoryMst> requirementcategorys = requirementcategoryservice.getAllRequirementCategory();

			if (requirementcategorys.isEmpty()) {
				logger.debug("inside modeofeixtcontroller getAllRequirementCategory() method");
				logger.info("requirementcategory list is empty ");
				throw new ResourceNotFoundException("Requirement category not found");
			}
			return requirementcategorys;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new RequirementCategoryException("internal server error");
		}
	}

	@GetMapping("/requirementcategory/{id}")
	public ResponseEntity<RequirementCategoryMst> getRequirementCategoryById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(requirementcategoryservice.getRequirementCategoryById(id));

	}

	@GetMapping("/requirementcategoryByCode/{code}")
	public ResponseEntity<RequirementCategoryMst> getRequirementCategoryByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(requirementcategoryservice.getRequirementCategoryByCode(code));

	}
}
