package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.controller.LayerTypeController;
import com.lic.epgs.mst.entity.LayerTypeMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.LayerTypeRepository;

@Service
@Transactional
public class LayerTypeServiceImpl implements LayerTypeService {
	@Autowired
	private LayerTypeRepository layerTypeRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(LayerTypeController.class);

	@Override
	public List<LayerTypeMaster> getAllLayerType() {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get layer type list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return layerTypeRepository.findAll();
	}

	@Override
	public LayerTypeMaster getLayerTypeById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LayerTypeMaster> layerTypeDb = this.layerTypeRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for layer type By Id" + id);
		if (layerTypeDb.isPresent()) {
			logger.info("layer type is  found with id" + id);
			return layerTypeDb.get();
		} else {
			throw new ResourceNotFoundException("layer type not found with id:" + id);
		}
	}

	@Override
	public LayerTypeMaster getLayerTypeByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LayerTypeMaster> layerTypeCodeDb = this.layerTypeRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for layer type By code" + code);
		if (layerTypeCodeDb.isPresent()) {
			logger.info("layer type is  found with code" + code);
			return layerTypeCodeDb.get();
		} else {
			throw new ResourceNotFoundException("layer type not found with code:" + code);
		}
	}

}
