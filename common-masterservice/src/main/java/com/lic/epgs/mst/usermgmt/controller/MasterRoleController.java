package com.lic.epgs.mst.usermgmt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.usermgmt.entity.MasterRoleAppEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterRoleEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.MasterRoleException;
import com.lic.epgs.mst.usermgmt.service.MasterRoleService;

@CrossOrigin("*")
@RestController
public class MasterRoleController {

	private static final Logger logger = LoggerFactory.getLogger(MasterRoleController.class);

	@Autowired
	private MasterRoleService masterRoleService;

	String className = this.getClass().getSimpleName();

	/*
	 * Description: This function is used for getting all the data from MASTER ROLE Module
	 * Table Name- MASTER_ROLE
	 * Author- Nandini R
	 */
	
	 @GetMapping("/usermgmt/getAllRoles")
	public ResponseEntity<Map<String, Object>> getAllRoles() throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<MasterRoleAppEntity> roles = masterRoleService.getAllRolesByApp();

			if (roles.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", roles);
			}

			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.error(" get All Roles  exception occured." + ex.getMessage());
		}
		return null;
	}
	
	/*
	 * Description: This function is used for getting all the active role data from MASTER ROLE Module
	 * Table Name- MASTER_ROLE
	 * Author- Nandini R
	 */

	@GetMapping("/usermgmt/getAllActiveRole")
	public ResponseEntity<Map<String, Object>> getAllActiveRole() throws Exception {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<MasterRoleEntity> getallactiverole = masterRoleService.getAllActiveRoleByCondition();

			if (getallactiverole.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", getallactiverole);
			}
			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.error(" get All active role  exception occured." + ex.getMessage());

		}
		return null;
	}

	/*
	 * Description: This function is used for adding the data into MASTER ROLE Module
	 * Table Name- MASTER_ROLE
	 * Author- Nandini R
	 */
	
	@PostMapping("/usermgmt/addRole")
	public ResponseEntity<Map<String, Object>> addRole(@RequestBody MasterRoleEntity masterRoleEntity)
			throws Exception {
		return masterRoleService.addRole(masterRoleEntity);

	}

	/*
	 * Description: This function is used for updating the data in MASTER ROLE Module
	 * Table Name- MASTER_ROLE
	 * Author- Nandini R
	 */
	
	@PutMapping(value = "/usermgmt/editRole")
	public ResponseEntity<Map<String, Object>> editRole(@RequestBody MasterRoleEntity masterRoleEntity) {
		return masterRoleService.updateMasterRole(masterRoleEntity);
	}

	/*
	 * Description: This function is used for soft deleting the data in MASTER ROLE Module by using primary key
	 * Table Name- MASTER_ROLE
	 * Author- Nandini R
	 */
	
	@DeleteMapping(value = "/usermgmt/deleteMasterRole/{roleId}")
	public ResponseEntity<Map<String, Object>> deleteMasterRole(@PathVariable("roleId") Long roleId) {
		return masterRoleService.deleteMasterRole(roleId);
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
}
