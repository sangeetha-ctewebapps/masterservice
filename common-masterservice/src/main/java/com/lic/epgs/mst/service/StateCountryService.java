package com.lic.epgs.mst.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.entity.CountryMaster;
import com.lic.epgs.mst.entity.StateMaster;
import com.lic.epgs.mst.entity.StateMasterEntity;
import com.lic.epgs.mst.exceptionhandling.StateServiceException;

public interface StateCountryService {

	CountryMaster addCountry(CountryMaster country);

	StateMaster addStates(long countryId, StateMaster states);

	List<CountryMaster> getAllCountry();
	
	List<CountryMaster> getAllCountryByCondition();
	
	public List<StateMasterEntity> getStateByStateName(String stateName);

	public CountryMaster getCountryById(long countryId);

	public CountryMaster findByCountryCode(String countrycode);

	StateMaster updateStates(long countryId, long stateId, StateMaster states);

	List<StateMasterEntity> getAllStates();
	
	List<StateMaster> getAllStatesByCondition();

	public StateMasterEntity getStatesById(long stateId);
	
	public ResponseEntity<Map<String, Object>> deleteStates(Long id) throws StateServiceException;

	public StateMaster getStatesByCode(String statecode);
	
	 List<StateMaster> getSearchByStateCode(String stateCode, String description) throws SQLException, StateServiceException;


	

}