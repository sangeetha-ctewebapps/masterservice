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
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.constant.ErrorMessageDTO;
import com.lic.epgs.mst.entity.CitySearchEntity;
import com.lic.epgs.mst.entity.DesignationMaster;
import com.lic.epgs.mst.entity.UnitOffice;
import com.lic.epgs.mst.exceptionhandling.DepartmentServiceException;
import com.lic.epgs.mst.exceptionhandling.DesignationServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.DesignationService;
import com.lic.epgs.mst.usermgmt.entity.FunctionalAreaEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.UserRoleMappingException;

@RestController
@CrossOrigin("*")
public class DesignationController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(DesignationController.class);


	 @Autowired
	private DesignationService designationService;

	String className = this.getClass().getSimpleName();

	@GetMapping("/designation")
	public ResponseEntity<Map<String, Object>> getAllDesignation() throws DesignationServiceException {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<DesignationMaster> getAllDesignation = designationService.getAllDesignation();

			if (getAllDesignation.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", getAllDesignation);
			}

			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.error(" get All designation   exception occured." + ex.getMessage());
			throw new DesignationServiceException("Internal server Error");
		}
	}



	@GetMapping("/designationByCondition")
	public List<DesignationMaster> getAllDesignationByCondition() throws ResourceNotFoundException, DesignationServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<DesignationMaster> designation= designationService.getAllDesignationByCondition();

			if (designation.isEmpty()) {
				logger.debug("inside designation controller getAllDepartmentTypeByCondition() method");
				logger.info("designation list is empty ");
				throw new ResourceNotFoundException("designation not found");
			}
			return designation;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new DesignationServiceException("internal server error");
		}
	}

	@GetMapping("/designation/{id}")
	public ResponseEntity<DesignationMaster> getDesignationById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(designationService.getDesignationById(id));

	}

	@GetMapping("/designationByCode/{code}")
	public ResponseEntity<DesignationMaster> getDesignationByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(designationService.getDesignationtByCode(code));

	}
	
	/*
	 * @PostMapping("/addDesignation") public ResponseEntity<DesignationMaster>
	 * addDesignation(@RequestBody addDesignation addDesignation) {
	 * logger.info("designation list is found" + designation.toString()); return
	 * ResponseEntity.ok().body(designationService.addDesignation(designation)); }
	 */
	
	@PostMapping("/addDesignation")
	public ResponseEntity<Map<String, Object>> addDesignation(@Valid @RequestBody DesignationMaster designation) throws DesignationServiceException {
		
		try {
			return designationService.addDesignation(designation);
		} catch (Exception e) {
			logger.error("Exception" + e.getMessage());
			throw new DesignationServiceException ("Internal Server Error");
		}
	}
			
	 @PutMapping(value = "/modifyDesignation")
		public ResponseEntity<Map<String, Object>> updateDesignation(@RequestBody DesignationMaster designation) throws DesignationServiceException {

			return  designationService.updateDesignation(designation);
		}
		
	
	/*@PutMapping("/modifyDesignation/{id}")
	public ResponseEntity <Map<String, Object>> updateDesignation(@PathVariable Long id, @RequestBody DesignationMaster designation) throws DesignationServiceException {
		designation.setDesignationId(id);
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto;
		DesignationMaster designationMaster = null;
		DesignationMaster designationMaster1 = null;
		if (null == designation) {
			erroMsgDto = new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
			response.put("status", 0);
			response.put("error", erroMsgDto);
			return ResponseEntity.badRequest().body(response);
		}

		try {
			designationMaster = designationService.getDesignationtByCode(designation.getDesignationCode());
			designationMaster1 = designationService.getDesignationById(id);
		} catch (Exception e) {
			throw new DesignationServiceException("internal server error");
		}
		if (designationMaster == null || designationMaster.getDesignationCode().equals(designationMaster1.getDesignationCode()) ) {
			DesignationMaster saveDesignation = designationService.updateDesignation(designation);
			response.put("designation", saveDesignation);
			response.put("message", " designation modified succesfully");
			return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", "designation already exist");
			return ResponseEntity.accepted().body(response);
		}
	}*/

	/*@DeleteMapping("/deleteDesignation/{id}")
	public ResponseEntity<HttpStatus> deleteDesignation(@PathVariable Long id) throws DepartmentServiceException {
		try {
			this.designationService.deleteDesignation(id);
			logger.info("designation deleted with id" + id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			throw new DepartmentServiceException(e.getMessage());

		}
	}*/
	
	@DeleteMapping(value = "/deleteDesignation/{designationId}")
	public ResponseEntity<Map<String, Object>> deleteDesignation(@PathVariable("designationId") Long designationId) throws DesignationServiceException {
		return designationService.deleteDesignation(designationId);
	}
	
	@PostMapping(("/getSearchByDesignationCode"))
	ResponseEntity<Map<String, Object>> getSearchByDesignationCode(@RequestBody(required = false) DesignationMaster designationMaster) throws DesignationServiceException {

		logger.info("Method Start--CityCode--");

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);

		

		try {
			List<DesignationMaster> mstResponse = designationService.getSearchByDesignationCode(designationMaster.getDesignationCode(), designationMaster.getDescription(),designationMaster.getOfficeType());
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
		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
			throw new DesignationServiceException("Internal server error");
		}

	}
	

}
