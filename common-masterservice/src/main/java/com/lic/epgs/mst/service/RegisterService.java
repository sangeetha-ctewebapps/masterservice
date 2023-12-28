package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.RegisterTypeMst;

public interface RegisterService {

	List<RegisterTypeMst> getAllRegister();

	public RegisterTypeMst getRegisterById(long registerId);

	public RegisterTypeMst getRegisterByCode(String code);

}
