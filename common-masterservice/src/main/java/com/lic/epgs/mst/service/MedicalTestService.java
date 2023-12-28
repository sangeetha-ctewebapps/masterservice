package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.entity.MedicalTestMst;
import com.lic.epgs.mst.exceptionhandling.MedicalTestServiceException;

public interface MedicalTestService {
	MedicalTestMst addMedicalTest(MedicalTestMst medicaltest);

	MedicalTestMst updateMedicalTest(MedicalTestMst medicaltest);

	List<MedicalTestMst> getAllMedicalTest();
	
	List<MedicalTestMst> getAllMedicalTestByCondition();

	public MedicalTestMst getMedicalTestById(long medicaltestId);

	void deleteMedicalTest(long id);

	public MedicalTestMst getMedicalTestByCode(String code);
	
	public ResponseEntity<Map<String, Object>> deleteMedicalTest(Long medicalTestId) throws MedicalTestServiceException;
}
