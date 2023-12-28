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

import com.lic.epgs.mst.entity.MergerTypeMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.MergerTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.MergerTypeService;

@RestController
@CrossOrigin("*")
public class MergerTypeController {

	private static final Logger logger = LoggerFactory.getLogger(MergerTypeController.class);

	@Autowired
	private MergerTypeService mergerTypeService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/mergerType")
	public List<MergerTypeMaster> getAllMergerType() throws ResourceNotFoundException, MergerTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<MergerTypeMaster> mergerType = mergerTypeService.getAllMergerType();

			if (mergerType.isEmpty()) {
				logger.debug("inside merger type controller getAllMergerType() method");
				logger.info("merger type list is empty ");
				throw new ResourceNotFoundException("merger type not found");
			}
			return mergerType;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new MergerTypeServiceException("internal server error");
		}
	}

	@GetMapping("/mergerType/{id}")
	public ResponseEntity<MergerTypeMaster> getMergerTypeById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(mergerTypeService.getMergerTypeById(id));

	}

	@GetMapping("/mergerTypeByCode/{code}")
	public ResponseEntity<MergerTypeMaster> getMergerTypeByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(mergerTypeService.getMergerTypeByCode(code));

	}

}
