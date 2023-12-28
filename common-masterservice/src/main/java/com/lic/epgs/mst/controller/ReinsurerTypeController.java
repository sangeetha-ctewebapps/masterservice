package com.lic.epgs.mst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lic.epgs.mst.entity.ReinsurerType;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ReinsurerServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.ReinsurerService;
import com.lic.epgs.mst.service.ReinsurerServiceImpl;

@RestController
@CrossOrigin("*")
public class ReinsurerTypeController {

	@Autowired(required = false)
	private ReinsurerService reinsurerService;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(ReinsurerTypeController.class);

	/** GET All Reinsurers */
	 @GetMapping("/allReinsurer")
	public ResponseEntity<Object> getAllReunsurer() throws ResourceNotFoundException, ReinsurerServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<ReinsurerType> ReinsurerList = reinsurerService.getAllReunsurer();

			if (ReinsurerList.isEmpty()) {
				logger.debug("inside Reinsurercontroller getAllReinsurer() method");
				logger.info("Reinsurer type list is empty ");

				throw new ResourceNotFoundException("Reinsurer not found");
			}
			return ResponseEntity.ok(reinsurerService.getAllReunsurer());
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new ReinsurerServiceException("internal server error");
		}

	}

	/** GET Reinsurers By ID */
	@GetMapping("/getReinsurerTypeId/{id}")
	public ResponseEntity<ReinsurerType> getReinsurerTypeById(@PathVariable Long id)
			throws ReinsurerServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			ReinsurerType newReinsurer = reinsurerService.searchReinsurerTypeById(id);

			if (newReinsurer == null) {
				logger.debug("inside Reinsurercontroller getReinsurerTypeById() method");
				logger.info("Reinsurer type is empty ");

				throw new ResourceNotFoundException("Reinsurer not found");
			}
			return ResponseEntity.ok().body(reinsurerService.searchReinsurerTypeById(id));
		} catch (Exception ex) {
			logger.error("ReunsurerType not found with id:" + id);
			throw new ReinsurerServiceException("ReunsurerType not found with id:" + id);
		}

	}

	/** GET Reinsurer By Code */
	@GetMapping("/getReinsurerTypeCode/{reinsurerCode}")
	public ResponseEntity<ReinsurerType> getReinsurerTypeByCode(@PathVariable String reinsurerCode)
			throws ReinsurerServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		try {
			ReinsurerType newReinsurer = reinsurerService.searchReinsurerTypeByCode(reinsurerCode);

			if (newReinsurer == null) {
				logger.debug("inside Reinsurercontroller getReinsurerTypeByCode() method");
				logger.info("Reinsurer type is empty ");

				throw new ResourceNotFoundException("Reinsurer not found");
			}
			return ResponseEntity.ok().body(reinsurerService.searchReinsurerTypeByCode(reinsurerCode));
		} catch (Exception ex) {
			logger.error("ReunsurerType not found with code:" + reinsurerCode);
			throw new ReinsurerServiceException("ReunsurerType not found with code:" + reinsurerCode);
		}

	}

	@PostMapping("/addReinsurer")
	public ResponseEntity<Object> createReinsurerType(@RequestBody ReinsurerType reinsurerType) throws ReinsurerServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		ReinsurerType newReinsurer = null;
		HttpHeaders headers = new HttpHeaders();
		try {
			newReinsurer = reinsurerService.addReinsurerType(reinsurerType);

		} catch (Exception e) {
			throw new ReinsurerServiceException("internal server error");
		}
		return new ResponseEntity<Object>(newReinsurer, headers, HttpStatus.CREATED);
	}

}
