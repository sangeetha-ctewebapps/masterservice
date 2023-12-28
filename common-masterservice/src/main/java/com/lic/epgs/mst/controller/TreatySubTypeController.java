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

import com.lic.epgs.mst.entity.TreatySubTypeMst;
import com.lic.epgs.mst.exceptionhandling.TreatySubTypeException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.TreatySubTypeService;

@RestController
@CrossOrigin("*")
public class TreatySubTypeController {

	private static final Logger logger = LoggerFactory.getLogger(TreatySubTypeController.class);

	@Autowired
	private TreatySubTypeService treatysubtypeService;

	String className = this.getClass().getSimpleName();

	@GetMapping("/TreatySubType")
	 public List<TreatySubTypeMst> getAllTreatySubType() throws ResourceNotFoundException, TreatySubTypeException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<TreatySubTypeMst> treatysubtype = treatysubtypeService.getAllTreatySubType();

			if (treatysubtype.isEmpty()) {
				logger.debug("inside TreatySubType controller getAllTreatySubType() method");
				logger.info("TreatySubType list is empty ");
				throw new ResourceNotFoundException("TreatySubType not found");
			}
			return treatysubtype;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new TreatySubTypeException("internal server error");
		}
	}

	@GetMapping("/TreatySubType/{id}")
	public ResponseEntity<TreatySubTypeMst> getTreatySubTypeById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(treatysubtypeService.getTreatySubTypeById(id));

	}

	@GetMapping("/TreatySubTypeByCode/{code}")
	public ResponseEntity<TreatySubTypeMst> getTreatySubTypeByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(treatysubtypeService.getTreatySubTypeByCode(code));

	}

}
