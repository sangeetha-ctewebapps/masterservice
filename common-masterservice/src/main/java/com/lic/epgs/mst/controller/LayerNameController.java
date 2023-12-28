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

import com.lic.epgs.mst.entity.LayerNameMaster;
import com.lic.epgs.mst.exceptionhandling.LayerNameServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.LayerNameService;

@RestController
@CrossOrigin("*")
public class LayerNameController {
	private static final Logger logger = LoggerFactory.getLogger(LayerNameController.class);

	@Autowired
	private LayerNameService layerNameService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/layername")
	public List<LayerNameMaster> getAllLayerName() throws ResourceNotFoundException, LayerNameServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<LayerNameMaster> layerName = layerNameService.getAllLayerName();

			if (layerName.isEmpty()) {
				logger.debug("inside layer name controller getAllLayerType() method");
				logger.info("layer name list is empty ");
				throw new ResourceNotFoundException("layername not found");
			}
			return layerName;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new LayerNameServiceException("internal server error");
		}
	}

	@GetMapping("/layername/{id}")
	public ResponseEntity<LayerNameMaster> getLayerNameById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(layerNameService.getLayerNameById(id));

	}

	@GetMapping("/layernameByCode/{code}")
	public ResponseEntity<LayerNameMaster> getLayerNameByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(layerNameService.getLayerNameByCode(code));

	}

}
