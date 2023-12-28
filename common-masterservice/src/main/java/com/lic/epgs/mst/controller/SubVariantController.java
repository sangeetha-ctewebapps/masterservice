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

import com.lic.epgs.mst.entity.SubVariantMst;
import com.lic.epgs.mst.exceptionhandling.SubVariantException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.SubVariantService;

@RestController
@CrossOrigin("*")
public class SubVariantController {

	private static final Logger logger = LoggerFactory.getLogger(SubVariantController.class);

	@Autowired
	private SubVariantService subvariantService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/SubVariant")
	public List<SubVariantMst> getAllSubVariant() throws ResourceNotFoundException, SubVariantException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<SubVariantMst> subvariant = subvariantService.getAllSubVariant();

			if (subvariant.isEmpty()) {
				logger.debug("inside SubVariant controller getAllSubVariant() method");
				logger.info("SubVariant list is empty ");
				throw new ResourceNotFoundException("SubVariant not found");
			}
			return subvariant;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new SubVariantException("internal server error");
		}
	}

	@GetMapping("/SubVariant/{id}")
	public ResponseEntity<SubVariantMst> getSubVariantById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(subvariantService.getSubVariantById(id));

	}

	@GetMapping("/SubVariantByCode/{code}")
	public ResponseEntity<SubVariantMst> getSubVariantByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(subvariantService.getSubVariantByCode(code));

	}

}
