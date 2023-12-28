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

import com.lic.epgs.mst.entity.GSTExmpIssBy;
import com.lic.epgs.mst.exceptionhandling.GSTServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.GSTExmpIssByService;

@RestController
@CrossOrigin("*")
public class GSTExmpIssByController {

	@Autowired
	private GSTExmpIssByService gstService;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(GSTExmpIssByController.class);

	 @GetMapping("/gst")
	public List<GSTExmpIssBy> getAllGst() throws ResourceNotFoundException, GSTServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<GSTExmpIssBy> gsts = gstService.getAllGst();

			if (gsts.isEmpty()) {
				logger.debug("inside gstController getAllGst() method");
				logger.info("gsts type list is empty ");
				throw new ResourceNotFoundException("gsts not found");
			}
			return gsts;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new GSTServiceException("internal server error");
		}
	}

	@GetMapping("/gst/{id}")
	public ResponseEntity<GSTExmpIssBy> getGstById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("GST Exmp Iss By search by id");
		return ResponseEntity.ok().body(gstService.getGSTById(id));

	}

	@GetMapping("/gst/code/{code}")
	public ResponseEntity<GSTExmpIssBy> getGstByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("GST Exmp Iss By search by code");
		return ResponseEntity.ok().body(gstService.getGSTByCode(code));

	}

}