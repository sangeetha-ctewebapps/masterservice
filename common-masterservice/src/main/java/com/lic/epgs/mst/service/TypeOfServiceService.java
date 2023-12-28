package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.TypeOfService;

public interface TypeOfServiceService {

	List<TypeOfService> getAllTypeOfService();

	public TypeOfService getTypeOfServiceById(long typeosId);

	public TypeOfService findByTypeOfServiceCode(String typeoscode);

}
