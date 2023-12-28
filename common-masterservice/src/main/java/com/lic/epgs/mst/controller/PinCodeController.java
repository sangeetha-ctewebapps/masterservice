	package com.lic.epgs.mst.controller;

import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.ErrorMessageDTO;
import com.lic.epgs.mst.entity.PinCode;
import com.lic.epgs.mst.entity.PinEntity;
import com.lic.epgs.mst.entity.PinSearchEntity;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.PinCodeServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.PinCodeService;

@RestController
@CrossOrigin("*")
public class PinCodeController {

	@Autowired
	private PinCodeService pinCodeService;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(PinCodeController.class);

	
	@GetMapping("/pin/{start_t}/{end_t}")
	public List<PinEntity> getAllPin(@PathVariable Long start_t, @PathVariable Long end_t ) throws ResourceNotFoundException, PinCodeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<PinEntity> pines = pinCodeService.getAllPin(start_t, end_t);

			if (pines == null) {
				logger.debug("inside pincontroller getAllPin() method");
				logger.info("pin type list is empty ");
				throw new ResourceNotFoundException("pin not found");
			}
			return pines;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new PinCodeServiceException("internal server error");
		}
	}
	
//	Start Commented by Vijay 
	/*@GetMapping("/pin")
	public List<PinEntity> getAllPin() throws ResourceNotFoundException, PinCodeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<PinEntity> pines = pinCodeService.getAllPin();

			if (pines == null) {
				logger.debug("inside pincontroller getAllPin() method");
				logger.info("pin type list is empty ");
				throw new ResourceNotFoundException("pin not found");
			}
			return pines;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new PinCodeServiceException("internal server error");
		}
	}
	
	@GetMapping("/pinByCondition")
	public List<PinCode> getAllPinByCondition() throws ResourceNotFoundException, PinCodeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<PinCode> pines = pinCodeService.getAllPinByCondition();

			if (pines == null) {
				logger.debug("inside pincontroller getAllPinByCondition() method");
				logger.info("pin type list is empty ");
				throw new ResourceNotFoundException("pin not found");
			}
			return pines;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new PinCodeServiceException("internal server error");
		}
	}*/
	
//	End Commented by Vijay
    
    @RequestMapping(value = "/searchByPinCode", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getSearchByPinCode( @RequestBody PinSearchEntity pinSearch) throws ResourceNotFoundException, PinCodeServiceException, SQLException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		
		Map<String, Object> response = new HashMap<String, Object>();
		 ErrorMessageDTO erroMsgDto = null;
		List<PinSearchEntity> pinSearchEntity = null;
		if (null == pinSearch) {
			erroMsgDto = new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
			response.put("error", erroMsgDto);
			return ResponseEntity.badRequest().body(response);
		}
		try {
			pinSearchEntity = pinCodeService.getSearchByPinCode(pinSearch.getPinCode(), pinSearch);
		} catch (Exception e) {
			throw new ResourceNotFoundException ("Internal Server Error");
		}
		if (pinSearchEntity == null) {
			response.put("message", " No Data Found");
			return ResponseEntity.accepted().body(response);
		} else {
			response.put("pinSearch", pinSearchEntity);
			return ResponseEntity.accepted().body(response);
		}
	}


	@GetMapping("/pinById/{id}")
	public ResponseEntity<PinEntity> getPinById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(pinCodeService.getPinById(id));

	}

	@GetMapping("/pinByCode/{pincode}")
	public ResponseEntity<PinCode> getPinCodeCode(@PathVariable String pincode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Pin search by pinCode");

		return ResponseEntity.ok().body(pinCodeService.getPinCodeCode(pincode));
	}
	
	/*
	 * @RequestMapping(value = "/searchByPinCode/{PinSearchEntity}", method =
	 * RequestMethod.GET) public ResponseEntity<PinSearchEntity> getSearchByPinCode
	 * (@PathVariable("pinId") long pinId) throws JsonProcessingException { String
	 * methodName= Thread.currentThread().getStackTrace()[1].getMethodName();
	 * LoggingUtil.logInfo(className, methodName, "Started");
	 * LoggingUtil.logInfo(className, methodName, "Search for Pin Code--->"+pinId);
	 * 
	 * PinSearchEntity pinSearchEntity; if(pinId != null) {
	 * pinSearchEntity=pinCodeService.getSearchByPinCode(pinId);
	 * if(pinSearchEntity!=null) { LoggingUtil.logInfo(className, methodName,
	 * "PinCode Details for pin number-"+pinId+"-->"+mapper.writeValueAsString(
	 * pinSearchEntity)); LoggingUtil.logInfo(className, methodName, "Completed");
	 * return new ResponseEntity<>(pinSearchEntity,HttpStatus.OK); } else {
	 * LoggingUtil.logInfo(className, methodName,
	 * "No details found for the given pin nuber"+pinId);
	 * LoggingUtil.logInfo(className, methodName, "Completed"); return new
	 * ResponseEntity<>(null,HttpStatus.NOT_FOUND); } } else {
	 * LoggingUtil.logInfo(className, methodName,
	 * "PinCode number is null, please provide a pin number");
	 * LoggingUtil.logInfo(className, methodName, "Completed"); return new
	 * ResponseEntity<>(null,HttpStatus.NOT_FOUND); }
	 * 
	 * }
	 */

	/*
	 * @PostMapping("/addPin") public ResponseEntity<PinCode> createPin(@RequestBody
	 * PinCode pin) { logger.info("pin list is found" + pin.toString()); return
	 * ResponseEntity.ok().body(pinCodeService.createPin(pin));
	 * 
	 * }
	 */
	@PostMapping("/city/pin/{id}")
	public ResponseEntity<Map<String, Object>> createPin(@PathVariable("id") 
	Long id,@Valid @RequestBody PinCode pin) throws PinCodeServiceException {
		logger.info("pin list is found" + pin.toString());
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto = null;
		PinCode pinCode = null;
		if (null == pin) {
			erroMsgDto = new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
			response.put("status", 0);
			response.put("error", erroMsgDto);
			return ResponseEntity.badRequest().body(response);
		}
		try {
			pinCode = pinCodeService.getPinCodeCode(pin.getPinCode());
		} catch (Exception e) {
			throw new PinCodeServiceException("internal server error");
		}
		if (pinCode == null) {
			PinCode savePin = pinCodeService.createPin(id, pin);
			response.put("cityId", id);
			response.put("pin", savePin);
			response.put("message", " pincode created succesfully");
			return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", "pincode already exist");
			return ResponseEntity.accepted().body(response);
		}
	}
	
	
	@PutMapping("/city/{cityId}/pin/{pinId}")
	public ResponseEntity <Map<String, Object>>  updatePin(@PathVariable(value = "cityId") Long cityId,
			@PathVariable(value = "pinId") Long pinId, @RequestBody PinEntity pin) {
		pin.setPinId(pinId);
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto = null;
		PinCode pinCode = null;
		PinEntity pinCode1 = null;
		if (null == pin) {
			erroMsgDto = new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
			response.put("status", 0);
			response.put("error", erroMsgDto);
			return ResponseEntity.badRequest().body(response);
		}
		try {
			pinCode = pinCodeService.getPinCodeCode(pin.getPinCode());
			pinCode1 = pinCodeService.getPinById(pinId);
		} catch (Exception e) {
		}
		if (pinCode == null || (pinCode1 != null && pinCode.getPinCode().equals(pinCode1.getPinCode())) ) {
			PinEntity savePin = pinCodeService.updatePin(cityId, pinId, pin);
			response.put("cityId", pin.getCityId());
			response.put("pin", savePin);
			response.put("message", " pincode modified succesfully");
			return ResponseEntity.accepted().body(response);
		} else { 
			response.put("message", "pincode already exist");
			return ResponseEntity.accepted().body(response);
		}
	}
	
	
	
	@DeleteMapping(value = "/deletePin/{pinId}")
	public ResponseEntity<Map<String, Object>> deletePinCode(@PathVariable("pinId") Long pinId) {
		return pinCodeService.deletePinCode(pinId);
	}
	
	@GetMapping("/getAllPinsByCityId/{cityId}")
	public ResponseEntity<PinEntity> getPinByCityId(@PathVariable Long cityId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(pinCodeService.getPinByCityId(cityId));

	}
}