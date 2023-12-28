package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.CustomerReqType;
import com.lic.epgs.mst.exceptionhandling.CustomerReqTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;

public interface CustomerReqTypeService {

	List<CustomerReqType> getAllCustomer() throws ResourceNotFoundException, CustomerReqTypeServiceException;

	public CustomerReqType getCustomerById(long customerId);

	public CustomerReqType getCustomerByCode(String customerCode);

}
