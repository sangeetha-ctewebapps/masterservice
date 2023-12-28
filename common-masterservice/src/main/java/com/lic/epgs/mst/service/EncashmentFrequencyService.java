package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.EncashmentFrequencyMaster;

public interface EncashmentFrequencyService {

	List<EncashmentFrequencyMaster> getAllEncashmentFrequency();

	public EncashmentFrequencyMaster getEncashmentFrequencyById(long id);

	public EncashmentFrequencyMaster getEncashmentFrequencyByCode(String code);

}
