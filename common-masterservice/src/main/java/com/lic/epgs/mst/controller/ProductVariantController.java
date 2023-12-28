package com.lic.epgs.mst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.ProductNameMaster;
import com.lic.epgs.mst.entity.ProductVariantEntity;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ProductNameServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.ProductVariantService;


@RestController
@CrossOrigin("*")
public class ProductVariantController {

	
	@Autowired
	private ProductVariantService productVariantService;
	
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(ProductVariantController.class);

	 @GetMapping("/getAllVariant")
	public List<ProductVariantEntity> getAllVariant() throws ResourceNotFoundException, ProductNameServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<ProductVariantEntity> productName = productVariantService.getAllProductVariant();

			if (productName.isEmpty()) {
				logger.debug("inside Product Variant controller getAllProductName() method");
				logger.info(" Variant list is empty ");
				throw new ResourceNotFoundException("Variant not found");
			}
			return productName;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ProductNameServiceException("internal server error");
		}
	}
	 
	 @GetMapping("/getVariantsDetailsByProductNameId/{productNameId}")
		public List<ProductVariantEntity> getVariantsDetailsByProductNameId(@PathVariable("productNameId") Long productNameId) throws ResourceNotFoundException, ProductNameServiceException {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			LoggingUtil.logInfo(className, methodName, "Started");

			try {
				
				List<ProductVariantEntity> productName = productVariantService.getVariantsDetailsByProductNameId(productNameId);

				if (productName.isEmpty()) {
					logger.debug("inside Product Variant controller getAllVariantByProductNameId() method");
					logger.info("variant list is empty ");
					throw new ResourceNotFoundException("Variant not found");
				}
				return productName;
			} catch (Exception ex) {
				logger.error("Internal Server Error");
				throw new ProductNameServiceException("internal server error");
			}
		}
		
}
