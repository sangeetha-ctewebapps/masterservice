package com.lic.epgs.mst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lic.epgs.mst.entity.LanguagePreferenceMst;

@Repository
public interface LanguagePreferenceRepository extends JpaRepository<LanguagePreferenceMst, Long> {

	/*
	 * @Query(value =
	 * "SELECT * FROM LANGUAGE_PREFERENCE WHERE LANGUAGE_PREFERENCE_CODE = :languagePreferenceCode"
	 * , nativeQuery = true) Optional<LanguagePreferenceMst> findByCode(String
	 * languagePreferenceCode);
	 */
	
	@Query("FROM LanguagePreferenceMst WHERE languagePreferenceCode = :code")
	public Optional<LanguagePreferenceMst> findByCode(String code);

}
