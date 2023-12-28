package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.CalculationValueMst;

public interface CalculationValueService {

	List<CalculationValueMst> getAllCalculationValue();

	public CalculationValueMst getCalculationValueById(long id);

	public CalculationValueMst getCalculationValueByCode(String code);

}
