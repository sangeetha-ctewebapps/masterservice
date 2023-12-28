package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.TypeOfEndorsement;

public interface TypeOfEndorsementService {

	List<TypeOfEndorsement> getAllTypeOfEndorsement();

	public TypeOfEndorsement getTypeOfEndorsementById(long typeoeId);

	public TypeOfEndorsement findByTypeOfEndorsementCode(String typeoecode);

}
