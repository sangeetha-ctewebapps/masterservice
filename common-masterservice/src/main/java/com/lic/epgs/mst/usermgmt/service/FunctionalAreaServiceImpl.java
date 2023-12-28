package com.lic.epgs.mst.usermgmt.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.entity.DesignationMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.repository.JDBCConnection;
import com.lic.epgs.mst.usermgmt.entity.FunctionalAreaEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.repository.FunctionalAreaRepository;

@Service
@Transactional
public class FunctionalAreaServiceImpl implements FunctionalAreaService {

	@Autowired
	private FunctionalAreaRepository functionalAreaRepository;
	
	
	@Autowired
	JDBCConnection jdbcConnection;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(FunctionalAreaService.class);

	/*
	 * Description: This function is used for getting all the data from FunctionalArea Module
	 * Table Name- FUNCTIONAL_AREA
	 * Author- Nandini R
	 */
	
	@Override
	 public List<FunctionalAreaEntity> getAllFunctionalArea() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get all functional areas ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return functionalAreaRepository.findAll();
	}

	
	@Override
	public ResponseEntity<Map<String, Object>> addFuncionalArea(FunctionalAreaEntity functionalAreaEntity) throws Exception{
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			 Date today = new Date();
			if (functionalAreaEntity == null) {
				response.put(Constant.STATUS, 201);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} else {
				if(!isUnique(functionalAreaEntity, "ADD")) {
					response.put(Constant.STATUS, 201);
					response.put(Constant.MESSAGE, Constant.FAILED);
					response.put(Constant.DATA,"Duplicate Entry");
				} 
				else {
					
					functionalAreaRepository.save(functionalAreaEntity);
					response.put(Constant.STATUS, 200);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("FUNCTIONIDID", functionalAreaEntity.getFunctionId());
				}

			}

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Exception" + exception.getMessage());
		}
		return null;
	}
	
	
	@Override
	public ResponseEntity<Map<String, Object>> editFunctionalArea(FunctionalAreaEntity functionalAreaEntity) throws Exception{
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {

			 if (functionalAreaEntity == null) {
				response.put(Constant.STATUS, 201);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} else {
				if (isValid(functionalAreaEntity, "EDIT")) {
					Date date = new Date();
					FunctionalAreaEntity existingFunctionalArea = functionalAreaRepository.getAllFunctionalAreaDetail(functionalAreaEntity.getFunctionId());
					existingFunctionalArea.setFunctionId(functionalAreaEntity.getFunctionId());
					existingFunctionalArea.setFunctionDescription(functionalAreaEntity.getFunctionDescription());
					existingFunctionalArea.setIsActive(functionalAreaEntity.getIsActive());
					existingFunctionalArea.setIsDeleted(functionalAreaEntity.getIsDeleted());
					existingFunctionalArea.setCreatedBy(functionalAreaEntity.getCreatedBy());
					existingFunctionalArea.setCreatedOn(functionalAreaEntity.getCreatedOn());
					existingFunctionalArea.setModifiedBy(functionalAreaEntity.getModifiedBy());
					existingFunctionalArea.setModifiedOn(functionalAreaEntity.getModifiedOn());
					functionalAreaRepository.save(existingFunctionalArea);
					response.put(Constant.STATUS, 200);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", "Updated Functional Area for: " + functionalAreaEntity.getFunctionId());
				}
				else {
					response.put(Constant.STATUS, 201);
					response.put(Constant.MESSAGE, Constant.FAILED);
					response.put(Constant.DATA, "Duplicate Entry Found");
				}
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception ex) {
			logger.info("Could not update Functional Area  due to : " + ex.getMessage());
		}
		return null;
	}
	
	private boolean isValid(FunctionalAreaEntity functionalAreaEntity, String operation) {
		if(!(functionalAreaRepository.findById(functionalAreaEntity.getFunctionId()).isPresent())) {
			return false;
		}
		if(operation.equals("EDIT") && (functionalAreaEntity.getFunctionId() == 0.00 || functionalAreaEntity.getFunctionId() == 0.00)) {
			return false;
		}
		return true;
	}
	
	private boolean isUnique(FunctionalAreaEntity functionalAreaEntity, String operation) throws Exception {
		List<FunctionalAreaEntity> result = functionalAreaRepository.findDuplicate(functionalAreaEntity.getFunctionDescription());
		if(operation.equals("ADD") && !(result.size() > 0)) {
			return true;
		}
		if(operation.equals("EDIT") && !(result.size() > 1)) {
			return true;
		}
		return false;
	}


	@Override
	public ResponseEntity<Map<String, Object>> deleteFuctionalArea(Long functionId) throws Exception {
	logger.info("Enter UserManagementService : deleteUser");

	Map<String, Object> response = new HashMap<String, Object>();
	response.put(Constant.STATUS, 0);
	response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
	try {


		if (functionId== null) {
			response.put(Constant.STATUS, 201);
			response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
		} 
		else {
				functionalAreaRepository.deleteFunctionalArea(functionId);
				response.put(Constant.STATUS, 200);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", "Deleted Function Id : " + functionId );
			}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	} catch (Exception ex) {
		ex.printStackTrace();
		logger.info("Could not delete functional Area  due to : " + ex.getMessage());
		
	}
	return null;
}
	
	


	@Override
	public List<FunctionalAreaEntity> getSearchByFunctionName(String functionName) throws SQLException {
		logger.info("Start Service  master users Search");
		ResultSet rs = null;
		List<FunctionalAreaEntity> UsersSearchList = null;
		PreparedStatement preparestatements = null;
		String sqlmu = null;
		Connection connection = jdbcConnection.getConnection();

		try {
			
			sqlmu = "SELECT * from MASTER_FUNCTION_NAME  MF where IS_ACTIVE = 'Y' AND IS_DELETED = 'N' AND ((UPPER(MF.FUNCTIONDESCRIPTION) LIKE UPPER('%'||?||'%') ) OR (? ='')) ";

			
			
			preparestatements = connection.prepareStatement(sqlmu);
			preparestatements.setString(1, functionName);
			preparestatements.setString(2, functionName);
			rs = preparestatements.executeQuery();

			UsersSearchList = new ArrayList<>();
			while (rs.next()) {
				FunctionalAreaEntity functionalAreaEntity = new FunctionalAreaEntity();
				
				functionalAreaEntity.setFunctionId(rs.getLong("FUNCTION_ID"));
				functionalAreaEntity.setFunctionDescription(rs.getString("FUNCTIONDESCRIPTION"));
				functionalAreaEntity.setIsActive(rs.getString("IS_ACTIVE"));
				functionalAreaEntity.setIsDeleted(rs.getString("IS_DELETED"));
				functionalAreaEntity.setCreatedBy(rs.getString("CREATEDBY"));
				functionalAreaEntity.setCreatedOn(rs.getDate("CREATEDON"));
				functionalAreaEntity.setModifiedBy(rs.getString("MODIFIEDBY"));
				functionalAreaEntity.setModifiedOn(rs.getDate("MODIFIEDON"));
				UsersSearchList.add(functionalAreaEntity);
				
			}	

			logger.info("city code Search executed successfully.");
			return UsersSearchList;
		} catch (Exception e) {
			logger.info("exception occured." + e.getMessage());
			throw e;
		} finally {
			if (null != rs && !rs.isClosed()) {
				rs.close();
			}
			if (preparestatements != null) {
				preparestatements.close();
			}
			if(!connection.isClosed()) {
				connection.close();
			}
			preparestatements = null;
		}


	}
}