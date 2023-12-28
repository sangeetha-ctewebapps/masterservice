
package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.BankDetails;
import com.lic.epgs.mst.exceptionhandling.BankDetailsServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;

public interface BankDetailsService {

	List<BankDetails> getAllBankDetails() throws ResourceNotFoundException, BankDetailsServiceException;
	
	public BankDetails getBankDetailsById(long bankId);

	//List<BankDetails> findByIfscCode(String ifscCode, BankDetails bankDetails);

}
