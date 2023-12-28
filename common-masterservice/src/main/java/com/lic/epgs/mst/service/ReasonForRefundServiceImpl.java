package com.lic.epgs.mst.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.entity.ReasonForRefundEntity;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ReasonForRefundException;
import com.lic.epgs.mst.repository.ReasonForRefundRepository;
import com.lic.epgs.mst.usermgmt.controller.ModuleController;

@Service
@Transactional

public class ReasonForRefundServiceImpl implements ReasonForRefundService {

	@Autowired
	private ReasonForRefundRepository reasonForRefundRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);

	@Override
	public List<ReasonForRefundEntity> getAllReasonForRefund() {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		LoggingUtil.logInfo(className, methodName, "Started");

		return reasonForRefundRepository.getAllReasonForRefund();
	}

	@Override
	public ReasonForRefundEntity getRefundReasonByRefundCode(String refundCode) throws ReasonForRefundException {
		logger.info("Start getRefundReasonByRefundCode");

		logger.info("refundCode--" + refundCode);

		ReasonForRefundEntity objReasonForRefundEntity = reasonForRefundRepository
				.getRefundReasonByRefundCode(refundCode);

		logger.info("End getRefundReasonByRefundCode");

		return objReasonForRefundEntity;
	}

}
