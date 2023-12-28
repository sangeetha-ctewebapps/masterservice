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

import com.lic.epgs.mst.entity.UnderwritingLoadingMst;
import com.lic.epgs.mst.exceptionhandling.UnderwritingLoadingServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.UnderwritingLoadingService;

@RestController
@CrossOrigin("*")
public class UnderwritingLoadingController {

	private static final Logger logger = LoggerFactory.getLogger(UnderwritingLoadingController.class);

	@Autowired
	private UnderwritingLoadingService underwritingloadingService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/UnderwritingLoading")
	public List<UnderwritingLoadingMst> getAllUnderwritingLoading() throws ResourceNotFoundException, UnderwritingLoadingServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<UnderwritingLoadingMst> underwritingloading = underwritingloadingService.getAllUnderwritingLoading();

			if (underwritingloading.isEmpty()) {
				logger.debug("inside UnderwritingLoading controller getAllUnderwritingLoading() method");
				logger.info("UnderwritingLoading list is empty ");
				throw new ResourceNotFoundException("UnderwritingLoading not found");
			}
			return underwritingloading;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new UnderwritingLoadingServiceException("internal server error");
		}
	}

	@GetMapping("/UnderwritingLoading/{id}")
	public ResponseEntity<UnderwritingLoadingMst> getUnderwritingLoadingById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(underwritingloadingService.getUnderwritingLoadingById(id));

	}

	@GetMapping("/UnderwritingLoadingByCode/{code}")
	public ResponseEntity<UnderwritingLoadingMst> getUnderwritingLoadingByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(underwritingloadingService.getUnderwritingLoadingByCode(code));

	}

}
