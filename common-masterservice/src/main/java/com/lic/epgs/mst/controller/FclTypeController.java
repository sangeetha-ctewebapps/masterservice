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

import com.lic.epgs.mst.entity.FclTypeMst;
import com.lic.epgs.mst.exceptionhandling.FclTypeException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.FclTypeService;

@RestController
@CrossOrigin("*")
public class FclTypeController {

	private static final Logger logger = LoggerFactory.getLogger(FclTypeController.class);

	@Autowired
	private FclTypeService fcltypeService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/FclType")
	public List<FclTypeMst> getAllFclType() throws ResourceNotFoundException, FclTypeException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<FclTypeMst> fcltype = fcltypeService.getAllFclType();

			if (fcltype.isEmpty()) {
				logger.debug("inside Fcl Type controller getAllFclType() method");
				logger.info("FclType list is empty ");
				throw new ResourceNotFoundException("FclType not found");
			}
			return fcltype;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new FclTypeException("internal server error");
		}
	}

	@GetMapping("/FclType/{id}")
	public ResponseEntity<FclTypeMst> getFclTypeById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(fcltypeService.getFclTypeById(id));

	}

	@GetMapping("/FclTypeByCode/{code}")
	public ResponseEntity<FclTypeMst> getFclTypeByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(fcltypeService.getFclTypeByCode(code));

	}

}
