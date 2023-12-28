package com.lic.epgs.mst.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.lic.epgs.mst.controller.CityController;
import com.lic.epgs.mst.entity.CityEntity;
import com.lic.epgs.mst.entity.CityMaster;
import com.lic.epgs.mst.entity.CitySearchEntity;
import com.lic.epgs.mst.exceptionhandling.CityServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.CityEntityRepository;
import com.lic.epgs.mst.repository.CityRepository;
import com.lic.epgs.mst.repository.DistrictRepository;
import com.lic.epgs.mst.repository.JDBCConnection;

@Service
@Transactional

public class CityServiceImpl implements CityService {

	private static final Logger logger = LoggerFactory.getLogger(CityController.class);
	
	@Autowired
	DistrictRepository districtRepository;
	
	@Autowired
	CityEntityRepository cityEntityRepository;
	
	@Autowired
	private JDBCConnection jdbcConnection;
	
	@Autowired
	private CityRepository cityRepository;
	String className = this.getClass().getSimpleName();

	@Override
	 public List<CityEntity> getAllCity(Long start_t, Long end_t) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		List<CityEntity> objCityMaster= cityEntityRepository.findAllWithDistrictIds(start_t , end_t);
		return objCityMaster;
	}
	
	@Override
	public List<CityMaster> getAllCityByCondition() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return cityRepository.findAllByCondition();

	}

	@Override
	public CityEntity getCitytById(long cityId) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<CityEntity> cityDb = this.cityEntityRepository.findById(cityId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for city By Id" + cityId);
		if (cityDb.isPresent()) {
			logger.info("department is  found with id" + cityId);
			return cityDb.get();
		} else {
			throw new ResourceNotFoundException("city not found with id:" + cityId);
		}
	}

	@Override
	public CityMaster getCityByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<CityMaster> cityDb = this.cityRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for city By code" + code);
		if (cityDb.isPresent()) {
			logger.info("city is  found with code" + code);
			return cityDb.get();
		} else {
			throw new ResourceNotFoundException("city not found with code:" + code);
		}
	}

	
	@Override
	public CityMaster addCity(long id, CityMaster city) {
		logger.info("Add city type with district method addCity()");
		return districtRepository.findById(id).map(district -> {
			city.setDistrict(district);
			return cityRepository.save(city);
		}).orElseThrow(() -> new ResourceNotFoundException("DistrictId " + id + " not found"));
	}
	

	
	@Override
	//@CachePut(cacheNames = "masterState")
	public CityEntity updateCity(long id, long cityId, CityEntity cityRequest) {
	String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	LoggingUtil.logInfo(className, methodName, "Started");
	if (!districtRepository.existsById(id)) {
		throw new ResourceNotFoundException("DistrictId " + id + " not found");
	}
	return cityEntityRepository.findById(cityId).map(city -> {
		city.setDistrictId(cityRequest.getDistrictId());
		city.setDescription(cityRequest.getDescription());
		city.setCityCode(cityRequest.getCityCode());
		city.setIsActive(cityRequest.getIsActive());
		city.setModifiedBy(cityRequest.getModifiedBy());
		return cityEntityRepository.save(city);
	}).orElseThrow(() -> new ResourceNotFoundException("StateId " + cityId + " not found"));
	}
	
	
	@Override
	public ResponseEntity<Map<String, Object>> deleteCity(Long cityId) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			if (cityId == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} 

				else {
					cityRepository.deleteBycityId(cityId);
					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", "Soft Deleted city with city Id : " + cityId);
				}
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception ex) {
			logger.info("Could not delete user role due to : ", ex.getMessage());
			throw new ResourceNotFoundException("city not found with code:" + cityId);
		}
	}

	
	@Override
	 public List<CityEntity> getAllCityByDistrictId(Long districtId, Long startAt) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		List<CityEntity> cityList= cityEntityRepository.findAllByDistrictId(districtId, startAt);
		return cityList;
	}

	

	@Override

	public List<CitySearchEntity> getSearchByCityCode(String cityCode, String description) throws SQLException {
		logger.info("Start Service  master users Search");
		List<CitySearchEntity> citySearchList = null;
		String sqlmu = null;
		Connection connection = jdbcConnection.getConnection();
		try {
		
			sqlmu = "SELECT MC.CITY_ID, MC.DISTRICT_ID, MC.DESCRIPTION, MC.IS_ACTIVE, MC.IS_DELETED, \n"
					+ "MC.CITY_CODE,MC.MODIFIED_ON, MC.MODIFIED_BY FROM MASTER_CITY MC \n"
					+ "WHERE ((UPPER(MC.CITY_CODE) LIKE UPPER('%'||?||'%') ) OR (? =''))  \n"
					+ "AND ((UPPER(MC.DESCRIPTION) LIKE UPPER('%'||?||'%') ) OR (? =''))\n"
					+ "order by mc.modified_on desc";

			try(PreparedStatement preparestatements = connection.prepareStatement(sqlmu);)
			{
				preparestatements.setString(1, cityCode);
				preparestatements.setString(2, cityCode);
				preparestatements.setString(3, description);
				preparestatements.setString(4, description);
				
				try(ResultSet rs = preparestatements.executeQuery();)
				{
					citySearchList = new ArrayList<>();
					while (rs.next()) {
						CitySearchEntity citySearchEntity = new CitySearchEntity();
						
						citySearchEntity.setCityId(rs.getLong("CITY_ID"));
						citySearchEntity.setDistrictId(rs.getLong("DISTRICT_ID"));
						citySearchEntity.setModifiedBy(rs.getString("MODIFIED_BY"));
						citySearchEntity.setModifiedOn(rs.getDate("MODIFIED_ON"));
						citySearchEntity.setDescription(rs.getString("DESCRIPTION"));
						citySearchEntity.setIsActive(rs.getString("IS_ACTIVE"));
						citySearchEntity.setIsDeleted(rs.getString("IS_DELETED"));
						citySearchEntity.setCityCode(rs.getString("CITY_CODE"));
						
						citySearchList.add(citySearchEntity);
					}	
		
					logger.info("city code Search executed successfully.");
				}
				catch(Exception e)
				{
					logger.error("getSearchByCityCode exception occured." + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("getSearchByCityCode exception occured." + e.getMessage());
			}
			return citySearchList;
		} catch (Exception e) {
			logger.error("exception occured." + e.getMessage());
			throw new SQLException ("Internal server error");
		} finally {
			if(!connection.isClosed()) {
				connection.close();
			}
		}

	}
@Override
public List<CityEntity> getCityByDistrictId(Long districtId) throws CityServiceException {
	logger.info("Start getCityByDistrictId"); 


	logger.info("districtId--"+districtId);

	List<CityEntity> objCityEntityList = cityEntityRepository.getCityByDistrictId(districtId);

	logger.info("End getUserRoleByLocation");

	return objCityEntityList;
}

@Override
public List<CityEntity> getAllCitiesByStateCode(String stateCode) {
	String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
	LoggingUtil.logInfo(className, methodName, "Started");
	List<CityEntity> cityList= cityEntityRepository.getAllCitiesByStateCode(stateCode);
	return cityList;
}


}
