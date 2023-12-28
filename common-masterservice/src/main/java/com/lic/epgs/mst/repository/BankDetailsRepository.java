
package com.lic.epgs.mst.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.BankDetails;

@Repository
public interface BankDetailsRepository extends JpaRepository<BankDetails, Long> {

	/*
	 * @Query(value =
	 * "select * from MASTER_BANK_DETAILS WHERE (IFSC_CODE = :IFSCCode or :IFSCCode is null)  and \r\n"
	 * + "(BANK_NAME = :bankName or :bankName is null) and \r\n" +
	 * "(BRANCH_NAME = :branchName or :branchName is null)", nativeQuery = true)
	 * public Optional<BankDetails> findByIfscCode(String ifscCode, BankDetails
	 * bankDetails);
	 */

}
