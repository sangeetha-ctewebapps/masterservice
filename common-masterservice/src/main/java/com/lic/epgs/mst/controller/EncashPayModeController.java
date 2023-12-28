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

import com.lic.epgs.mst.entity.EncashPayModeMst;
import com.lic.epgs.mst.exceptionhandling.EncashPayModeException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.EncashPayModeService;

@RestController
@CrossOrigin("*")
public class EncashPayModeController {

	private static final Logger logger = LoggerFactory.getLogger(EncashPayModeController.class);

	@Autowired
	private EncashPayModeService encashpaymodeService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/EncashPayMode")
	public List<EncashPayModeMst> getAllEncashPayMode() throws ResourceNotFoundException, EncashPayModeException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<EncashPayModeMst> encashpaymode = encashpaymodeService.getAllEncashPayMode();

			if (encashpaymode.isEmpty()) {
				logger.debug("inside Encash Pay Mode controller getAllEncashPayMode() method");
				logger.info("EncashPayMode list is empty ");
				throw new ResourceNotFoundException("EncashPayMode not found");
			}
			return encashpaymode;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new EncashPayModeException("internal server error");
		}
	}

	@GetMapping("/EncashPayMode/{id}")
	public ResponseEntity<EncashPayModeMst> getEncashPayModeById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(encashpaymodeService.getEncashPayModeById(id));

	}

	@GetMapping("/EncashPayModeByCode/{code}")
	public ResponseEntity<EncashPayModeMst> getEncashPayModeByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(encashpaymodeService.getEncashPayModeByCode(code));

	}

}
