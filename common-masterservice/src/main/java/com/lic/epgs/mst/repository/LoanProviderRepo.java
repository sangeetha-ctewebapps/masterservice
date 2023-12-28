package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.LoanProvider;

@Repository
public interface LoanProviderRepo extends JpaRepository<LoanProvider, Long> {

	Optional<LoanProvider> findByLoanCode(String loanCode);

}
