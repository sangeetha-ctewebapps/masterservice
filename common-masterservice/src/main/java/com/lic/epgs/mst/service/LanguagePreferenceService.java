package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.LanguagePreferenceMst;

public interface LanguagePreferenceService {

	List<LanguagePreferenceMst> getAllLanguagePreference();

	public LanguagePreferenceMst getLanguagePreferenceById(long id);
	
	public LanguagePreferenceMst getLanguagePreferenceByCode(String code);

}
