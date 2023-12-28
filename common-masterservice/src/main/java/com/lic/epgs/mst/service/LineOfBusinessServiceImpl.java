package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.LineOfBusinessController;
import com.lic.epgs.mst.entity.LineOfBusinessMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.LineOfBusinessRepository;

@Service
@Transactional
public class LineOfBusinessServiceImpl implements LineOfBusinessService {

	@Autowired
	private LineOfBusinessRepository lineOfBusinessRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(LineOfBusinessController.class);

	@Override
	public List<LineOfBusinessMaster> getAllLineOfBusiness() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get line of Business list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return lineOfBusinessRepository.findAll();
	}
	
	@Override
	public List<LineOfBusinessMaster> getAllLineOfBusinessByCondition() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get line of Business list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return lineOfBusinessRepository.findAllByCondition();
	}

	@Override
	public LineOfBusinessMaster getLineOfBusinessById(long id) {
		// TODO Auto-generated method stub

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LineOfBusinessMaster> lineOfBusinessDb = this.lineOfBusinessRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for line of business By Id" + id);
		if (lineOfBusinessDb.isPresent()) {
			logger.info("line of business is  found with id" + id);
			return lineOfBusinessDb.get();
		} else {
			throw new ResourceNotFoundException("line of business not found with id:" + id);
		}
	}

	@Override
	public LineOfBusinessMaster getLineBusinessByCode(String code) {
		// TODO Auto-generated method stub

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LineOfBusinessMaster> lineOfBusinessCodeDb = this.lineOfBusinessRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for line of business By code" + code);
		if (lineOfBusinessCodeDb.isPresent()) {
			logger.info("layer type is  found with code" + code);
			return lineOfBusinessCodeDb.get();
		} else {
			throw new ResourceNotFoundException("line of business not found with code:" + code);
		}
	}

	@Override
	public LineOfBusinessMaster createLineOfBusiness(LineOfBusinessMaster lineOfBusiness) {
		// TODO Auto-generated method stub
		return lineOfBusinessRepository.save(lineOfBusiness);
	}

	@Override
	public LineOfBusinessMaster updateLineOfBusiness(LineOfBusinessMaster lineOfBusiness) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		Optional<LineOfBusinessMaster> lineOfBusinessDb = this.lineOfBusinessRepository
				.findById(lineOfBusiness.getLineBusinessId());
		LoggingUtil.logInfo(className, methodName, "update for Line Of Business By Id" + lineOfBusiness);
		if (lineOfBusinessDb.isPresent()) {
			LineOfBusinessMaster lineOfBusinessUpdate = lineOfBusinessDb.get();
			lineOfBusinessUpdate.setLineBusinessId(lineOfBusiness.getLineBusinessId());
			lineOfBusinessUpdate.setDescription(lineOfBusiness.getDescription());
			lineOfBusinessUpdate.setLineBusinessCode(lineOfBusiness.getLineBusinessCode());
			lineOfBusinessUpdate.setIsActive(lineOfBusiness.getIsActive());
			lineOfBusinessUpdate.setModifiedBy(lineOfBusiness.getModifiedBy());
			lineOfBusinessRepository.save(lineOfBusinessUpdate);
			return lineOfBusinessUpdate;
		} else {
			throw new ResourceNotFoundException(
					" line of business not found with id:" + lineOfBusiness.getLineBusinessId());
		}
	}
	

	@Override
	public void deleteLineOfBusiness(Long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		Integer lineOfBusinessDb = this.lineOfBusinessRepository.findDeletedById(id);
		LoggingUtil.logInfo(className, methodName, "delete lineOfBusiness By Id" + id);
		if (lineOfBusinessDb != null) {
			logger.info("lineOfBusiness soft not deleted with id " + id);

		} else {
			throw new ResourceNotFoundException("lineOfBusiness soft not found :" + id);
		}

	}

	

}
