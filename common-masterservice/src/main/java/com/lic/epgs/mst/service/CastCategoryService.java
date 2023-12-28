package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.CastCategoryMst;

public interface CastCategoryService {

	List<CastCategoryMst> getAllCastCategory();

	public CastCategoryMst getCastCategoryById(long id);

	public CastCategoryMst getCastCategoryByCode(String code);

}
