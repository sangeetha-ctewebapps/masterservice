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

import com.lic.epgs.constant.Constant;
import com.lic.epgs.constant.ErrorMessageDTO;
import com.lic.epgs.mst.entity.District;
import com.lic.epgs.mst.entity.DistrictEntity;
import com.lic.epgs.mst.entity.DistrictStateResponseModel;
import com.lic.epgs.mst.entity.UnitOffice;
import com.lic.epgs.mst.exceptionhandling.DistrictServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.DistrictService;

@RestController
@CrossOrigin("*")
public class DistrictController {

	@Autowired
	private DistrictService districtService;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(DistrictController.class);

	@GetMapping("/district")
	public List<DistrictEntity> getAllDistrict() throws ResourceNotFoundException, DistrictServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<DistrictEntity> objDistrictEntity = districtService.getAllDistrict();

			if (objDistrictEntity == null) {
				logger.debug("inside districtcontroller getAllDistrict() method");
				logger.info("district type list is empty ");
				throw new ResourceNotFoundException("district not found");
			}
			return objDistrictEntity;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new DistrictServiceException("internal server error");
		}
	}

	@GetMapping("/districtByCondition")
	public List<District> getAllDistrictByCondition() throws ResourceNotFoundException, DistrictServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<District> objDistrict = districtService.getAllDistrictByCondition();

			if (objDistrict == null) {
				logger.debug("inside districtcontroller getAllDistrictByCondition() method");
				logger.info("district type list is empty ");
				throw new ResourceNotFoundException("district not found");
			}
			return objDistrict;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new DistrictServiceException("internal server error");
		}
	}

	@GetMapping("/districtById/{id}")
	public ResponseEntity<DistrictEntity> getDistrictById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(districtService.getDistrictById(id));

	}

	@GetMapping("/districtByCode/{districtcode}")
	public ResponseEntity<District> getDistrictCode(@PathVariable String districtcode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("District search by districtCode");

		return ResponseEntity.ok().body(districtService.findByDistrictCode(districtcode));
	}

	/*
	 * @PostMapping("/addDistrict") public ResponseEntity<District>
	 * createDistrict(@RequestBody District district) {
	 * logger.info("district list is found" + district.toString()); return
	 * ResponseEntity.ok().body(districtService.createDistrict(district));
	 * 
	 * }
	 */
	@PostMapping("/states/district/{stateId}")
	public ResponseEntity<Map<String, Object>> createDistrict(@PathVariable("stateId") Long stateId,
			@Valid @RequestBody District district) throws DistrictServiceException {
		logger.info("district list is found" + district.toString());

		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto = null;
		District district1 = new District();
		try {

			if (district.getDistrictCode() != null && !district.getDistrictCode().isEmpty()) {
				district1 = districtService.findByDistrictCode(district.getDistrictCode());

			} else {
				erroMsgDto = new ErrorMessageDTO();
				erroMsgDto.setCode("400");
				erroMsgDto.setMessage("Invalid RequestStates Payload.");
				response.put("status", 0);
				response.put("error", erroMsgDto);
				return ResponseEntity.badRequest().body(response);
			}
		} catch (Exception e) {
			logger.error(" create district  exception occured." + e.getMessage());
			throw new DistrictServiceException("internal server error");
		}
		if (district1 == null) {
			District saveDistrict = districtService.createDistrict(stateId, district);
			response.put("stateId", stateId);
			response.put("district", saveDistrict);
			response.put("message", " district created succesfully");
			return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", "district already exist");
			return ResponseEntity.accepted().body(response);
		}
	}

	@PutMapping("/states/{stateId}/district/{id}")
	public ResponseEntity<Map<String, Object>> updateDistrict(@PathVariable(value = "stateId") Long stateId,
			@PathVariable(value = "id") Long id, @Valid @RequestBody DistrictEntity district) throws DistrictServiceException  {
		district.setDistrictId(id);
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto = null;
		District district1 = null;
		DistrictEntity district2 = null;
		if (null == district) {
			erroMsgDto = new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
			response.put("status", 0);
			response.put("error", erroMsgDto);
			return ResponseEntity.badRequest().body(response);
		}
		try {
			district1 = districtService.findByDistrictCode(district.getDistrictCode());
			district2 = districtService.getDistrictById(id);
		} catch (Exception e) {
			logger.error(" update Co office  exception occured." + e.getMessage());
			throw new DistrictServiceException("internal server error");
		}
		if (district1 == null || district1.getDistrictCode().equals(district2.getDistrictCode())) {
			DistrictEntity saveDistrict = districtService.updateDistrict(stateId, id, district);
			response.put("stateId", stateId);
			response.put("district", saveDistrict);
			response.put("message", " district modified succesfully");
			return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", "district already exist");
			return ResponseEntity.accepted().body(response);
		}
	}

	@DeleteMapping(value = "/deleteDistrict/{districtId}")
	public ResponseEntity<Map<String, Object>> deleteDistrict(@PathVariable("districtId") Long districtId) throws DistrictServiceException {
		try {
			return districtService.deleteDistrict(districtId);
		} catch (DistrictServiceException e) {

			logger.info("Exception in fetching Districts by state id", e.getMessage());
			throw new DistrictServiceException("internal server error");
		}
	
	}

	@GetMapping("/getAllDistrictsByStateId/{stateId}")
	public ResponseEntity<Map<String, Object>> getAllDistrictByStateId(@PathVariable String stateId)
			throws DistrictServiceException {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.FAILED);

		LoggingUtil.logInfo(className, methodName, "Started" + stateId);
		try {
			List<DistrictEntity> districtList = districtService.getAllDistrictByStateId(stateId);

			if (districtList.isEmpty()|| districtList == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.FAILED);
				logger.info("District list is empty");
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", districtList);
			}
			return ResponseEntity.accepted().body(response);

		} catch (Exception ex) {
			logger.info("Exception in fetching Districts by state id", ex.getMessage());
			throw new DistrictServiceException("internal server error");
		}

	}
}