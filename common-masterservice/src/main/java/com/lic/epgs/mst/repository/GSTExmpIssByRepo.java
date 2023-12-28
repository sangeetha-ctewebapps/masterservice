package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.GSTExmpIssBy;

 @Repository
public interface GSTExmpIssByRepo extends JpaRepository<GSTExmpIssBy, Long>{

	Optional<GSTExmpIssBy> findByGstCode (String gstCode);

}
