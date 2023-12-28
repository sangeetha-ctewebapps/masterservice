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

import com.lic.epgs.mst.entity.RelationshipMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.RelationshipException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.RelationshipService;

@RestController
@CrossOrigin("*")
public class RelationshipController {

	@Autowired
	private RelationshipService relationshipservice;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(RelationshipController.class);

	 @GetMapping("/Relationship")
	public List<RelationshipMst> getAllRelationship() throws ResourceNotFoundException, RelationshipException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<RelationshipMst> relationship = relationshipservice.getAllRelationship();

			if (relationship.isEmpty()) {
				logger.debug("inside modeofeixtcontroller getAllRelationship() method");
				logger.info("Relationship list is empty ");
				throw new ResourceNotFoundException("Relationship not found");
			}
			return relationship;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new RelationshipException("internal server error");
		}
	}

	@GetMapping("/Relationship/{id}")
	public ResponseEntity<RelationshipMst> getRelationshipById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(relationshipservice.getRelationshipById(id));
	}

	@GetMapping("/RelationshipByCode/{code}")
	public ResponseEntity<RelationshipMst> getRelationshipByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(relationshipservice.findByRelationshipCode(code));

	}
}
