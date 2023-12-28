package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.TreatyTypeMst;
import com.lic.epgs.mst.entity.TypeOfLoanMst;

@Repository
public interface TypeOfLoanRepository extends JpaRepository<TypeOfLoanMst, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM TYPE_OF_LOAN WHERE TYPE_LOAN_CODE = :typeLoanCode",
	 * nativeQuery = true) Optional<TypeOfLoanMst> findByCode(String typeLoanCode);
	 */
	
	@Query("FROM TypeOfLoanMst WHERE typeOfLoanCode = :code")
	public Optional<TypeOfLoanMst> findByCode(String code);

}
