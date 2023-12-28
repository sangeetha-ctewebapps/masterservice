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

import com.lic.epgs.constant.Constant;
import  com.lic.epgs.constant.ErrorMessageDTO;
import com.lic.epgs.mst.entity.CountryMaster;
import com.lic.epgs.mst.entity.StateMaster;
import com.lic.epgs.mst.entity.StateMasterEntity;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.StateServiceException;
import com.lic.epgs.mst.service.StateCountryService;

@RestController
@CrossOrigin("*")
public class StateCountryController {
	@Autowired
	private StateCountryService statecountryservice;

	String className = this.getClass().getSimpleName();


	private static final Logger logger = LoggerFactory.getLogger(StateCountryController.class);

	 @GetMapping("/states")
	public ResponseEntity<Map<String, Object>> getAllStates() throws StateServiceException {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<StateMasterEntity> getAllStates = statecountryservice.getAllStates();

			if (getAllStates.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", getAllStates);
			}

			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.error(" get All States  exception occured." + ex.getMessage());
			throw new StateServiceException("internal server error");
		}
	}
	 

	 @GetMapping("/getStateByStateName/{stateName}")
	public ResponseEntity<Map<String, Object>> getStateByStateName(@PathVariable String stateName) throws StateServiceException {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<StateMasterEntity> getAllStates = statecountryservice.getStateByStateName(stateName);

			if (getAllStates.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", getAllStates);
			}

			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.error(" get All States  exception occured." + ex.getMessage());
			throw new StateServiceException("internal server error");
		}
	}
	
	@GetMapping("/statesByCondition")
	public List<StateMaster> getAllStatesByCondition() throws ResourceNotFoundException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<StateMaster> statelist = statecountryservice.getAllStatesByCondition();

			if (statelist.isEmpty()) {
				logger.debug("inside statecountrycontroller getAllSatesByCondition() method");
				logger.info("state type list is empty ");
				throw new ResourceNotFoundException("states not found");
			}
			return statelist;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ResourceNotFoundException("internal server error");
		}
	}

	/*
	 * @PostMapping("/country/states/{countryId}") public
	 * ResponseEntity<StateMaster> addStates(@PathVariable(value = "countryId") Long
	 * countryId,
	 * 
	 * @Valid @RequestBody StateMaster states) { String methodName =
	 * Thread.currentThread().getStackTrace()[1].getMethodName();
	 * LoggingUtil.logInfo(className, methodName, "Started"); return
	 * ResponseEntity.ok().body(statecountryservice.addStates(countryId, states));
	 * 
	 * }
	 */
	@PostMapping("/country/states/{countryId}")
	public  ResponseEntity <Map<String, Object>> addStates(@PathVariable(value = "countryId") 
	Long countryId, @Valid @RequestBody StateMaster states){
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto=null;
		StateMaster stateMaster = new StateMaster();
		try {
			
		
		if (states.getStateCode()!=null && !states.getStateCode().isEmpty()) {
			stateMaster = statecountryservice.getStatesByCode(states.getStateCode());
		}
		else {
			erroMsgDto= new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
            response.put("status", 0);
            response.put("error", erroMsgDto);
            return ResponseEntity.badRequest().body(response);	
        }
		}
		
		catch (Exception e) {
			logger.error(" add States  exception occured." + e.getMessage());
		}
		if(stateMaster ==null) {
			StateMaster addStates = statecountryservice.addStates(countryId, states);
			response.put("CountryId", countryId);
	        response.put("states", addStates);
	        response.put("messgae", "State Created successfully.");
	        return ResponseEntity.accepted().body(response);
		
		} else { 
			response.put("messgae", "State code already exist.");
				return ResponseEntity.accepted().body(response);
		}
	}
	
	@GetMapping("/states/{id}")
	public ResponseEntity<StateMasterEntity> getStatesById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(statecountryservice.getStatesById(id));

	}
	@PutMapping("/country/{countryId}/states/{stateId}")
	public ResponseEntity <Map<String, Object>> updateStates(@PathVariable(value = "countryId") Long countryId,
			@PathVariable(value = "stateId") Long stateId, @Valid @RequestBody StateMaster stateRequest) throws StateServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		stateRequest.setStateId(stateId);
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto;
		StateMaster stateMaster = null;
		StateMasterEntity stateMaster1 = null;
		if (null == stateRequest) {
			erroMsgDto= new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
            response.put("status", 0);
            response.put("error", erroMsgDto);
            return ResponseEntity.badRequest().body(response);	
        }
		try {
			stateMaster = statecountryservice.getStatesByCode(stateRequest.getStateCode());
			stateMaster1 = statecountryservice.getStatesById(stateId);
		}
		catch (Exception e) {
			logger.error(" update states  exception occured." + e.getMessage());
			throw new StateServiceException("internal server error");
		}
		if(stateMaster ==null || stateMaster.getStateCode().equals(stateMaster1.getStateCode())) {
			StateMaster addStates = statecountryservice.updateStates(countryId, stateId, stateRequest);
			response.put("CountryId", countryId);
			response.put("states", addStates);
	        response.put("messgae", "State modified successfully.");
	        return ResponseEntity.accepted().body(response);
		
		} else { 
			response.put("messgae", "State code already exist.");
				return ResponseEntity.accepted().body(response);
		}
	}

	/*
	 * @PutMapping("/country/{countryId}/states/{stateId}") public
	 * ResponseEntity<StateMaster> updateStates(@PathVariable(value = "countryId")
	 * Long countryId,
	 * 
	 * @PathVariable(value = "stateId") Long stateId, @Valid @RequestBody
	 * StateMaster stateRequest) { String methodName =
	 * Thread.currentThread().getStackTrace()[1].getMethodName();
	 * LoggingUtil.logInfo(className, methodName, "Started");
	 * stateRequest.setStateId(stateId); return
	 * ResponseEntity.ok().body(statecountryservice.updateStates(countryId, stateId,
	 * stateRequest)); }
	 */

	@GetMapping("/country")
	public List<CountryMaster> getAllCountry() throws ResourceNotFoundException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			logger.info("before list country ");
			List<CountryMaster> countryList = statecountryservice.getAllCountry();
			logger.info("inside all country list ");
			if (countryList.isEmpty()) {
				logger.debug("inside statecountryController getAllCountry() method");
				logger.info("country type list is empty ");
				throw new ResourceNotFoundException("country not found");
			}
			return countryList;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ResourceNotFoundException("internal server error");
		}
	}
	
	@GetMapping("/countryByCondition")
	public List<CountryMaster> getAllCountryByCondition() throws ResourceNotFoundException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			logger.info("before list country ");
			List<CountryMaster> countryList = statecountryservice.getAllCountryByCondition();
			logger.info("inside all country list ");
			if (countryList.isEmpty()) {
				logger.debug("inside statecountryController getAllCountryByCondition() method");
				logger.info("country type list is empty ");
				throw new ResourceNotFoundException("country not found");
			}
			return countryList;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ResourceNotFoundException("internal server error");
		}
	}

	@GetMapping("/country/countryById/{id}")
	public ResponseEntity<CountryMaster> getCountryById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(statecountryservice.getCountryById(id));
	}

	@GetMapping("/country/countryByCode/{code}")
	public ResponseEntity<CountryMaster> getCountryByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("country search by countryCode");

		return ResponseEntity.ok().body(statecountryservice.findByCountryCode(code));
	}

	@GetMapping("/states/statecode/{statecode}")
	public ResponseEntity<StateMaster> getStatesBycode(@PathVariable String statecode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("state search by stateCode");

		return ResponseEntity.ok().body(statecountryservice.getStatesByCode(statecode));
	}

	@PostMapping("/addCountry")
	public ResponseEntity<CountryMaster> addCountry(@RequestBody CountryMaster country) {
		logger.info("country list is found" + country.toString());
		return ResponseEntity.ok().body(statecountryservice.addCountry(country));
	}

	
	@DeleteMapping(value = "/states/deleteState/{stateId}")
	public ResponseEntity<Map<String, Object>> deleteStates(@PathVariable("stateId") Long stateId) throws StateServiceException {
		return statecountryservice.deleteStates(stateId);
	}
	
	@PostMapping(("/getSearchByStateCode"))
	ResponseEntity<Map<String, Object>> getSearchByStateCode(@RequestBody(required = false) StateMaster stateMaster) throws StateServiceException {

		logger.info("Method Start--CityCode--");

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		


		try {
			List<StateMaster> mstResponse = statecountryservice.getSearchByStateCode(stateMaster.getStateCode(), stateMaster.getDescription());
			if (mstResponse == null || mstResponse.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", mstResponse);
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
			throw new StateServiceException("internal server error");
		}

	}

}
