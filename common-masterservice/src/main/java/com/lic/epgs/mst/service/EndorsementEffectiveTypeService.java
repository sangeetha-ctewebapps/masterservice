package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.EndorsementEffectiveType;

public interface EndorsementEffectiveTypeService {

	List<EndorsementEffectiveType> getAllEndorsementEffectiveType();

	public EndorsementEffectiveType getEndorsementEffectiveTypeById(long endorsementetId);

	public EndorsementEffectiveType findByEndorsementETCode(String endorsementetcode);

}
