package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.TypeOfLoanMst;

public interface TypeOfLoanService {

	List<TypeOfLoanMst> getAllTypeOfLoan();

	public TypeOfLoanMst getTypeOfLoanById(long id);

	public TypeOfLoanMst getTypeOfLoanByCode(String code);
}
