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
import com.lic.epgs.mst.controller.MedicalTestController;
import com.lic.epgs.mst.entity.MedicalTestMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.MedicalTestServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.MedicalTestRepository;

@Service
@Transactional
public class MedicalTestServiceImpl implements MedicalTestService {

	@Autowired
	MedicalTestRepository medicalTestRepository;
	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(MedicalTestController.class);

	@Override
	public List<MedicalTestMst> getAllMedicalTest() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return medicalTestRepository.findAll();
	}
	
	@Override
	 public List<MedicalTestMst> getAllMedicalTestByCondition() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return medicalTestRepository.findAllByCondition();
	}

	@Override
	public MedicalTestMst addMedicalTest(MedicalTestMst medicaltest) {
		// TODO Auto-generated method stub
		return medicalTestRepository.save(medicaltest);
	}

	@Override
	public MedicalTestMst getMedicalTestById(long medicaltestId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<MedicalTestMst> MedicalTestDb = this.medicalTestRepository.findById(medicaltestId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for medicaltest By Id" + medicaltestId);
		if (MedicalTestDb.isPresent()) {
			logger.info("medical is  found with id" + medicaltestId);
			return MedicalTestDb.get();
		} else {
			throw new ResourceNotFoundException("medicaltest not found with id:" + medicaltestId);
		}

	}

	@Override
	public MedicalTestMst updateMedicalTest(MedicalTestMst medicalTest) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		Optional<MedicalTestMst> medicalTestDb = this.medicalTestRepository.findById(medicalTest.getMedicalTestId());
		LoggingUtil.logInfo(className, methodName, "update for MedicalTest By Id" + medicalTest);
		if (medicalTestDb.isPresent()) {
			MedicalTestMst medicalTestUpdate = medicalTestDb.get();
			medicalTestUpdate.setMedicalTestId(medicalTest.getMedicalTestId());
			medicalTestUpdate.setDescription(medicalTest.getDescription());
			medicalTestUpdate.setMedicalTestCode(medicalTest.getMedicalTestCode());
			medicalTestUpdate.setIsActive(medicalTest.getIsActive());
			medicalTestUpdate.setModifiedBy(medicalTest.getModifiedBy());
			medicalTestRepository.save(medicalTestUpdate);
			return medicalTestUpdate;
		} else {
			throw new ResourceNotFoundException("medical test not found with id:" + medicalTest.getMedicalTestId());
		}
	}

	@Override
	public MedicalTestMst getMedicalTestByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<MedicalTestMst> medicaltestDb = this.medicalTestRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for MedicalTest By code" + code);
		if (medicaltestDb.isPresent()) {
			logger.info("medicaltest is  found with code" + code);
			return medicaltestDb.get();
		} else {
			throw new ResourceNotFoundException("medicaltest not found with code:" + code);
		}
	}
	
	@Override
	public void deleteMedicalTest(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		Integer medicaltestDb = this.medicalTestRepository.findDeletedById(id);
		LoggingUtil.logInfo(className, methodName, "delete MedicalTest ById" + id);
		if (medicaltestDb != null) {
			logger.info("MedicalTest soft not deleted with id " + id);

		} else {
			throw new ResourceNotFoundException("MedicalTest soft not found :" + id);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> deleteMedicalTest(Long medicalTestId) throws MedicalTestServiceException {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			if (medicalTestId == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} 

				else {
					medicalTestRepository.deleteByMedicalTestId(medicalTestId);
					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", "Soft Deleted medical test with medical Id : " + medicalTestId);
				}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception ex) {
			logger.info("Could not delete medical test due to : ", ex.getMessage());
			throw new MedicalTestServiceException ("Internal server error");
		}
	}
	
	
}
