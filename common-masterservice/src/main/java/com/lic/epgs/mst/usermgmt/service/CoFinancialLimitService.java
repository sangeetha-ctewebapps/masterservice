package com.lic.epgs.mst.usermgmt.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import com.lic.epgs.mst.modal.UserHistoryInputModel;
import com.lic.epgs.mst.usermgmt.entity.CoFinancialLimitEntity;
import com.lic.epgs.mst.usermgmt.entity.CoFinancialLimitNameEntity;
import com.lic.epgs.mst.usermgmt.modal.CoFinancialHistoryInputModel;
import com.lic.epgs.mst.usermgmt.modal.CoFinancialHistoryModel;
import com.lic.epgs.mst.usermgmt.modal.FunctionNameDesignationModel;
import com.lic.epgs.mst.usermgmt.modal.HistoryDetailsModel;
import com.lic.epgs.mst.usermgmt.modal.LoggedInUserModel;

public interface CoFinancialLimitService {

	List<CoFinancialLimitEntity> getAllCoFinLimit() throws Exception;

	public ResponseEntity<Map<String, Object>> updateCoFinancialLimit(List<CoFinancialLimitEntity> coFinancialLimitEntity) throws Exception;

	public ResponseEntity<Map<String, Object>> checkerApproveReject(List<CoFinancialLimitEntity> coFinancialLimitEntity) throws Exception;
	
	public ResponseEntity<Map<String, Object>> deleteCoFinancialLimit(List<CoFinancialLimitEntity> coFinancialLimitEntity) throws Exception;
	
	public ResponseEntity<Map<String, Object>> addCoFinancialLimit(List<CoFinancialLimitEntity> coFinancialLimitEntity) throws Exception;

	public List<CoFinancialLimitNameEntity> getAllCoFinacialLimitByName() throws Exception;
	

     List<CoFinancialLimitNameEntity> searchCoFinancialLimit(String designation, String functionName,String officeName,String status,String loggedInUser) throws SQLException;
     
     List<CoFinancialLimitNameEntity> searchCoFinancialLimitchecker(String designation, String functionName,String officeName,String status,String loggedInUsername) throws SQLException;
     
     public  List<FunctionNameDesignationModel> getDesignationAndFunctionDetailsByUserName(String srNumber) throws SQLException;
     
     public JSONObject getCoFinancialLimit(String userName, String functionName, String amount,String srNumber,String productCode,String groupVariantCode) throws SQLException;
     
     
     public List<CoFinancialHistoryModel> getCoFinancialHistoryDetails(CoFinancialHistoryInputModel userHistoryInputModel);
     
 //    public ResponseEntity<Map<String, Object>> getFinancialLimitBasedOnUserName(String userName, String designation,String functionName) throws Exception;

	/*ResponseEntity<Map<String, Object>> getFinancialLimitBasedOnUserName(String functionName)
			throws Exception;*/

}
