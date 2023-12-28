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
import com.lic.epgs.mst.entity.ClaimRecommendation;
import com.lic.epgs.mst.exceptionhandling.ClaimRecommendationServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.ClaimRecommendationService;

@RestController
@CrossOrigin("*")
public class ClaimRecommendationController {

	@Autowired
	private ClaimRecommendationService claimService;

	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(ClaimRecommendationController.class);

	 @GetMapping("/claimrecommendations")
	public List<ClaimRecommendation> getAllClaimRecommendation()
			throws ResourceNotFoundException, ClaimRecommendationServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<ClaimRecommendation> claims = claimService.getAllClaim();

			if (claims.isEmpty()) {
				logger.debug("inside Claim Recommendation Controller getAllClaim() method");
				logger.info("Claim Recommendation list is empty ");
				throw new ResourceNotFoundException("Claim Recommendation is not found");
			}
			return claims;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ClaimRecommendationServiceException("internal server error");
		}
	}

	@GetMapping("/claimrecommendations/{id}")
	public ResponseEntity<ClaimRecommendation> getClaimById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Claim Recommendation search by id");
		return ResponseEntity.ok().body(claimService.getClaimById(id));

	}

	@GetMapping("/claimrecommendations/code/{code}")
	public ResponseEntity<ClaimRecommendation> getClaimByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Claim Recommendation search by Code");
		return ResponseEntity.ok().body(claimService.getClaimByCode(code));
	}
}
