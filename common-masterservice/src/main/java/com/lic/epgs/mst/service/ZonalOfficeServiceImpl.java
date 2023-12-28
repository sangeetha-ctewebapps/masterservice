package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.UnitStateEntity;
import com.lic.epgs.mst.entity.ZonalEntity;
import com.lic.epgs.mst.entity.ZonalOffice;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.ZonalEntityRepository;
import com.lic.epgs.mst.repository.ZonalOfficeRepository;

@Service
@Transactional
public class ZonalOfficeServiceImpl implements ZonalOfficeService {

	@Autowired
	ZonalOfficeRepository zonalOfficeRepository;
	
	@Autowired
	ZonalEntityRepository zonalEntityRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ZonalOfficeServiceImpl.class);

	@Override
	 public List<ZonalEntity> getAllZonalOffice() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("i get zonal type list ");
		LoggingUtil.logInfo(className, methodName, "Started");
		List<ZonalEntity> objZonalEntity= zonalEntityRepository.getAllZonalOffice();
		return objZonalEntity;
//		return zonalOfficeRepository.findAll();
	}
		
	@Override
	public ZonalOffice getZonalOfficeById(long zonalId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ZonalOffice> zonalDb = this.zonalOfficeRepository.findById(zonalId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for zonal By Id" + zonalId);
		if (zonalDb.isPresent()) {
			logger.info("zonal  type is found with id" + zonalId);
			return zonalDb.get();
		} else {
			throw new ResourceNotFoundException("zonal not found with id:" + zonalId);
		}
	}

	@Override
	public ZonalOffice findByZonalOfficeCode(String zonalCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		Optional<ZonalOffice> zonalDb = this.zonalOfficeRepository.findByZonalOfficeCode(zonalCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search zonal details By zonalCode with " + zonalCode);

		if (zonalDb.isPresent()) {
			logger.info("zonalCode is present with code" + zonalCode);

			return zonalDb.get();
		} else {
			throw new ResourceNotFoundException("zonalCode not found with code :" + zonalCode);

		}
	}
	
	@Override
	public ZonalOffice addZonalOffice(ZonalOffice zonal) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return zonalOfficeRepository.save(zonal);
	}
	
	@Override
	public ZonalOffice updateZonalOffice(ZonalOffice zonal) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		Optional<ZonalOffice> zonalDb = this.zonalOfficeRepository.findById(zonal.getZonalId());
		if (zonalDb.isPresent()) {
			ZonalOffice zonalUpdate = zonalDb.get();
			zonalUpdate.setZonalId(zonal.getZonalId());
			zonalUpdate.setDescription(zonal.getDescription());
			zonalUpdate.setZonalCode(zonal.getZonalCode());
			zonalUpdate.setIsActive(zonal.getIsActive());
			zonalUpdate.setModifiedBy(zonal.getModifiedBy());
			zonalUpdate.setPincode(zonal.getPincode());
			zonalUpdate.setCityName(zonal.getCityName());
			zonalUpdate.setDistrictName(zonal.getDistrictName());
			zonalUpdate.setStateName(zonal.getStateName());
			zonalUpdate.setAddress1(zonal.getAddress1());
			zonalUpdate.setAddress2(zonal.getAddress2());
			zonalUpdate.setAddress3(zonal.getAddress3());
			zonalUpdate.setAddress4(zonal.getAddress4());
			zonalUpdate.setEmailId(zonal.getEmailId());
			zonalUpdate.setTelephoneNo(zonal.getTelephoneNo());
			zonalUpdate.setTin(zonal.getTin());
			zonalUpdate.setGstIn(zonal.getGstIn());
			zonalOfficeRepository.save(zonalUpdate);
			return zonalUpdate;
		} else {
			throw new ResourceNotFoundException("Record not found with id:" + zonal.getZonalId());
		}
	}
	
	@Override
	public void deleteZonalOffice(long zonalId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		
		Integer zonalDB = this.zonalOfficeRepository.findDeletedById(zonalId);
		LoggingUtil.logInfo(className, methodName, "zonalOffice MedicalTest ById" + zonalId);
		if (zonalDB != null) {
			logger.info("zonalOffice soft not deleted with id " + zonalId);

		} else {
			throw new ResourceNotFoundException("zonalOffice soft not found :" + zonalId);
		}
		
		
		
	}
	
	@Override
	public List<ZonalOffice> getAllZonalOfficeByCondition() {
	String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	logger.info("i get zonal type list ");
	LoggingUtil.logInfo(className, methodName, "Started");
	return zonalOfficeRepository.findAllByCondition();
	}
	
	
	

	@Override
	public ZonalEntity getAllZoneDetailsByUnitCode(String unitCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		ZonalEntity cityList= zonalEntityRepository.getAllZoneDetailsByUnitCode(unitCode);
		return cityList;
	}
	

}
