
package com.lic.epgs.mst.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.entity.SatelliteEntity;
import com.lic.epgs.mst.entity.SatelliteOffice;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.SatelliteServiceException;
import com.lic.epgs.mst.repository.SatelliteEntityRepository;
import com.lic.epgs.mst.repository.SatelliteOfficeRepository;
import com.lic.epgs.mst.repository.UnitOfficeRepository;

@Service

@Transactional

public class SatelliteOfficeServiceImpl implements SatelliteOfficeService {

	@Autowired
	SatelliteOfficeRepository satelliteOfficeRepository;

	@Autowired
	private UnitOfficeRepository unitOfficeRepository;

	@Autowired
	SatelliteEntityRepository satelliteEntityRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(SatelliteOfficeServiceImpl.class);

	@Override

	public List<SatelliteEntity> getAllSatelliteOffice() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("i get satellite type list ");
		LoggingUtil.logInfo(className, methodName, "Started");
		List<SatelliteEntity> objSatelliteEntity = satelliteEntityRepository.getAllSatelliteOfficeWithUnitIds();
		return objSatelliteEntity;
	}

	public List<SatelliteOffice> getAllSatelliteOfficeByCondition() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("i get satellite type list ");
		LoggingUtil.logInfo(className, methodName, "Started");
		return satelliteOfficeRepository.findAllByCondition();
	}

	@Override

	public SatelliteOffice getSatelliteOfficeById(long satelliteId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<SatelliteOffice> satelliteDb = this.satelliteOfficeRepository.findById(satelliteId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for satellite By Id" + satelliteId);
		if (satelliteDb.isPresent()) {
			logger.info("satellite  type is found with id" + satelliteId);
			return satelliteDb.get();
		} else {
			throw new ResourceNotFoundException("satellite not found with id:" + satelliteId);
		}
	}

	@Override

	@Cacheable(value = "masterCache", key = "#satelliteCode")
	public SatelliteOffice findBySatelliteOfficeCode(String satelliteCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		Optional<SatelliteOffice> satelliteDb = this.satelliteOfficeRepository.findBySatelliteOfficeCode(satelliteCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search satellite details By satelliteCode with " + satelliteCode);

		if (satelliteDb.isPresent()) {
			logger.info("satelliteCode is present with code" + satelliteCode);

			return satelliteDb.get();
		} else {
			throw new ResourceNotFoundException("satelliteCode not found with code :" + satelliteCode);

		}
	}

	@Override

	//@Cacheable(value = "masterCache", key = "#satellite")
	public SatelliteOffice addSatelliteOffice(long unitId, SatelliteOffice satellite) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return unitOfficeRepository.findById(unitId).map(unit -> {
			satellite.setUnit(unit);
			return satelliteOfficeRepository.save(satellite);
		}).orElseThrow(() -> new ResourceNotFoundException("unitId " + unitId + " not found"));
	}

	@Override

	//@CachePut(cacheNames = "masterSatellite")
	public SatelliteEntity updateSatelliteOffice(long unitId, long satelliteId, SatelliteEntity satellite) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		if (!unitOfficeRepository.existsById(unitId)) {
			throw new ResourceNotFoundException("unitId " + unitId + " not found");
		}
		return satelliteEntityRepository.findById(satelliteId).map(satelliteUpdate -> {
			/*
			 * Optional<SatelliteOffice> satelliteDb =
			 * this.satelliteOfficeRepository.findById(satellite.getSatelliteId()); if
			 * (satelliteDb.isPresent()) { SatelliteOffice satelliteUpdate =
			 * satelliteDb.get();
			 * satelliteUpdate.setSatelliteId(satellite.getSatelliteId());
			 */
			satelliteUpdate.setUnitId(satellite.getUnitId());
			satelliteUpdate.setDescription(satellite.getDescription());
			satelliteUpdate.setSatelliteCode(satellite.getSatelliteCode());
			satelliteUpdate.setIsActive(satellite.getIsActive());
			satelliteUpdate.setModifiedBy(satellite.getModifiedBy());
			satelliteUpdate.setPincode(satellite.getPincode());
			satelliteUpdate.setCityName(satellite.getCityName());
			satelliteUpdate.setDistrictName(satellite.getDistrictName());
			satelliteUpdate.setStateName(satellite.getStateName());
			satelliteUpdate.setAddress1(satellite.getAddress1());
			satelliteUpdate.setAddress2(satellite.getAddress2());
			satelliteUpdate.setAddress3(satellite.getAddress3());
			satelliteUpdate.setAddress4(satellite.getAddress4());
			satelliteUpdate.setEmailId(satellite.getEmailId());
			satelliteUpdate.setTelephoneNo(satellite.getTelephoneNo());
			satelliteUpdate.setTin(satellite.getTin());
			satelliteUpdate.setGstIn(satellite.getGstIn());
			return satelliteEntityRepository.save(satelliteUpdate);
		}).orElseThrow(() -> new ResourceNotFoundException("satelliteId " + satelliteId + " not found"));
	}
	/*
	 * satelliteOfficeRepository.save(satelliteUpdate); return satelliteUpdate; }
	 * else { throw new ResourceNotFoundException("Record not found with id:" +
	 * satellite.getSatelliteId()); } }
	 */

	@Override
	//@CacheEvict(value = "masterCache", key = "#satelliteId")
	public void deleteSatelliteOffice(long satelliteId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		Integer satelliteDB = this.satelliteOfficeRepository.findDeletedById(satelliteId);
		LoggingUtil.logInfo(className, methodName, "delete satelliteOffice ById" + satelliteId);
		if (satelliteDB != null) {
			logger.info("satelliteOffice soft not deleted with id " + satelliteId);

		} else {
			throw new ResourceNotFoundException("satelliteOffice soft not found :" + satelliteId);
		}

	}

	@Override
	public ResponseEntity<Map<String, Object>> deleteSateliteOffie(Long satelliteId) throws SatelliteServiceException {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			if (satelliteId == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			}

			else {
				satelliteOfficeRepository.deleteBySateliteId(satelliteId);
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", "Soft Deleted satelite office with satelite Id : " + satelliteId);
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception ex) {
			logger.info("Could not delete satelite due to : ", ex.getMessage());
			throw new SatelliteServiceException("internal server error");
		}
	}



}
