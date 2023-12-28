package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.TypeOfLifeAssured;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.exceptionhandling.TypeOfLifeAssuredServiceException;

public interface TypeOfLifeAssuredService {

	List<TypeOfLifeAssured> getAllTypeOfLife() throws ResourceNotFoundException, TypeOfLifeAssuredServiceException;

	public TypeOfLifeAssured getTypeById(long id);

	public TypeOfLifeAssured getTypeByCode(String code);
}
