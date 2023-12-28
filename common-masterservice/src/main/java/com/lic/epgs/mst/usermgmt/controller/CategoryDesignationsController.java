package com.lic.epgs.mst.usermgmt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.usermgmt.entity.MasterCategoryDesignationsEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.CategoryDesignationsException;
import com.lic.epgs.mst.usermgmt.exceptionhandling.ZOAdminException;
import com.lic.epgs.mst.usermgmt.service.CategoryDesignationsService;

@CrossOrigin("*")
@RestController

public class CategoryDesignationsController {

	private static final Logger logger = LoggerFactory.getLogger(CategoryDesignationsController.class);

	@Autowired
	private CategoryDesignationsService categoryDesignationsService;

	String className = this.getClass().getSimpleName();

	/*
	 * Description: This function is used for getting all the data from Category Designation Module
	 * Table Name- MASTER_CATEGORY_DESIGNATION
	 * Author- Nandini R
	 */
	
	@GetMapping("/usermgmt/getAllCategoryDesignations")
	 public ResponseEntity<Map<String, Object>> getALLCategoryDesignations() throws CategoryDesignationsException {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<MasterCategoryDesignationsEntity> getAllDesignation = categoryDesignationsService.getALLCategoryDesignations();

			if (getAllDesignation.isEmpty()) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", getAllDesignation);
			}

			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.error(" get All Category Designations  exception occured." + ex.getMessage());
			throw new CategoryDesignationsException ("Internal Server Error");
		}
		//return null;
	}

}
