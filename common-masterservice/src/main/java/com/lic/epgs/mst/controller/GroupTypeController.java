package com.lic.epgs.mst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.GroupType;
import com.lic.epgs.mst.exceptionhandling.GroupTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.GroupTypeService;

@RestController
@CrossOrigin("*")
public class GroupTypeController {
	@Autowired
	private GroupTypeService grouptypeservice;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(GroupTypeController.class);

	 @GetMapping("/groupType")
	public List<GroupType> getAllGroupType() throws ResourceNotFoundException, GroupTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		try {
			List<GroupType> groupes = grouptypeservice.getAllGroupType();
			if (groupes.isEmpty()) {
				logger.debug("Getting All Group Type");
				throw new ResourceNotFoundException("Group Type is not found");
			}
			return groupes;
		} catch (Exception ex) {
			logger.error("Group type is not found with ID : Invalid Data", ex);
			throw new GroupTypeServiceException("Group type is not found");
		}
	}

	@GetMapping("/groupTypeById/{id}")
	public ResponseEntity<GroupType> getGroupTypeById(@PathVariable Long id) throws GroupTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new group Type By Id");
			return ResponseEntity.ok().body(grouptypeservice.getGroupTypeById(id));
		} catch (Exception e) {
			logger.error("Group type is not found with ID : Invalid Data", e);
			throw new GroupTypeServiceException("Group type is not found with ID" + id);
		}
	}

	@GetMapping("/groupTypeByCode/{code}")
	public ResponseEntity<GroupType> getGroupTypeByCode(@PathVariable String code)
			throws GroupTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new group Type By code");
			return ResponseEntity.ok().body(grouptypeservice.findByGroupCode(code));
		} catch (Exception e) {
			logger.error("Group type is not found with code : Invalid Data", e);
			throw new GroupTypeServiceException("Group type is not found with code" + code);
		}
	}
}
