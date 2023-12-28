package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.BulkWithdrawalTypeController;
import com.lic.epgs.mst.entity.BulkTypeMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.BulkWithdrawalTypeRepository;

@Service
@Transactional
public class BulkWithdrawalTypeServiceImpl implements BulkWithdrawalTypeService {

	@Autowired
	private BulkWithdrawalTypeRepository bulkWithdrawalTypeRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(BulkWithdrawalTypeController.class);

	@Override
	public List<BulkTypeMst> getAllBulkWithdrawalType() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get BulkWithdrawalType list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return bulkWithdrawalTypeRepository.findAll();
	}

	@Override
	public BulkTypeMst getBulkWithdrawalTypeById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<BulkTypeMst> bulkwithdrawaltypeDb = this.bulkWithdrawalTypeRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for BulkWithdrawalType By Id: " + id);
		if (bulkwithdrawaltypeDb.isPresent()) {
			logger.info("BulkWithdrawalType is  found with id:  " +  id);
			return bulkwithdrawaltypeDb.get();
		} else {
			throw new ResourceNotFoundException("BulkWithdrawalType not found with id:  " + id);
		}
	}

	@Override
	public BulkTypeMst getBulkWithdrawalTypeByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<BulkTypeMst> bulkwithdrawaltypeDb = this.bulkWithdrawalTypeRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for BulkWithdrawalType By code:  " + code);
		if (bulkwithdrawaltypeDb.isPresent()) {
			logger.info("BulkWithdrawalType is  found with code: " +   code);
			return bulkwithdrawaltypeDb.get();
		} else {
			throw new ResourceNotFoundException("BulkWithdrawalType not found with code: " + code);
		}
	}

}
