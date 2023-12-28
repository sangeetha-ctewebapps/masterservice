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

import com.lic.epgs.mst.entity.RegisterTypeMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.RegisterServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.RegisterService;

@RestController
@CrossOrigin("*")
public class RegisterController {

	@Autowired
	private RegisterService registerservice;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

	 @GetMapping("/register")
	public List<RegisterTypeMst> getAllRegister() throws ResourceNotFoundException, RegisterServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<RegisterTypeMst> registers = registerservice.getAllRegister();

			if (registers.isEmpty()) {
				logger.debug("inside registercontroller getAllRegister() method");
				logger.info("register type list is empty ");
				throw new ResourceNotFoundException("register not found");
			}
			return registers;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new RegisterServiceException("internal server error");
		}
	}

	@GetMapping("/register/{id}")
	public ResponseEntity<RegisterTypeMst> getRegisterById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(registerservice.getRegisterById(id));
	}

	

	@GetMapping("/registerByCode/{code}")
	public ResponseEntity<RegisterTypeMst> getRegisterByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(registerservice.getRegisterByCode(code));

	}
}
