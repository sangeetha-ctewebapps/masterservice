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

import com.lic.epgs.mst.entity.ModeTypeMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ModeOfExitServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.ModeOfExitService;

@RestController
@CrossOrigin("*")
public class ModeOfExitController {

	@Autowired
	private ModeOfExitService modeofexitservice;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(ModeOfExitController.class);

	 @GetMapping("/mode")
	public List<ModeTypeMst> getAllModeOfExit() throws ResourceNotFoundException, ModeOfExitServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<ModeTypeMst> mode = modeofexitservice.getAllModeOfExit();

			if (mode.isEmpty()) {
				logger.debug("inside modeofeixtcontroller getAllModeOfExit() method");
				logger.info("mode type list is empty ");
				throw new ResourceNotFoundException("mode not found");
			}
			return mode;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ModeOfExitServiceException("internal server error");
		}
	}

	@GetMapping("/mode/{id}")
	public ResponseEntity<ModeTypeMst> getModeOfExitById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(modeofexitservice.getModeOfExitById(id));
	}

	@GetMapping("/modeByCode/{code}")
	public ResponseEntity<ModeTypeMst> getModeOfExitByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(modeofexitservice.getModeOfExitByCode(code));

	}
}
