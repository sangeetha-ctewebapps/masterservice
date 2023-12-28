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

import com.lic.epgs.mst.entity.ZoneMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.ZoneServiceException;
import com.lic.epgs.mst.service.ZoneService;

@RestController
@CrossOrigin("*")
public class ZoneController {

	private static final Logger logger = LoggerFactory.getLogger(ZoneController.class);

	@Autowired
	private ZoneService zoneservice;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/zone")
	public List<ZoneMaster> getAllZone() throws ResourceNotFoundException, ZoneServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<ZoneMaster> zones = zoneservice.getAllZone();

			if (zones.isEmpty()) {
				logger.debug("inside zonecontroller getAllZone() method");
				logger.info("zone list is empty ");
				throw new ResourceNotFoundException("zone not found");
			}
			return zones;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ZoneServiceException("internal server error");
		}
	}

	@GetMapping("/zone/{zoneId}")
	public ResponseEntity<ZoneMaster> getZoneById(@PathVariable long zoneId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + zoneId);

		return ResponseEntity.ok().body(zoneservice.getZoneById(zoneId));

	}

	@GetMapping("/zoneByCode/{code}")
	public ResponseEntity<ZoneMaster> getZoneByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(zoneservice.getZoneByCode(code));

	}

}
