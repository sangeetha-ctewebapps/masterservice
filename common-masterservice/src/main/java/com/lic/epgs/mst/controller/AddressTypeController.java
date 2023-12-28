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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lic.epgs.constant.Constant;
import com.lic.epgs.constant.ErrorMessageDTO;
import com.lic.epgs.mst.entity.AccountingFrequencyMaster;
import com.lic.epgs.mst.entity.AddressType;
import com.lic.epgs.mst.exceptionhandling.AccountingFrequencyServiceException;
import com.lic.epgs.mst.exceptionhandling.AddressServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.AddressTypeService;
import com.lic.epgs.mst.usermgmt.encryptdecrypt.EncryptandDecryptAES;
import com.lic.epgs.mst.usermgmt.encryptdecrypt.EncryptionModel;

@RestController
@CrossOrigin("*")
public class AddressTypeController {

	@Autowired
	private AddressTypeService addressservice;
	String className = this.getClass().getSimpleName();

	 private static final Logger logger = LoggerFactory.getLogger(AddressTypeController.class);
	 
	 @Autowired
		private EncryptandDecryptAES encryptandDecryptAES;


	
	@GetMapping("/address")
	public ResponseEntity<Object> getAllAddress(@RequestHeader("Authorization") String token)
			throws ResourceNotFoundException, AccountingFrequencyServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		Map<String, Object> response = new HashMap<>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		Map<String, Object> response1 = new HashMap<>();

		try {
			 
				List<AddressType> addresses = addressservice.getAllAddress();

			if (addresses.isEmpty()) {
				logger.debug("inside accounting Frequency controller getAllAccountFrequency() method");
				logger.info("accounting Frequency list is empty ");
				response.put(Constant.STATUS, 201);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
				ObjectMapper obj = new ObjectMapper(); 
				 String jsonStr = obj.writeValueAsString(response); 
				//ENcryption Technique
				response1.put(Constant.STATUS, 200);                 
				response1.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);                
				response1.put("body", EncryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr)); 
				return new ResponseEntity<>(response1, HttpStatus.UNAUTHORIZED);
			} else {
				response.put(Constant.STATUS, 200);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", addresses);
				ObjectMapper obj = new ObjectMapper(); 
				 String jsonStr = obj.writeValueAsString(response); 
				//ENcryption Technique
				String encaccessResponse = EncryptandDecryptAES.EncryptAESECBPKCS5Padding(jsonStr);
				response1.put(Constant.STATUS, 200);                 
				response1.put(Constant.MESSAGE, Constant.SUCCESS);                
				response1.put("body", encaccessResponse); 
				return new ResponseEntity<Object>(response1, HttpStatus.OK);
				}
			
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new AccountingFrequencyServiceException("internal server error");
		}
	}
	
	@GetMapping("/addressByCondition")
	public List<AddressType> getAllAddressByCondition() throws ResourceNotFoundException, AddressServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		try {
			List<AddressType> addresses = addressservice.getAllAddressByCondition();

			if (addresses.isEmpty()) {
				logger.debug("inside addresscontroller getAllAddressByCondition() method");
				logger.info("address type list is empty ");
				throw new ResourceNotFoundException("address not found");
			}
			return addresses;
		} catch (Exception ex) {
			logger.error("get address type exception occured." + ex.getMessage());
			throw new AddressServiceException("internal server error");
		}
	}

	@GetMapping("/addressById/{id}")
	public ResponseEntity<AddressType> getAddressById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(addressservice.getAddressById(id));

	}

	@GetMapping("/addressByCode/{addresscode}")
	public ResponseEntity<AddressType> getAddressTypeCode(@PathVariable String addresscode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.info("Address search by addressCode");

		return ResponseEntity.ok().body(addressservice.findByAddressTypeCode(addresscode));
	}

	/*
	 * @PostMapping("/addAddress") public ResponseEntity<AddressType>
	 * createAddress(@RequestBody AddressType address) {
	 * logger.info("address list is found" + address.toString()); return
	 * ResponseEntity.ok().body(addressservice.createAddress(address));
	 * 
	 * }
	 */
	@PostMapping("/addAddress")
	public ResponseEntity <Map<String, Object>> addAddressType(@Valid @RequestBody AddressType address) {
		logger.info("address list is found" + address.toString());
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto=null;
		AddressType addressType = new AddressType();
		try {
		if (address.getAddressCode()!=null && !address.getAddressCode().isEmpty()) {
			addressType = addressservice.findByAddressTypeCode(address.getAddressCode());
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
			logger.error(" add address type exception occured." + e.getMessage());
			throw e;
		}
		if(addressType == null) {
			AddressType saveaddress=addressservice.createAddress(address);
			response.put("address", saveaddress);
			response.put("message", "address type created succesfully");
				return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", "address type already exist");
				return ResponseEntity.accepted().body(response);
		}
	}
	
	@PutMapping("/modifyAddress/{id}")
	public ResponseEntity <Map<String, Object>> updateAddress(@PathVariable Long id, @RequestBody AddressType address) {
		address.setId(id);
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto=null;
		AddressType addressType = null;
		AddressType addressType1 = null;
		if (null == address) {
			erroMsgDto= new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
            response.put("status", 0);
            response.put("error", erroMsgDto);
            return ResponseEntity.badRequest().body(response);	
        }
		try {
			addressType = addressservice.findByAddressTypeCode(address.getAddressCode());
			addressType1 = addressservice.getAddressById(id);
		}
		catch (Exception e) {
			logger.error(" update address type exception occured." + e.getMessage());
		}
		if(addressType == null || (addressType1 != null && addressType.getAddressCode().equals(addressType1.getAddressCode())) ) {
			AddressType saveaddress=addressservice.updateAddress(address);
			response.put("address", saveaddress);
			response.put("message", "address type modified succesfully");
				return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", "address type already exist");
				return ResponseEntity.accepted().body(response);
		}
	}
	/*
	 * @PutMapping("/modifyAddress/{id}") public ResponseEntity<AddressType>
	 * updateAddress(@PathVariable Long id, @RequestBody AddressType address) {
	 * address.setId(id); return
	 * ResponseEntity.ok().body(addressservice.updateAddress(address)); }
	 */

	@DeleteMapping("/deleteAddress/{id}")
	public ResponseEntity<HttpStatus> deleteAddress(@PathVariable Long id) throws AddressServiceException {
		try {
			this.addressservice.deleteAddress(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			throw new AddressServiceException(e.getMessage());

		}
	}
}