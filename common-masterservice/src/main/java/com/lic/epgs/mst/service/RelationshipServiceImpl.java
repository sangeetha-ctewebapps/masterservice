package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.RelationshipController;
import com.lic.epgs.mst.entity.RelationshipMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.RelationshipRepository;

@Service
@Transactional
public class RelationshipServiceImpl implements RelationshipService {
	@Autowired
	RelationshipRepository relationshiprepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(RelationshipController.class);

	
	public List<RelationshipMst> getAllRelationship() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return relationshiprepository.findAll();
	}

	@Override
	public RelationshipMst getRelationshipById(long relationshipId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<RelationshipMst> RelationshipDb = this.relationshiprepository.findById(relationshipId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for relationship By Id" + relationshipId);
		if (RelationshipDb.isPresent()) {
			logger.info("Relationship is  found with id" + relationshipId);
			return RelationshipDb.get();
		} else {
			throw new ResourceNotFoundException("Relationship not found with id:" + relationshipId);
		}
	}

	@Override
	public RelationshipMst findByRelationshipCode(String relationshipcode) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<RelationshipMst> RelationshipDb = this.relationshiprepository.findByRelationshipCode(relationshipcode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Relationship By code" + relationshipcode);
		if (RelationshipDb.isPresent()) {
			logger.info("Relationship is  found with code" + relationshipcode);
			return RelationshipDb.get();
		} else {
			throw new ResourceNotFoundException("Relationship not found with code:" + relationshipcode);
		}
	}

}
