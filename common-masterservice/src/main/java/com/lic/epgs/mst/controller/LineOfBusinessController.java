package com.lic.epgs.mst.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.lic.epgs.mst.entity.LineOfBusinessMaster;
import com.lic.epgs.mst.exceptionhandling.LineOfBusinessServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.LineOfBusinessService;

@RestController
@CrossOrigin("*")
public class LineOfBusinessController {

	 private static final Logger logger = LoggerFactory.getLogger(LineOfBusinessController.class);

	@Autowired
	private LineOfBusinessService lineOfBusinessService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/lineOfBusiness")
	public List<LineOfBusinessMaster> getAllLineOfBusiness()
			throws ResourceNotFoundException, LineOfBusinessServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<LineOfBusinessMaster> lineBusiness = lineOfBusinessService.getAllLineOfBusiness();

			if (lineBusiness.isEmpty()) {
				logger.debug("inside line businessr get All Line Of business () method");
				logger.info("line of business list is empty ");
				throw new ResourceNotFoundException("line of business not found");
			}
			return lineBusiness;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new LineOfBusinessServiceException("internal server error");
		}
	}
	
	@GetMapping("/lineOfBusinessByCondition")
	public List<LineOfBusinessMaster> getAllLineOfBusinessByCondition()
			throws ResourceNotFoundException, LineOfBusinessServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<LineOfBusinessMaster> lineBusiness = lineOfBusinessService.getAllLineOfBusinessByCondition();
			if (lineBusiness.isEmpty()) {
				logger.debug("inside line businessr getAllLineOfBusinessByCondition() method");
				logger.info("line of business list is empty ");
				throw new ResourceNotFoundException("line of business not found");
			}
			return lineBusiness;
		} catch (Exception ex) 
		{
			logger.error("Internal Server Error");
			throw new LineOfBusinessServiceException("internal server error");
		}
	}

	@GetMapping("/lineOfBusiness/{id}")
	public ResponseEntity<LineOfBusinessMaster> getLineOfBusinessById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(lineOfBusinessService.getLineOfBusinessById(id));

	}

	@GetMapping("/lineOfBusinessByCode/{code}")
	public ResponseEntity<LineOfBusinessMaster> getLineBusinessByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(lineOfBusinessService.getLineBusinessByCode(code));

	}

	/*
	 * @PostMapping("/addLineOfBusiness") public
	 * ResponseEntity<LineOfBusinessMaster> createLineOfBusiness(@RequestBody
	 * LineOfBusinessMaster lineOfBusiness) {
	 * logger.info("line of business list is found" + lineOfBusiness.toString());
	 * return ResponseEntity.ok().body(lineOfBusinessService.createLineOfBusiness(
	 * lineOfBusiness));
	 * 
	 * }
	 */
	@PostMapping("/addLineOfBusiness")
	public ResponseEntity<Map<String, Object>> createLineOfBusiness(
			@Valid @RequestBody LineOfBusinessMaster lineOfBusiness) throws LineOfBusinessServiceException {
		logger.info("LineOfBusiness list is found" + lineOfBusiness.toString());
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto = null;
		LineOfBusinessMaster lineOfBusinessMaster = new LineOfBusinessMaster();
		try {
			
		
		if (lineOfBusiness.getLineBusinessCode()!=null && !lineOfBusiness.getLineBusinessCode().isEmpty()) {
			lineOfBusinessMaster = lineOfBusinessService.getLineBusinessByCode(lineOfBusiness.getLineBusinessCode());
		}
		else {
			erroMsgDto = new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
			response.put("status", 0);
			response.put("error", erroMsgDto);
			return ResponseEntity.badRequest().body(response);
		}
		}
		
		catch (Exception e) {
			logger.error(" add Line of Business  exception occured." + e.getMessage());
			throw new LineOfBusinessServiceException("internal server error");
		}
		if (lineOfBusinessMaster == null) {
			LineOfBusinessMaster saveLineOfBusiness = lineOfBusinessService.createLineOfBusiness(lineOfBusiness);
			response.put("lineOfBusiness", saveLineOfBusiness);
			response.put("message", " lineOfBusiness created succesfully");
			return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", "lineOfBusiness already exist");
			return ResponseEntity.accepted().body(response);
		}
	}


	@PutMapping("/modifyLineOfBusiness/{id}")
	public ResponseEntity <Map<String, Object>> updateLineOfBusiness(@PathVariable Long id,
			@RequestBody LineOfBusinessMaster lineOfBusiness) throws LineOfBusinessServiceException {
		lineOfBusiness.setLineBusinessId(id);
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto = null;
		LineOfBusinessMaster lineOfBusinessMaster = null;
		LineOfBusinessMaster lineOfBusinessMaster1 = null;
		if (null == lineOfBusiness) {
			erroMsgDto = new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
			response.put("status", 0);
			response.put("error", erroMsgDto);
			return ResponseEntity.badRequest().body(response);
		}
		try {
			lineOfBusinessMaster = lineOfBusinessService.getLineBusinessByCode(lineOfBusiness.getLineBusinessCode());
			lineOfBusinessMaster1 = lineOfBusinessService.getLineOfBusinessById(id);
		} catch (Exception e) {
			logger.error(" modify Line Of Business  exception occured." + e.getMessage());
			throw new LineOfBusinessServiceException("internal server error");
		}
		if (lineOfBusinessMaster == null || lineOfBusinessMaster.getLineBusinessCode().equals(lineOfBusinessMaster1.getLineBusinessCode()) ) {
			LineOfBusinessMaster saveLineOfBusiness = lineOfBusinessService.updateLineOfBusiness(lineOfBusiness);
			response.put("lineOfBusiness", saveLineOfBusiness);
			response.put("message", " lineOfBusiness modified succesfully");
			return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", "lineOfBusiness already exist");
			return ResponseEntity.accepted().body(response);
		}
	}

	@DeleteMapping("/deleteLineOfBusiness/{id}")
	public ResponseEntity<HttpStatus> deleteLineOfBusiness(@PathVariable Long id)
			throws LineOfBusinessServiceException {
		try {
			this.lineOfBusinessService.deleteLineOfBusiness(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			throw new LineOfBusinessServiceException(e.getMessage());

		}
	}

}
