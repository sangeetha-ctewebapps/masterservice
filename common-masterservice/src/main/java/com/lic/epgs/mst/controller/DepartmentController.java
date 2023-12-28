package com.lic.epgs.mst.controller;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.constant.ErrorMessageDTO;
import com.lic.epgs.mst.entity.DepartmentMaster;
import com.lic.epgs.mst.exceptionhandling.DepartmentServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.DepartmentService;

@RestController
@CrossOrigin("*")
public class DepartmentController {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	private DepartmentService departmentService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/department")
	public List<DepartmentMaster> getAllDepartment() throws ResourceNotFoundException, DepartmentServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<DepartmentMaster> department = departmentService.getAllDepartment();

			if (department.isEmpty()) {
				logger.debug("inside department controller getAllDepartmentType() method");
				logger.info("department list is empty ");
				throw new ResourceNotFoundException("department not found");
			}
			return department;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new DepartmentServiceException("internal server error");
		}
	}
	
	@GetMapping("/departmentByCondition")
	public List<DepartmentMaster> getAllDepartmentByCondition() throws ResourceNotFoundException, DepartmentServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<DepartmentMaster> department = departmentService.getAllDepartmentByCondition();

			if (department.isEmpty()) {
				logger.debug("inside department controller getAllDepartmentTypeByCondition() method");
				logger.info("department list is empty ");
				throw new ResourceNotFoundException("department not found");
			}
			return department;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new DepartmentServiceException("internal server error");
		}
	}

	@GetMapping("/department/{id}")
	public ResponseEntity<DepartmentMaster> getDepartmentById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(departmentService.getDepartmentById(id));

	}

	@GetMapping("/departmentByCode/{code}")
	public ResponseEntity<DepartmentMaster> getDepartmentByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(departmentService.getDepartmentByCode(code));

	}

	/*
	 * @PostMapping("/addDepartment") public ResponseEntity<DepartmentMaster>
	 * addDepartment(@RequestBody DepartmentMaster department) {
	 * logger.info("department list is found" + department.toString()); return
	 * ResponseEntity.ok().body(departmentService.addDepartment(department)); }
	 */
	@PostMapping("/addDepartment")
	public ResponseEntity<Map<String, Object>> addDepartment(@Valid @RequestBody DepartmentMaster department) throws DepartmentServiceException{
		logger.info("department list is found" + department.toString());
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto = null;
		DepartmentMaster departmentMaster = new DepartmentMaster();
		try {
			if(department.getDepartmentCode()!=null && !department.getDepartmentCode().isEmpty()) {
			departmentMaster = departmentService.getDepartmentByCode(department.getDepartmentCode());
		} 
		else
		{
			erroMsgDto = new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
			response.put("status", 0);
			response.put("error", erroMsgDto);
			return ResponseEntity.badRequest().body(response);
		}
		}

		catch (Exception e) {
			logger.error(" add Department  exception occured." + e.getMessage());
		}
		if (departmentMaster == null) {
			DepartmentMaster saveDepartment = departmentService.addDepartment(department);
			response.put("department", saveDepartment);
			response.put("message", " department created succesfully");
			return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", "department already exist");
			return ResponseEntity.accepted().body(response);
		}
	}

	@PutMapping("/modifyDepartment/{id}")
	public ResponseEntity <Map<String, Object>> updateDepartment(@PathVariable Long id,
			@RequestBody DepartmentMaster department) {
		department.setDepartmentId(id);
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto = null;
		DepartmentMaster departmentMaster = null;
		DepartmentMaster departmentMaster1 = null;
		if (null == department) {
			erroMsgDto = new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
			response.put("status", 0);
			response.put("error", erroMsgDto);
			return ResponseEntity.badRequest().body(response);
		}

		try {
			departmentMaster = departmentService.getDepartmentByCode(department.getDepartmentCode());
			departmentMaster1 = departmentService.getDepartmentById(id);
		} catch (Exception e) {
			logger.error(" modify Department exception occured." + e.getMessage());
		}
		if (departmentMaster == null || (departmentMaster1 != null && departmentMaster.getDepartmentCode().equals(departmentMaster1.getDepartmentCode())) ) {
			DepartmentMaster saveDepartment = departmentService.updateDepartment(department);
			response.put("department", saveDepartment);
			response.put("message", " department modified succesfully");
			return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", "department already exist");
			return ResponseEntity.accepted().body(response);
		}
	}

	@DeleteMapping("/deleteDepartment/{id}")
	public ResponseEntity<HttpStatus> deleteDeparment(@PathVariable Long id) throws DepartmentServiceException {
		try {
			this.departmentService.deleteDeparment(id);
			logger.info("department deleted with id" + id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			throw new DepartmentServiceException(e.getMessage());

		}
	}

	
	@PostMapping(("/getSearchByDepartmentCode"))
	ResponseEntity<Map<String, Object>> getSearchByDepartmentCode(@RequestBody(required = false) DepartmentMaster departmentMaster) throws  DepartmentServiceException, SQLException {

		logger.info("Method Start--CityCode--");

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		

		try {
			List<DepartmentMaster> mstResponse = departmentService.getSearchByDepartmentCode(departmentMaster.getDepartmentCode(), departmentMaster.getDescription());
			logger.info("UserRoleMapping.getUserRoleBySrNumber::" + mstResponse);
			if (mstResponse == null || mstResponse.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", mstResponse);
			}

			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			logger.error(" get Search By Department Code  exception occured." + e.getMessage());
			throw new DepartmentServiceException(e.getMessage());
		}

	}
}
