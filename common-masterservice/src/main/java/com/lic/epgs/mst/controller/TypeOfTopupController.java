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

import com.lic.epgs.mst.entity.TypeOfTopupMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.TypeOfTopupServiceException;
import com.lic.epgs.mst.service.TypeOfTopupService;

@RestController
@CrossOrigin("*")
public class TypeOfTopupController {

	private static final Logger logger = LoggerFactory.getLogger(TypeOfTopupController.class);

	@Autowired
	private TypeOfTopupService typeOfTopupService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/typeOfTopup")
	public List<TypeOfTopupMaster> getAllTypeOfTopup() throws ResourceNotFoundException, TypeOfTopupServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<TypeOfTopupMaster> typeOfTopup = typeOfTopupService.getAllTypeOfTopup();

			if (typeOfTopup.isEmpty()) {
				logger.debug("inside type of topup controller getAllTypeOfTopup() method");
				logger.info(" type of topup list is empty ");
				throw new ResourceNotFoundException(" type of topup not found");
			}
			return typeOfTopup;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new TypeOfTopupServiceException("internal server error");
		}
	}

	@GetMapping("/typeOfTopup/{id}")
	public ResponseEntity<TypeOfTopupMaster> getTypeOfTopupById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(typeOfTopupService.getTypeOfTopupById(id));

	}

	@GetMapping("/typeOfTopupByCode/{code}")
	public ResponseEntity<TypeOfTopupMaster> getTypeOfTopupByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(typeOfTopupService.getTypeOfTopupByCode(code));

	}

}
