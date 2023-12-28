package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.QuotationType;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.QuotationTypeRepository;

@Service
@Transactional
public class QuotationTypeServiceImpl implements QuotationTypeService {
	@Autowired
	QuotationTypeRepository quotationTypeRepository;
	String className = this.getClass().getSimpleName();

	@Override
	public List<QuotationType> getAllQuotationType() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return quotationTypeRepository.findAll();
	}

	@Override
	public QuotationType getQuotationTypeById(long QuotationId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<QuotationType> QuotationTypeDb = this.quotationTypeRepository.findById(QuotationId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Quotation type By Id" + QuotationId);
		if (QuotationTypeDb.isPresent()) {
			return QuotationTypeDb.get();
		} else {
			throw new ResourceNotFoundException("Quotation type not found with id:" + QuotationId);
		}
	}

	@Override
	public QuotationType findByQuotationCode(String QuotationCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Object quotationDb = this.quotationTypeRepository.findByQuotationCode(QuotationCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search Quotation details By QuotationCode:" + QuotationCode);

		if (quotationDb != null) {

			return (QuotationType) quotationDb;
		} else {
			throw new ResourceNotFoundException("Quotation type code not found:" + QuotationCode);
		}
	}
}
