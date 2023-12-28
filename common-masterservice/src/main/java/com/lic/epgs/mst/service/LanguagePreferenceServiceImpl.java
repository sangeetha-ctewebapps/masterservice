package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.LanguagePreferenceController;
import com.lic.epgs.mst.entity.LanguagePreferenceMst;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.LanguagePreferenceRepository;

@Service
@Transactional
public class LanguagePreferenceServiceImpl implements LanguagePreferenceService {

	@Autowired
	private LanguagePreferenceRepository languagePreferenceRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(LanguagePreferenceController.class);

	@Override
	//@Cacheable()
	public List<LanguagePreferenceMst> getAllLanguagePreference() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get LanguagePreference list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return languagePreferenceRepository.findAll();
	}

	@Override
	public LanguagePreferenceMst getLanguagePreferenceById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LanguagePreferenceMst> languagepreferenceDb = this.languagePreferenceRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for LanguagePreference By Id" + id);
		if (languagepreferenceDb.isPresent()) {
			logger.info("LanguagePreference is  found with id" + id);
			return languagepreferenceDb.get();
		} else {
			throw new ResourceNotFoundException("LanguagePreference not found with id:" + id);
		}
	}

	@Override
	public LanguagePreferenceMst getLanguagePreferenceByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LanguagePreferenceMst> languagepreferenceDb = this.languagePreferenceRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for LanguagePreference By code" + code);
		if (languagepreferenceDb.isPresent()) {
			logger.info("LanguagePreference is  found with code" + code);
			return languagepreferenceDb.get();
		} else {
			throw new ResourceNotFoundException("LanguagePreference not found with code:" + code);
		}
	}

}
