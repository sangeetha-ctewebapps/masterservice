package com.lic.epgs.mst.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.DynamicUrlConfig;
import com.lic.epgs.mst.repository.DynamicUrlConfigRepository;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/urlconfig")
public class UrlConfigController {

	@Autowired
	DynamicUrlConfigRepository configRepository;

	@GetMapping("/configList")
	public ResponseEntity<List<DynamicUrlConfig>> getAllConfigUrl(@RequestParam(required = false) String name) {
		try {
			List<DynamicUrlConfig> config = new ArrayList<DynamicUrlConfig>();
			if (name != null) {
				configRepository.findByName(name).forEach(config::add);
				if (config.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
			} else {
				configRepository.findAll().forEach(config::add);
			}
			return new ResponseEntity<>(config, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/config/{id}")
	public ResponseEntity<DynamicUrlConfig> getConfigUrlById(@PathVariable("id") long id) {
		try {
			Optional<DynamicUrlConfig> config = configRepository.findById(id);
			if (config.isPresent()) {
				return new ResponseEntity<>(config.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/addConfig")
	public ResponseEntity<DynamicUrlConfig> createTutorial(@RequestBody DynamicUrlConfig config) {
		try {
			DynamicUrlConfig saveConfig = configRepository
					.save(new DynamicUrlConfig(config.getName(), config.getUrlvalue(), config.getProvider(),
							config.getProfile(), config.getIsactive(), config.getIsdeleted()));
			return new ResponseEntity<>(saveConfig, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/updateConfig/{id}")
	public ResponseEntity<DynamicUrlConfig> updateTutorial(@PathVariable("id") long id,
			@RequestBody DynamicUrlConfig config) {
		Optional<DynamicUrlConfig> configData = configRepository.findById(id);
		if (configData.isPresent()) {
			DynamicUrlConfig _config = configData.get();
			_config.setName(config.getName());
			_config.setUrlvalue(config.getUrlvalue());
			_config.setProvider(config.getProvider());
			_config.setIsactive(config.getIsactive());
			_config.setIsdeleted(config.getIsdeleted());
			return new ResponseEntity<>(configRepository.save(_config), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/deleteConfig/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		try {
			configRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
