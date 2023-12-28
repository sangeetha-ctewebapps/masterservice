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

import com.lic.epgs.mst.entity.TypeOfSwitchMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.TypeOfSwitchServiceException;
import com.lic.epgs.mst.service.TypeOfSwitchService;

@RestController
@CrossOrigin("*")
public class TypeOfSwitchController {

	private static final Logger logger = LoggerFactory.getLogger(TypeOfSwitchController.class);

	@Autowired
	private TypeOfSwitchService typeOfSwitchService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/typeOfSwitch")
	public List<TypeOfSwitchMaster> getAllTypeOfSwitchType()
			throws ResourceNotFoundException, TypeOfSwitchServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<TypeOfSwitchMaster> typeOfSwitch = typeOfSwitchService.getAllTypeOfSwitchType();

			if (typeOfSwitch.isEmpty()) {
				logger.debug("inside type of switch controller getAllTypeOfSwitch() method");
				logger.info(" type of switch list is empty ");
				throw new ResourceNotFoundException(" type of switch not found");
			}
			return typeOfSwitch;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new TypeOfSwitchServiceException("internal server error");
		}
	}

	@GetMapping("/typeOfSwitch/{id}")
	public ResponseEntity<TypeOfSwitchMaster> getTypeOfSwitchById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(typeOfSwitchService.getTypeOfSwitchById(id));

	}

	@GetMapping("/typeOfSwitchByCode/{code}")
	public ResponseEntity<TypeOfSwitchMaster> getTypeOfSwitchByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(typeOfSwitchService.getTypeOfSwitchByCode(code));

	}

}
