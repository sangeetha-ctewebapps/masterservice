package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.RegisterTypeMst;

@Repository
public interface RegisterRepository extends JpaRepository<RegisterTypeMst, Long> {

	@Query(value = "SELECT * FROM Master_Register_With WHERE REGISTER_TYPE_CODE = :registerTypeCode", nativeQuery = true)
	Optional<RegisterTypeMst> findByCode(String registerTypeCode);
}
