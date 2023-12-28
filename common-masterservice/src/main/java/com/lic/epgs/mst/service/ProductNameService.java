package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.ProductNameMaster;

public interface ProductNameService {
	ProductNameMaster addProductName(ProductNameMaster productname);

	ProductNameMaster updateProductName(ProductNameMaster productname);

	List<ProductNameMaster> getAllProductName();
	
	List<ProductNameMaster> getAllProductNameByCondition();

	public ProductNameMaster getProductNameById(long productnameId);

	void deleteProductName(long id);

	public ProductNameMaster getProductNameByCode(String code);
}
