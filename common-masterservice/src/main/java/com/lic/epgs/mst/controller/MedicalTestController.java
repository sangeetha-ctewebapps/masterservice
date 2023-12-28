package com.lic.epgs.mst.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.ErrorMessageDTO;
import com.lic.epgs.mst.entity.MedicalTestMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.MedicalTestServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.MedicalTestService;

@RestController
@CrossOrigin("*")
public class MedicalTestController {

	@Autowired
	private MedicalTestService medicalTestService;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(MedicalTestController.class);

	@GetMapping("/medicalTest")
	public List<MedicalTestMst> getAllMedicalTest() throws ResourceNotFoundException, MedicalTestServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<MedicalTestMst> medicalTest = medicalTestService.getAllMedicalTest();

			if (medicalTest.isEmpty()) {
				logger.debug("inside medical testcontroller getAllMedicalTest() method");
				logger.info("medicaltest list is empty ");
				throw new ResourceNotFoundException("medicaltest not found");
			}
			return medicalTest;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new MedicalTestServiceException("internal server error");
		}
	}

	@GetMapping("/medicalTestByCondition")
	public List<MedicalTestMst> getAllMedicalTestByCondition()
			throws ResourceNotFoundException, MedicalTestServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<MedicalTestMst> medicalTest = medicalTestService.getAllMedicalTestByCondition();

			if (medicalTest.isEmpty()) {
				logger.debug("inside medical testcontroller getAllMedicalTestByCondition() method");
				logger.info("medicaltest list is empty ");
				throw new ResourceNotFoundException("medicaltest not found");
			}
			return medicalTest;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new MedicalTestServiceException("internal server error");
		}
	}

	@GetMapping("/medicalTest/{id}")
	public ResponseEntity<MedicalTestMst> getMedicalTestById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(medicalTestService.getMedicalTestById(id));
	}

	/*
	 * @PostMapping("/addMedicalTest") public ResponseEntity<MedicalTestMst>
	 * addMedicalTest(@RequestBody MedicalTestMst medicalTest) {
	 * logger.info("medicaltest list is found" + medicalTest.toString()); return
	 * ResponseEntity.ok().body(medicalTestService.addMedicalTest(medicalTest)); }
	 */
	@PostMapping("/addMedicalTest")
	public ResponseEntity<Map<String, Object>> addMedicalTest(@Valid @RequestBody MedicalTestMst medicalTest)throws MedicalTestServiceException {
		logger.info("medicalTest list is found" + medicalTest.toString());
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto = null;
		MedicalTestMst medicalTestMst = null;
		if (null == medicalTest) {
			erroMsgDto = new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
			response.put("status", 0);
			response.put("error", erroMsgDto);
			return ResponseEntity.badRequest().body(response);
		}
		try {
			medicalTestMst = medicalTestService.getMedicalTestByCode(medicalTest.getMedicalTestCode());
		} catch (Exception e) {
			throw new MedicalTestServiceException("internal server error");
		}
		if (medicalTestMst == null) {
			MedicalTestMst saveMedicalTest = medicalTestService.addMedicalTest(medicalTest);
			response.put("medicalTest", saveMedicalTest);
			response.put("message", " medicalTest created succesfully");
			return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", "medicalTest already exist");
			return ResponseEntity.accepted().body(response);
		}
	}

	@PutMapping("/modifyMedicalTest/{id}")
	public ResponseEntity<Map<String, Object>> updateMedicalTest(@PathVariable Long id,
			@RequestBody MedicalTestMst medicalTest) throws MedicalTestServiceException {
		medicalTest.setMedicalTestId(id);
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto = null;
		MedicalTestMst medicalTestMst = null;
		MedicalTestMst medicalTestMst1 = null;
		if (null == medicalTest) {
			erroMsgDto = new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
			response.put("status", 0);
			response.put("error", erroMsgDto);
			return ResponseEntity.badRequest().body(response);
		}
		try {
			medicalTestMst = medicalTestService.getMedicalTestByCode(medicalTest.getMedicalTestCode());
			medicalTestMst1 = medicalTestService.getMedicalTestById(id);
		} catch (Exception e) {
			logger.error("Internal Server Error");
			throw new MedicalTestServiceException("internal server error");
		}
		if (medicalTestMst == null
				|| medicalTestMst.getMedicalTestCode().equals(medicalTestMst1.getMedicalTestCode())) {
			MedicalTestMst saveMedicalTest = medicalTestService.updateMedicalTest(medicalTest);
			response.put("medicalTest", saveMedicalTest);
			response.put("message", " medicalTest modified succesfully");
			return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", "medicalTest already exist");
			return ResponseEntity.accepted().body(response);
		}
	}

	/*
	 * @DeleteMapping("/deleteMedicalTest/{id}") public ResponseEntity<HttpStatus>
	 * deleteMedicalTest(@PathVariable Long id) throws MedicalTestServiceException {
	 * try { this.medicalTestService.deleteMedicalTest(id); return new
	 * ResponseEntity<>(HttpStatus.OK); } catch (Exception e) { throw new
	 * MedicalTestServiceException(e.getMessage());
	 * 
	 * } }
	 */

	@DeleteMapping(value = "/deleteMedicalTest/{medicalTestId}")
	public ResponseEntity<Map<String, Object>> deleteMedicalTest(@PathVariable("medicalTestId") Long medicalTestId)
			throws MedicalTestServiceException {
		try {
			return medicalTestService.deleteMedicalTest(medicalTestId);
		} catch (MedicalTestServiceException e) {
			// TODO Auto-generated catch block
			throw new MedicalTestServiceException("Internal Server error : delete medical test");
		}
	}

	@GetMapping("/medicalTestByCode/{code}")
	public ResponseEntity<MedicalTestMst> getMedicalTestByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(medicalTestService.getMedicalTestByCode(code));

	}
}
