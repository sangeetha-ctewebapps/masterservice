package com.lic.epgs.mst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.ProductVariantEntity;

@Repository
public interface ProductVariantRepository  extends JpaRepository<ProductVariantEntity, Long> {
	
	@Query(value = "select * from master_product_variant_details where product_name_id = :productNameId", nativeQuery = true) 
	  public List<ProductVariantEntity> findAllVariantbyProductNameId(@Param("productNameId") Long productNameId);

}
