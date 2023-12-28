package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.controller.LayerNameController;
import com.lic.epgs.mst.entity.LayerNameMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.LayerNameRepository;

@Service
@Transactional
public class LayerNameServiceImpl implements LayerNameService {

	@Autowired
	private LayerNameRepository layerNameRepository;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(LayerNameController.class);

	@Override
	public List<LayerNameMaster> getAllLayerName() {
		// TODO Auto-generated method stub

		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

		logger.info("i get layer name list ");
		LoggingUtil.logInfo(className, methodName, "Started");

		return layerNameRepository.findAll();

	}

	@Override
	public LayerNameMaster getLayerNameById(long id) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LayerNameMaster> layerNameDb = this.layerNameRepository.findById(id);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for layer name By Id" + id);
		if (layerNameDb.isPresent()) {
			logger.info("layer type is  found with id" + id);
			return layerNameDb.get();
		} else {
			throw new ResourceNotFoundException("layer name not found with id:" + id);
		}

	}

	@Override
	public LayerNameMaster getLayerNameByCode(String code) {
		// TODO Auto-generated method stub
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<LayerNameMaster> layerNmaeCodeDb = this.layerNameRepository.findByCode(code);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for layer name By code" + code);
		if (layerNmaeCodeDb.isPresent()) {
			logger.info("layer name is  found with code" + code);
			return layerNmaeCodeDb.get();
		} else {
			throw new ResourceNotFoundException("layer name not found with code:" + code);
		}
	}

}
