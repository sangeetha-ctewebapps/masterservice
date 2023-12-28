package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.ZoneController;
import com.lic.epgs.mst.entity.ZoneMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.ZoneRepository;

@Service
@Transactional
public class ZoneServiceImpl implements ZoneService {

	@Autowired
	private ZoneRepository zonerepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ZoneController.class);

	@Override
	public List<ZoneMaster> getAllZone() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("i get zone type list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return zonerepository.findAll();

	}

	@Override
	public ZoneMaster getZoneById(long zoneId) {
		// TODO Auto-generated method stub

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ZoneMaster> zoneDb = this.zonerepository.findById(zoneId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for zone By Id" + zoneId);
		if (zoneDb.isPresent()) {
			logger.info("zone type is  found with id" + zoneId);
			return zoneDb.get();
		} else {
			throw new ResourceNotFoundException("zone not found with id:" + zoneId);
		}

	}

	@Override
	public ZoneMaster getZoneByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ZoneMaster> zoneCodeDb = this.zonerepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for zone By code" + code);
		if (zoneCodeDb.isPresent()) {
			logger.info("zone type is  found with code" + code);
			return zoneCodeDb.get();
		} else {
			throw new ResourceNotFoundException("zone not found with code:" + code);
		}
	}

}
