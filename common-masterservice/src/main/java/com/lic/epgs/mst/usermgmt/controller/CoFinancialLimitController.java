package com.lic.epgs.mst.usermgmt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.JSONObject;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.modal.UserHistoryInputModel;
import com.lic.epgs.mst.usermgmt.entity.CoFinancialLimitEntity;
import com.lic.epgs.mst.usermgmt.entity.CoFinancialLimitNameEntity;
import com.lic.epgs.mst.usermgmt.modal.CoFinancialHistoryInputModel;
import com.lic.epgs.mst.usermgmt.modal.CoFinancialHistoryModel;
import com.lic.epgs.mst.usermgmt.modal.FunctionNameDesignationModel;
import com.lic.epgs.mst.usermgmt.modal.HistoryDetailsModel;
import com.lic.epgs.mst.usermgmt.service.CoFinancialLimitService;

@CrossOrigin("*")
@RestController
public class CoFinancialLimitController {

	@Autowired
	private CoFinancialLimitService coFinancialLimitService;

	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(CoFinancialLimitController.class);
	
	/*
	 * Description: This function is used for getting all the data from Co Fin Limit Module
	 * Table Name- CO_FINANCIAL_LIMIT
	 * Author- Nandini R
	 */

	@PutMapping(value = "/usermgmt/checkerApproveReject")
	public ResponseEntity<Map<String, Object>> checkerApproveReject(@RequestBody List<CoFinancialLimitEntity> coFinancialLimitEntity) throws Exception {
		// return masterUsersService.editMasterUser(masterUsersEntity);
		return coFinancialLimitService.checkerApproveReject(coFinancialLimitEntity);
	}
	
	 @GetMapping("/usermgmt/getAllCoFinLimit")
	public ResponseEntity<Map<String, Object>> getAllCoFinLimit() throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		
		try {
			//List<CoFinancialLimitEntity> coFinnancial = coFinancialLimitService.getAllCoFinLimit();
			List<CoFinancialLimitNameEntity> coFinnancial = coFinancialLimitService.getAllCoFinacialLimitByName();
			if(coFinnancial.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", coFinnancial);
			}
			
			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.error(" get All Co Fin Limit  exception occured." + ex.getMessage());
		}
		return null;
			
	}
	
	/*
	 * Description: This function is used for adding the data into Co Fin Limit Module
	 * Table Name- CO_FINANCIAL_LIMIT
	 * Author- Nandini R
	 */
	
	@PostMapping("/addCoFinancialLimit")
	public ResponseEntity<Map<String, Object>> addCoFinancialLimit(@RequestBody List<CoFinancialLimitEntity> cofinanciallimit) throws Exception {
		return coFinancialLimitService.addCoFinancialLimit(cofinanciallimit);

	}
	
	/*
	 * Description: This function is used for updating the data in Co Fin Limit Module
	 * Table Name- CO_FINANCIAL_LIMIT
	 * Author- Nandini R
	 */

	@PutMapping(value = "/usermgmt/updateCoFinancialLimit")
	public ResponseEntity<Map<String, Object>> updateCoFinancialLimit( @RequestBody List<CoFinancialLimitEntity> coFinancialLimitEntity) throws Exception {

		return coFinancialLimitService.updateCoFinancialLimit(coFinancialLimitEntity);

	}
	
	/*
	 * Description: This function is used for soft deleting the data in Co Fin Limit Module by using primary key
	 * Table Name- CO_FINANCIAL_LIMIT
	 * Author- Nandini R
	 */

	@DeleteMapping(value = "/usermgmt/deleteCoFinancialLimit")
	public ResponseEntity<Map<String, Object>> deleteCoFinancialLimit(List<CoFinancialLimitEntity> coFinancialLimitEntity) throws Exception {

		return coFinancialLimitService.deleteCoFinancialLimit(coFinancialLimitEntity);

	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    Map<String, Object> response = new HashMap<>();
	    if(ex.getBindingResult().getAllErrors() != null) {
	    	response.put(Constant.STATUS, 12);
	    	response.put(Constant.MESSAGE, Constant.INVALID_PAYLOAD);
	    }
	    return response;
	}
	
	/*
	 * Description: This function is used for searching the data in  Co Fin Limit Module using the filters like LobName, CadreId, FinLimit
	 * Table Name- CO_FINANCIAL_LIMIT
	 * Author- Nandini R
	 */
	
	
	
	@PostMapping(("/searchCoFinancialLimit"))
	ResponseEntity<Map<String, Object>> searchCoFinancialLimit(@RequestBody(required = false) CoFinancialLimitNameEntity coFinancialLimitEntity) throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		List<CoFinancialLimitNameEntity> mstResponse=new ArrayList<CoFinancialLimitNameEntity>();
		if(coFinancialLimitEntity.getSearchType() != null && coFinancialLimitEntity.getSearchType().equalsIgnoreCase("checker") ) {
			
			 mstResponse = coFinancialLimitService.searchCoFinancialLimitchecker(coFinancialLimitEntity.getDesignation(), coFinancialLimitEntity.getFunctionName(),coFinancialLimitEntity.getOfficeName(),coFinancialLimitEntity.getStatus(),coFinancialLimitEntity.getLoggedInUserName());
			
		}else if(coFinancialLimitEntity.getSearchType() != null && coFinancialLimitEntity.getSearchType().equalsIgnoreCase("senttomaker") ) {
		
			mstResponse = coFinancialLimitService.searchCoFinancialLimit(coFinancialLimitEntity.getDesignation(), coFinancialLimitEntity.getFunctionName(),coFinancialLimitEntity.getOfficeName(),coFinancialLimitEntity.getStatus(),coFinancialLimitEntity.getLoggedInUserName());
		}
		
		else {
			
		 mstResponse = coFinancialLimitService.searchCoFinancialLimit(coFinancialLimitEntity.getDesignation(), coFinancialLimitEntity.getFunctionName(),coFinancialLimitEntity.getOfficeName(),coFinancialLimitEntity.getStatus(),coFinancialLimitEntity.getLoggedInUserName());
		
		}
		try {
			if (mstResponse == null || mstResponse.isEmpty()) {
				response.put(Constant.STATUS, 201);
				response.put(Constant.DATA, Constant.NO_DATA_FOUND);
				response.put(Constant.MESSAGE, Constant.ERROR);
			} else {
				response.put(Constant.STATUS, 200);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", mstResponse);
			//	response.put("countOfUsers ",mstResponse.size());
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.error(" search CoFinancial Limit  exception occured." + ex.getMessage());
			ex.printStackTrace();
		}
		
		return null;

	}
	
	@GetMapping(("/getFunctionNameAndDesignationByUserName/{srNumber}"))
	ResponseEntity<Map<String, Object>> getFunctionNameAndDesignationByUserName(@PathVariable("srNumber") String srNumber) throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		List<FunctionNameDesignationModel> mstResponse = coFinancialLimitService.getDesignationAndFunctionDetailsByUserName(srNumber);
		//logger.info("UserRoleMapping.getUserRoleBySrNumber::" + mstResponse);

		try {
			if (mstResponse == null || mstResponse.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", mstResponse);
				response.put(Constant.MESSAGE,mstResponse.size());
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.error(" search CoFinancial Limit  exception occured." + ex.getMessage());
		}
		return null;

	}
	
	@PostMapping("/usermgmt/getAllCoFinancialHistoryDetails")
	public ResponseEntity<Map<String, Object>> getAllCoFinancialHistoryDetails(@RequestBody(required = false) CoFinancialHistoryInputModel userHistoryInputModel) throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<CoFinancialHistoryModel> getHistoryDetails = coFinancialLimitService.getCoFinancialHistoryDetails(userHistoryInputModel);

			if (getHistoryDetails.isEmpty() || getHistoryDetails == null) {
				response.put(Constant.STATUS, 201);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 200);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", getHistoryDetails);
			}
			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.error(" getAllHistoryDetails exception occured." + ex.getMessage());
		}
		return null;
	}
	
	@PostMapping(("/getCoFinancialLimit"))
	ResponseEntity<Map<String, Object>> getCoFinancialLimit(@RequestBody Map<String, String> coFinancialLimitEntity) throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		try {
			JSONObject mstResponse = coFinancialLimitService.getCoFinancialLimit(coFinancialLimitEntity.get("userName"), coFinancialLimitEntity.get("functionName"), coFinancialLimitEntity.get("inputParam"),coFinancialLimitEntity.get("srNumber"),coFinancialLimitEntity.get("productCode"),coFinancialLimitEntity.get("groupVariantCode"));
			if (mstResponse == null) {
				response.put(Constant.STATUS, 201);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 200);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", mstResponse.toMap());
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception ex) {
			logger.error("getCoFinancialLimit  exception occured." + ex.getMessage());
		}
		return null;

	}
}



