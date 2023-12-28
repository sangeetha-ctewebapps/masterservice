package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.TypeOfCommunicationMaster;

@Repository
public interface TypeOfCommunicationRepository extends JpaRepository<TypeOfCommunicationMaster, Long> {

	@Query(value = "SELECT * FROM MASTER_TYPE_COMMUNICATION WHERE TYPE_COMMUNICATION_CODE = :typeCommunicationCode", nativeQuery = true)
	Optional<TypeOfCommunicationMaster> findByCode(String typeCommunicationCode);

}
