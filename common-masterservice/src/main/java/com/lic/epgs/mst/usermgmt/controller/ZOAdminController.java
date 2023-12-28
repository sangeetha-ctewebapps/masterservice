package com.lic.epgs.mst.usermgmt.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.usermgmt.entity.ZOAdminEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.ZOAdminException;
import com.lic.epgs.mst.usermgmt.service.ZOAdminService;

@CrossOrigin("*")
@RestController
public class ZOAdminController {

	private static final Logger logger = LoggerFactory.getLogger(ZOAdminController.class);
	
	@Autowired
	private ZOAdminService zoAdminService;

	@PostMapping(value = "/usermgmt/addZOAdmin")
	public ResponseEntity<Map<String, Object>> addZoAdmin(@Valid @RequestBody ZOAdminEntity zOAdminEntity) throws ZOAdminException {

		return zoAdminService.addZoAdmin(zOAdminEntity);

	}
	
	@PutMapping(value = "/usermgmt/updateZOAdmin")
	public ResponseEntity<Map<String, Object>> updateZoAdmin(@Valid @RequestBody ZOAdminEntity zOAdminEntity) throws ZOAdminException {

		return zoAdminService.updateZoAdmin(zOAdminEntity);

	}
	
	 @DeleteMapping(value = "/usermgmt/deleteZoAdmin/{zoAdminId}")
	public ResponseEntity<Map<String, Object>> deleteZoAdmin(@PathVariable("zoAdminId") Long zoAdminId) throws ZOAdminException{

		return zoAdminService.deleteZoAdmin(zoAdminId);

	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    Map<String, Object> response = new HashMap<>();
	    if(ex.getBindingResult().getAllErrors() != null) {
	    	response.put("Status", 12);
	    	response.put("Message", Constant.INVALID_PAYLOAD);
	    }
	    return response;
	}


}
