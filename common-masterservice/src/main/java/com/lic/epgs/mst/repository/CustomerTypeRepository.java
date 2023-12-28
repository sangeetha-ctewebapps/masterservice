package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.CustomerType;

@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerType, Long> {

	@Query(value = "SELECT * FROM MASTER_CUSTOMER WHERE CUSTOMER_CODE = :customerCode", nativeQuery = true)
	Optional<CustomerType> findByCode(String customerCode);

}
