package com.lic.epgs.mst.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.entity.CityEntity;
import com.lic.epgs.mst.entity.CityMaster;
import com.lic.epgs.mst.entity.CitySearchEntity;
import com.lic.epgs.mst.exceptionhandling.CityServiceException;

public interface CityService {

	public List<CityEntity> getAllCity(Long start_t, Long end_t);
	
	List<CityMaster> getAllCityByCondition();

	public CityEntity getCitytById(long cityId);

	public CityMaster getCityByCode(String code);

	CityMaster addCity(long districtId, CityMaster city);

	CityEntity updateCity(long districtId, long cityId, CityEntity city);

	//void deleteCity(Long id);
	
	public ResponseEntity<Map<String, Object>> deleteCity(Long cityId);


   List<CitySearchEntity> getSearchByCityCode(String cityCode, String description) throws SQLException;

	
	public List<CityEntity> getAllCityByDistrictId(Long districtId, Long startAt);

	public List<CityEntity> getAllCitiesByStateCode(String stateCode);
	
	public List<CityEntity> getCityByDistrictId(Long districtId) throws CityServiceException;
	
	//public List<PinSearchEntity> getSearchByCityCode(String cityCode) throws SQLException;

}
