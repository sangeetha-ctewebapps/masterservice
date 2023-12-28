package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.PaymentFrequency;

public interface PaymentFrequencyService {

	List<PaymentFrequency> getAllPaymentFrequency();

	public PaymentFrequency getPaymentFrequencyById(long paymentId);

	public PaymentFrequency getPaymentFrequencyByCode(String paymentCode);

}
