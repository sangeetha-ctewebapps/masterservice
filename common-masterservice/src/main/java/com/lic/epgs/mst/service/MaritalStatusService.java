package com.lic.epgs.mst.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.MaritalStatus;
import com.lic.epgs.mst.exceptionhandling.MaritalStatusServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;

@Service
public interface MaritalStatusService {

	List<MaritalStatus> getAllMarital() throws ResourceNotFoundException, MaritalStatusServiceException;

	public MaritalStatus getMaritalById(long maritalId);

	public MaritalStatus getMaritalByCode(String maritalCode);

}
