package com.lic.epgs.mst.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lic.epgs.mst.entity.GetBankListResponseModel;
import com.lic.epgs.mst.entity.IFSCSearchModel;
import com.lic.epgs.mst.exceptionhandling.IfscCodeException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;

@Service
public class IFSCCodeService {

	private static final Logger logger = LogManager.getLogger(IFSCCodeService.class);
	@Autowired
	RestTemplate restTemplate;

	public HttpHeaders restHeader() {

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		return headers;
	}

	/*
	 * public Map<String, Object> getAllBankMasterDetails() {
	 * 
	 * final String baseUrl =
	 * "http://10.7.18.240:8080/ePGS_ACCOUNTING/LIC_ePGS/Accounting/BankMasters/getAllBankMasterDetails";
	 * Map<String, Object> responseM = new HashMap<String, Object>(); try {
	 * HttpEntity<String> entity = new HttpEntity<String>(restHeader());
	 * 
	 * ResponseEntity<Map<String, Object>> response = restTemplate.exchange(baseUrl,
	 * HttpMethod.GET, entity, new ParameterizedTypeReference<Map<String, Object>>()
	 * { });
	 * 
	 * responseM = response.getBody(); } catch (Exception e) { e.printStackTrace();
	 * } return responseM;
	 * 
	 * }
	 */
	public Map<String, Object> getAllBankMasterDetails() {

		 final String baseUrl = "https://d1stvrrpgca01.licindia.com:8443/accountingcoreservice/ePGS/Accounts/Receipts/getBankName";
		Map<String, Object> responseM = new HashMap<String, Object>();
		try {
			HttpEntity<String> entity = new HttpEntity<String>(restHeader());

			ResponseEntity<Map<String, Object>> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity,
					new ParameterizedTypeReference<Map<String, Object>>() {
					});

			responseM = response.getBody();
		} catch (Exception e) {
			logger.error(" get All Bank Master details exception occured." + e.getMessage());
			throw new ResourceNotFoundException ("Internal Server error");
		}
		return responseM;

	}

	public Map<String, Object> loadBankMasterByBankMasterId(String bankMasterId) {

		final String baseUrl = "http://10.7.18.240:8080/ePGS_ACCOUNTING/LIC_ePGS/Accounting/BankMasters/searchWithbankMasterId/?bankMasterId={bankMasterId}";
		Map<String, Object> viewBankMAsterDetails = new HashMap<String, Object>();
		try {
			HttpEntity<String> entity = new HttpEntity<String>(restHeader());

			ResponseEntity<Map<String, Object>> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity,
					new ParameterizedTypeReference<Map<String, Object>>() {
					}, bankMasterId);

			viewBankMAsterDetails = response.getBody();
		} catch (Exception e) {
			logger.error(" load bank Master By Bank Master Id occured." + e.getMessage());
			throw new ResourceNotFoundException ("Internal Server error");
		}
		return viewBankMAsterDetails;

	}

	public List<Object> getAllBranchByBankName(String bankName) throws IfscCodeException {

		final String baseUrl = "https://10.240.34.7:8443/accountingcoreservice/ePGS/Accounts/collections/bankDetails/getAllBranchByBankName?bankName=" + bankName;
		// final String baseUrl =
		// "http://10.7.18.240:8080/ePGS-Accounts-1.1/ePGS/Accounts/collections/bankDetails/getAllBranchByBankName?bankName={bankName}";
		List<Object> viewBankDetails = null;
		try {
			HttpEntity<String> entity = new HttpEntity<String>(restHeader());

			ResponseEntity<List<Object>> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<Object>>() {
					}, bankName);

			viewBankDetails = response.getBody();
		} catch (Exception e) {
			logger.error(" get All Branch By Bank Name exception occured." + e.getMessage());
			throw new IfscCodeException("Internal Server error");
		}
		return viewBankDetails;
	}

	public Map<String, Object> getifscCode(String ifscCode) {

		final String baseUrl = "http://10.7.18.240:8080/ePGS_ACCOUNTING/LIC_ePGS/Accounting/IfscCodeMaster/searchByIFSCCode/?ifscCode={ifscCode}";

		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			HttpEntity<String> entity = new HttpEntity<String>(restHeader());

			ResponseEntity<Map<String, Object>> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity,
					new ParameterizedTypeReference<Map<String, Object>>() {
					}, ifscCode);

			responseMap = response.getBody();
		} catch (Exception e) {
			logger.error(" get ifsc code exception occured." + e.getMessage());
			throw new ResourceNotFoundException ("Internal Server error");
		}

		return responseMap;

	}

	public Map<String, Object> searchIFSCCodeWithOtherCriteria(IFSCSearchModel ifscSearchModelRequest) {

	//	HashMap<String, String> map = new HashMap<String, String>();
		Map<String, Object> ob = new HashMap<String, Object>();
		final String baseUrl = "http://10.7.18.240:8080/ePGS_ACCOUNTING/LIC_ePGS/Accounting/IfscCodeMaster/searchIFSCCodeWithOtherCriteria";

		IFSCSearchModel ifscSearchModel = new IFSCSearchModel();

		ifscSearchModel.setBankBranch(ifscSearchModelRequest.getBankBranch());
		ifscSearchModel.setBankName(ifscSearchModelRequest.getBankName());
		ifscSearchModel.setStatus(ifscSearchModelRequest.getStatus());

		HttpEntity<IFSCSearchModel> entity = new HttpEntity<IFSCSearchModel>(ifscSearchModel, restHeader());

		ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(baseUrl, HttpMethod.POST, entity,
				new ParameterizedTypeReference<Map<String, Object>>() {
				});
		ob = responseEntity.getBody();

		return ob;
	}

	public Map<String, Object> getIFSCByMatched(String ifscCode) {
		/* Megham URL */
//		final String baseUrl = "http://10.7.18.240:8080/Lic-Account-Application-3.0/ePGS/Accounts/Receipts/getBankDetailsByIfsc?ifscCode="
//				+ ifscCode;
		/* UAT URL */
		final String baseUrl = "https://10.240.34.17:8443/accountingcoreservice/ePGS/Accounts/Receipts/getBankDetailsByIfsc?ifscCode="
				+ ifscCode;

		/*
		 * TODO Please maintain below url as environment specific and remove unused one
		 */
		// final String baseUrl =
		// "http://10.7.18.240:8080/ePGS_ACCOUNTING/LIC_ePGS/Accounting/IfscCodeMaster/searchByMatchedIFSCCode/?ifscCode={ifscCode}";
		// final String baseUrl =
		// "http://d1utvrrpgca01.licindia.com:8443/ePGS-Accounts-1.1_uat/LIC_ePGS/Accounting/IfscCodeMaster/searchByMatchedIFSCCode/?ifscCode={ifscCode}";
		// final String baseUrl
		// ="https://d1utvrrpgca01.licindia.com:8443/ePGS-Accounts-1.1_uat/ePGS/Accounts/StateAndDistrictCode?ifscCode={ifscCode}";
		// final String baseUrl
		// ="https://epgs.licindia.com/ePGS-Accounts-1.1/ePGS/Accounts/StateAndDistrictCode?ifscCode={ifscCode}";

		Map<String, Object> responseMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			HttpEntity<String> entity = new HttpEntity<String>(restHeader());

			ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);

			GetBankListResponseModel bankList = mapper.readValue(response.getBody(), GetBankListResponseModel.class);
			responseMap.put("Data", bankList.getBody());
		} catch (Exception e) {
			logger.error(" get All Branch By Bank Name exception occured." + e.getMessage());
			throw new ResourceNotFoundException ("Internal Server error");
		}

		return responseMap;

	}
}
