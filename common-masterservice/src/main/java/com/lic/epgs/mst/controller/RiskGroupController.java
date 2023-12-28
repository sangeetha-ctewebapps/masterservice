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

import com.lic.epgs.mst.entity.RiskGroupMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.RiskGroupServiceException;
import com.lic.epgs.mst.service.RiskGroupService;

@RestController
@CrossOrigin("*")
public class RiskGroupController {

	private static final Logger logger = LoggerFactory.getLogger(RiskGroupController.class);

	@Autowired
	private RiskGroupService riskGroupService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/riskgroup")
	public List<RiskGroupMaster> getAllRiskGroup() throws ResourceNotFoundException, RiskGroupServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<RiskGroupMaster> riskGroup = riskGroupService.getAllRiskGroup();

			if (riskGroup.isEmpty()) {
				logger.debug("inside risk group controller getAllZone() method");
				logger.info("risk group list is empty ");
				throw new ResourceNotFoundException("risk group not found");
			}
			return riskGroup;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new RiskGroupServiceException("internal server error");
		}
	}

	@GetMapping("/riskgroup/{id}")
	public ResponseEntity<RiskGroupMaster> getRiskGroupById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(riskGroupService.getRiskGroupById(id));

	}

	@GetMapping("/riskgroupByCode/{code}")
	public ResponseEntity<RiskGroupMaster> getRiskGroupByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(riskGroupService.getRiskGroupByCode(code));

	}

}
