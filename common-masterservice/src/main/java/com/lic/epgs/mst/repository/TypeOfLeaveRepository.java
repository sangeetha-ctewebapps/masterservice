package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.TypeOfLeaveMaster;

@Repository
public interface TypeOfLeaveRepository extends JpaRepository<TypeOfLeaveMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_TYPE_LEAVE WHERE TYPE_LEAVE_CODE = :typeLeaveCode", nativeQuery = true)
	Optional<TypeOfLeaveMaster> findByCode(String typeLeaveCode);

}
