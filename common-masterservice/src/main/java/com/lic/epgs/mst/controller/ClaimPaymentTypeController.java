package com.lic.epgs.mst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.ClaimPaymentTypeMst;
import com.lic.epgs.mst.exceptionhandling.ClaimPaymentTypeException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.ClaimPaymentTypeService;

@RestController
public class ClaimPaymentTypeController {

	private static final Logger logger = LoggerFactory.getLogger(ClaimPaymentTypeController.class);

	@Autowired
	private ClaimPaymentTypeService claimpaymenttypeService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/ClaimPaymentType")
	public List<ClaimPaymentTypeMst> getClaimPaymentType() throws ResourceNotFoundException, ClaimPaymentTypeException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<ClaimPaymentTypeMst> claimpaymenttype = claimpaymenttypeService.getAllClaimPaymentType();

			if (claimpaymenttype.isEmpty()) {
				logger.debug("inside Claim Payment Type controller getAllClaimPaymentType() method");
				logger.info("Claim Payment Type list is empty ");
				throw new ResourceNotFoundException("Claim Payment Type not found");
			}
			return claimpaymenttype;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ClaimPaymentTypeException("internal server error");
		}
	}

	@GetMapping("/ClaimPaymentType/{id}")
	public ResponseEntity<ClaimPaymentTypeMst> getClaimPaymentTypeById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(claimpaymenttypeService.getClaimPaymentTypeById(id));

	}

	@GetMapping("/ClaimPaymentTypeByCode/{code}")
	public ResponseEntity<ClaimPaymentTypeMst> getClaimPaymentTypeByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(claimpaymenttypeService.getClaimPaymentTypeByCode(code));

	}

}
