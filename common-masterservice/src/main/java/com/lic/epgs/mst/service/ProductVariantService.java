package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.ProductVariantEntity;


public interface ProductVariantService {
	
	
	public List<ProductVariantEntity> getAllProductVariant();
	
	public List<ProductVariantEntity> getVariantsDetailsByProductNameId(Long productNameId);
}
