
package com.lic.epgs.mst.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.IFSCSearchModel;
import com.lic.epgs.mst.exceptionhandling.IfscCodeException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.IFSCCodeService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/LIC_ePGS/IfscCodeMaster")
public class IFSCCodeController {

	@Autowired
	public IFSCCodeService ifscCodeService;

	@GetMapping(value = "/getAllBankMasterDetails")
	public ResponseEntity<Map<String, Object>> getAllBankMasterDetails() throws ResourceNotFoundException {
		Map<String, Object> response = new HashMap<>();

		response = (Map<String, Object>) ifscCodeService.getAllBankMasterDetails();

		return ResponseEntity.accepted().body(response);
	}

	@GetMapping(value = "/searchWithbankMasterId")
	public ResponseEntity<Map<String, Object>> loadBankMasterDetailsByBankMasterId(
			@RequestParam("bankMasterId") String bankMasterId) throws ResourceNotFoundException {
		Map<String, Object> response = new HashMap<>();

		response = (Map<String, Object>) ifscCodeService.loadBankMasterByBankMasterId(bankMasterId);

		return ResponseEntity.accepted().body(response);
	}

	@GetMapping(value = "/searchByIFSCCode")
	public ResponseEntity<Map<String, Object>> getifscCode(@RequestParam("ifscCode") String ifscCode)
			throws ResourceNotFoundException {
		Map<String, Object> response = new HashMap<>();
		response = (Map<String, Object>) ifscCodeService.getifscCode(ifscCode);

		return ResponseEntity.accepted().body(response);
	}

	@PostMapping(value = "/searchIFSCCodeWithOtherCriteria")
	public ResponseEntity<Map<String, Object>> getifscCodeWithOtherCriteria(
			@RequestBody IFSCSearchModel ifscSearchModelRequest) throws ResourceNotFoundException {
		Map<String, Object> response = new HashMap<>();
		response = (Map<String, Object>) ifscCodeService.searchIFSCCodeWithOtherCriteria(ifscSearchModelRequest);

		return ResponseEntity.accepted().body(response);
	}

	@GetMapping(value = "/getAllBranchByBankName")
	public ResponseEntity<List<Object>> searchIfscByBankName(@RequestParam("bankName") String bankName)
			throws ResourceNotFoundException, IfscCodeException {
		List<Object> response = null;
		response = ifscCodeService.getAllBranchByBankName(bankName);

		return ResponseEntity.accepted().body(response);

	}

	@GetMapping(value = "/searchByMatchedIFSCCode")
	public ResponseEntity<Map<String, Object>> getIFSCByMatched(@RequestParam("ifscCode") String ifscCode)
			throws ResourceNotFoundException {
		Map<String, Object> response = null;
		response = ifscCodeService.getIFSCByMatched(ifscCode);

		return ResponseEntity.accepted().body(response);

	}
}
