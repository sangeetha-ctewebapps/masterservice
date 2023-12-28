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

import com.lic.epgs.mst.entity.ClaimTypeMst;
import com.lic.epgs.mst.exceptionhandling.ClaimCancellationReasonServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.ClaimCancellationReasonService;

@RestController
@CrossOrigin("*")
public class ClaimCancellationReasonController {

	@Autowired
	private ClaimCancellationReasonService claimcancellationreasonservice;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(ClaimCancellationReasonController.class);

	 @GetMapping("/getAllClaimCancellationReason")
    public List<ClaimTypeMst> getAllClaimCancellationReason()
            throws ResourceNotFoundException, ClaimCancellationReasonServiceException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        LoggingUtil.logInfo(className, methodName, "Started");

        try {
            List<ClaimTypeMst> claim = claimcancellationreasonservice.getAllClaimCancellationReason();
            if (claim.isEmpty()) {
                logger.info("claim type list is empty ");
                throw new ResourceNotFoundException("claim not found");
            }
            return claim;
        } catch (Exception ex) {
            logger.info("Internal Server Error");
            throw new ClaimCancellationReasonServiceException("internal server error");
        }
    }

	@GetMapping("/claim/{id}")
	public ResponseEntity<ClaimTypeMst> getClaimCancellationReasonById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(claimcancellationreasonservice.getClaimCancellationReasonById(id));
	}

	@GetMapping("/claimByCode/{code}")
	public ResponseEntity<ClaimTypeMst> getClaimCancellationReasonByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(claimcancellationreasonservice.getClaimCancellationReasonByCode(code));

	}
}
