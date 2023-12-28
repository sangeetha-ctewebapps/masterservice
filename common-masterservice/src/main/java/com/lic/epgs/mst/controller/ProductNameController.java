package com.lic.epgs.mst.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.constant.ErrorMessageDTO;
import com.lic.epgs.mst.entity.ProductNameMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ProductNameServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.ProductNameService;

@RestController
@CrossOrigin("*")
public class ProductNameController {

	@Autowired
	private ProductNameService productNameService;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(ProductNameController.class);

	 @GetMapping("/ProductName")
	public List<ProductNameMaster> getAllProductName() throws ResourceNotFoundException, ProductNameServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<ProductNameMaster> productName = productNameService.getAllProductName();

			if (productName.isEmpty()) {
				logger.debug("inside Product Name controller getAllProductName() method");
				logger.info("Product Name list is empty ");
				throw new ResourceNotFoundException("Product Name not found");
			}
			return productName;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ProductNameServiceException("internal server error");
		}
	}
	
	@GetMapping("/ProductNameByCondition")
	public List<ProductNameMaster> getAllProductNameByCondition() throws ResourceNotFoundException, ProductNameServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<ProductNameMaster> productName = productNameService.getAllProductNameByCondition();

			if (productName.isEmpty()) {
				logger.debug("inside Product Name controller getAllProductNameByCondition() method");
				logger.info("Product Name list is empty ");
				throw new ResourceNotFoundException("Product Name not found");
			}
			return productName;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ProductNameServiceException("internal server error");
		}
	}
	
	@GetMapping("/ProductName/{id}")
	public ResponseEntity<ProductNameMaster> getProductNameById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(productNameService.getProductNameById(id));
	}

	/*
	 * @PostMapping("/addProductName") public ResponseEntity<ProductNameMaster>
	 * addProductName(@RequestBody ProductNameMaster productName) {
	 * logger.info("ProductName list is found" + productName.toString()); return
	 * ResponseEntity.ok().body(productNameService.addProductName(productName)); }
	 */
	
	@PostMapping("/addProductName")
	public ResponseEntity<Map<String, Object>> addProductName(@Valid @RequestBody ProductNameMaster productName) throws ProductNameServiceException {
		logger.info("productName list is found" + productName.toString());
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto = null;
		ProductNameMaster productNameMaster = new ProductNameMaster();
		try {

		if (productName.getProductNameCode()!=null && !productName.getProductNameCode().isEmpty()) {
			
			productNameMaster = productNameService.getProductNameByCode(productName.getProductNameCode());
			
		}
		else {
			erroMsgDto = new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
			response.put("status", 0);
			response.put("error", erroMsgDto);
			return ResponseEntity.badRequest().body(response);
		}
			
		} catch (Exception e) {
			logger.error("Internal Server Error");
			throw new ProductNameServiceException("internal server error");
		}
		if (productNameMaster == null) {
			ProductNameMaster saveProductName = productNameService.addProductName(productName);
			response.put("productName", saveProductName);
			response.put("message", " productName created succesfully");
			return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", "productName already exist");
			return ResponseEntity.accepted().body(response);
		}
	}
	
	@PutMapping("/modifyProductName/{id}")
	public ResponseEntity <Map<String, Object>> updateProductName(@PathVariable Long id, @RequestBody ProductNameMaster productName) throws ProductNameServiceException {
		productName.setProductNameId(id);
		Map<String, Object> response = new HashMap<>();
		ErrorMessageDTO erroMsgDto = null;
		ProductNameMaster productNameMaster = null;
		ProductNameMaster productNameMaster1 = null;
		if (null == productName) {
			erroMsgDto = new ErrorMessageDTO();
			erroMsgDto.setCode("400");
			erroMsgDto.setMessage("Invalid RequestStates Payload.");
			response.put("status", 0);
			response.put("error", erroMsgDto);
			return ResponseEntity.badRequest().body(response);
		}
		try {
			productNameMaster = productNameService.getProductNameByCode(productName.getProductNameCode());
			productNameMaster1 = productNameService.getProductNameById(id);
		} catch (Exception e) {
			logger.error("Internal Server Error");
			throw new ProductNameServiceException("internal server error");
		}
		if (productNameMaster == null || productNameMaster.getProductNameCode().equals(productNameMaster1.getProductNameCode()) ) {
			ProductNameMaster saveProductName = productNameService.updateProductName(productName);
			response.put("productName", saveProductName);
			response.put("message", " productName modified succesfully");
			return ResponseEntity.accepted().body(response);
		} else {
			response.put("message", "productName already exist");
			return ResponseEntity.accepted().body(response);
		}
	}
	
	@DeleteMapping("/deleteProductName/{id}")
	public ResponseEntity<HttpStatus> deleteProductName(@PathVariable Long id) throws ProductNameServiceException {
		try {
			this.productNameService.deleteProductName(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			throw new ProductNameServiceException(e.getMessage());

		}
	}

	@GetMapping("/ProductNameByCode/{code}")
	public ResponseEntity<ProductNameMaster> getProductNameByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(productNameService.getProductNameByCode(code));

	}
}
