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
import com.lic.epgs.mst.entity.AddressType;
import com.lic.epgs.mst.entity.CoOffice;
import com.lic.epgs.mst.entity.ZonalOffice;
import com.lic.epgs.mst.exceptionhandling.CoOfficeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.CoOfficeService;

@RestController
@CrossOrigin("*")
public class CoOfficeController {
	
	
	@Autowired
	private CoOfficeService coOfficeService;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(CoOfficeController.class);

	 @GetMapping("/coOffice")
	public List<CoOffice> getAllCoOffice() throws ResourceNotFoundException, CoOfficeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		try {
			List<CoOffice> cos = coOfficeService.getAllCoOffice();
			if (cos.isEmpty()) {
				logger.debug("Getting All CO Office");
				throw new ResourceNotFoundException("CO Office is not found");
			}
			return cos;
		} catch (Exception ex) {
			throw new CoOfficeServiceException("CO Office is not found");
		}
	}

	@GetMapping("/coOfficeById/{id}")
	public ResponseEntity<CoOffice> getCoOfficeById(@PathVariable Long id) throws CoOfficeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new customer Office By Id");
			return ResponseEntity.ok().body(coOfficeService.getCoOfficeById(id));
		} catch (Exception e) {
			logger.error("CO Office is not found with ID : Invalid Data", e);
			throw new CoOfficeServiceException("CO Office is not found with ID" + id);
		}
	}
	
	@PostMapping("/addCo")
	public ResponseEntity <Map<String, Object>>  addCoOffice(@Valid @RequestBody CoOffice co) throws CoOfficeServiceException {
		logger.info("co list is found" + co.toString());
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto=null;
		CoOffice coOffice = new CoOffice();
		try {
			if (co.getCo_code()!=null && !co.getCo_code().isEmpty()) {
			coOffice = coOfficeService.findByCoCode(co.getCo_code());
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
			logger.error(" add Co office  exception occured." + e.getMessage());
			throw new CoOfficeServiceException("CO Office is not found with code");
		}
		if(coOffice == null) {
			CoOffice saveco= coOfficeService.addCoOffice(co);
			response.put("co", saveco);
			response.put("message", "Co Office created succesfully");
				return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", "Co Office already exist");
			return ResponseEntity.accepted().body(response);
		}
	}

	@GetMapping("/coOfficeByCode/{code}")
	public ResponseEntity<CoOffice> getCoOfficeByCode(@PathVariable String code) throws CoOfficeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new customer Office By code");
			return ResponseEntity.ok().body(coOfficeService.findByCoCode(code));
		} catch (Exception e) {
			logger.error("CO Office is not found with code : Invalid Data", e);
			throw new CoOfficeServiceException("CO Office is not found with code" + code);
		}
	}
	@PutMapping("/updateCo/{coId}")
	public ResponseEntity <Map<String, Object>> updateCo(@PathVariable Long coId, @RequestBody CoOffice co) throws CoOfficeServiceException {
		co.setCoId(coId);
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto=null;
		CoOffice addressType = null;
		CoOffice addressType1 = null;
		if (null == co) {
			erroMsgDto= new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
            response.put("status", 0);
            response.put("error", erroMsgDto);
            return ResponseEntity.badRequest().body(response);	
        }
		try {
			addressType = coOfficeService.findByCoCode(co.getCo_code());
			addressType1 = coOfficeService.getCoOfficeById(coId);
		}
		catch (Exception e) {
			logger.error(" update Co office  exception occured." + e.getMessage());
			throw new CoOfficeServiceException("CO Office is not found with code" + co);
		}
		if(addressType == null || addressType.getCo_code().equals(addressType1.getCo_code()) ) {
			CoOffice saveCo= coOfficeService.updateCoOffice(co);
			response.put("address", saveCo);
			response.put("message", "address type modified succesfully");
				return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", "address type already exist");
				return ResponseEntity.accepted().body(response);
		}
	}
	
	@DeleteMapping(value = "/deleteCoOffice/{coId}")
	public ResponseEntity<Map<String, Object>> deleteCoOffice(@PathVariable("coId") Long coId) {
		return coOfficeService.deleteCoOffice(coId);
	}
	
	
	/*@DeleteMapping("/deleteCo/{id}")
	public ResponseEntity<HttpStatus> deleteCoOffice(@PathVariable Long id) {

		try {
			this.coOfficeService.deleteZonalOffice(id);
			logger.info("co deleted with id" + id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			throw new ResourceNotFoundException(e.getMessage());

		}
	}*/
}
