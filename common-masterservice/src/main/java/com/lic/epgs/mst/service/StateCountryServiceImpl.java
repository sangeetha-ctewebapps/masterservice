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
import com.lic.epgs.mst.entity.CountryMaster;
import com.lic.epgs.mst.entity.StateMaster;
import com.lic.epgs.mst.entity.StateMasterEntity;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.StateServiceException;
import com.lic.epgs.mst.repository.CountryRepository;
import com.lic.epgs.mst.repository.JDBCConnection;
import com.lic.epgs.mst.repository.StateEntityRepository;
import com.lic.epgs.mst.repository.StateRepository;

@Service
@Transactional
public class StateCountryServiceImpl implements StateCountryService {

	@Autowired
	CountryRepository countryrepository;

	@Autowired
	private JDBCConnection jdbcConnection;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	StateEntityRepository stateEntityRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(StateCountryServiceImpl.class);

	@Override
	public List<CountryMaster> getAllCountry() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		// System.out.println("i am in allCountry METHOD");
		logger.info("i get country type list ");
		LoggingUtil.logInfo(className, methodName, "Started");
		return countryrepository.findAll();
	}

	

	@Override
	public List<StateMasterEntity> getAllStates() {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		LoggingUtil.logInfo(className, methodName, "Started");

		return stateEntityRepository.getAllStates();
	}
	
	
	@Override
	public List<StateMasterEntity> getStateByStateName(String stateName) {

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		LoggingUtil.logInfo(className, methodName, "Started");

		return stateEntityRepository.getStateByStateName(stateName);
	}

	@Override
	public List<CountryMaster> getAllCountryByCondition() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		// System.out.println("i am in allCountry METHOD");
		logger.info("i get country type list ");
		LoggingUtil.logInfo(className, methodName, "Started");
		return countryrepository.findAllByCondition();
	}

	@Override
	public List<StateMaster> getAllStatesByCondition() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("country type list ");
		LoggingUtil.logInfo(className, methodName, "Started");
		return stateRepository.findAllByCondition();
	}

	@Override
	public CountryMaster addCountry(CountryMaster country) {
		logger.info("Add country  type method addCountry()");
		return countryrepository.save(country);
	}

	@Override
	public StateMaster addStates(long countryId, StateMaster states) {
		logger.info("Add states type with country method addState()");
		return countryrepository.findById(countryId).map(country -> {
			states.setCountry(country);
			return stateRepository.save(states);
		}).orElseThrow(() -> new ResourceNotFoundException("CountryId " + countryId + " not found"));
	}

	@Override
	public CountryMaster getCountryById(long countryId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<CountryMaster> countryDb = this.countryrepository.findById(countryId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for country By Id" + countryId);
		if (countryDb.isPresent()) {
			logger.info("country  type is found with id" + countryId);
			return countryDb.get();
		} else {
			throw new ResourceNotFoundException("country not found with id:" + countryId);
		}
	}

	@Override
	public CountryMaster findByCountryCode(String countryCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		Optional<CountryMaster> countryDb = this.countryrepository.findByCountryCode(countryCode);

		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search country details By countryCode with " + countryCode);

		if (countryDb.isPresent()) {
			logger.info("countryCode is present with code" + countryCode);

			return countryDb.get();
		} else {
			throw new ResourceNotFoundException("countryCode not found with code :" + countryCode);

		}
	}

	@Override
	public StateMaster updateStates(long countryId, long stateId, StateMaster stateRequest) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		if (!countryrepository.existsById(countryId)) {
			throw new ResourceNotFoundException("CountryId " + countryId + " not found");
		}
		return stateRepository.findById(stateId).map(state -> {
			state.setDescription(stateRequest.getDescription());
			state.setStateCode(stateRequest.getStateCode());
			state.setIsActive(stateRequest.getIsActive());
			state.setModifiedBy(stateRequest.getModifiedBy());
			return stateRepository.save(state);
		}).orElseThrow(() -> new ResourceNotFoundException("StateId " + stateId + " not found"));
	}

	@Override
	public StateMasterEntity getStatesById(long stateId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<StateMasterEntity> stateDb = this.stateEntityRepository.findById(stateId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for state By Id" + stateId);
		if (stateDb.isPresent()) {
			logger.info("stateId is present with id" + stateId);

			return stateDb.get();

		} else {
			throw new ResourceNotFoundException("state is not found with id:" + stateId);
		}

	}

	@Override
	public StateMaster getStatesByCode(String stateCode) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<StateMaster> stateDb = this.stateRepository.findByStateCode(stateCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for stateCode By Id" + stateCode);
		if (stateDb.isPresent()) {
			logger.info("StateCode is found for search with id" + stateCode);
			return stateDb.get();
		} else {
			throw new ResourceNotFoundException("stateCode not found with id:" + stateCode);
		}

	}

	@Override
	public ResponseEntity<Map<String, Object>> deleteStates(Long stateId) throws StateServiceException {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			if (stateId == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			}

			else {
				stateRepository.deleteBystateId(stateId);
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", "Soft Deleted state with State Id : " + stateId);
			}

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception ex) {
			logger.info("Could not delete user role due to : ", ex.getMessage());
			throw new StateServiceException("internal server error");

		}
	}

	@Override

	public List<StateMaster> getSearchByStateCode(String stateCode, String description)
			throws SQLException, StateServiceException {
		logger.info("Start Service  master users Search");
		List<StateMaster> stateSearchList = null;
		String sqlmu = null;
		Connection connection = jdbcConnection.getConnection();
		try {

			sqlmu = "SELECT MS.STATE_ID, MS.COUNTRY_ID, MS.DESCRIPTION, MS.IS_ACTIVE, MS.IS_DELETED, \n"
					+ "MS.STATE_CODE,MS.MODIFIED_ON, MS.MODIFIED_BY FROM MASTER_STATE MS\n"
					+ "WHERE ((UPPER(MS.STATE_CODE) LIKE UPPER('%'||?||'%') ) OR (? =''))  \n"
					+ "AND ((UPPER(MS.DESCRIPTION) LIKE UPPER('%'||?||'%') ) OR (? =''))\n"
					+ "order by ms.modified_on desc";

			try(PreparedStatement preparestatements = connection.prepareStatement(sqlmu);)
			{
				preparestatements.setString(1, stateCode);
				preparestatements.setString(2, stateCode);
				preparestatements.setString(3, description);
				preparestatements.setString(4, description);
	
				try(ResultSet rs = preparestatements.executeQuery();)
				{
					stateSearchList = new ArrayList<>();
					while (rs.next()) {
						StateMaster stateMaster = new StateMaster();
		
						stateMaster.setStateId(rs.getLong("STATE_ID"));
						stateMaster.setModifiedBy(rs.getString("MODIFIED_BY"));
						stateMaster.setModifiedOn(rs.getDate("MODIFIED_ON"));
						stateMaster.setDescription(rs.getString("DESCRIPTION"));
						// stateMaster.setIsActive(rs.getNCharacterStream(""));
						// stateMaster.setIsDeleted(rs.getString("IS_DELETED"));
						stateMaster.setStateCode(rs.getString("STATE_CODE"));
		
						stateSearchList.add(stateMaster);
					}
		
					logger.info("city code Search executed successfully.");
				}
				catch(Exception e)
				{
					logger.error("getSearchByStateCode exception occured." + e.getMessage());
				}
			}
			catch(Exception e)
			{
				logger.error("getSearchByStateCode exception occured." + e.getMessage());
			}
			return stateSearchList;
		} catch (Exception e) {
			logger.error("exception occured." + e.getMessage());
			throw new StateServiceException("internal server error");
		} finally {
			if (!connection.isClosed()) {
				connection.close();
			}
		}

	}
}
