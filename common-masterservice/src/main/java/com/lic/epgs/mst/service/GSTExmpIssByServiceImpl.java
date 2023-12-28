package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.GSTExmpIssByController;
import com.lic.epgs.mst.entity.GSTExmpIssBy;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.GSTExmpIssByRepo;

@Service
@Transactional
public class GSTExmpIssByServiceImpl implements GSTExmpIssByService {

	@Autowired
	GSTExmpIssByRepo gstRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(GSTExmpIssByController.class);

	@Override
	public List<GSTExmpIssBy> getAllGst() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return gstRepository.findAll();
	}

	@Override
	public GSTExmpIssBy getGSTById(long gstId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<GSTExmpIssBy> gstDb = this.gstRepository.findById(gstId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for gst By Id" + gstId);
		if (gstDb.isPresent()) {
			logger.info("gst type is not found with id" + gstId);
			return gstDb.get();
		} else {
			throw new ResourceNotFoundException("gst type not found with id:" + gstId);
		}
	}

	@Override
	public GSTExmpIssBy getGSTByCode(String gstCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<GSTExmpIssBy> gstDb = this.gstRepository.findByGstCode(gstCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for gst By Id" + gstCode);
		if (gstDb.isPresent()) {
			logger.info("gst type is not found with code" + gstCode);
			return gstDb.get();
		} else {
			throw new ResourceNotFoundException("gst not found with code:" + gstCode);
		}
	}

}