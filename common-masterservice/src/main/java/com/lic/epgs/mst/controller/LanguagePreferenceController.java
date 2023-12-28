package com.lic.epgs.mst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.LanguagePreferenceMst;
import com.lic.epgs.mst.exceptionhandling.LanguagePreferenceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.LanguagePreferenceService;

@RestController
@CrossOrigin("*")
public class LanguagePreferenceController {

	private static final Logger logger = LoggerFactory.getLogger(LanguagePreferenceController.class);

	@Autowired
	private LanguagePreferenceService languagepreferenceService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/LanguagePreference")
	public List<LanguagePreferenceMst> getLanguagePreference() throws ResourceNotFoundException, LanguagePreferenceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<LanguagePreferenceMst> languagepreference = languagepreferenceService.getAllLanguagePreference();

			if (languagepreference.isEmpty()) {
				logger.debug("inside LanguagePreference controller getAllLanguagePreference() method");
				logger.info("LanguagePreference list is empty ");
				throw new ResourceNotFoundException("LanguagePreference not found");
			}
			return languagepreference;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new LanguagePreferenceException("internal server error");
		}
	}

	@GetMapping("/LanguagePreference/{id}")
	public ResponseEntity<LanguagePreferenceMst> getLanguagePreferenceById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(languagepreferenceService.getLanguagePreferenceById(id));

	}

	@GetMapping("/LanguagePreferenceByCode/{code}")
	public ResponseEntity<LanguagePreferenceMst> getLanguagePreferenceByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(languagepreferenceService.getLanguagePreferenceByCode(code));

	}

}
