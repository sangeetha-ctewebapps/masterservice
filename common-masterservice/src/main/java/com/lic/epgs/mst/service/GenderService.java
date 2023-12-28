package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.GenderMst;

public interface GenderService {

	List<GenderMst> getAllGender();

	public GenderMst getGenderById(long id);


	public GenderMst getGenderByCode(String code);
}
