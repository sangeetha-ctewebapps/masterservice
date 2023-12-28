package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.QuotationType;

public interface QuotationTypeService {

	List<QuotationType> getAllQuotationType();

	public QuotationType getQuotationTypeById(long quotationId);

	public QuotationType findByQuotationCode(String quotationCode);

}
