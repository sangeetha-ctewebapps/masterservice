package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.PaymentFrequency;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.PaymentFrequencyRepository;

@Service
@Transactional
public class PaymentFrequencyServiceImpl implements PaymentFrequencyService {
	@Autowired
	PaymentFrequencyRepository paymentFrequencyRepository;
	String className = this.getClass().getSimpleName();

	@Override
	public List<PaymentFrequency> getAllPaymentFrequency() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return paymentFrequencyRepository.findAll();
	}

	@Override
	public PaymentFrequency getPaymentFrequencyById(long PaymentId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<PaymentFrequency> PaymentFrequencyDb = this.paymentFrequencyRepository.findById(PaymentId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for Payment type By Id" + PaymentId);
		if (PaymentFrequencyDb.isPresent()) {
			return PaymentFrequencyDb.get();
		} else {
			throw new ResourceNotFoundException("Payment type not found with id:" + PaymentId);
		}
	}

	@Override
	public PaymentFrequency getPaymentFrequencyByCode(String paymentCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Object paymentDb = this.paymentFrequencyRepository.findByPaymentFrequencyCode(paymentCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search Payment details By paymentCode:" + paymentCode);

		if (paymentDb != null) {

			return (PaymentFrequency) paymentDb;
		} else {
			throw new ResourceNotFoundException("Payment type code not found:" + paymentCode);
		}
	}

}
