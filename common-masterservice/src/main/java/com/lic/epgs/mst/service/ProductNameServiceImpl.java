package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.ProductNameController;
import com.lic.epgs.mst.entity.ProductNameMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.ProductNameRepository;

@Service
@Transactional
public class ProductNameServiceImpl implements ProductNameService {

	@Autowired
	ProductNameRepository productNameRepository;
	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ProductNameController.class);

	@Override
	public List<ProductNameMaster> getAllProductName() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return productNameRepository.findAll();
	}
	
	@Override
	public List<ProductNameMaster> getAllProductNameByCondition() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return productNameRepository.findAllByCondition();
	}

	@Override
	public ProductNameMaster addProductName(ProductNameMaster productname) {
		// TODO Auto-generated method stub
		return productNameRepository.save(productname);
	}

	@Override
	public ProductNameMaster getProductNameById(long productnameId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ProductNameMaster> ProductNameDb = this.productNameRepository.findById(productnameId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for ProductName By Id" + productnameId);
		if (ProductNameDb.isPresent()) {
			logger.info("ProductName is  found with id" + productnameId);
			return ProductNameDb.get();
		} else {
			throw new ResourceNotFoundException("ProductName not found with id:" + productnameId);
		}

	}

	@Override
	public ProductNameMaster updateProductName(ProductNameMaster productName) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		Optional<ProductNameMaster> productNameDb = this.productNameRepository.findById(productName.getProductNameId());
		LoggingUtil.logInfo(className, methodName, "update for ProductName By Id" + productName);
		if (productNameDb.isPresent()) {
			ProductNameMaster productNameUpdate = productNameDb.get();
			productNameUpdate.setProductNameId(productName.getProductNameId());
			productNameUpdate.setDescription(productName.getDescription());
			productNameUpdate.setProductNameCode(productName.getProductNameCode());
			productNameUpdate.setIsActive(productName.getIsActive());
			productNameUpdate.setModifiedBy(productName.getModifiedBy());
			productNameRepository.save(productNameUpdate);
			return productNameUpdate;
		} else {
			throw new ResourceNotFoundException("ProductName not found with id:" + productName.getProductNameId());
		}
	}

	@Override
	public ProductNameMaster getProductNameByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<ProductNameMaster> productnameDb = this.productNameRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for ProductName By code" + code);
		if (productnameDb.isPresent()) {
			logger.info("ProductName is  found with code" + code);
			return productnameDb.get();
		} else {
			throw new ResourceNotFoundException("ProductName not found with code:" + code);
		}
	}
	
	@Override
	 public void deleteProductName(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		Integer productnameDb = this.productNameRepository.findDeletedById(id);
		LoggingUtil.logInfo(className, methodName, "delete productName ById" + id);
		if (productnameDb != null) {
			logger.info("productName soft not deleted with id " + id);

		} else {
			throw new ResourceNotFoundException("productName soft not found :" + id);
		}

	}

	
}
