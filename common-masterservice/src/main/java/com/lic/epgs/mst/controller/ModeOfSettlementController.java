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

import com.lic.epgs.mst.entity.ModeOfSettlementMst;
import com.lic.epgs.mst.exceptionhandling.ModeOfSettlementException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.ModeOfSettlementService;

@RestController
@CrossOrigin("*")
public class ModeOfSettlementController {

	@Autowired
	private ModeOfSettlementService modeofsettlementservice;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(ModeOfSettlementController.class);

	 @GetMapping("/ModeOfSettlement")
	public List<ModeOfSettlementMst> getAllModeOfSettlement() throws ResourceNotFoundException, ModeOfSettlementException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<ModeOfSettlementMst> modeofsettlement = modeofsettlementservice.getAllModeOfSettlement();

			if (modeofsettlement.isEmpty()) {
				logger.debug("inside modeofeixtcontroller getAllModeOfSettlement() method");
				logger.info("ModeOfSettlement list is empty ");
				throw new ResourceNotFoundException("ModeOfSettlement not found");
			}
			return modeofsettlement;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ModeOfSettlementException("internal server error");
		}
	}

	@GetMapping("/ModeOfSettlement/{id}")
	public ResponseEntity<ModeOfSettlementMst> getModeOfSettlementById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(modeofsettlementservice.getModeOfSettlementById(id));
	}

	@GetMapping("/ModeOfSettlementByCode/{code}")
	public ResponseEntity<ModeOfSettlementMst> getModeOfSettlementByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(modeofsettlementservice.getModeOfSettlementByCode(code));

	}
}
