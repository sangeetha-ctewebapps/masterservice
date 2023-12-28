package com.lic.epgs.mst.usermgmt.controller;

import java.sql.SQLException;
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
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.entity.SOAdminEntity;
import com.lic.epgs.mst.usermgmt.entity.UOAdminEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.usermgmt.modal.SoAdminModel;
import com.lic.epgs.mst.usermgmt.modal.UoZoAdminModel;
import com.lic.epgs.mst.usermgmt.service.SoAdminService;
import com.lic.epgs.mst.usermgmt.service.UoAdminService;

@CrossOrigin("*")
@RestController
public class SoAdminController {

	private static final Logger logger = LoggerFactory.getLogger(SoAdminController.class);

	@Autowired
	SoAdminService soAdminService;

	String className = this.getClass().getSimpleName();
	
	 @PostMapping(value = "/usermgmt/addSoAdmin")
	public ResponseEntity<Map<String, Object>> addSoAdmin(@Valid @RequestBody SOAdminEntity soAdminEntity)
			throws Exception {

		return soAdminService.addSoAdmin(soAdminEntity);
	}

	@GetMapping("/soadmin")
	public ResponseEntity<Map<String, Object>> getAllType() throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<SOAdminEntity> soAd = soAdminService.getAllType();

			if (soAd.size() == 0) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", soAd);
			}

			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.info("Exception" + ex.getMessage());
		}
		return null;
	}
	
	@PutMapping(value = "/usermgmt/updateSoAdmin")
	public ResponseEntity<Map<String, Object>> updateSoAdmin(@Valid @RequestBody SOAdminEntity soAdminEntity)
			throws Exception {

		return soAdminService.updateSoAdmin(soAdminEntity);
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
	
	
	
	/*
	 * Description: This function is used for soft deleting the data in UO Admin Module by using primary key
	 * Table Name- UO_ADMIN
	 * Author- Nandini R
	 */
	
	@DeleteMapping(value = "/usermgmt/deleteSoAdmin/{soAdminId}")
	public ResponseEntity<Map<String, Object>> deleteSoAdmin(@PathVariable("soAdminId") Long soAdminId) throws Exception
			 {

		return soAdminService.deleteSoAdmin(soAdminId);

	}
	
	@PostMapping(("/usermgmt/getSOAdminSearch"))
	ResponseEntity<Map<String, Object>> getSOAdminSearch(
			@RequestBody(required = false) MasterUsersEntity mstUserEntity) throws SQLException {
		{

			logger.info("Method Start--getSoAdminSearch--");
		}

		logger.info("LOCATION, SRNUMBER, USERNAME:" + mstUserEntity.getLocation() + " " + mstUserEntity.getLocationType() + " " + mstUserEntity.getSrNumber() + " "
				+ mstUserEntity.getUserName());

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			
			List<SoAdminModel> mstResponse = soAdminService.soSearch(mstUserEntity.getLocationType(),mstUserEntity.getLocation(),
					mstUserEntity.getSrNumber(), mstUserEntity.getUserName());
			logger.info("SoAdminController.soAdminSearch::" + mstResponse);
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
			throw new SQLException ("Error in query");
		}
		
	}


}