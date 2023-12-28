package com.lic.epgs.mst.usermgmt.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.usermgmt.entity.FunctionalAreaEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.service.FunctionalAreaService;

@RestController
@CrossOrigin("*")
public class FunctionalAreaController {

	private static final Logger logger = LoggerFactory.getLogger(FunctionalAreaController.class);

	@Autowired
	private FunctionalAreaService functionalAreaService;

	String className = this.getClass().getSimpleName();

	/*
	 * Description: This function is used for getting all the data from FunctionalArea Module
	 * Table Name- FUNCTIONAL_AREA
	 * Author- Nandini R
	 */
	
	 @GetMapping("/usermgmt/getAllFunctionalArea")
	public ResponseEntity<Map<String, Object>> getAllFunctionalAreas() {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<FunctionalAreaEntity> functionalAreas = functionalAreaService.getAllFunctionalArea();

			if (functionalAreas.isEmpty() || functionalAreas == null) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", functionalAreas);
			}
			return ResponseEntity.accepted().body(response);

		} catch (Exception ex) {
			logger.error(" get All Functional Area  exception occured." + ex.getMessage());
		}

		return null;
	}
	 
	 @PostMapping(value = "/usermgmt/addFunctionalArea")
		public ResponseEntity<Map<String, Object>> addFunctionalArea(@Valid @RequestBody FunctionalAreaEntity functionalAreaEntity) throws Exception {

			return functionalAreaService.addFuncionalArea(functionalAreaEntity);
		}
	 
	 @PostMapping(value = "/usermgmt/editFunctionalArea")
		public ResponseEntity<Map<String, Object>> editFunctionalArea(@Valid @RequestBody FunctionalAreaEntity functionalAreaEntity) throws Exception {

			return functionalAreaService.editFunctionalArea(functionalAreaEntity);
		}
	 
	 @DeleteMapping(value = "/usermgmt/deleteFunctionalArea/{functionId}")
		public ResponseEntity<Map<String, Object>> deleteFunctionalArea(@PathVariable("functionId") Long functionId) throws Exception {

			return functionalAreaService.deleteFuctionalArea(functionId);
		}
	 
	 
	 @GetMapping(("/getSearchByFunctionName/{functionName}"))
		ResponseEntity<Map<String, Object>> getSearchByFunctionName(@PathVariable("functionName") String functionName )throws Exception {

			Map<String, Object> response = new HashMap<String, Object>();
			response.put(Constant.STATUS, 0);
			response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

			List<FunctionalAreaEntity> mstResponse = functionalAreaService.getSearchByFunctionName(functionName);

			try {
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
			}
			return null;

		}

}