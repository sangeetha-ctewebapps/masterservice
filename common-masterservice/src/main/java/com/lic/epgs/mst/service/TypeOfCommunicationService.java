package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.TypeOfCommunicationMaster;

public interface TypeOfCommunicationService {

	List<TypeOfCommunicationMaster> getAllTypeCommunication();

	public TypeOfCommunicationMaster getTypeCommunicationById(long id);

	public TypeOfCommunicationMaster getTypeCommunicationByCode(String code);

}
