package com.lic.epgs.mst.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.LenderBorGrpCate;


@Repository
public interface LenderBorGrpCateRepository extends JpaRepository<LenderBorGrpCate, Long> {

	/*
	 * @Async
	 * 
	 * @Query("SELECT r FROM LenderBorGrpCate r WHERE r.lenderBorGrpCateeCode = :code"
	 * ) Optional<LenderBorGrpCate> findByCode(String code);
	 */
	
	@Query("FROM LenderBorGrpCate WHERE lenderBorGrpCateeCode = :code")
	public Optional<LenderBorGrpCate> findByCode(String code);
	 

}
