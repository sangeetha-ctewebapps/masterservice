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

import com.lic.epgs.mst.entity.GenderMst;
import com.lic.epgs.mst.exceptionhandling.GenderException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.GenderService;

@RestController
@CrossOrigin("*")
public class GenderController {

	private static final Logger logger = LoggerFactory.getLogger(GenderController.class);

	@Autowired
	private GenderService genderService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/Gender")
	public List<GenderMst> getAllGender() throws ResourceNotFoundException, GenderException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<GenderMst> gender = genderService.getAllGender();

			if (gender.isEmpty()) {
				logger.debug("inside Gender controller getAllGender() method");
				logger.info("Gender list is empty ");
				throw new ResourceNotFoundException("Gender not found");
			}
			return gender;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new GenderException("internal server error");
		}
	}

	@GetMapping("/Gender/{id}")
	public ResponseEntity<GenderMst> getGenderById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(genderService.getGenderById(id));

	}

	@GetMapping("/GenderByCode/{code}")
	public ResponseEntity<GenderMst> getGenderByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(genderService.getGenderByCode(code));

	}

}
