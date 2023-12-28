package com.lic.epgs.mst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.PaymentFrequency;

@Repository
public interface PaymentFrequencyRepository extends JpaRepository<PaymentFrequency, Long> {

	 @Query("SELECT DISTINCT r FROM PaymentFrequency r WHERE r.payment_code = :paymentCode")
	public Object findByPaymentFrequencyCode(String paymentCode);

}
