package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.CustomerType;

public interface CustomerTypeService {

	List<CustomerType> getAllCustomer();

	public CustomerType getCustomerById(long id);

	public CustomerType getCustomerByCode(String code);

}
