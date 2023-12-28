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

import com.lic.epgs.mst.entity.TypeOfLeaveMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.TypeOfLeaveServiceException;
import com.lic.epgs.mst.service.TypeOfLeaveService;

@RestController
@CrossOrigin("*")
public class TypeOfLeaveController {

	private static final Logger logger = LoggerFactory.getLogger(TypeOfLeaveController.class);

	@Autowired
	private TypeOfLeaveService typeOfLeaveService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/typeOfLeave")
	public List<TypeOfLeaveMaster> getAllTypeOfLeave() throws ResourceNotFoundException, TypeOfLeaveServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<TypeOfLeaveMaster> typeOfLeave = typeOfLeaveService.getAllTypeOfLeave();

			if (typeOfLeave.isEmpty()) {
				logger.debug("inside type of switch controller getAllTypeOfSwitch() method");
				logger.info(" type of switch list is empty ");
				throw new ResourceNotFoundException(" type of switch not found");
			}
			return typeOfLeave;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new TypeOfLeaveServiceException("internal server error");
		}
	}

	@GetMapping("/typeOfLeave/{id}")
	public ResponseEntity<TypeOfLeaveMaster> getTypeOfLeaveById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(typeOfLeaveService.getTypeOfLeaveById(id));

	}

	@GetMapping("/typeOfLeaveByCode/{code}")
	public ResponseEntity<TypeOfLeaveMaster> getTypeOfLeaveByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(typeOfLeaveService.getTypeOfLeaveByCode(code));

	}

}
