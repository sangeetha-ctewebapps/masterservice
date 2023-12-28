package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.LeadSourceTypeMaster;

public interface LeadSourceTypeService {

	List<LeadSourceTypeMaster> getAllLeadSourceType();

	public LeadSourceTypeMaster getLeadSourceTypeById(long id);

	public LeadSourceTypeMaster getLeadSourceTypeByCode(String code);

}
