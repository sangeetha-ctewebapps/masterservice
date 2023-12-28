package com.lic.epgs.mst.usermgmt.controller;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.usermgmt.entity.UOAdminEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.usermgmt.service.UoAdminService;

@CrossOrigin("*")
@RestController
public class UoAdminController {

	private static final Logger logger = LoggerFactory.getLogger(UoAdminController.class);

	@Autowired
	UoAdminService uoAdminService;

	String className = this.getClass().getSimpleName();
	
	/*
	 * Description: This function is used for adding the data into UO Admin Module
	 * Table Name- UO_ADMIN
	 * Author- Nandini R
	 */

	 @PostMapping(value = "/usermgmt/addUoAdmin")
	public ResponseEntity<Map<String, Object>> addUoAdmin(@Valid @RequestBody UOAdminEntity uoAdminEntity)
			throws Exception {

		return uoAdminService.addUoAdmin(uoAdminEntity);
	}

	/*
	 * Description: This function is used for adding the data into UO Admin Module
	 * Table Name- UO_ADMIN
	 * Author- Nandini R
	 */
	
	@GetMapping("/uoadmin")
	public ResponseEntity<Map<String, Object>> getAllType() throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<UOAdminEntity> uoAd = uoAdminService.getAllType();

			if (uoAd.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", uoAd);
			}

			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.info("Exception" + ex.getMessage());
		}
		return null;
	}

	/*
	 * Description: This function is used for soft deleting the data in UO Admin Module by using primary key
	 * Table Name- UO_ADMIN
	 * Author- Nandini R
	 */
	
	@DeleteMapping(value = "/usermgmt/deleteUoAdmin/{uoAdminId}")
	public ResponseEntity<Map<String, Object>> deleteUoAdmin(@PathVariable("uoAdminId") Long uoAdminId) throws Exception
			 {

		return uoAdminService.deleteUoAdmin(uoAdminId);

	}

	/*
	 * Description: This function is used for updating the data in Uo Admin Module
	 * Table Name-UCO_ADMIN
	 * Author- Nandini R
	 */
	
	@PutMapping(value = "/usermgmt/updateUoAdmin")
	public ResponseEntity<Map<String, Object>> updateUoAdmin(@Valid @RequestBody UOAdminEntity uoAdminEntity)
			throws Exception {

		return uoAdminService.updateUoAdmin(uoAdminEntity);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, Object> response = new HashMap<>();
		if (ex.getBindingResult().getAllErrors() != null) {
			response.put("Status", 12);
			response.put("Message", Constant.INVALID_PAYLOAD);
		}
		return response;
	}

}