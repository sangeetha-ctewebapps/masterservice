package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.AccountingFrequencyMaster;

public interface AccountingFrequencyService {

	List<AccountingFrequencyMaster> getAllAccountingFrequency();

	public AccountingFrequencyMaster getAccountingFrequencyById(long id);

	public AccountingFrequencyMaster getAccountingFrequencyByCode(String code);

}
