package com.lic.epgs.mst.usermgmt.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.modal.UserHistoryInputModel;
import com.lic.epgs.mst.repository.JDBCConnection;
import com.lic.epgs.mst.usermgmt.controller.CoFinancialLimitController;
import com.lic.epgs.mst.usermgmt.entity.CoFinancialLimitEntity;
import com.lic.epgs.mst.usermgmt.entity.CoFinancialLimitHistoryEntity;
import com.lic.epgs.mst.usermgmt.entity.CoFinancialLimitNameEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersHistoryDetailsEntity;
import com.lic.epgs.mst.usermgmt.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.usermgmt.modal.CoFinancialHistoryInputModel;
import com.lic.epgs.mst.usermgmt.modal.CoFinancialHistoryModel;
import com.lic.epgs.mst.usermgmt.modal.FunctionNameDesignationModel;
import com.lic.epgs.mst.usermgmt.modal.HistoryDetailsModel;
import com.lic.epgs.mst.usermgmt.repository.CoFinacialLimitByNameRepository;
import com.lic.epgs.mst.usermgmt.repository.CoFinancialLimitRepository;
import com.lic.epgs.mst.usermgmt.repository.CoFinanciallimitHistRepository;
import com.lic.epgs.mst.usermgmt.repository.FunctionalAreaRepository;
import com.lic.epgs.mst.usermgmt.repository.MasterUsersRepository;

@Service
@Transactional
public class CoFinancialLimitServiceImpl implements CoFinancialLimitService {
  
	
	
	@Autowired
	CoFinancialLimitRepository coFinancialLimitRepository;

	@Autowired
	CoFinacialLimitByNameRepository coFinacialLimitByNameRepository;

	@Autowired
	private FunctionalAreaRepository functionalAreaRepository;
	
	@Autowired
	CoFinanciallimitHistRepository coFinancialLimitHistRepository;
	
	@Autowired
	private JDBCConnection jdbcConnection;
	
	@Autowired
	private MasterUsersRepository masterUsersRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(CoFinancialLimitController.class);

	/*
	 * Description: This function is used for getting all the data from Co Fin Limit Module
	 * Table Name- CO_FINANCIAL_LIMIT
	 * Author- Nandini R
	 */
	
	@Override
	 public List<CoFinancialLimitNameEntity> getAllCoFinacialLimitByName() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get Roles list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return coFinacialLimitByNameRepository.getCoFinancialLimitByName();

	}	

	@Override
	public List<CoFinancialLimitEntity> getAllCoFinLimit() throws Exception {
		// TODO Auto-generated method stub

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		try {
			return coFinancialLimitRepository.findAll();
		}
		catch (Exception exception) {
			logger.info("Exception : " + exception.getMessage());
		}
		return null;
	}


	/*public ResponseEntity<Map<String, Object>> updateCoFinancialLimit(CoFinancialLimitEntity coFinancialLimitEntity) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.info("Enter updateCoFinancialLimitService : " + methodName);

		try {

			if (coFinancialLimitEntity == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} 
				else {
					Date currentDate = new Date(System.currentTimeMillis());
					coFinancialLimitEntity.getCofinlimitId();
					CoFinancialLimitEntity coFinancialLimitEntity1 = new CoFinancialLimitEntity();

					coFinancialLimitEntity1 = coFinancialLimitRepository.getCofinancialLimitDetail(coFinancialLimitEntity.getCofinlimitId());
					coFinancialLimitEntity1.setFunctionName(coFinancialLimitEntity.getFunctionName());
					coFinancialLimitEntity1.setDesignation(coFinancialLimitEntity.getDesignation());
					coFinancialLimitEntity1.setDepartment(coFinancialLimitEntity.getDepartment());
					coFinancialLimitEntity1.setOfficeName(coFinancialLimitEntity.getOfficeName());
					coFinancialLimitEntity1.setHodFlag(coFinancialLimitEntity.getHodFlag());
					coFinancialLimitEntity1.setInchargeFlag(coFinancialLimitEntity.getInchargeFlag());
					coFinancialLimitEntity1.setFinancialLimit(coFinancialLimitEntity.getFinancialLimit());
					coFinancialLimitEntity1.setSanctionedBudgetLimit(coFinancialLimitEntity.getSanctionedBudgetLimit());
					coFinancialLimitEntity1.setIsActive(coFinancialLimitEntity.getIsActive());
					coFinancialLimitEntity1.setIsDeleted(coFinancialLimitEntity.getIsDeleted());
					coFinancialLimitEntity1.setModifiedBy(coFinancialLimitEntity.getModifiedBy());
					coFinancialLimitEntity1.setModifiedOn(currentDate);
					coFinancialLimitEntity1.setCreatedBy(coFinancialLimitEntity.getCreatedBy());
					coFinancialLimitEntity1.setCreatedOn(currentDate);

					coFinancialLimitRepository.save(coFinancialLimitEntity1);

					response.put(Constant.STATUS, 1);
					response.put(Constant.MESSAGE, Constant.SUCCESS);
					response.put("Data", "Updated CoFinancial Limit for Id : " + coFinancialLimitEntity.getCofinlimitId());
				}

			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception exception) {
			logger.info("Could not update CoFinancial Limit to : " + exception.getMessage());
		}
		return null;
	}*/

	/*
	 * Description: This function is used for soft deleting the data in Co Fin Limit Module by using primary key
	 * Table Name- CO_FINANCIAL_LIMIT
	 * Author- Nandini R
	 */

	public ResponseEntity<Map<String, Object>> deleteCoFinancialLimit(List<CoFinancialLimitEntity> coFinancialLimitEntity) throws Exception{
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		List<CoFinancialLimitEntity> objCoFinDetailEntityList = new ArrayList<>();
		List<CoFinancialLimitHistoryEntity> objCoFinDetailEntityHistList = new ArrayList<>();

		try {
			if (coFinancialLimitEntity == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
				
			} 
			else {
				
				for(int i=0;i<coFinancialLimitEntity.size();i++) {
					 Date today = new Date();
					CoFinancialLimitEntity cofinDetails = coFinancialLimitRepository.getAllCofinancialLimitDetail(coFinancialLimitEntity.get(i).getCofinlimitId());
					cofinDetails.setStatus("SentForApproval");
					cofinDetails.setWorkFlowStatus(1L);
					cofinDetails.setAction("DELETE");
					cofinDetails.setRemarks(coFinancialLimitEntity.get(i).getRemarks());
					
					 String SrNumber =  masterUsersRepository.getAllMasterUserDetailByUserName1(coFinancialLimitEntity.get(i).getLoggedInUserName());		  
					  CoFinancialLimitHistoryEntity cofinancialLimitHistoryEntity= new 	CoFinancialLimitHistoryEntity();
					//  cofinancialLimitHistoryEntity.setCofinlimitId(coFinancialLimitEntity.get(i).getCofinlimitId());
					  cofinancialLimitHistoryEntity.setFunctionName(cofinDetails.getFunctionName());
					  cofinancialLimitHistoryEntity.setDesignation(cofinDetails.getDesignation());
					  cofinancialLimitHistoryEntity.setDepartment(cofinDetails.getDepartment());
					  cofinancialLimitHistoryEntity.setOfficeName(cofinDetails.getOfficeName());
					  cofinancialLimitHistoryEntity.setFinancialLimit(cofinDetails.getFinancialLimit());
					  cofinancialLimitHistoryEntity.setSanctionedBudgetLimit(cofinDetails.getSanctionedBudgetLimit());
					  cofinancialLimitHistoryEntity.setIsActive("Y");
					  cofinancialLimitHistoryEntity.setIsDeleted("N");
					  cofinancialLimitHistoryEntity.setCreatedBy(SrNumber);
					  cofinancialLimitHistoryEntity.setCreatedOn(today);
					  cofinancialLimitHistoryEntity.setStatus("SentForApproval");
					  cofinancialLimitHistoryEntity.setLoggedInUserSrNumber(SrNumber);
					  cofinancialLimitHistoryEntity.setAction("DELETE");
					//coFinancialLimitRepository.findAndDeletedById(coFinLimitId);
					
					  objCoFinDetailEntityList.add(cofinDetails);
					  objCoFinDetailEntityHistList.add(cofinancialLimitHistoryEntity);
				
			}
			}
			 coFinancialLimitRepository.saveAll(objCoFinDetailEntityList);
			 coFinancialLimitHistRepository.saveAll(objCoFinDetailEntityHistList);
			  logger.info("edited cofinlimit  Successfully");
			  response.put(Constant.STATUS, 200);
				response.put(Constant.MESSAGE,Constant.SUCCESS);
				response.put(Constant.DATA,"Sent for Approval sucessfully");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception ex) {
			logger.info("Exception : " + ex.getMessage());
		}
		return null;

	}


	private boolean isValidDeletion(Long id) {
		Optional<CoFinancialLimitEntity> result = coFinancialLimitRepository.findById(id);
		if(!result.isPresent()) {
			return false;
		}
		if(result.get().getIsDeleted()!= null && result.get().getIsDeleted().equals("Y")) {
			return false;
		}
		if(result.get().getIsActive()!= null && result.get().getIsActive().equals("N")) {
			return false;
		}
		else { return true;  }
	}

	/*
	 * Description: This function is used for adding the data into Co Fin Limit Module
	 * Table Name- CO_FINANCIAL_LIMIT
	 * Author- Nandini R
	 */

	public ResponseEntity<Map<String, Object>> addCoFinancialLimit(List<CoFinancialLimitEntity> coFinancialLimitEntity) {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		List<CoFinancialLimitEntity> objCoFinDetailEntityList = new ArrayList<>();
		List<CoFinancialLimitHistoryEntity> objCoFinDetailEntityHistList = new ArrayList<>();
		try {
			
			if (coFinancialLimitEntity == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} 
			
				else {
					/*List<List<CoFinancialLimitEntity>> listAll = Arrays.asList(coFinancialLimitEntity);

					// Create a list with the distinct elements using stream.
					List<List<CoFinancialLimitEntity>> listDistinct = listAll.stream().distinct().collect(Collectors.toList());

					// Display them to terminal using stream::collect with a build in Collector.
					String collectAll = listAll.stream().collect(Collectors.joining(", "));
					System.out.println(collectAll); //=> CO2, CH4, SO2, CO2, CH4 etc..
					String collectDistinct = listDistinct.stream().collect(Collectors.joining(", "));
					System.out.println(collectDistinct);*/
				
					 for(int i=0;i<coFinancialLimitEntity.size();i++) {
						 Date today = new Date();
						/* if(!isValid(coFinancialLimitEntity, "ADD")) {
								response.put(Constant.STATUS, 201);
								response.put(Constant.MESSAGE, Constant.FAILED);
								response.put(Constant.DATA,"Invalid Payload");
							}*/
							if(!isUnique(coFinancialLimitEntity.get(i), "ADD")) {
								response.put(Constant.STATUS, 201);
								response.put(Constant.MESSAGE, Constant.FAILED);
								response.put(Constant.DATA,"Duplicate Entry");
							}
							else {
						  logger.info(" add CoFinancial limit executed successfully.");
						  String SrNumber =  masterUsersRepository.getAllMasterUserDetailByUserName1(coFinancialLimitEntity.get(i).getLoggedInUserName());		  
						  CoFinancialLimitHistoryEntity cofinancialLimitHistoryEntity= new 	CoFinancialLimitHistoryEntity();
						//  cofinancialLimitHistoryEntity.setCofinlimitId(coFinancialLimitEntity.get(i).getCofinlimitId());
						  cofinancialLimitHistoryEntity.setFunctionName(coFinancialLimitEntity.get(i).getFunctionName());
						  cofinancialLimitHistoryEntity.setDesignation(coFinancialLimitEntity.get(i).getDesignation());
						  cofinancialLimitHistoryEntity.setDepartment(coFinancialLimitEntity.get(i).getDepartment());
						  cofinancialLimitHistoryEntity.setOfficeName(coFinancialLimitEntity.get(i).getOfficeName());
						  cofinancialLimitHistoryEntity.setFinancialLimit(coFinancialLimitEntity.get(i).getFinancialLimit());
						  cofinancialLimitHistoryEntity.setSanctionedBudgetLimit(coFinancialLimitEntity.get(i).getSanctionedBudgetLimit());
						  cofinancialLimitHistoryEntity.setIsActive(coFinancialLimitEntity.get(i).getIsActive());
						  cofinancialLimitHistoryEntity.setIsDeleted(coFinancialLimitEntity.get(i).getIsDeleted());
						  cofinancialLimitHistoryEntity.setCreatedBy(coFinancialLimitEntity.get(i).getCreatedBy());
						  cofinancialLimitHistoryEntity.setCreatedOn(today);
						  cofinancialLimitHistoryEntity.setStatus("SentForApproval");
						  cofinancialLimitHistoryEntity.setLoggedInUserSrNumber(SrNumber);
						  cofinancialLimitHistoryEntity.setAction("ADD");
						  
						  
						  CoFinancialLimitEntity coFinEntity = new CoFinancialLimitEntity();
						  
						  coFinEntity.setFunctionName(coFinancialLimitEntity.get(i).getFunctionName());
						  coFinEntity.setDesignation(coFinancialLimitEntity.get(i).getDesignation());
						  coFinEntity.setDepartment(coFinancialLimitEntity.get(i).getDepartment());
						  coFinEntity.setOfficeName(coFinancialLimitEntity.get(i).getOfficeName());
						  coFinEntity.setHodFlag(coFinancialLimitEntity.get(i).getHodFlag());
						  coFinEntity.setInchargeFlag(coFinancialLimitEntity.get(i).getInchargeFlag());
						  coFinEntity.setFinancialLimit(coFinancialLimitEntity.get(i).getFinancialLimit());
						  coFinEntity.setSanctionedBudgetLimit(coFinancialLimitEntity.get(i).getSanctionedBudgetLimit());
						  coFinEntity.setIsActive(coFinancialLimitEntity.get(i).getIsActive());
						  coFinEntity.setIsDeleted(coFinancialLimitEntity.get(i).getIsDeleted());
						  coFinEntity.setCreatedBy(coFinancialLimitEntity.get(i).getCreatedBy());
						  coFinEntity.setCreatedOn(coFinancialLimitEntity.get(i).getCreatedOn());
						  coFinEntity.setModifiedBy(coFinancialLimitEntity.get(i).getModifiedBy());
						  coFinEntity.setModifiedOn(coFinancialLimitEntity.get(i).getModifiedOn());
						  coFinEntity.setRemarks(coFinancialLimitEntity.get(i).getRemarks());
						  coFinEntity.setWorkFlowStatus(1L);
						  coFinEntity.setStatus("SentForApproval");
						  coFinEntity.setApprovedBy(null);
						  coFinEntity.setAction("ADD");
						  objCoFinDetailEntityList.add(coFinEntity);
						  objCoFinDetailEntityHistList.add(cofinancialLimitHistoryEntity);
						  coFinancialLimitRepository.saveAll(objCoFinDetailEntityList);
							 coFinancialLimitHistRepository.saveAll(objCoFinDetailEntityHistList);
					  }
					  
					
				}
					 coFinancialLimitRepository.saveAll(objCoFinDetailEntityList);
					 coFinancialLimitHistRepository.saveAll(objCoFinDetailEntityHistList);
					  logger.info("added cofinlimit  Successfully");
					  response.put(Constant.STATUS, 200);
						response.put(Constant.MESSAGE,Constant.SUCCESS);
						response.put(Constant.DATA,"Sent for Approval sucessfully");
						
						return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

				}
		} catch (Exception exception) {
			logger.info("Could not save user role due to : " + exception.getMessage());
		}
		return null;
	}

private boolean isValid(List<CoFinancialLimitEntity> coFinancialLimitEntity, String operation) {

		if(!(coFinancialLimitRepository.findById(coFinancialLimitEntity.get(0).getCofinlimitId())).isPresent()) {
			return false;
		}
		if(operation.equals("EDIT") && coFinancialLimitEntity.get(0).getCofinlimitId() == 0.00) {
			return false;
		}
		return true;
	}

	private boolean isUnique(CoFinancialLimitEntity coFinancialLimitEntity, String operation) {
		 List<CoFinancialLimitEntity> result = coFinancialLimitRepository.findDuplicate(coFinancialLimitEntity.getFunctionName(), coFinancialLimitEntity.getDesignation());
		if(operation.equals("ADD") && !(result.size() > 0)) {
			return true;
		}
		if(operation.equals("EDIT") && !(result.size() > 1)) {
			return true;
		}
		return false;
	}
	/*
	 * Description: This function is used for searching the data in  Co Fin Limit Module using the filters like LobName, CadreId, FinLimit
	 * Table Name- CO_FINANCIAL_LIMIT
	 * Author- Nandini R
	 */
	
	

	@Override
	public List<CoFinancialLimitNameEntity> searchCoFinancialLimit(String designation, String functionName,String officeName,String status,String loggedInUser)  throws SQLException {
	logger.info("Start Service  master users Search");
	List<CoFinancialLimitNameEntity> coFinSearchList = null;
	String sqlmu = null;
	Connection connection = jdbcConnection.getConnection();
	PreparedStatement preparestatements = null;	

	try {
		
		
	if(status != null && status.equalsIgnoreCase("approved")) {
		sqlmu = "SELECT CFL.COFINLIMIT_ID, CFL.FUNCTION_NAME, CFL.DESIGNATION, CFL.FINANCIAL_LIMIT,CFL.IS_ACTIVE\r\n" + 
				" ,CFL.IS_DELETED,CFL.OFFICENAME,CFL.WORKFLOW_STATUS,CFL.STATUS,CFL.CREATEDBY,CFL.APPROVED_BY,CFL.ACTION,CFL.REMARKS \r\n" + 
				"FROM CO_FINANCIAL_LIMIT CFL WHERE \r\n" + 
				"((? is null) OR (UPPER(CFL.STATUS) LIKE UPPER('%'||?||'%'))) \r\n" + 
				"AND ((? is null) OR (UPPER(CFL.DESIGNATION) LIKE UPPER('%'||?||'%'))) \r\n" + 
				"AND ((? is null) OR (UPPER(CFL.FUNCTION_NAME) LIKE UPPER('%'||?||'%')))\r\n" + 
				"AND ((? is null ) OR (UPPER(CFL.OFFICENAME) LIKE UPPER('%'||?||'%')))"
				+ "                order by cfl.modifiedon desc";
		
		preparestatements = connection.prepareStatement(sqlmu);
		preparestatements.setString(1, status);
		preparestatements.setString(2, status);
		preparestatements.setString(3, designation);
		preparestatements.setString(4, designation);
		preparestatements.setString(5, functionName);
		preparestatements.setString(6, functionName);
		preparestatements.setString(7, officeName);
		preparestatements.setString(8, officeName);

	}else {
		sqlmu = "SELECT CFL.COFINLIMIT_ID, CFL.FUNCTION_NAME, CFL.DESIGNATION, CFL.FINANCIAL_LIMIT,CFL.IS_ACTIVE\r\n" + 
				" ,CFL.IS_DELETED,CFL.OFFICENAME,CFL.WORKFLOW_STATUS,CFL.STATUS,CFL.CREATEDBY,CFL.APPROVED_BY,CFL.ACTION,CFL.REMARKS \r\n" + 
				"FROM CO_FINANCIAL_LIMIT CFL WHERE \r\n" + 
				"((? is null) OR (UPPER(CFL.STATUS) LIKE UPPER('%'||?||'%'))) \r\n" + 
				"AND ((? is null) OR (UPPER(CFL.DESIGNATION) LIKE UPPER('%'||?||'%'))) \r\n" + 
				"AND ((? is null) OR (UPPER(CFL.FUNCTION_NAME) LIKE UPPER('%'||?||'%')))\r\n" + 
				"AND ((? is null) OR (UPPER(CFL.OFFICENAME) LIKE UPPER('%'||?||'%')))"
				+ " AND((:createdBy is null) OR (UPPER(CFL.CREATEDBY) LIKE UPPER('%'||:createdBy||'%')))"
				+ "                order by cfl.modifiedon desc";
		preparestatements = connection.prepareStatement(sqlmu);
		preparestatements.setString(1, status);
		preparestatements.setString(2, status);
		preparestatements.setString(3, designation);
		preparestatements.setString(4, designation);
		preparestatements.setString(5, functionName);
		preparestatements.setString(6, functionName);
		preparestatements.setString(7, officeName);
		preparestatements.setString(8, officeName);
		preparestatements.setString(9, loggedInUser);
		preparestatements.setString(10, loggedInUser);

	}
			try
			{
				
				try(ResultSet rs = preparestatements.executeQuery();)
				{
					coFinSearchList = new ArrayList<>();
					while (rs.next()) {
						CoFinancialLimitNameEntity coFinancialLimitEntity = new CoFinancialLimitNameEntity();
				
						
						coFinancialLimitEntity.setCofinlimitId(rs.getLong("COFINLIMIT_ID"));
						coFinancialLimitEntity.setFunctionName(rs.getString("FUNCTION_NAME"));
						coFinancialLimitEntity.setDesignation(rs.getString("DESIGNATION"));
						coFinancialLimitEntity.setFinancialLimit(rs.getString("FINANCIAL_LIMIT"));
						coFinancialLimitEntity.setIsActive(rs.getString("IS_ACTIVE"));
						coFinancialLimitEntity.setIsDeleted(rs.getString("IS_DELETED"));
						coFinancialLimitEntity.setOfficeName(rs.getString("OFFICENAME"));
						coFinancialLimitEntity.setWorkflowStatus(rs.getLong("WORKFLOW_STATUS"));
						coFinancialLimitEntity.setStatus(rs.getString("STATUS"));
						coFinancialLimitEntity.setCreatedBy(rs.getString("CREATEDBY"));
						coFinancialLimitEntity.setApprovedBy(rs.getString("APPROVED_BY"));
						coFinancialLimitEntity.setRemarks(rs.getString("REMARKS"));
						coFinSearchList.add(coFinancialLimitEntity);
						//functionalAreaEntity.add(functionalAreaEntity1);
						//masterCadre.add(masterCadre1);
					
						}
					logger.info("uoSearch executed successfully.");
				}
				catch(Exception e)
				{
					logger.error("coSearch Exception : " + e.getMessage());
					e.printStackTrace();
				}
			}
			catch(Exception e)
			{
				logger.error("coSearch Exception : " + e.getMessage());
				e.printStackTrace();
			}
			return coFinSearchList;
		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
			e.printStackTrace();
			return coFinSearchList;
		} finally {
			if(!connection.isClosed()) {
				connection.close();
			}
		}
		
	}

	
	@Override
	public List<CoFinancialLimitNameEntity> searchCoFinancialLimitchecker(String designation, String functionName,String officeName,String status,String loggedInUser)  throws SQLException {
	logger.info("Start Service  master users Search");
	List<CoFinancialLimitNameEntity> coFinSearchList = null;
	String sqlmu = null;
	Connection connection = jdbcConnection.getConnection();

	try {
		String createdBy=loggedInUser;
			sqlmu = "SELECT CFL.COFINLIMIT_ID, CFL.FUNCTION_NAME, CFL.DESIGNATION, CFL.FINANCIAL_LIMIT,CFL.IS_ACTIVE\r\n" + 
					"																			,CFL.IS_DELETED,CFL.OFFICENAME,CFL.WORKFLOW_STATUS,CFL.STATUS,CFL.CREATEDBY,CFL.APPROVED_BY,CFL.ACTION,CFL.REMARKS  \r\n" + 
					"																				FROM CO_FINANCIAL_LIMIT CFL WHERE  \r\n" + 
					"					                                                            ((? is null) OR (UPPER(CFL.STATUS) LIKE UPPER('%'||?||'%'))) \r\n" + 
					"																				AND ((? is null) OR (UPPER(CFL.DESIGNATION) LIKE UPPER('%'||?||'%'))) \r\n" + 
					"																				AND ((? is null) OR (UPPER(CFL.FUNCTION_NAME) LIKE UPPER('%'||?||'%'))) \r\n" + 
					"																				AND ((? is null) OR (UPPER(CFL.OFFICENAME) LIKE UPPER('%'||?||'%')))\r\n" + 
					"																				AND ((UPPER(CFL.CREATEDBY) NOT IN UPPER(?))) order by cfl.modifiedon desc";

			try(PreparedStatement preparestatements = connection.prepareStatement(sqlmu);)
			{
				preparestatements.setString(1, status);
				preparestatements.setString(2, status);
				preparestatements.setString(3, designation);
				preparestatements.setString(4, designation);
				preparestatements.setString(5, functionName);
				preparestatements.setString(6, functionName);
				preparestatements.setString(7, officeName);
				preparestatements.setString(8, officeName);
			   preparestatements.setString(9, createdBy);
				try(ResultSet rs = preparestatements.executeQuery();)
				{
					coFinSearchList = new ArrayList<>();
					while (rs.next()) {
						CoFinancialLimitNameEntity coFinancialLimitEntity = new CoFinancialLimitNameEntity();
				
						
						coFinancialLimitEntity.setCofinlimitId(rs.getLong("COFINLIMIT_ID"));
						coFinancialLimitEntity.setFunctionName(rs.getString("FUNCTION_NAME"));
						coFinancialLimitEntity.setDesignation(rs.getString("DESIGNATION"));
						coFinancialLimitEntity.setFinancialLimit(rs.getString("FINANCIAL_LIMIT"));
						coFinancialLimitEntity.setIsActive(rs.getString("IS_ACTIVE"));
						coFinancialLimitEntity.setIsDeleted(rs.getString("IS_DELETED"));
						coFinancialLimitEntity.setOfficeName(rs.getString("OFFICENAME"));
						coFinancialLimitEntity.setWorkflowStatus(rs.getLong("WORKFLOW_STATUS"));
						coFinancialLimitEntity.setStatus(rs.getString("STATUS"));
						coFinancialLimitEntity.setCreatedBy(rs.getString("CREATEDBY"));
						coFinancialLimitEntity.setApprovedBy(rs.getString("APPROVED_BY"));
						coFinancialLimitEntity.setAction(rs.getString("ACTION"));
						coFinancialLimitEntity.setRemarks(rs.getString("REMARKS"));
						coFinSearchList.add(coFinancialLimitEntity);
						//functionalAreaEntity.add(functionalAreaEntity1);
						//masterCadre.add(masterCadre1);
					
						}
					logger.info("uoSearch executed successfully.");
				}
				catch(Exception e)
				{
					logger.error("coSearch Exception : " + e.getMessage());
					e.printStackTrace();
				}
			}
			catch(Exception e)
			{
				logger.error("coSearch Exception : " + e.getMessage());
				e.printStackTrace();
			}
			return coFinSearchList;
		} 
	catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
			e.printStackTrace();
			return coFinSearchList;
		} finally {
			if(!connection.isClosed()) {
				connection.close();
			}
		}
		
	}

	
	@Override
	public List<FunctionNameDesignationModel> getDesignationAndFunctionDetailsByUserName(String srNumber)
			throws SQLException {
		
		List<FunctionNameDesignationModel> coFinSearchList = null;
		String sqlmu = null;
		Connection connection = jdbcConnection.getConnection();
		
		try {
			
		
		sqlmu = "SELECT CFL.COFINLIMIT_ID, CFL.FUNCTION_NAME, CFL.DESIGNATION,CFL.IS_ACTIVE,CFL.IS_DELETED, MU.SRNUMBER,MU.EMAILID\r\n" + 
				"				                               FROM CO_FINANCIAL_LIMIT CFL, MASTER_USERS MU\r\n" + 
				"											WHERE CFL.DESIGNATION = MU.DESIGNATION  AND MU.IS_ACTIVE = 'Y' \r\n" + 
				"								                   AND ((UPPER(MU.SRNUMBER) LIKE UPPER('%'||?||'%') ) OR (? =''))   \r\n" + 
				"												order by cfl.cofinlimit_id asc";

		try(PreparedStatement preparestatements = connection.prepareStatement(sqlmu);)
		{
			preparestatements.setString(1, srNumber);
			preparestatements.setString(2, srNumber);
			
			
			
			try(ResultSet rs = preparestatements.executeQuery();)
			{

				coFinSearchList = new ArrayList<>();
				while (rs.next()) {
					FunctionNameDesignationModel coFinancialLimitEntity = new FunctionNameDesignationModel();
			
					
					coFinancialLimitEntity.setCofinlimitId(rs.getLong(1));
					coFinancialLimitEntity.setFunctionName(rs.getString(2));
					coFinancialLimitEntity.setDesignation(rs.getString(3));
					coFinancialLimitEntity.setIsActive(rs.getString(4));
					coFinancialLimitEntity.setIsDeleted(rs.getString(5));
					coFinancialLimitEntity.setSrNumber(rs.getString(6));
					coFinancialLimitEntity.setEmailId(rs.getString(7));
					
					coFinSearchList.add(coFinancialLimitEntity);
					//functionalAreaEntity.add(functionalAreaEntity1);
					//masterCadre.add(masterCadre1);
				}	
	
				logger.info("city code Search executed successfully.");
			}
			catch(Exception e)
			{
				logger.error("getSearchByLob exception occured." + e.getMessage());
			}
		
		}
			catch(Exception e)
			{
				logger.error("getSearchByLob exception occured." + e.getMessage());
			}
		
		return coFinSearchList;
		
	} 
		catch(Exception e)
		{
			logger.error("getSearchByLob exception occured." + e.getMessage());
			return coFinSearchList;
		}
		finally {
		if(!connection.isClosed()) {
			connection.close();
		}


	}
}

	@Override
	public JSONObject getCoFinancialLimit(String userName, String functionName, String amount,String srNumber,String productCode, String groupVariantCode) throws SQLException 
	{
		logger.info("getCoFinancialLimit method started");
		JSONObject financialLimitObj = null;
		MasterUsersEntity masterUsersEntity = new MasterUsersEntity();
		try
		{
			if(userName!=null && srNumber==null) {
			 masterUsersEntity = masterUsersRepository.findUserByUserNameOrSrNumber(userName, srNumber);
			}else if(userName==null && srNumber!=null) {
				 masterUsersEntity = masterUsersRepository.findUserByUserNameOrSrNumber(userName, srNumber);
			}else {
				 masterUsersEntity = masterUsersRepository.findUserByUserNameOrSrNumber(userName, srNumber);
			}
			if(masterUsersEntity != null)
			{
				if(!functionName.equalsIgnoreCase("Individual sum proposed")  && !functionName.equalsIgnoreCase("Maximum Age at New Business & Renewal") && (masterUsersEntity.getLocationType().equalsIgnoreCase("ZO") || masterUsersEntity.getLocationType().equalsIgnoreCase("CO"))&& !masterUsersEntity.getCofinancialDesignation().equalsIgnoreCase("ADM") && !masterUsersEntity.getCofinancialDesignation().equalsIgnoreCase("DM") &&  !masterUsersEntity.getCofinancialDesignation().equalsIgnoreCase("RM(P&GS)")) {
					financialLimitObj = new JSONObject();
					financialLimitObj.put("financialLimitExceeded", true);
					financialLimitObj.put("message","No setup found" );
					return financialLimitObj;
				}
				CoFinancialLimitEntity coFinancialLimitEntity = coFinancialLimitRepository.getCofinancialLimitDetailUsingMultipleParameters(masterUsersEntity.getCofinancialDesignation(), masterUsersEntity.getLocationType(),functionName,productCode,groupVariantCode);
				if(coFinancialLimitEntity == null) {
					coFinancialLimitEntity = coFinancialLimitRepository.getCofinancialLimitDetailUsingMultipleParametersWithGroupForUW(masterUsersEntity.getLocationType(), functionName, groupVariantCode);
				}if(coFinancialLimitEntity == null) {
					coFinancialLimitEntity = coFinancialLimitRepository.getCofinancialLimitDetailUsingMultipleParametersForUW(masterUsersEntity.getLocationType(), functionName);
				}
				if(coFinancialLimitEntity != null)
				{
					String financialLimit = coFinancialLimitEntity.getFinancialLimit();
					String designation  =  masterUsersEntity.getDesignation();
					String functionality = coFinancialLimitEntity.getFunctionality();
					String productFromDb = coFinancialLimitEntity.getProduct();
					String groupVariantFromDb = coFinancialLimitEntity.getGroupVariant();
					if(functionality != null && functionality.equalsIgnoreCase("Financial Powers")) {
						if(financialLimit != null && financialLimit.equalsIgnoreCase("Full")) {
							financialLimitObj = new JSONObject();
							financialLimitObj.put("financialLimitExceeded", false);
							financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
							financialLimitObj.put("financialLimit", financialLimit.replaceAll(",", ""));
							return financialLimitObj;
						}
						if(financialLimit.contains(","))
						{
							financialLimitObj = new JSONObject();
							if(Double.parseDouble(amount) > Double.parseDouble(financialLimit.replaceAll(",", "")))
							{
								financialLimitObj.put("financialLimitExceeded", true);
								financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
								financialLimitObj.put("financialLimit", financialLimit.replaceAll(",", ""));
							}
							else
							{
								financialLimitObj.put("financialLimitExceeded", false);
								financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
								financialLimitObj.put("financialLimit", financialLimit.replaceAll(",", ""));
							}
						}else {
							financialLimitObj = new JSONObject();
							if(Double.parseDouble(amount) > Double.parseDouble(financialLimit))
							{
								financialLimitObj.put("financialLimitExceeded", true);
								financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
								financialLimitObj.put("financialLimit", financialLimit);
							}
							else
							{
								financialLimitObj.put("financialLimitExceeded", false);
								financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
								financialLimitObj.put("financialLimit", financialLimit);
							}
						}
					}else if(functionality != null && functionality.equalsIgnoreCase("Underwriting")) {
						financialLimitObj = new JSONObject();
						if(functionName.equalsIgnoreCase("Number of lives") && masterUsersEntity.getLocationType().equalsIgnoreCase("UO")) {
							financialLimitObj = new JSONObject();
							if(productFromDb.toLowerCase().contains(productCode.toLowerCase()) && groupVariantFromDb.toLowerCase().equalsIgnoreCase(groupVariantCode.toLowerCase())) {
								if(Double.parseDouble(amount) > Double.parseDouble(financialLimit.replaceAll(",", ""))){								
									financialLimitObj.put("financialLimitExceeded", true);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
									financialLimitObj.put("message", "No of lives limit exceeded");
								}else {
									financialLimitObj.put("financialLimitExceeded", false);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
								}
							}else {
								financialLimitObj.put("financialLimitExceeded", true);
								financialLimitObj.put("message","No setup found" );
							}
						}else if(functionName.equalsIgnoreCase("Number of lives") && masterUsersEntity.getLocationType().equalsIgnoreCase("ZO")) {	
							if(productFromDb.toLowerCase().contains(productCode.toLowerCase()) && groupVariantFromDb.toLowerCase().equalsIgnoreCase(groupVariantCode.toLowerCase())) {
								if((masterUsersEntity.getCofinancialDesignation().equalsIgnoreCase("ADM") || masterUsersEntity.getCofinancialDesignation().equalsIgnoreCase("DM")) && Double.parseDouble(amount) > Double.parseDouble(financialLimit.replaceAll(",", ""))) {
									financialLimitObj.put("financialLimitExceeded", true);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
									financialLimitObj.put("message", "No of lives limit exceeded");
								}else if((masterUsersEntity.getCofinancialDesignation().equalsIgnoreCase("ADM") || masterUsersEntity.getCofinancialDesignation().equalsIgnoreCase("DM")) && Double.parseDouble(amount) <= Double.parseDouble(financialLimit.replaceAll(",", ""))) {
									financialLimitObj.put("financialLimitExceeded", false);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
								}else if(!masterUsersEntity.getCofinancialDesignation().equalsIgnoreCase("RM(P&GS)")  && Double.parseDouble(amount) > Double.parseDouble(financialLimit.replaceAll(",", ""))) {
									financialLimitObj.put("financialLimitExceeded", true);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
									financialLimitObj.put("message", "No of lives greater than 50000 must be processed by RM(P&GS)");
								}else if(masterUsersEntity.getCofinancialDesignation().equalsIgnoreCase("RM(P&GS)")  && Double.parseDouble(amount) > Double.parseDouble(financialLimit.replaceAll(",", ""))) {
									financialLimitObj.put("financialLimitExceeded", false);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
								}
							}else {
								financialLimitObj.put("financialLimitExceeded", true);
								financialLimitObj.put("message","No setup found" );
							}
						}else if(functionName.equalsIgnoreCase("Individual sum proposed") && masterUsersEntity.getLocationType().equalsIgnoreCase("UO")) {
							financialLimitObj = new JSONObject();
							if(productFromDb.toLowerCase().contains(productCode.toLowerCase()) && groupVariantFromDb.toLowerCase().equalsIgnoreCase(groupVariantCode.toLowerCase())) {
								if(Double.parseDouble(amount) > Double.parseDouble(financialLimit.replaceAll(",", ""))){								
									financialLimitObj.put("financialLimitExceeded", true);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
									financialLimitObj.put("message", "Individual sum proposed limit exceeded");
								}else {
									financialLimitObj.put("financialLimitExceeded", false);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
								}
							}else {
								financialLimitObj.put("financialLimitExceeded", true);
								financialLimitObj.put("message","No setup found" );
							}
						}else if(functionName.equalsIgnoreCase("Individual sum proposed") && (masterUsersEntity.getLocationType().equalsIgnoreCase("ZO") || masterUsersEntity.getLocationType().equalsIgnoreCase("CO"))) {	
							if(productFromDb.toLowerCase().contains(productCode.toLowerCase()) && groupVariantFromDb.toLowerCase().equalsIgnoreCase(groupVariantCode.toLowerCase())) {
								if(Double.parseDouble(amount) > Double.parseDouble(financialLimit.replaceAll(",", "")) || Double.parseDouble(amount) < Double.parseDouble(financialLimit.replaceAll(",", ""))) {
									financialLimitObj.put("financialLimitExceeded", false);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
								}
							}else {
								financialLimitObj.put("financialLimitExceeded", true);
								financialLimitObj.put("message","No setup found" );
							}
						}else if(functionName.equalsIgnoreCase("Premium table NB") && masterUsersEntity.getLocationType().equalsIgnoreCase("UO")) {
							if(productFromDb.toLowerCase().contains(productCode.toLowerCase()) && groupVariantFromDb.toLowerCase().equalsIgnoreCase(groupVariantCode.toLowerCase())) {
								List<String> items = Arrays.asList(financialLimit.split("\\s*,\\s*"));
								if(!items.contains(amount)) {
									financialLimitObj.put("financialLimitExceeded", true);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
									financialLimitObj.put("message", "Premium table NB should be from NP2 to NP6");
								}else {
									financialLimitObj.put("financialLimitExceeded", false);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
								}	
							}else {
								financialLimitObj.put("financialLimitExceeded", true);
								financialLimitObj.put("message","No setup found" );
							}
						}else if(functionName.equalsIgnoreCase("Premium table NB") && masterUsersEntity.getLocationType().equalsIgnoreCase("ZO")) {
							if(productFromDb.toLowerCase().contains(productCode.toLowerCase()) && groupVariantFromDb.toLowerCase().equalsIgnoreCase(groupVariantCode.toLowerCase())) {
								List<String> items = Arrays.asList(financialLimit.split("\\s*,\\s*"));
								if(!items.contains(amount)) {
									financialLimitObj.put("financialLimitExceeded", true);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
									financialLimitObj.put("message", "Premium table NB should be from NP0 for RM and NP1 for ADM/DM");
								}else {
									financialLimitObj.put("financialLimitExceeded", false);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
								}	
							}else {
								financialLimitObj.put("financialLimitExceeded", true);
								financialLimitObj.put("message","No setup found" );
							}
						}else if(functionName.equalsIgnoreCase("Premium table NB / Renewal") && masterUsersEntity.getLocationType().equalsIgnoreCase("UO")) {
							if(productFromDb.toLowerCase().contains(productCode.toLowerCase()) && groupVariantFromDb.toLowerCase().equalsIgnoreCase(groupVariantCode.toLowerCase())) {
								List<String> items = Arrays.asList(financialLimit.split("\\s*,\\s*"));
								if(!items.contains(amount)) {
									financialLimitObj.put("financialLimitExceeded", true);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
									financialLimitObj.put("message", "Premium table NB / Renewal should be from NP2 to NP6 or P3 to P8");
								}else {
									financialLimitObj.put("financialLimitExceeded", false);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
								}	
							}else {
								financialLimitObj.put("financialLimitExceeded", true);
								financialLimitObj.put("message","No setup found" );
							}
						}else if(functionName.equalsIgnoreCase("Premium table NB / Renewal") && masterUsersEntity.getLocationType().equalsIgnoreCase("ZO")) {
							if(productFromDb.toLowerCase().contains(productCode.toLowerCase()) && groupVariantFromDb.toLowerCase().equalsIgnoreCase(groupVariantCode.toLowerCase())) {
								List<String> items = Arrays.asList(financialLimit.split("\\s*,\\s*"));
								if(!items.contains(amount)) {
									financialLimitObj.put("financialLimitExceeded", true);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
									financialLimitObj.put("message", "Premium table NB / Renewal should be from NP0 or P1 for RM and NP1 or P2 for ADM/DM");
								}else {
									financialLimitObj.put("financialLimitExceeded", false);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
								}	
							}else {
								financialLimitObj.put("financialLimitExceeded", true);
								financialLimitObj.put("message","No setup found" );
							}
						}else if(functionName.equalsIgnoreCase("Individual sum Assured")) {
							if(productFromDb.toLowerCase().contains(productCode.toLowerCase()) && groupVariantFromDb.toLowerCase().equalsIgnoreCase(groupVariantCode.toLowerCase())) {
								if(Double.parseDouble(amount) > Double.parseDouble(financialLimit.replaceAll(",", ""))){
									financialLimitObj.put("financialLimitExceeded", true);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
									financialLimitObj.put("message", "Individual sum Assured limit exceeded");
								}else {
									financialLimitObj.put("financialLimitExceeded", false);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
								}
							}else {
								financialLimitObj.put("financialLimitExceeded", true);
								financialLimitObj.put("message","No setup found" );
							}
						}else if(functionName.equalsIgnoreCase("Maximum Age at New Business & Renewal")) {
							if(masterUsersEntity.getLocationType().equalsIgnoreCase("ZO") && productFromDb.toLowerCase().contains(productCode.toLowerCase()) && groupVariantFromDb.toLowerCase().equalsIgnoreCase(groupVariantCode.toLowerCase())) {
								if(Double.parseDouble(amount) > Double.parseDouble(financialLimit.replaceAll(",", ""))){
									financialLimitObj.put("financialLimitExceeded", true);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
									financialLimitObj.put("message", "Maximum Age at New Business & Renewal limit exceeded");
								}else {
									financialLimitObj.put("financialLimitExceeded", false);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
								}
							}else {
								financialLimitObj.put("financialLimitExceeded", true);
								financialLimitObj.put("message","No setup found" );
							}
						}else if(functionName.equalsIgnoreCase("Number of lives with Member Data")) {
							if(productFromDb.toLowerCase().contains(productCode.toLowerCase()) && groupVariantFromDb.toLowerCase().equalsIgnoreCase(groupVariantCode.toLowerCase())) {
								if(Double.parseDouble(amount) > Double.parseDouble(financialLimit.replaceAll(",", ""))){
									financialLimitObj.put("financialLimitExceeded", true);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
									financialLimitObj.put("message", "Number of lives with Member Data limit exceeded");
								}else {
									financialLimitObj.put("financialLimitExceeded", false);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
								}
							}else {
								financialLimitObj.put("financialLimitExceeded", true);
								financialLimitObj.put("message","No setup found" );
							}
						}else if(functionName.equalsIgnoreCase("Number of lives without Member Data")) {
							if(productFromDb.toLowerCase().contains(productCode.toLowerCase()) && groupVariantFromDb.toLowerCase().equalsIgnoreCase(groupVariantCode.toLowerCase())) {
								if(Double.parseDouble(amount) > Double.parseDouble(financialLimit.replaceAll(",", ""))){
									financialLimitObj.put("financialLimitExceeded", true);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
									financialLimitObj.put("message", "Number of lives with Member Data limit exceeded");
								}else {
									financialLimitObj.put("financialLimitExceeded", false);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
								}
							}else {
								financialLimitObj.put("financialLimitExceeded", true);
								financialLimitObj.put("message","No setup found" );
							}
						}else if(functionName.equalsIgnoreCase("Total sum assured")) {
							if(productFromDb.toLowerCase().contains(productCode.toLowerCase()) && groupVariantFromDb.toLowerCase().equalsIgnoreCase(groupVariantCode.toLowerCase())) {
								if(Double.parseDouble(amount) > Double.parseDouble(financialLimit.replaceAll(",", ""))){
									financialLimitObj.put("financialLimitExceeded", true);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
									financialLimitObj.put("message", "Total sum assured limit exceeded");
								}else {
									financialLimitObj.put("financialLimitExceeded", false);
									financialLimitObj.put("Designation",masterUsersEntity.getCofinancialDesignation() );
									financialLimitObj.put("financialLimit", financialLimit);
								}
							}else {
								financialLimitObj.put("financialLimitExceeded", true);
								financialLimitObj.put("message","No setup found" );
							}
						}
					}
				}
			}
			
			
		}
		catch(Exception e)
		{
			logger.error("getCoFinancialLimit exception occured." + e.getMessage());
		}
		logger.info("getCoFinancialLimit method started");
		return financialLimitObj;
	}
	
	@Override
	public ResponseEntity<Map<String, Object>> checkerApproveReject(List<CoFinancialLimitEntity> coFinancialEntity) throws Exception{
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		CoFinancialLimitEntity CoFinancialLimitEntity1= new  CoFinancialLimitEntity();
	//	CoFinancialLimitEntity ExistCoFinancialLimitEntity= new  CoFinancialLimitEntity();
		Date date = new Date();
		try {
			if (coFinancialEntity == null) {
				response.put(Constant.STATUS, 201);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			}else {
			//	CoFinancialLimitEntity	ExistCoFinancialLimitEntity = coFinancialLimitRepository.getCofinancialLimitDetail(coFinancialEntity.getCofinlimitId());
			for(int i=0;i<coFinancialEntity.size();i++)
			{
				CoFinancialLimitEntity	ExistCoFinancialLimitEntity = coFinancialLimitRepository.getCofinancialLimitDetail(coFinancialEntity.get(i).getCofinlimitId());
				CoFinancialLimitEntity coFinLimit= 	coFinancialEntity.get(i);
			
			if(coFinLimit.getStatus().equalsIgnoreCase("senttomaker")) {
				ExistCoFinancialLimitEntity.setApprovedBy(coFinLimit.getApprovedBy());
				ExistCoFinancialLimitEntity.setStatus(coFinLimit.getStatus());
				ExistCoFinancialLimitEntity.setWorkFlowStatus(0L);
				ExistCoFinancialLimitEntity.setRemarks(coFinLimit.getRemarks());
				coFinancialLimitRepository.save(ExistCoFinancialLimitEntity);
				Date today = new Date();
				  String SrNumber =  masterUsersRepository.getAllMasterUserDetailByUserName1(coFinancialEntity.get(i).getLoggedInUserName());		  
				  CoFinancialLimitHistoryEntity cofinancialLimitHistoryEntity= new 	CoFinancialLimitHistoryEntity();
				//  cofinancialLimitHistoryEntity.setCofinlimitId(coFinancialLimitEntity.get(i).getCofinlimitId());
				  cofinancialLimitHistoryEntity.setFunctionName(coFinancialEntity.get(i).getFunctionName());
				  cofinancialLimitHistoryEntity.setDesignation(coFinancialEntity.get(i).getDesignation());
				  cofinancialLimitHistoryEntity.setDepartment(coFinancialEntity.get(i).getDepartment());
				  cofinancialLimitHistoryEntity.setOfficeName(coFinancialEntity.get(i).getOfficeName());
				  cofinancialLimitHistoryEntity.setFinancialLimit(coFinancialEntity.get(i).getFinancialLimit());
				  cofinancialLimitHistoryEntity.setSanctionedBudgetLimit(coFinancialEntity.get(i).getSanctionedBudgetLimit());
				  cofinancialLimitHistoryEntity.setIsActive(coFinancialEntity.get(i).getIsActive());
				  cofinancialLimitHistoryEntity.setIsDeleted(coFinancialEntity.get(i).getIsDeleted());
				  cofinancialLimitHistoryEntity.setCreatedBy(coFinancialEntity.get(i).getCreatedBy());
				  cofinancialLimitHistoryEntity.setCreatedOn(today);
				  cofinancialLimitHistoryEntity.setStatus(coFinancialEntity.get(i).getStatus());
				  cofinancialLimitHistoryEntity.setApprovedBy(coFinLimit.getApprovedBy());
				  cofinancialLimitHistoryEntity.setModifiedBy(coFinLimit.getModifiedBy());
				  cofinancialLimitHistoryEntity.setApprovedOn(today);
				  cofinancialLimitHistoryEntity.setModifiedOn(today);
				  cofinancialLimitHistoryEntity.setLoggedInUserSrNumber(SrNumber);
				  if(ExistCoFinancialLimitEntity.getAction().equalsIgnoreCase("ADD")) {
					  cofinancialLimitHistoryEntity.setAction("ADD");
					  }
					  else if (ExistCoFinancialLimitEntity.getAction().equalsIgnoreCase("EDIT")) {
						  cofinancialLimitHistoryEntity.setAction("EDIT");
					  }
					  else if (ExistCoFinancialLimitEntity.getAction().equalsIgnoreCase("DELETE")) {
						  cofinancialLimitHistoryEntity.setAction("DELETE");
					  }
					  coFinancialLimitHistRepository.save(cofinancialLimitHistoryEntity);
//				response.put(Constant.STATUS, 200);
//				response.put(Constant.MESSAGE, Constant.SUCCESS);
//				response.put("Data", "Sent To Maker Successfully ");
				
			}
			else if(coFinLimit.getStatus().equalsIgnoreCase("approved")) {
				ExistCoFinancialLimitEntity.setApprovedBy(coFinLimit.getApprovedBy());
				ExistCoFinancialLimitEntity.setStatus(coFinLimit.getStatus());
				ExistCoFinancialLimitEntity.setWorkFlowStatus(2L);
				ExistCoFinancialLimitEntity.setRemarks(coFinLimit.getRemarks());
				coFinancialLimitRepository.save(ExistCoFinancialLimitEntity);
				if(ExistCoFinancialLimitEntity.getAction().equalsIgnoreCase("DELETE")){
					coFinancialLimitRepository.findAndDeletedById(coFinLimit.getCofinlimitId());
				}
				Date today = new Date();
				  String SrNumber =  masterUsersRepository.getAllMasterUserDetailByUserName1(coFinancialEntity.get(i).getLoggedInUserName());		  
				  CoFinancialLimitHistoryEntity cofinancialLimitHistoryEntity= new 	CoFinancialLimitHistoryEntity();
				//  cofinancialLimitHistoryEntity.setCofinlimitId(coFinancialLimitEntity.get(i).getCofinlimitId());
				  cofinancialLimitHistoryEntity.setFunctionName(coFinancialEntity.get(i).getFunctionName());
				  cofinancialLimitHistoryEntity.setDesignation(coFinancialEntity.get(i).getDesignation());
				  cofinancialLimitHistoryEntity.setDepartment(coFinancialEntity.get(i).getDepartment());
				  cofinancialLimitHistoryEntity.setOfficeName(coFinancialEntity.get(i).getOfficeName());
				  cofinancialLimitHistoryEntity.setFinancialLimit(coFinancialEntity.get(i).getFinancialLimit());
				  cofinancialLimitHistoryEntity.setSanctionedBudgetLimit(coFinancialEntity.get(i).getSanctionedBudgetLimit());
				  cofinancialLimitHistoryEntity.setIsActive(coFinancialEntity.get(i).getIsActive());
				  cofinancialLimitHistoryEntity.setIsDeleted(coFinancialEntity.get(i).getIsDeleted());
				  cofinancialLimitHistoryEntity.setCreatedBy(coFinancialEntity.get(i).getCreatedBy());
				  cofinancialLimitHistoryEntity.setCreatedOn(today);
				  cofinancialLimitHistoryEntity.setStatus(coFinancialEntity.get(i).getStatus());
				  cofinancialLimitHistoryEntity.setApprovedBy(coFinLimit.getApprovedBy());
				  cofinancialLimitHistoryEntity.setModifiedBy(coFinLimit.getModifiedBy());
				  cofinancialLimitHistoryEntity.setApprovedOn(today);
				  cofinancialLimitHistoryEntity.setModifiedOn(today);
				  cofinancialLimitHistoryEntity.setLoggedInUserSrNumber(SrNumber);
				  if(ExistCoFinancialLimitEntity.getAction().equalsIgnoreCase("ADD")) {
				  cofinancialLimitHistoryEntity.setAction("ADD");
				  }
				  else if (ExistCoFinancialLimitEntity.getAction().equalsIgnoreCase("EDIT")) {
					  cofinancialLimitHistoryEntity.setAction("EDIT");
				  }
				  else if (ExistCoFinancialLimitEntity.getAction().equalsIgnoreCase("DELETE")) {
					  cofinancialLimitHistoryEntity.setAction("DELETE");
				  }
				  coFinancialLimitHistRepository.save(cofinancialLimitHistoryEntity);
//				response.put(Constant.STATUS, 200);
//				response.put(Constant.MESSAGE, Constant.SUCCESS);
//				response.put("Data", " Approved Successfully ");
				
			}else if(coFinLimit.getStatus().equalsIgnoreCase("rejected")) {
				ExistCoFinancialLimitEntity.setApprovedBy(coFinLimit.getApprovedBy());
				ExistCoFinancialLimitEntity.setStatus(coFinLimit.getStatus());
				ExistCoFinancialLimitEntity.setWorkFlowStatus(3L);
				ExistCoFinancialLimitEntity.setRemarks(coFinLimit.getRemarks());
				coFinancialLimitRepository.save(ExistCoFinancialLimitEntity);
				Date today = new Date();
				  String SrNumber =  masterUsersRepository.getAllMasterUserDetailByUserName1(coFinancialEntity.get(i).getLoggedInUserName());		  
				  CoFinancialLimitHistoryEntity cofinancialLimitHistoryEntity= new 	CoFinancialLimitHistoryEntity();
				//  cofinancialLimitHistoryEntity.setCofinlimitId(coFinancialLimitEntity.get(i).getCofinlimitId());
				  cofinancialLimitHistoryEntity.setFunctionName(coFinancialEntity.get(i).getFunctionName());
				  cofinancialLimitHistoryEntity.setDesignation(coFinancialEntity.get(i).getDesignation());
				  cofinancialLimitHistoryEntity.setDepartment(coFinancialEntity.get(i).getDepartment());
				  cofinancialLimitHistoryEntity.setOfficeName(coFinancialEntity.get(i).getOfficeName());
				  cofinancialLimitHistoryEntity.setFinancialLimit(coFinancialEntity.get(i).getFinancialLimit());
				  cofinancialLimitHistoryEntity.setSanctionedBudgetLimit(coFinancialEntity.get(i).getSanctionedBudgetLimit());
				  cofinancialLimitHistoryEntity.setIsActive(coFinancialEntity.get(i).getIsActive());
				  cofinancialLimitHistoryEntity.setIsDeleted(coFinancialEntity.get(i).getIsDeleted());
				  cofinancialLimitHistoryEntity.setCreatedBy(coFinancialEntity.get(i).getCreatedBy());
				  cofinancialLimitHistoryEntity.setCreatedOn(today);
				  cofinancialLimitHistoryEntity.setStatus(coFinancialEntity.get(i).getStatus());
				  cofinancialLimitHistoryEntity.setApprovedBy(coFinLimit.getApprovedBy());
				  cofinancialLimitHistoryEntity.setModifiedBy(coFinLimit.getModifiedBy());
				  cofinancialLimitHistoryEntity.setApprovedOn(today);
				  cofinancialLimitHistoryEntity.setModifiedOn(today);
				  cofinancialLimitHistoryEntity.setLoggedInUserSrNumber(SrNumber);
				  if(ExistCoFinancialLimitEntity.getAction().equalsIgnoreCase("ADD")) {
					  cofinancialLimitHistoryEntity.setAction("ADD");
					  }
					  else if (ExistCoFinancialLimitEntity.getAction().equalsIgnoreCase("EDIT")) {
						  cofinancialLimitHistoryEntity.setAction("EDIT");
					  }
					  else if (ExistCoFinancialLimitEntity.getAction().equalsIgnoreCase("DELETE")) {
						  cofinancialLimitHistoryEntity.setAction("DELETE");
					  }
					  coFinancialLimitHistRepository.save(cofinancialLimitHistoryEntity);
//				response.put(Constant.STATUS, 200);
//				response.put(Constant.MESSAGE, Constant.SUCCESS);
//				response.put("Data", " Rejected Successfully ");
			}
			}
			response.put(Constant.STATUS, 200);
			response.put(Constant.MESSAGE, Constant.SUCCESS);
			response.put("Data", " Data Updated Successfully ");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		}catch(Exception ex) {
			response.put(Constant.STATUS, 201);
			response.put(Constant.MESSAGE, Constant.FAILED);
			response.put("Data", "Something went wrong please Check");
			ex.printStackTrace();
			logger.info("Could not update co financial  due to : " + ex.getMessage());
		}
		
		
		return null;
	}

	 @Override
	public ResponseEntity<Map<String, Object>> updateCoFinancialLimit(
			List<CoFinancialLimitEntity> coFinancialLimitEntity) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		List<CoFinancialLimitEntity> objCoFinDetailEntityList = new ArrayList<>();
		List<CoFinancialLimitHistoryEntity> objCoFinDetailEntityHistList = new ArrayList<>();
		try {
			
			if (coFinancialLimitEntity == null) {
				response.put(Constant.STATUS, 0);
				response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
			} 
			
				else {
				
					 for(int i=0;i<coFinancialLimitEntity.size();i++) {
						 Date today = new Date();
						 if(coFinancialLimitEntity.get(i).getIsEdited().equalsIgnoreCase("Y")) {
						  logger.info(" edit CoFinancial limit executed successfully.");
							CoFinancialLimitEntity coFinancialLimitEntity1 = new CoFinancialLimitEntity();
                            coFinancialLimitEntity1 = coFinancialLimitRepository.getCofinancialLimitDetail(coFinancialLimitEntity.get(i).getCofinlimitId());
							coFinancialLimitEntity1.setCofinlimitId(coFinancialLimitEntity.get(i).getCofinlimitId());
							coFinancialLimitEntity1.setFunctionName(coFinancialLimitEntity.get(i).getFunctionName());
							coFinancialLimitEntity1.setDesignation(coFinancialLimitEntity.get(i).getDesignation());
							coFinancialLimitEntity1.setDepartment(coFinancialLimitEntity.get(i).getDepartment());
							coFinancialLimitEntity1.setOfficeName(coFinancialLimitEntity.get(i).getOfficeName());
							coFinancialLimitEntity1.setHodFlag(coFinancialLimitEntity.get(i).getHodFlag());
							coFinancialLimitEntity1.setInchargeFlag(coFinancialLimitEntity.get(i).getInchargeFlag());
							coFinancialLimitEntity1.setFinancialLimit(coFinancialLimitEntity.get(i).getFinancialLimit());
							coFinancialLimitEntity1.setSanctionedBudgetLimit(coFinancialLimitEntity.get(i).getSanctionedBudgetLimit());
							coFinancialLimitEntity1.setIsActive(coFinancialLimitEntity.get(i).getIsActive());
							coFinancialLimitEntity1.setIsDeleted(coFinancialLimitEntity.get(i).getIsDeleted());
							coFinancialLimitEntity1.setModifiedBy(coFinancialLimitEntity.get(i).getModifiedBy());
							coFinancialLimitEntity1.setModifiedOn(today);
							coFinancialLimitEntity1.setCreatedBy(coFinancialLimitEntity.get(i).getCreatedBy());
							coFinancialLimitEntity1.setCreatedOn(coFinancialLimitEntity.get(i).getCreatedOn());
                            coFinancialLimitEntity1.setWorkFlowStatus(1L);
							coFinancialLimitEntity1.setStatus("SentForApproval");
							coFinancialLimitEntity1.setApprovedBy(null);
							coFinancialLimitEntity1.setAction("EDIT");
							coFinancialLimitEntity1.setRemarks(coFinancialLimitEntity.get(i).getRemarks());
						  objCoFinDetailEntityList.add(coFinancialLimitEntity1);
						  String SrNumber =  masterUsersRepository.getAllMasterUserDetailByUserName1(coFinancialLimitEntity.get(i).getLoggedInUserName());		  
						  CoFinancialLimitHistoryEntity cofinancialLimitHistoryEntity= new 	CoFinancialLimitHistoryEntity();
						  cofinancialLimitHistoryEntity.setFunctionName(coFinancialLimitEntity.get(i).getFunctionName());
						  cofinancialLimitHistoryEntity.setDesignation(coFinancialLimitEntity.get(i).getDesignation());
						  cofinancialLimitHistoryEntity.setDepartment(coFinancialLimitEntity.get(i).getDepartment());
						  cofinancialLimitHistoryEntity.setOfficeName(coFinancialLimitEntity.get(i).getOfficeName());
						  cofinancialLimitHistoryEntity.setFinancialLimit(coFinancialLimitEntity.get(i).getFinancialLimit());
						  cofinancialLimitHistoryEntity.setSanctionedBudgetLimit(coFinancialLimitEntity.get(i).getSanctionedBudgetLimit());
						  cofinancialLimitHistoryEntity.setIsActive(coFinancialLimitEntity.get(i).getIsActive());
						  cofinancialLimitHistoryEntity.setIsDeleted(coFinancialLimitEntity.get(i).getIsDeleted());
						  cofinancialLimitHistoryEntity.setCreatedBy(coFinancialLimitEntity.get(i).getCreatedBy());
						  cofinancialLimitHistoryEntity.setCreatedOn(today);
						  cofinancialLimitHistoryEntity.setStatus("SentForApproval");
						  cofinancialLimitHistoryEntity.setLoggedInUserSrNumber(SrNumber);
						  cofinancialLimitHistoryEntity.setAction("EDIT");
						  objCoFinDetailEntityHistList.add(cofinancialLimitHistoryEntity);
						 }
						 else if(coFinancialLimitEntity.get(i).getIsDelete().equalsIgnoreCase("Y")) {
							 CoFinancialLimitEntity cofinDetails = coFinancialLimitRepository.getAllCofinancialLimitDetail(coFinancialLimitEntity.get(i).getCofinlimitId());
								cofinDetails.setStatus("SentForApproval");
								cofinDetails.setWorkFlowStatus(1L);
								cofinDetails.setAction("DELETE");
								cofinDetails.setRemarks(coFinancialLimitEntity.get(i).getRemarks());
								cofinDetails.setCreatedBy(coFinancialLimitEntity.get(i).getCreatedBy());
								
								 String SrNumber =  masterUsersRepository.getAllMasterUserDetailByUserName1(coFinancialLimitEntity.get(i).getLoggedInUserName());		  
								  CoFinancialLimitHistoryEntity cofinancialLimitHistoryEntity= new 	CoFinancialLimitHistoryEntity();
								//  cofinancialLimitHistoryEntity.setCofinlimitId(coFinancialLimitEntity.get(i).getCofinlimitId());
								  cofinancialLimitHistoryEntity.setFunctionName(cofinDetails.getFunctionName());
								  cofinancialLimitHistoryEntity.setDesignation(cofinDetails.getDesignation());
								  cofinancialLimitHistoryEntity.setDepartment(cofinDetails.getDepartment());
								  cofinancialLimitHistoryEntity.setOfficeName(cofinDetails.getOfficeName());
								  cofinancialLimitHistoryEntity.setFinancialLimit(cofinDetails.getFinancialLimit());
								  cofinancialLimitHistoryEntity.setSanctionedBudgetLimit(cofinDetails.getSanctionedBudgetLimit());
								  cofinancialLimitHistoryEntity.setIsActive("Y");
								  cofinancialLimitHistoryEntity.setIsDeleted("N");
								  cofinancialLimitHistoryEntity.setCreatedBy(coFinancialLimitEntity.get(i).getCreatedBy());
								  cofinancialLimitHistoryEntity.setCreatedOn(today);
								  cofinancialLimitHistoryEntity.setStatus("SentForApproval");
								  cofinancialLimitHistoryEntity.setLoggedInUserSrNumber(SrNumber);
								  cofinancialLimitHistoryEntity.setAction("DELETE");
								//coFinancialLimitRepository.findAndDeletedById(coFinLimitId);
								
								  objCoFinDetailEntityList.add(cofinDetails);
								  objCoFinDetailEntityHistList.add(cofinancialLimitHistoryEntity);
						 }
					  }
					  
					 coFinancialLimitRepository.saveAll(objCoFinDetailEntityList);
					 coFinancialLimitHistRepository.saveAll(objCoFinDetailEntityHistList);
					  logger.info("edited cofinlimit  Successfully");
					  response.put(Constant.STATUS, 200);
						response.put(Constant.MESSAGE,Constant.SUCCESS);
						response.put(Constant.DATA,"Sent for Approval sucessfully");
						
						return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				}
			


		} catch (Exception exception) {
			logger.info("Could not edit cofinancial due to : " + exception.getMessage());
		}
		return null;
	}

	 @Override
	 public List<CoFinancialHistoryModel> getCoFinancialHistoryDetails(CoFinancialHistoryInputModel userHistoryInputModel) {
	 	logger.info("Start Service  getHistoryDetails");
	 	List<CoFinancialHistoryModel> modalList = new ArrayList();
	 	try {
	 		//List<String>locationCriteria = new ArrayList();;
	 		List<CoFinancialLimitHistoryEntity> masterRoleEntity = new ArrayList();
	 	/*	
	 		MasterUsersEntity objMasterUsersEntity = masterUsersRepository.getUserByUserName(userHistoryInputModel.getLoggedInUser());
	 		if(objMasterUsersEntity.getLocationType().equalsIgnoreCase("CO")) {
	 			locationCriteria.add("CO");
	 			locationCriteria.add("ZO");
	 			locationCriteria.add("UO");
	 		}else if(objMasterUsersEntity.getLocationType().equalsIgnoreCase("ZO")) {
	 			locationCriteria.add("ZO");
	 			locationCriteria.add("UO");
	 		}else if(objMasterUsersEntity.getLocationType().equalsIgnoreCase("UO")) {
	 			locationCriteria.add("UO");
	 		}
	 		*/
	 		if(userHistoryInputModel.getFromDate() == null && userHistoryInputModel.getToDate() != null) {
	 			
	 			List<CoFinancialLimitHistoryEntity> MasterUsersHistoryDetailsEntities = coFinancialLimitHistRepository.getAllHistoryDetails();
	 			CoFinancialLimitHistoryEntity masterUsersHistoryDetailsEntity = MasterUsersHistoryDetailsEntities.get(MasterUsersHistoryDetailsEntities.size()-1);
	 			LocalDate dateObj = LocalDate.now();
	 			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	 			//String date = dateObj.format(formatter);
	 			Date dmyFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(String.valueOf(masterUsersHistoryDetailsEntity.getCreatedOn()));
	 			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	 			String date = dateFormat.format(dmyFormat);
	 			userHistoryInputModel.setFromDate(date);
	 		}else if(userHistoryInputModel.getFromDate() != null && userHistoryInputModel.getToDate() == null) {
	 			LocalDate dateObj = LocalDate.now();
	 			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	 			String date = dateObj.format(formatter);
	 			userHistoryInputModel.setToDate(date);
	 		}
	 		 if((userHistoryInputModel.getAdminSrNumber() != null || (userHistoryInputModel.getAdminSrNumber()+"").equalsIgnoreCase("")) && (userHistoryInputModel.getFromDate() == null || (userHistoryInputModel.getFromDate()+"").equalsIgnoreCase("")) && (userHistoryInputModel.getToDate() == null || (userHistoryInputModel.getFromDate()+"").equalsIgnoreCase(""))) {
	 			 masterRoleEntity = coFinancialLimitHistRepository.getAllHistoryDetailsBasedOnAdminSrNo(userHistoryInputModel.getAdminSrNumber());
	 		}else if((userHistoryInputModel.getAdminSrNumber() != null || (userHistoryInputModel.getAdminSrNumber()+"").equalsIgnoreCase("")) && (userHistoryInputModel.getFromDate() != null || (userHistoryInputModel.getFromDate()+"").equalsIgnoreCase("")) && (userHistoryInputModel.getToDate() != null || (userHistoryInputModel.getFromDate()+"").equalsIgnoreCase(""))) {
	 			 masterRoleEntity = coFinancialLimitHistRepository.getAllHistoryDetailsBasedOnAdminSrNoAndDate(userHistoryInputModel.getAdminSrNumber(), userHistoryInputModel.getFromDate(),userHistoryInputModel.getToDate());
	 		} else if ((userHistoryInputModel.getAdminSrNumber() == null || (userHistoryInputModel.getAdminSrNumber()+"").equalsIgnoreCase("")) && (userHistoryInputModel.getFromDate() == null || (userHistoryInputModel.getFromDate()+"").equalsIgnoreCase("")) && (userHistoryInputModel.getToDate() == null || (userHistoryInputModel.getFromDate()+"").equalsIgnoreCase(""))) {
	 			 masterRoleEntity = coFinancialLimitHistRepository.getAllHistoryDetails();
	 		}else if((userHistoryInputModel.getAdminSrNumber() == null || (userHistoryInputModel.getAdminSrNumber()+"").equalsIgnoreCase("")) && (userHistoryInputModel.getFromDate() != null || (userHistoryInputModel.getFromDate()+"").equalsIgnoreCase("")) && (userHistoryInputModel.getToDate() != null || (userHistoryInputModel.getFromDate()+"").equalsIgnoreCase(""))) {
	 			 masterRoleEntity = coFinancialLimitHistRepository.getAllHistoryDetailsBasedOnAdminSrNoAndDate(userHistoryInputModel.getAdminSrNumber(), userHistoryInputModel.getFromDate(),userHistoryInputModel.getToDate());
	 		}
	 		
	 	
	 		for(int i=0 ; i <masterRoleEntity.size();i ++) {
	 			
	 			CoFinancialHistoryModel  historyModal= new CoFinancialHistoryModel();
	 			historyModal.setLoggedInUserSrNumber(masterRoleEntity.get(i).getLoggedInUserSrNumber());
	 			historyModal.setFunctionName(masterRoleEntity.get(i).getFunctionName());
	 			historyModal.setDesignation(masterRoleEntity.get(i).getDesignation());
	 			historyModal.setOfficeName(masterRoleEntity.get(i).getOfficeName());
	 			historyModal.setFinancialLimit(masterRoleEntity.get(i).getFinancialLimit());
	 			historyModal.setCreatedBy(masterRoleEntity.get(i).getCreatedBy());
	 			historyModal.setCreatedOn(masterRoleEntity.get(i).getCreatedOn());
	 			historyModal.setModifiedBy(masterRoleEntity.get(i).getModifiedBy());
	 			historyModal.setModifiedon(masterRoleEntity.get(i).getModifiedOn());
	 			historyModal.setStatus(masterRoleEntity.get(i).getStatus());
	 			historyModal.setAction(masterRoleEntity.get(i).getAction());
	 			Date dmyFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(String.valueOf(masterRoleEntity.get(i).getCreatedOn()));
	 			SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 			Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(f2.format(dmyFormat));  
	 			historyModal.setCreatedOn(date1);
	 			modalList.add(historyModal);
	 		}
	 		logger.info("End Service getHistoryDetails");
	 	}catch(Exception e) {
	 		e.printStackTrace();
	 	}

	 	return modalList;
	 	
	 }

	 }


	
	



