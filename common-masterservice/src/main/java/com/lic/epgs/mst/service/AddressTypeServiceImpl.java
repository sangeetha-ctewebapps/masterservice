package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.AddressTypeController;
import com.lic.epgs.mst.entity.AddressType;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.AddressTypeRepository;

@Service
@Transactional
public class AddressTypeServiceImpl implements AddressTypeService {
	@Autowired
	AddressTypeRepository addressrepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(AddressTypeController.class);

	 @Override
	public List<AddressType> getAllAddress() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return addressrepository.findAll();
	}
	
	@Override
	public List<AddressType> getAllAddressByCondition() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return addressrepository.findAllByCondition();
	}

	@Override
	public AddressType createAddress(AddressType address) {
		return addressrepository.save(address);
	}

	@Override
	public AddressType getAddressById(long addressId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<AddressType> addressDb = this.addressrepository.findById(addressId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for address By Id" + addressId);
		if (addressDb.isPresent()) {
			logger.info("Address Type is not found with id" + addressId);
			return addressDb.get();
		} else {
			throw new ResourceNotFoundException("Address not found with id:" + addressId);
		}
	}

	@Override
	public AddressType findByAddressTypeCode(String addressCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<AddressType> addressDb = this.addressrepository.findByAddressTypeCode(addressCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search Address details By addressCode with " + addressCode);
		if (addressDb.isPresent()) {
			logger.info("addressCode is present with code" + addressCode);
			return addressDb.get();
		} else {
			throw new ResourceNotFoundException("Address Code not found with code :" + addressCode);
		}
	}

	@Override
	public AddressType updateAddress(AddressType address) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		Optional<AddressType> addressDb = this.addressrepository.findById(address.getId());
		LoggingUtil.logInfo(className, methodName, "update for address By Id" + address);
		if (addressDb.isPresent()) {
			AddressType addressUpdate = addressDb.get();
			addressUpdate.setId(address.getId());
			addressUpdate.setDescription(address.getDescription());
			addressUpdate.setAddressCode(address.getAddressCode());
			addressUpdate.setIsActive(address.getIsActive());
			addressUpdate.setModifiedBy(address.getModifiedBy());
			addressrepository.save(addressUpdate);
			return addressUpdate;
		} else {
			throw new ResourceNotFoundException("Address not found with id:" + address.getId());
		}

	}

	
	
	
	@Override
	public void deleteAddress(long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		Integer addressDb = this.addressrepository.findDeletedById(id);
		LoggingUtil.logInfo(className, methodName, "delete address type ById" + id);
		if (addressDb != null) {
			logger.info("Address Type is not deleted with id " + id);
		} else {
			throw new ResourceNotFoundException("Address Type not found :" + id);
		}

	}
	 
	
	

}
