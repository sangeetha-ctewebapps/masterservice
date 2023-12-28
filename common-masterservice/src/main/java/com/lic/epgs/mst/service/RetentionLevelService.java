package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.RetentionLevel;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.RetentionLevelServiceException;

public interface RetentionLevelService {

	List<RetentionLevel> getAllRetention() throws ResourceNotFoundException, RetentionLevelServiceException;

	public RetentionLevel getRetentionById(long id);

	public RetentionLevel getRetentionByCode(String code);
}
