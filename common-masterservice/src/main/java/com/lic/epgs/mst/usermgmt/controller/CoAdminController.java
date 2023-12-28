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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.usermgmt.entity.CoAdminEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.COAdminException;
import com.lic.epgs.mst.usermgmt.exceptionhandling.ZOAdminException;
import com.lic.epgs.mst.usermgmt.modal.CoAdminModel;
import com.lic.epgs.mst.usermgmt.modal.UoZoAdminModel;
import com.lic.epgs.mst.usermgmt.service.CoAdminService;

@CrossOrigin("*")
@RestController

public class CoAdminController {

	private static final Logger logger = LoggerFactory.getLogger(CoAdminController.class);

	@Autowired
	private CoAdminService coAdminService;

	String className = this.getClass().getSimpleName();
	
	/*
	 * Description: This function is used for getting all the data from Co Admin Module
	 * Table Name- CO_ADMIN
	 * Author- Nandini R
	 */

	 @GetMapping("/usermgmt/getAllCoAdmin")
	public ResponseEntity<Map<String, Object>> getAllCoAdmin()  {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<CoAdminEntity> getAllCoAdmin = coAdminService.getAllCoAdmin();

			if (getAllCoAdmin.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", getAllCoAdmin);
			}

			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.error(" get All Co admin  exception occured." + ex.getMessage());
		}
		return null;
	}
	
	/*
	 * Description: This function is used for adding the data into Co Admin Module
	 * Table Name- CO_ADMIN
	 * Author- Nandini R
	 */
	
	@PostMapping(value = "/usermgmt/addCoAdmin")
	public ResponseEntity<Map<String, Object>> addCoAdmin(@Valid @RequestBody CoAdminEntity coAdminEntity) throws COAdminException {

		try {
			return  coAdminService.addCoAdmin(coAdminEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(" add Co admin exception occured." + e.getMessage());
			throw new COAdminException ("Internal Server Error");
		}
	}
	
	/*
	 * Description: This function is used for searching the data in Co Admin Module using the filters like LOCATION, SRNUMBER, USERNAME
	 * Table Name- CO_ADMIN
	 * Author- Nandini R
	 */
	
	@PostMapping(("/usermgmt/getCOAdminSearch"))
	ResponseEntity<Map<String, Object>> getCoAdminSearch(
			@RequestBody(required = false) MasterUsersEntity mstUserEntity) throws Exception {
		{

			logger.info("Method Start--getUsersWithCriteria2--");
		}

		logger.info("LOCATION, SRNUMBER, USERNAME:" + mstUserEntity.getLocation() + " " + mstUserEntity.getLocationType() + " " + mstUserEntity.getSrNumber() + " "
				+ mstUserEntity.getUserName());

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		List<CoAdminModel> mstResponse = coAdminService.coSearch(mstUserEntity.getLocationType(),mstUserEntity.getLocation(),
				mstUserEntity.getSrNumber(), mstUserEntity.getUserName());
		logger.info("UserRoleMapping.getUserRoleBySrNumber::" + mstResponse);

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
			logger.error(" get Co Admin Search  exception occured." + ex.getMessage());
		}
		return null;
	}
	
	/*
	 * Description: This function is used for updating the data in Co Admin Module
	 * Table Name- CO_ADMIN
	 * Author- Nandini R
	 */
	
	@PutMapping(value = "/usermgmt/updateCoAdmin")
    public ResponseEntity<Map<String, Object>> updateCoAdmin(@Valid @RequestBody CoAdminEntity coAdminEntity)
            throws COAdminException {
 
        try {
			return coAdminService.updateCoAdmin(coAdminEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new COAdminException ("Internal Server Error");
		}
    }

	/*
	 * Description: This function is used for soft deleting the data in Co Admin Module by using primary key
	 * Table Name- CO_ADMIN
	 * Author- Nandini R
	 */

    @DeleteMapping(value = "/usermgmt/deleteCoAdmin/{coAdminId}")
    public ResponseEntity<Map<String, Object>> deleteCoAdmin(@PathVariable("coAdminId") Long coAdminId) throws COAdminException{
 
        return coAdminService.deleteCoAdmin(coAdminId);
 
    }

}
