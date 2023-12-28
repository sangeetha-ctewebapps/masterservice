package com.lic.epgs.mst.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.ProductVariantEntity;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.repository.ProductVariantRepository;

@Service
@Transactional
public class ProductVariantServiceImpl implements ProductVariantService{
	
	@Autowired
	ProductVariantRepository productVariantRepository;
	
	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ProductVariantServiceImpl.class);

	@Override
	public List<ProductVariantEntity> getAllProductVariant() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return productVariantRepository.findAll();
	}

	@Override
	public List<ProductVariantEntity> getVariantsDetailsByProductNameId(Long productNameId) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return productVariantRepository.findAllVariantbyProductNameId(productNameId);
	}

}
