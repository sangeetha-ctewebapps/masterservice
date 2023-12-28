package com.lic.epgs.mst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.QuotationType;

@Repository
public interface QuotationTypeRepository extends JpaRepository<QuotationType, Long> {

	@Query("SELECT DISTINCT r FROM QuotationType r WHERE r.quotation_code = :quotationcode")
	public Object findByQuotationCode(String quotationcode);

}
