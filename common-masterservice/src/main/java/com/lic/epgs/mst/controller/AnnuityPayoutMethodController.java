package com.lic.epgs.mst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.AnnuityPayoutMethodMst;
import com.lic.epgs.mst.exceptionhandling.AnnuityPayoutMethodException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.AnnuityPayoutMethodService;

@RestController
public class AnnuityPayoutMethodController {

	private static final Logger logger = LoggerFactory.getLogger(AnnuityPayoutMethodController.class);

	 @Autowired
	private AnnuityPayoutMethodService annuitypayoutmethodService;

	String className = this.getClass().getSimpleName();

	@GetMapping("/AnnuityPayoutMethod")
	public List<AnnuityPayoutMethodMst> getAllAnnuityPayoutMethod() throws ResourceNotFoundException, AnnuityPayoutMethodException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<AnnuityPayoutMethodMst> annuitypayoutmethod = annuitypayoutmethodService.getAllAnnuityPayoutMethod();

			if (annuitypayoutmethod.isEmpty()) {
				logger.debug("inside Annuity Payout Method controller getAllAnnuityPayoutMethod() method");
				logger.info("AnnuityPayoutMethod list is empty ");
				throw new ResourceNotFoundException("AnnuityPayoutMethod not found");
			}
			return annuitypayoutmethod;
		} catch (Exception ex) {
			logger.error(" get All Annuity Payout  exception occured." + ex.getMessage());
			throw new AnnuityPayoutMethodException("internal server error");
		}
	}

	@GetMapping("/AnnuityPayoutMethod/{id}")
	public ResponseEntity<AnnuityPayoutMethodMst> getAnnuityPayoutMethodById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(annuitypayoutmethodService.getAnnuityPayoutMethodById(id));

	}

	@GetMapping("/AnnuityPayoutMethodByCode/{code}")
	public ResponseEntity<AnnuityPayoutMethodMst> getAnnuityPayoutMethodByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(annuitypayoutmethodService.getAnnuityPayoutMethodByCode(code));

	}

}
