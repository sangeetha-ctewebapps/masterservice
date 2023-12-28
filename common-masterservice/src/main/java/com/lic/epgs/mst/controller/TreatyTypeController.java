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

import com.lic.epgs.mst.entity.TreatyTypeMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.TreatyTypeException;
import com.lic.epgs.mst.service.TreatyTypeService;

@RestController
@CrossOrigin("*")
public class TreatyTypeController {

	private static final Logger logger = LoggerFactory.getLogger(TreatyTypeController.class);

	@Autowired
	private TreatyTypeService treatytypeService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/TreatyType")
	public List<TreatyTypeMst> getAllTreatyType() throws ResourceNotFoundException, TreatyTypeException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<TreatyTypeMst> treatytype = treatytypeService.getAllTreatyType();

			if (treatytype.isEmpty()) {
				logger.debug("inside TreatyType controller getAllTreatyType() method");
				logger.info("TreatyType list is empty ");
				throw new ResourceNotFoundException("TreatyType not found");
			}
			return treatytype;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new TreatyTypeException("internal server error");
		}
	}

	@GetMapping("/TreatyType/{id}")
	public ResponseEntity<TreatyTypeMst> getTreatyTypeById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(treatytypeService.getTreatyTypeById(id));

	}

	@GetMapping("/TreatyTypeByCode/{code}")
	public ResponseEntity<TreatyTypeMst> getTreatyTypeByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(treatytypeService.getTreatyTypeByCode(code));

	}

}
