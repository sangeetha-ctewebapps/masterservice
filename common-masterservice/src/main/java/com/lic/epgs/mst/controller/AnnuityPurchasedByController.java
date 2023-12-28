package com.lic.epgs.mst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.AnnuityPurchasedBy;
import com.lic.epgs.mst.exceptionhandling.AnnuityPurchasedByServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.AnnuityPurchasedByService;

@RestController
public class AnnuityPurchasedByController {

	private static final Logger logger = LoggerFactory.getLogger(AnnuityPurchasedByController.class);

	@Autowired
	private AnnuityPurchasedByService annuitypurchasedbyService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/annuitypurchased")
	public List<AnnuityPurchasedBy> getAllAnnuityPurchasedBy() throws ResourceNotFoundException, AnnuityPurchasedByServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<AnnuityPurchasedBy> annuitypurchasedby = annuitypurchasedbyService.getAllAnnuityPurchasedBy();

			if (annuitypurchasedby.isEmpty()) {
				logger.debug("inside Annuity Purchased By controller getAllAnnuityPurchasedBy() method");
				logger.info("AnnuityPurchasedBy list is empty ");
				throw new ResourceNotFoundException("AnnuityPurchasedBy not found");
			}
			return annuitypurchasedby;
		} catch (Exception ex) {
			logger.error(" get All Annuity Purchased By  exception occured." + ex.getMessage());
			throw new AnnuityPurchasedByServiceException("internal server error");
		}
	}

	@GetMapping("/annuitypurchased/{id}")
	public ResponseEntity<AnnuityPurchasedBy> getAnnuityPurchasedByById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(annuitypurchasedbyService.getAnnuityPurchasedByById(id));

	}

	@GetMapping("/annuitypurchasedByCode/{code}")
	public ResponseEntity<AnnuityPurchasedBy> getAnnuityPurchasedByByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(annuitypurchasedbyService.findByCode(code));

	}

}
