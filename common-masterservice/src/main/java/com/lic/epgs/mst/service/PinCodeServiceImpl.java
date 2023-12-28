
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
import com.lic.epgs.mst.controller.PinCodeController;
import com.lic.epgs.mst.entity.PinCode;
import com.lic.epgs.mst.entity.PinEntity;
import com.lic.epgs.mst.entity.PinSearchEntity;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.PinCodeServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.CityRepository;
import com.lic.epgs.mst.repository.JDBCConnection;
import com.lic.epgs.mst.repository.PinCodeRepository;
import com.lic.epgs.mst.repository.PinEntityRepository;

@Service
@Transactional
public class PinCodeServiceImpl implements PinCodeService {

	@Autowired
	PinCodeRepository pinCodeRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	PinEntityRepository pinEntityRepository;

	@Autowired
	private JDBCConnection jdbcConnection;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(PinCodeController.class);

	@Override
	public List<PinEntity> getAllPin(Long start_t, Long end_t) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		List<PinEntity> objPinEntity = pinEntityRepository.findAllWithDistrictIds(start_t , end_t);
		return objPinEntity;
//		return pinCodeRepository.findAll();
	}

	@Override
	public List<PinCode> getAllPinByCondition() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return pinCodeRepository.findAllByCondition();
	}

	@Override
	public PinCode createPin(Long cityId, PinCode pin) {
		return cityRepository.findById(cityId).map(city -> {
			pin.setCity(city);
			return pinCodeRepository.save(pin);
		}).orElseThrow(() -> new ResourceNotFoundException("cityId " + cityId + " not found"));
	}

	@Override
	public PinEntity getPinById(long pinId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<PinEntity> pinDb = this.pinEntityRepository.findById(pinId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for pin By Id" + pinId);
		if (pinDb.isPresent()) {
			logger.info("Pin Type is not found with id" + pinId);
			return pinDb.get();
		} else {
			throw new ResourceNotFoundException("Pin not found with id:" + pinId);
		}
	}

	@Override
	public PinCode getPinCodeCode(String pinCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<PinCode> pinDb = this.pinCodeRepository.findByPinCode(pinCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search Pin details By pinCode with " + pinCode);
		if (pinDb.isPresent()) {
			logger.info("pinCode is present with code" + pinCode);
			return pinDb.get();
		} else {
			throw new ResourceNotFoundException("Pin Code not found with code :" + pinCode);
		}
	}

	@Override
	public PinEntity updatePin(long cityId, long pinId, PinEntity pin) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		if (!cityRepository.existsById(cityId)) {
			throw new ResourceNotFoundException("cityId " + cityId + " not found");
		}
		return pinEntityRepository.findById(pinId).map(pinUpdate -> {
			// Optional<PinCode> pinDb = this.pinCodeRepository.findById(pin.getId());
			// LoggingUtil.logInfo(className, methodName, "update for pin By Id" + pin);
			// if (pinDb.isPresent()) {
			// PinCode pinUpdate = pinDb.get();
			// pinUpdate.setId(pin.getId());
			pinUpdate.setCityId(pin.getCityId());
			pinUpdate.setDescription(pin.getDescription());
			pinUpdate.setPinCode(pin.getPinCode());
			pinUpdate.setIsActive(pin.getIsActive());
			pinUpdate.setModifiedBy(pin.getModifiedBy());
			return pinEntityRepository.save(pinUpdate);
		}).orElseThrow(() -> new ResourceNotFoundException("Pinid " + pinId + " not found"));
		// return pinUpdate;
		// } else {
		// throw new ResourceNotFoundException("Pin not found with id:" + pin.getId());
		// }
	}

	@Override
	public void deletePin(long pinid) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		Integer pinDb = this.pinCodeRepository.findDeletedById(pinid);
		LoggingUtil.logInfo(className, methodName, "delete pinCode ById" + pinid);
		if (pinDb != null) {
			logger.info("pinCode soft not deleted with id " + pinid);

		} else {
			throw new ResourceNotFoundException("pinCode soft not found :" + pinid);
		}

	}



	 public List<PinSearchEntity> getSearchByPinCode(String pinCode, PinSearchEntity pinSearch) throws SQLException, PinCodeServiceException {
		List<PinSearchEntity> pinCodeList = null;
		String sql = null;
		String sql1 = null;
		Connection connection = jdbcConnection.getConnection();
		try {
			
			// System.out.println("connection"+connection);
			if (pinSearch.getPinCode() != null) {
				sql = "SELECT MP.PIN_ID, MP.PIN_CODE,MP.IS_ACTIVE,MP.IS_DELETED,MP.MODIFIED_BY,MP.MODIFIED_ON, MC.CITY_ID, MC.CITY_CODE, MC.DESCRIPTION CITY_DESCRIPTION, MD.DISTRICT_ID, MD.DISTRICT_CODE, MD.DESCRIPTION DISTRICT_DESCRIPTION, MS.STATE_ID,MS.STATE_CODE, MS.DESCRIPTION STATE_DESCRIPTION, MCC.COUNTRY_ID,MCC.COUNTRY_CODE, MCC.DESCRIPTION COUNTRY_DESCRIPTION\n"
						+ "from MASTER_CITY mc,MASTER_DISTRICT md,MASTER_STATE ms,MASTER_COUNTRY mcc,MASTER_PIN_CODE MP "
						+ "where mcc.COUNTRY_ID=ms.COUNTRY_ID and ms.STATE_ID=md.STATE_ID "
						+ "and mc.DISTRICT_ID=md.DISTRICT_ID and MP.CITY_ID = MC.CITY_ID and MP.PIN_CODE=? ";

				try(PreparedStatement preparestatements = connection.prepareStatement(sql);)
				{
					if (pinSearch.getPinCode() == null || pinSearch.getPinCode().equalsIgnoreCase("")) {
						preparestatements.setString(1, "");
					} else {
						preparestatements.setString(1, pinSearch.getPinCode());
					}
	
	
					try(ResultSet rs = preparestatements.executeQuery();)
					{
						pinCodeList = new ArrayList<>();
						while (rs.next()) {
							PinSearchEntity pinSearchEntity = new PinSearchEntity();
							pinSearchEntity.setPinId(rs.getLong("PIN_ID"));
							pinSearchEntity.setPinCode(rs.getString("PIN_CODE"));
							pinSearchEntity.setIsActive(rs.getString("IS_ACTIVE"));
							pinSearchEntity.setIsDeleted(rs.getString("IS_DELETED"));
							pinSearchEntity.setModifiedBy(rs.getString("MODIFIED_BY"));
							pinSearchEntity.setModifiedOn(rs.getDate("MODIFIED_ON"));
							 pinSearchEntity.setCityId(rs.getLong("CITY_ID"));
							pinSearchEntity.setCityCode(rs.getString("CITY_CODE"));
							pinSearchEntity.setCityDescription(rs.getString("CITY_DESCRIPTION"));
							 pinSearchEntity.setDistrictId(rs.getLong("DISTRICT_ID"));
							pinSearchEntity.setDistrictCode(rs.getString("DISTRICT_CODE"));
							pinSearchEntity.setDistrictDescription(rs.getString("DISTRICT_DESCRIPTION"));
							pinSearchEntity.setStateId(rs.getLong("STATE_ID"));
							pinSearchEntity.setStateCode(rs.getString("STATE_CODE"));
							pinSearchEntity.setStateDescription(rs.getString("STATE_DESCRIPTION"));
							pinSearchEntity.setCountryId(rs.getLong("COUNTRY_ID"));
							pinSearchEntity.setCountryCode(rs.getString("COUNTRY_CODE"));
							pinSearchEntity.setCountryDescription(rs.getString("COUNTRY_DESCRIPTION"));
		
							pinCodeList.add(pinSearchEntity);
						}
					}
					catch(Exception e)
					{
						logger.error("getPinCode exception occured." + e.getMessage());
					}
				}
				catch(Exception e)
				{
					logger.error("getPinCode exception occured." + e.getMessage());
				}
			} else if (pinSearch.getDistrictId() + "" != null && pinSearch.getStateId() + "" != null
					&& pinSearch.getCityId() + "" != null) {
//				sql1 = "select PIN_ID, PIN_CODE FROM MASTER_PIN_CODE where CITY_ID=?";
				sql1 = " SELECT P.PIN_ID, P.PIN_CODE,P.IS_ACTIVE,P.IS_DELETED,P.MODIFIED_BY,P.MODIFIED_ON, C.CITY_ID, C.CITY_CODE,C.DESCRIPTION AS C_DESCRIPTION, "+
						"D.DISTRICT_ID, D.DISTRICT_CODE, D.DESCRIPTION AS D_DESCRIPTION,S.STATE_ID, S.STATE_CODE, "+
						"S.DESCRIPTION AS S_DESCRIPTION FROM MASTER_PIN_CODE P "+
						"INNER JOIN MASTER_CITY C ON P.CITY_ID=C.CITY_ID "+
						"INNER JOIN MASTER_DISTRICT D ON D.DISTRICT_ID=C.DISTRICT_ID "+
						"INNER JOIN MASTER_STATE S ON S.STATE_ID=D.STATE_ID "+
						"WHERE P.CITY_ID=? ";
				try(PreparedStatement preparestatements = connection.prepareStatement(sql1);)
				{
					preparestatements.setLong(1, pinSearch.getCityId());
					try(ResultSet rs1 = preparestatements.executeQuery();)
					{
						pinCodeList = new ArrayList<>();
						while (rs1.next()) {
							PinSearchEntity pinSearchEntity1 = new PinSearchEntity();
							pinSearchEntity1.setPinId(rs1.getLong("PIN_ID"));
							pinSearchEntity1.setPinCode(rs1.getString("PIN_CODE"));
							pinSearchEntity1.setIsActive(rs1.getString("IS_ACTIVE"));
							pinSearchEntity1.setIsDeleted(rs1.getString("IS_DELETED"));
							pinSearchEntity1.setModifiedBy(rs1.getString("MODIFIED_BY"));
							pinSearchEntity1.setModifiedOn(rs1.getDate("MODIFIED_ON"));
							pinSearchEntity1.setCityId(rs1.getLong("CITY_ID"));
							pinSearchEntity1.setCityCode(rs1.getString("CITY_CODE"));
							pinSearchEntity1.setCityDescription(rs1.getString("C_DESCRIPTION"));
							pinSearchEntity1.setDistrictId(rs1.getLong("DISTRICT_ID"));
							pinSearchEntity1.setDistrictCode(rs1.getString("DISTRICT_CODE"));
							pinSearchEntity1.setDistrictDescription(rs1.getString("D_DESCRIPTION"));
							pinSearchEntity1.setStateId(rs1.getLong("STATE_ID"));
							pinSearchEntity1.setStateCode(rs1.getString("STATE_CODE"));
							pinSearchEntity1.setStateDescription(rs1.getString("S_DESCRIPTION"));
							pinSearchEntity1.setCountryId(1L);
							pinSearchEntity1.setCountryCode("Ind");
							pinSearchEntity1.setCountryDescription("India");
		
							pinCodeList.add(pinSearchEntity1);
							}
					}
					catch(Exception e)
					{
						logger.error("getPinCode exception occured." + e.getMessage());
					}
				}
				catch(Exception e)
				{
					logger.error("getPinCode exception occured." + e.getMessage());
				}
			} else {
				logger.info("Required Data Not Found");
			}
			// System.out.println("sql"+sql);

			logger.info("getPinCode executed successfully.");
			return pinCodeList;
		} catch (Exception e) {
			logger.error("getPinCode exception occured." + e.getMessage());
			throw new PinCodeServiceException ("Internal Server Error");
			
		} finally {
			if(!connection.isClosed()) {
				connection.close();
			}
		}
	}

	
	
	@Override
	public PinEntity getPinByCityId(Long cityId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<PinEntity> pinDb = this.pinEntityRepository.findById(cityId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for pin By Id" + cityId);
		if (pinDb.isPresent()) {
			logger.info("Pin Type is found with id" + cityId);
			return pinDb.get();
		} else {
			throw new ResourceNotFoundException("Pin not found with id:" + cityId);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> deletePinCode(Long pinId) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			if (pinId == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} 

				else {
					pinCodeRepository.deleteByPinId(pinId);
					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", "Soft Deleted pinCode with pin Id : " + pinId);
				}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception ex) {
			logger.info("Could not delete pin due to : ", ex.getMessage());
			throw new ResourceNotFoundException("Pin not found with id:" + pinId);
		}
	}

}
