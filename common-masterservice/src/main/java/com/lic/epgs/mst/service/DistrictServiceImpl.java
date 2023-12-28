package com.lic.epgs.mst.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.controller.DistrictController;
import com.lic.epgs.mst.entity.District;
import com.lic.epgs.mst.entity.DistrictEntity;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.DistrictEntityRepository;
import com.lic.epgs.mst.repository.DistrictRepository;
import com.lic.epgs.mst.repository.StateRepository;

@Service
@Transactional
public class DistrictServiceImpl implements DistrictService {
	
	@Autowired
	StateRepository stateRepository;
	
	@Autowired
	DistrictRepository districtRepository;

	@Autowired
	DistrictEntityRepository districtEntityRepository;
	

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(DistrictController.class);

	@Override
	 public List<DistrictEntity> getAllDistrict() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		List<DistrictEntity> objDistrictEntity= districtEntityRepository.findAllWithStateIds();
		
		return objDistrictEntity;
	}
	
	@Override
	public List<District> getAllDistrictByCondition() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return districtRepository.findAllByCondition();

	}
	
	@Override
	public District createDistrict(long stateId, District district) {
		return stateRepository.findById(stateId).map(state -> {
			district.setStates(state);
		return districtRepository.save(district);
		}).orElseThrow(() -> new ResourceNotFoundException("stateId " + stateId + " not found"));
	}
	

	@Override
	public DistrictEntity getDistrictById(long districtId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<DistrictEntity> districtDb = this.districtEntityRepository.findById(districtId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for district By Id" + districtId);
		if (districtDb.isPresent()) {
			logger.info("District Type is not found with id" + districtId);
			return districtDb.get();
		} else {
			throw new ResourceNotFoundException("District not found with id:" + districtId);
		}
	}

	@Override
	public District findByDistrictCode(String districtCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<District> districtDb = this.districtRepository.findByDistrictCode(districtCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search District details By districtCode with " + districtCode);
		if (districtDb.isPresent()) {
			logger.info("districtCode is present with code" + districtCode);
			return districtDb.get();
		} else {
			throw new ResourceNotFoundException("District Code not found with code :" + districtCode);
		}
	}

	@Override
	public DistrictEntity updateDistrict(long stateId, long id, DistrictEntity district) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		if (!stateRepository.existsById(stateId)) {
			throw new ResourceNotFoundException("stateId " + stateId + " not found");
		}
		return districtEntityRepository.findById(id).map(districtUpdate -> {
			districtUpdate.setStateId(stateId);
			districtUpdate.setDescription(district.getDescription());
			districtUpdate.setDistrictCode(district.getDistrictCode());
			districtUpdate.setIsActive(district.getIsActive());
			districtUpdate.setModifiedBy(district.getModifiedBy());
			return districtEntityRepository.save(districtUpdate);
		}).orElseThrow(() -> new ResourceNotFoundException("DistrictId " + id + " not found"));
			//districtRepository.save(districtUpdate);
			//return districtUpdate;
		//} else {
			//throw new ResourceNotFoundException("District not found with id:" + district.getId());
		//}

	}
	
	
	@Override
	public ResponseEntity<Map<String, Object>> deleteDistrict(Long districtId) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			if (districtId == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} 

				else {
					districtRepository.deleteBydistrictId(districtId);
					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", "Soft Deleted district with district Id : " + districtId);
				}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception ex) {
			logger.info("Could not delete district due to : ", ex.getMessage());
			return null;
		}
	}

	
	@Override
	public List<DistrictEntity> getAllDistrictByStateId(String stateId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		List<DistrictEntity> districtList= districtEntityRepository.findByStateId(stateId);
		
		return districtList;
	}

}
