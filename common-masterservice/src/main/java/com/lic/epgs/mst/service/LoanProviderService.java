package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.LoanProvider;
import com.lic.epgs.mst.exceptionhandling.LoanProviderServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;

public interface LoanProviderService {

	List<LoanProvider> getAllLoanProvider() throws ResourceNotFoundException, LoanProviderServiceException;

	public LoanProvider getLoanProviderById(long loanId);

	public LoanProvider getLoanProviderByCode(String loanCode);

}
