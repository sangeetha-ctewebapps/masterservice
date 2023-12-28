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

import com.lic.epgs.mst.entity.PaymentFrequency;
import com.lic.epgs.mst.exceptionhandling.PaymentFrequencyServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.PaymentFrequencyService;

@RestController
@CrossOrigin("*")
public class PaymentFrequencyController {
	@Autowired
	private PaymentFrequencyService paymentFrequencyService;
	String className = this.getClass().getSimpleName();

	private static final Logger logger = LoggerFactory.getLogger(PaymentFrequencyController.class);

	 @GetMapping("/paymentFrequency")
	public List<PaymentFrequency> getAllPaymentFrequencies() throws ResourceNotFoundException, PaymentFrequencyServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		try {
			List<PaymentFrequency> paymentes = paymentFrequencyService.getAllPaymentFrequency();
			if (paymentes.isEmpty()) {
				logger.debug("Getting All Payment Type");
				throw new ResourceNotFoundException("Payment Type is not found");
			}
			return paymentes;
		} catch (Exception ex) {
			throw new PaymentFrequencyServiceException("Payment type is not found");
		}
	}

	@GetMapping("/paymentFrequencyById/{id}")
	public ResponseEntity<PaymentFrequency> getPaymentFrequencyById(@PathVariable Long id) throws PaymentFrequencyServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new payment Type By Id");
			return ResponseEntity.ok().body(paymentFrequencyService.getPaymentFrequencyById(id));
		} catch (Exception e) {
			logger.error("Payment type is not found with ID : Invalid Data", e);
			throw new PaymentFrequencyServiceException("Payment type is not found with ID" + id);
		}
	}

	@GetMapping("/paymentFrequencyByCode/{code}")
	public ResponseEntity<PaymentFrequency> getPaymentFrequencyByCode(@PathVariable String code)
			throws PaymentFrequencyServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		logger.debug(methodName + " Start ");
		try {
			logger.debug("Getting new payment Type By code");
			return ResponseEntity.ok().body(paymentFrequencyService.getPaymentFrequencyByCode(code));
		} catch (Exception e) {
			logger.error("Payment type is not found with code : Invalid Data", e);
			throw new PaymentFrequencyServiceException("Payment type is not found with code" + code);
		}
	}
}
