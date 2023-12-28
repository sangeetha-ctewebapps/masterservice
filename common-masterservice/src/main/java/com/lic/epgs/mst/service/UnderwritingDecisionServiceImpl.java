package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.UnderwritingDecisionController;
import com.lic.epgs.mst.entity.UnderwritingDecisionMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.UnderwritingDecisionRepository;

@Service
@Transactional
public class UnderwritingDecisionServiceImpl implements UnderwritingDecisionService {
	@Autowired
	UnderwritingDecisionRepository underwritingdecisionrepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(UnderwritingDecisionController.class);

	@Override
	public List<UnderwritingDecisionMst> getAllUnderwritingDecision() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return underwritingdecisionrepository.findAll();
	}

	@Override
	public UnderwritingDecisionMst createUnderwritingDecision(UnderwritingDecisionMst underwritingdecision) {
		// TODO Auto-generated method stub
		return underwritingdecisionrepository.save(underwritingdecision);
	}

	@Override
	public UnderwritingDecisionMst getUnderwritingDecisionById(long UnderwritingDecisionId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<UnderwritingDecisionMst> UnderwritingDecisionDb = this.underwritingdecisionrepository.findById(UnderwritingDecisionId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for UnderwritingDecision By Id" + UnderwritingDecisionId);
		if (UnderwritingDecisionDb.isPresent()) {
			logger.info("UnderwritingDecision is  found with id" + UnderwritingDecisionId);
			return UnderwritingDecisionDb.get();
		} else {
			throw new ResourceNotFoundException("UnderwritingDecision not found with id:" + UnderwritingDecisionId);
		}
	}

	@Override
	public UnderwritingDecisionMst getUnderwritingDecisionByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<UnderwritingDecisionMst> UnderwritingDecisionDb = this.underwritingdecisionrepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for UnderwritingDecision By code" + code);
		if (UnderwritingDecisionDb.isPresent()) {
			logger.info("UnderwritingDecision is  found with code" + code);
			return UnderwritingDecisionDb.get();
		} else {
			throw new ResourceNotFoundException("UnderwritingDecision not found with code:" + code);
		}
	}

}
