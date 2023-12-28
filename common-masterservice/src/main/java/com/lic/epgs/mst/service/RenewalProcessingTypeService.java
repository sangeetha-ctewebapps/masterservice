package com.lic.epgs.mst.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.RenewalProcessingType;
import com.lic.epgs.mst.exceptionhandling.RenewalProcessingTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;

@Service
public interface RenewalProcessingTypeService {
	List<RenewalProcessingType> getAllRenewal() throws ResourceNotFoundException, RenewalProcessingTypeServiceException;

	public RenewalProcessingType getRenewalById(long renewalId);

	public RenewalProcessingType getRenewalByCode(String renewalCode);

}
