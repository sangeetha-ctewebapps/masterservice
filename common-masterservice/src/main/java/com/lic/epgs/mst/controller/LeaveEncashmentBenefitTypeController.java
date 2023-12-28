package com.lic.epgs.mst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.LeaveEncashmentBenefitTypeMaster;
import com.lic.epgs.mst.exceptionhandling.LeaveEncashmentBenefitTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.LeaveEncashmentBenefitTypeService;

@RestController
@CrossOrigin("*")
public class LeaveEncashmentBenefitTypeController {

	private static final Logger logger = LoggerFactory.getLogger(LeaveEncashmentBenefitTypeController.class);

	@Autowired
	private LeaveEncashmentBenefitTypeService leaveEncashmentBenefitTypeService;

	 String className = this.getClass().getSimpleName();

	 @GetMapping("/leaveEncashmentBenefitType")
	public List<LeaveEncashmentBenefitTypeMaster> getAllLeaveEncashmentBenefitType()
			throws ResourceNotFoundException, LeaveEncashmentBenefitTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<LeaveEncashmentBenefitTypeMaster> leaveEncashment = leaveEncashmentBenefitTypeService
					.getAllLeaveEncashmentBenefitType();

			if (leaveEncashment.isEmpty()) {
				logger.debug("inside type of leave Encashment controller getAllLeaveEncashment() method");
				logger.info(" type of leave Encashment list is empty ");
				throw new ResourceNotFoundException(" type of leave Encashment not found");
			}
			return leaveEncashment;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new LeaveEncashmentBenefitTypeServiceException("internal server error");
		}
	}

	@GetMapping("/leaveEncashmentBenefitType/{id}")
	public ResponseEntity<LeaveEncashmentBenefitTypeMaster> getLeaveEncashmentBenefitTypeById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(leaveEncashmentBenefitTypeService.getLeaveEncashmentBenefitTypeById(id));

	}

	@GetMapping("/leaveEncashmentBenefitTypeByCode/{code}")
	public ResponseEntity<LeaveEncashmentBenefitTypeMaster> getLeaveEncashmentBenefitTypeByCode(
			@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(leaveEncashmentBenefitTypeService.getLeaveEncashmentBenefitTypeByCode(code));

	}

}
