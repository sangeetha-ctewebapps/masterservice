package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.LenderBorGrpCate;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.LenderBorGrpCateRepository;

@Transactional
@Service
public class LenderBorGrpCateServiceImpl implements LenderBorGrpCateService {
	@Autowired
	LenderBorGrpCateRepository lenderBorGrpCateRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(LenderBorGrpCateServiceImpl.class);

	/** Get All LenderBorGrpCate type */
	public List<LenderBorGrpCate> getAllLenderBorGrpCate() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		List<LenderBorGrpCate> lenderBorGrpCateList = lenderBorGrpCateRepository.findAll();

		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "All List of LenderBorGrpCate");

		if (lenderBorGrpCateList != null) {
			logger.info("fetched LenderBorGrpCate Lists");
			return lenderBorGrpCateList;
		} else {
			logger.info("LenderBorGrpCate  not found");
			throw new ResourceNotFoundException("LenderBorGrpCate not found ");
			// ResponseEntity.unprocessableEntity().body("failed to fetch LenderBorGrpCate
			// Lists ");
		}
	}

	@Override
	public LenderBorGrpCate getLenderBorGrpCateById(long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LenderBorGrpCate> LbgcByDb = this.lenderBorGrpCateRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for LenderBorGrpCate By Id" + id);
		if (LbgcByDb.isPresent()) {
			logger.info("LenderBorGrpCate is  found with id" + id);
			return LbgcByDb.get();
		} else {
			throw new ResourceNotFoundException("LenderBorGrpCate not found with id:" + id);
		}

	}

	@Override
	public LenderBorGrpCate getLenderBorGrpCateByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LenderBorGrpCate> LbgcByDb = this.lenderBorGrpCateRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for LenderBorGrpCate By code" + code);
		if (LbgcByDb.isPresent()) {
			logger.info("LenderBorGrpCate is  found with code" + code);
			return LbgcByDb.get();
		} else {
			throw new ResourceNotFoundException("LenderBorGrpCate not found with code:" + code);
		}
	}

	

}
