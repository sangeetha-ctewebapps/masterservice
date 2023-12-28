package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.entity.SatelliteEntity;
import com.lic.epgs.mst.entity.SatelliteOffice;
import com.lic.epgs.mst.exceptionhandling.SatelliteServiceException;
public interface SatelliteOfficeService {
	
	public List<SatelliteEntity> getAllSatelliteOffice();
	
	public List<SatelliteOffice> getAllSatelliteOfficeByCondition();

	public SatelliteOffice getSatelliteOfficeById(long satelliteId);

	public SatelliteOffice findBySatelliteOfficeCode(String satelliteCode);
	
	SatelliteOffice addSatelliteOffice(long unitId, SatelliteOffice satellite);

	SatelliteEntity updateSatelliteOffice(long unitId, long satelliteId, SatelliteEntity satellite);

	void deleteSatelliteOffice(long id);
	
	public ResponseEntity<Map<String, Object>> deleteSateliteOffie(Long satelliteId) throws SatelliteServiceException;

}