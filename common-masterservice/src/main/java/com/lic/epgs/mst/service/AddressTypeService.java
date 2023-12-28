package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.AddressType;
import com.lic.epgs.mst.exceptionhandling.AddressServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;

public interface AddressTypeService {

	List<AddressType> getAllAddress() throws ResourceNotFoundException, AddressServiceException;
	
	List<AddressType> getAllAddressByCondition() throws ResourceNotFoundException, AddressServiceException;

	public AddressType getAddressById(long addressId);

	public AddressType findByAddressTypeCode(String addressCode);

	AddressType createAddress(AddressType address);

	AddressType updateAddress(AddressType address);

	void deleteAddress(long id);

}
