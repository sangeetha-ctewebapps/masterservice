package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.CustomerReqType;

@Repository
public interface CustomerReqTypeRepo extends JpaRepository<CustomerReqType, Long> {

	public Optional<CustomerReqType> findByCustomerCode(String code);

}
