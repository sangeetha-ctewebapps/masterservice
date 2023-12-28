package com.lic.epgs.mst.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.Constant;
import com.lic.epgs.mst.entity.ReasonForRefundEntity;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ReasonForRefundServiceException;
import com.lic.epgs.mst.service.ReasonForRefundService;
import com.lic.epgs.mst.usermgmt.controller.ModuleController;
import com.lic.epgs.mst.usermgmt.entity.CoAdminEntity;
import com.lic.epgs.mst.usermgmt.entity.MasterApplicationModule;
import com.lic.epgs.mst.usermgmt.entity.MasterUsersEntity;
import com.lic.epgs.mst.usermgmt.service.ModuleService;

@CrossOrigin("*")
@RestController

public class ReasonForRefundController {
	
	private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);
	
	@Autowired
	private ReasonForRefundService reasonForRefundService;
	
	String className = this.getClass().getSimpleName();

	 @GetMapping("/getAllReasonForRefund")
	public ResponseEntity<Map<String, Object>> getAllReasonForRefund() throws ReasonForRefundServiceException {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<ReasonForRefundEntity> getAllRefund = reasonForRefundService.getAllReasonForRefund();

			if (getAllRefund.isEmpty()) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", getAllRefund);
			}

			return ResponseEntity.accepted().body(response);
		} catch (Exception ex) {
			logger.error(" get All Reason for refund  exception occured." + ex.getMessage());
			throw new ReasonForRefundServiceException("Internal server error");
		}
	}
	
	
	
	@GetMapping(value = "/getRefundReasonByRefundCode/{RefundCode}", produces = "application/json")
	public ResponseEntity<Map<String, Object>> getRefundReasonByRefundCode(@PathVariable String RefundCode) throws ReasonForRefundServiceException {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(Constant.STATUS, 0);
		response.put(Constant.MESSAGE, Constant.SOMETHING_WENT_WRONG);
		logger.info("Method Start--getRefundReasonByRefundCode--");
		
		try {
			ReasonForRefundEntity objReasonForRefundEntity = reasonForRefundService.getRefundReasonByRefundCode(RefundCode);
			if(objReasonForRefundEntity==null) {
				response.put(Constant.STATUS, 10);
				response.put(Constant.MESSAGE, Constant.NO_DATA_FOUND);
			} else {
				response.put(Constant.STATUS, 1);
				response.put(Constant.MESSAGE, Constant.SUCCESS);
				response.put("Data", objReasonForRefundEntity);
			}
			logger.info("Method End--getRefundReasonByRefundCode--");
			return ResponseEntity.accepted().body(response);
		}
		catch (Exception ex) {
			logger.error(" get All Reason for refund  exception occured." + ex.getMessage());
			throw new ReasonForRefundServiceException("Internal server error");
		}
	}
	
}
