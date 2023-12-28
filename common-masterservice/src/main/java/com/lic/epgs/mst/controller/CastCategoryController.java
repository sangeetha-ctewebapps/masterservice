package com.lic.epgs.mst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.CastCategoryMst;
import com.lic.epgs.mst.exceptionhandling.CastCategoryException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.CastCategoryService;

@RestController
public class CastCategoryController {

	private static final Logger logger = LoggerFactory.getLogger(CastCategoryController.class);

	@Autowired
	private CastCategoryService castcategoryService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/CastCategory")
	public List<CastCategoryMst> getAllCastCategory() throws ResourceNotFoundException, CastCategoryException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<CastCategoryMst> castcategory = castcategoryService.getAllCastCategory();

			if (castcategory.isEmpty()) {
				logger.debug("inside Cast Category controller getAllCastCategory() method");
				logger.info("CastCategory list is empty ");
				throw new ResourceNotFoundException("CastCategory not found");
			}
			return castcategory;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new CastCategoryException("internal server error");
		}
	}

	@GetMapping("/CastCategory/{id}")
	public ResponseEntity<CastCategoryMst> getCastCategoryById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(castcategoryService.getCastCategoryById(id));

	}

	@GetMapping("/CastCategoryByCode/{code}")
	public ResponseEntity<CastCategoryMst> getCastCategoryByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(castcategoryService.getCastCategoryByCode(code));

	}

}
