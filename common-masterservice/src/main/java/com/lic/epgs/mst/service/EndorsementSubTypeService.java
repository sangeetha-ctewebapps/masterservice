package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.EndorsementSubType;

public interface EndorsementSubTypeService {

	List<EndorsementSubType> getAllEndorsementSubType();

	public EndorsementSubType getEndorsementSubTypeById(long endorsementstId);

	public EndorsementSubType findByEndorsementSTCode(String endorsementstcode);

}
