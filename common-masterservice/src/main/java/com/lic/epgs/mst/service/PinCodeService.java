package com.lic.epgs.mst.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.entity.PinCode;
import com.lic.epgs.mst.entity.PinEntity;
import com.lic.epgs.mst.entity.PinSearchEntity;
import com.lic.epgs.mst.exceptionhandling.PinCodeServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;

public interface PinCodeService {

	public List<PinEntity> getAllPin(Long start_t, Long end_t) throws ResourceNotFoundException, PinCodeServiceException;
	
	public List<PinCode> getAllPinByCondition() throws ResourceNotFoundException, PinCodeServiceException;

	public PinEntity getPinById(long pinId);
	
	public PinCode getPinCodeCode(String pincode);
	
	PinCode createPin(Long cityId, PinCode pin);

	PinEntity updatePin(long cityId, long pinId, PinEntity pin);

	void deletePin(long id);

	public List<PinSearchEntity> getSearchByPinCode(String pinCode, PinSearchEntity pinSearch) throws SQLException, PinCodeServiceException;
	
	public PinEntity getPinByCityId(Long cityId);
	
	public ResponseEntity<Map<String, Object>> deletePinCode(Long pinId);

}
