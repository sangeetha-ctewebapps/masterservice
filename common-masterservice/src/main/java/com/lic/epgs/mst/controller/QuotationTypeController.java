package com.lic.epgs.mst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.QuotationType;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.QuotationTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.QuotationTypeService;

@RestController
@CrossOrigin("*")
public class QuotationTypeController {
	@Autowired
	private QuotationTypeService quotationTypeService;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(QuotationTypeController.class);

	 @GetMapping("/quotation")
	public List<QuotationType> getAllQuotationType() throws ResourceNotFoundException, QuotationTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		try {
			List<QuotationType> Quotationes = quotationTypeService.getAllQuotationType();
			if (Quotationes.isEmpty()) {
				logger.debug("Getting All Quotation Type");
				throw new ResourceNotFoundException("Quotation Type is not found");
			}
			return Quotationes;
		} catch (Exception ex) {
			throw new QuotationTypeServiceException("Quotation type is not found");
		}
	}

	@GetMapping("/quotationById/{id}")
	public ResponseEntity<QuotationType> getQuotationTypeById(@PathVariable Long id) throws QuotationTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new Quotation Type By Id");
			return ResponseEntity.ok().body(quotationTypeService.getQuotationTypeById(id));
		} catch (Exception e) {
			logger.error("Quotation type is not found with ID : Invalid Data", e);
			throw new QuotationTypeServiceException("Quotation type is not found with ID" + id);
		}
	}

	@GetMapping("/quotationByCode/{code}")
	public ResponseEntity<QuotationType> getQuotationTypeByCode(@PathVariable String code)
			throws QuotationTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new Quotation Type By code");
			return ResponseEntity.ok().body(quotationTypeService.findByQuotationCode(code));
		} catch (Exception e) {
			logger.error("Quotation type is not found with code : Invalid Data", e);
			throw new QuotationTypeServiceException("Quotation type is not found with code" + code);
		}
	}
}
