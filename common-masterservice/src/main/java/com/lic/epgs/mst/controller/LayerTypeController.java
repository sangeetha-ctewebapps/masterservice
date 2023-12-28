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

import com.lic.epgs.mst.entity.LayerTypeMaster;
import com.lic.epgs.mst.exceptionhandling.LayerTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.LayerTypeService;

@RestController
@CrossOrigin("*")
public class LayerTypeController {

	private static final Logger logger = LoggerFactory.getLogger(LayerTypeController.class);

	@Autowired
	private LayerTypeService layerTypeService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/layertype")
	public List<LayerTypeMaster> getAllLayerType() throws ResourceNotFoundException, LayerTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<LayerTypeMaster> layerType = layerTypeService.getAllLayerType();

			if (layerType.isEmpty()) {
				logger.debug("inside layertypecontroller getAllLayerType() method");
				logger.info("layertype list is empty ");
				throw new ResourceNotFoundException("layertype not found");
			}
			return layerType;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new LayerTypeServiceException("internal server error");
		}
	}

	@GetMapping("/layertype/{id}")
	public ResponseEntity<LayerTypeMaster> getLayerTypeById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(layerTypeService.getLayerTypeById(id));

	}

	@GetMapping("/layertypeByCode/{code}")
	public ResponseEntity<LayerTypeMaster> getLayerTypeByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(layerTypeService.getLayerTypeByCode(code));

	}

}
