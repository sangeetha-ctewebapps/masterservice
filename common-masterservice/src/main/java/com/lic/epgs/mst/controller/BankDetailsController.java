
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

import com.lic.epgs.mst.entity.BankDetails;
import com.lic.epgs.mst.exceptionhandling.AddressServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.BankDetailsService;

@RestController

@CrossOrigin("*")
public class BankDetailsController {

	@Autowired
	private BankDetailsService bankDetailsService;
	String className = this.getClass().getSimpleName();

	 private static final Logger logger = LoggerFactory.getLogger(BankDetailsController.class);

	@GetMapping("/bankDetails")
	public List<BankDetails> getAllBankDetails() throws ResourceNotFoundException, AddressServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		try {
			List<BankDetails> bDetails = bankDetailsService.getAllBankDetails();

			if (bDetails.isEmpty()) {
				logger.debug("Inside BankDetailsController getAllBankDetails() method");
				logger.info("Bank Details list is empty ");
				throw new ResourceNotFoundException("BankDetails not found");
			}
			return bDetails;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new AddressServiceException("internal server error");
		}
	}
	
	@GetMapping("/bankDetailsById/{id}")
	public ResponseEntity<BankDetails> getBankDetailsById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(bankDetailsService.getBankDetailsById(id));

	}
	

	/*
	 * @RequestMapping(value = "/searchByIfscCode", method = RequestMethod.POST)
	 * public ResponseEntity<Map<String, Object>> getSearchByIfscCode(@RequestBody
	 * BankDetails bankDetails) throws ResourceNotFoundException,
	 * BankDetailsServiceException, SQLException { String methodName =
	 * Thread.currentThread().getStackTrace()[1].getMethodName();
	 * LoggingUtil.logInfo(className, methodName, "Started");
	 * 
	 * Map<String, Object> response = new HashMap<String, Object>(); ErrorMessageDTO
	 * erroMsgDto = null; List<BankDetails> sbankDetails = null; if (null ==
	 * bankDetails) { erroMsgDto = new ErrorMessageDTO(); erroMsgDto.setCode("400");
	 * erroMsgDto.setMessage("Invalid RequestStates Payload.");
	 * response.put("error", erroMsgDto); return
	 * ResponseEntity.badRequest().body(response); } try { sbankDetails =
	 * bankDetailsService.findByIfscCode(bankDetails.getIfscCode(), bankDetails); }
	 * catch (Exception e) { } if (sbankDetails == null) { response.put("message",
	 * " No Data Found"); return ResponseEntity.accepted().body(response); } else {
	 * response.put("bankDetails", sbankDetails); return
	 * ResponseEntity.accepted().body(response); } }
	 */
	
	/*
	 * @GetMapping(value = "/searchByIFSCCode") public ResponseEntity<Map<String,
	 * Object>> getifscCode(@RequestParam("ifscCode") String ifscCode) throws
	 * ResourceNotFoundException { Map<String, Object> response = new HashMap<>();
	 * List<IFSCCodeMasterEntity> descriptionList =
	 * ifscCodeMasterService.getDeatilsByIFSCCode(ifscCode); if
	 * (descriptionList.isEmpty()) { throw new
	 * ResourceNotFoundException(Constant.NO_DATA_FOUND); } else {
	 * response.put("IfscCode", descriptionList); return
	 * ResponseEntity.accepted().body(response); }
	 * 
	 * }
	 * 
	 * @GetMapping(value = "/searchByBankName") public ResponseEntity<Map<String,
	 * Object>> searchByBankName(@RequestParam("bankName") String bankName) throws
	 * ResourceNotFoundException { Map<String, Object> response = new HashMap<>();
	 * List<IFSCCodeMasterEntity> descriptionList =
	 * ifscCodeMasterService.getDeatilsByBankName(bankName); if
	 * (descriptionList.isEmpty()) { throw new
	 * ResourceNotFoundException(Constant.NO_DATA_FOUND); }
	 * 
	 * else { response.put("IfscCode", descriptionList); return
	 * ResponseEntity.accepted().body(response); }
	 * 
	 * }
	 * 
	 * @GetMapping(value = "/searchIFSCWithOtherCriteria", produces =
	 * "application/json") public ResponseEntity<Map<String, Object>>
	 * searchIFSCWithOtherCriteria(@RequestParam(name = "bankName") String
	 * bankName, @RequestParam(name = "bankBranch") String bankBranch) throws
	 * ResourceNotFoundException { Map<String, Object> response = new HashMap<>();
	 * 
	 * List<IFSCCodeMasterEntity> detailsByOtherCriteria = ifscCodeMasterService
	 * .searchIFSCWithOtherCriteria(bankName,bankBranch); if
	 * (detailsByOtherCriteria.isEmpty()) { throw new
	 * ResourceNotFoundException(Constant.NO_DATA_FOUND); }
	 * 
	 * else { response.put("DetailsByOtherCriteriaList", detailsByOtherCriteria);
	 * return ResponseEntity.accepted().body(response);
	 * 
	 * } }
	 * 
	 * @PostMapping(value = "/searchIFSCCodeWithOtherCriteria") public
	 * ResponseEntity<Map<String, Object>> searchIFSCCodeWithOtherCriteria(
	 * 
	 * @RequestBody IFSCSearchModel ifscSearchModel) throws
	 * ResourceNotFoundException { Map<String, Object> response = new HashMap<>();
	 * 
	 * List<IFSCCodeMasterEntity> searchDataList =
	 * ifscCodeMasterService.searchIFSCCodeWithOtherCriteria(ifscSearchModel); if
	 * (searchDataList.isEmpty()) { throw new
	 * ResourceNotFoundException("No data found"); }
	 * 
	 * else { response.put("searchResult", searchDataList); return
	 * ResponseEntity.accepted().body(response); } }
	 */

}
