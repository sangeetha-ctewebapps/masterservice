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
import com.lic.epgs.mst.entity.CoOffice;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.CoOfficeRepository;

@Service
@Transactional
public class CoOfficeServiceImpl implements CoOfficeService {
	@Autowired
	CoOfficeRepository coOfficeRepository;
	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(CoOfficeServiceImpl.class);


	@Override
	public List<CoOffice> getAllCoOffice() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return coOfficeRepository.findAll();
	}

	@Override
	 public CoOffice getCoOfficeById(long CoId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<CoOffice> CoOfficeDb = this.coOfficeRepository.findById(CoId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Co Office By Id" + CoId);
		if (CoOfficeDb.isPresent()) {
			return CoOfficeDb.get();
		} else {
			throw new ResourceNotFoundException("Co Office not found with id:" + CoId);
		}
	}

	@Override
	public CoOffice findByCoCode(String CoCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Object coDb = this.coOfficeRepository.findByCoCode(CoCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search Co details By coCode:" + CoCode);

		if (coDb != null) {

			return (CoOffice) coDb;
		} else {
			throw new ResourceNotFoundException("Co Office code not found:" + CoCode);
		}
	}

	@Override
	public CoOffice addCoOffice(CoOffice co) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return coOfficeRepository.save(co);
	}

	@Override
	public void deleteZonalOffice(Long coId) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		
		Integer coDB = this.coOfficeRepository.findDeletedById(coId);
		LoggingUtil.logInfo(className, methodName, " coOffice MedicalTest ById" + coId);
		if (coDB != null) {
			logger.info("zonalOffice soft not deleted with id " + coDB);

		} else {
			throw new ResourceNotFoundException("zonalOffice soft not found :" + coDB);
		}
		
		
		
	}

	@Override
	public CoOffice updateCoOffice(CoOffice co) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		Optional<CoOffice> CoDb = this.coOfficeRepository.findById(co.getCoId());
		if (CoDb.isPresent()) {
		CoOffice CoUpdate = CoDb.get();
		CoUpdate.setCoId(co.getCoId());
		CoUpdate.setDescription(co.getDescription());
		CoUpdate.setCo_code(co.getCo_code());
		CoUpdate.setPinCode(co.getPinCode());
		CoUpdate.setCityName(co.getCityName());
		CoUpdate.setDistrictName(co.getDistrictName());
		CoUpdate.setStateCode(co.getStateCode());
		CoUpdate.setStateName(co.getStateName());
		CoUpdate.setAddress1(co.getAddress1());
		CoUpdate.setAddress2(co.getAddress2());
		CoUpdate.setAddress3(co.getAddress3());
		CoUpdate.setAddress4(co.getAddress4());
		CoUpdate.setEmailId(co.getEmailId());
		CoUpdate.setIsActive(co.getIsActive());
		CoUpdate.setIsDeleted(co.getIsDeleted());
		CoUpdate.setTelephoneNo(co.getTelephoneNo());
		CoUpdate.setTin(co.getTin());
		CoUpdate.setGstin(co.getGstin());
		coOfficeRepository.save(CoUpdate);
		return CoUpdate;
		} else {
		throw new ResourceNotFoundException("Record not found with id:" + co.getCoId());
		}
		}

	@Override
	public ResponseEntity<Map<String, Object>> deleteCoOffice(Long coId) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put(Constant.STATUS, 0);
			response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LoggingUtil.logInfo(className, methodName, "Started");

			try {
				if (coId == null) {
					response.put(Constant.STATUS, 0);
					response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
				} 

					else {
						coOfficeRepository.deleteByCoId(coId);
						response.put(Constant.STATUS, 1);
						response.put(Constant.MESSAGE, Constant.SUCCESS);
						response.put("Data", "Soft Deleted designation with Co Id : " + coId);
					}
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

			} catch (Exception ex) {
				logger.info("Could not delete Co_office due to : ", ex.getMessage());
				return null;
			}
		}

}
