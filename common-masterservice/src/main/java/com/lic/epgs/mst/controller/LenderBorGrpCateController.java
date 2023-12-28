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

import com.lic.epgs.mst.entity.LenderBorGrpCate;
import com.lic.epgs.mst.exceptionhandling.LenderBorGrpCateServiceException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.LenderBorGrpCateServiceImpl;

@RestController
@CrossOrigin("*")
public class LenderBorGrpCateController {

	@Autowired(required = false)
	private LenderBorGrpCateServiceImpl lenderBorGrpCateService;

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(LenderBorGrpCateController.class);

	/** GET All LenderBorGrpCate */
	  @GetMapping("/lenderBorGrpCater")
	public ResponseEntity<Object> getAllLenderBorGrpCate()
			throws ResourceNotFoundException, LenderBorGrpCateServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<LenderBorGrpCate> lenderBorGrpCateList = lenderBorGrpCateService.getAllLenderBorGrpCate();

			if (lenderBorGrpCateList.isEmpty()) {
				logger.debug("inside LenderBorGrpCatecontroller getAllLenderBorGrpCater() method");
				logger.info("LenderBorGrpCate type list is empty ");

				throw new ResourceNotFoundException("LenderBorGrpCate not found");
			}
			return ResponseEntity.ok(lenderBorGrpCateService.getAllLenderBorGrpCate());
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new LenderBorGrpCateServiceException("internal server error");
		}

	}


	@GetMapping("/lenderBorGrpCater/{id}")
	public ResponseEntity<LenderBorGrpCate> getLenderBorGrpCateById(@PathVariable Long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		return ResponseEntity.ok().body(lenderBorGrpCateService.getLenderBorGrpCateById(id));
	}

	@GetMapping("/lenderBorGrpCaterByCode/{code}")
	public ResponseEntity<LenderBorGrpCate> getLenderBorGrpCateByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(lenderBorGrpCateService.getLenderBorGrpCateByCode(code));

	}
	/*	*//** GET Reinsurers By ID */
	/*
	 * @GetMapping("/lenderBorGrpCater/{id}") public
	 * ResponseEntity<LenderBorGrpCate> getLenderBorGrpCateById(@PathVariable Long
	 * id) throws LenderBorGrpCateServiceException { String methodName =
	 * Thread.currentThread().getStackTrace()[1].getMethodName();
	 * LoggingUtil.logInfo(className, methodName, "Started");
	 * 
	 * try { LenderBorGrpCate newLenderBorGrpCate =
	 * lenderBorGrpCateService.searchLenderBorGrpCateById(id);
	 * 
	 * if (newLenderBorGrpCate == null) { logger.
	 * debug("inside LenderBorGrpCatecontroller getLenderBorGrpCateeById() method");
	 * logger.info("LenderBorGrpCat type is empty ");
	 * 
	 * throw new ResourceNotFoundException("LenderBorGrpCate not found"); } return
	 * ResponseEntity.ok().body(lenderBorGrpCateService.searchLenderBorGrpCateById(
	 * id)); } catch (Exception ex) { logger.error("Internal Server Error"); throw
	 * new LenderBorGrpCateServiceException("internal server error"); }
	 * 
	 * }
	 * 
	 *//** GET LenderBorGrpCate By Code *//*
											 * @GetMapping("/lenderBorGrpCaterByCode/{code}") public
											 * ResponseEntity<LenderBorGrpCate> getLenderBorGrpCateByCode(@PathVariable
											 * String code) throws LenderBorGrpCateServiceException { String methodName
											 * = Thread.currentThread().getStackTrace()[1].getMethodName();
											 * LoggingUtil.logInfo(className, methodName, "Started"); try {
											 * LenderBorGrpCate newLenderBorGrpCate =
											 * lenderBorGrpCateService.searchLenderBorGrpCateByCode(code);
											 * 
											 * if (newLenderBorGrpCate == null) { logger.
											 * debug("inside LenderBorGrpCatecontroller getLenderBorGrpCateByCode() method"
											 * ); logger.info("LenderBorGrpCate type is empty ");
											 * 
											 * throw new ResourceNotFoundException("LenderBorGrpCate not found"); }
											 * return ResponseEntity.ok().body(lenderBorGrpCateService.
											 * searchLenderBorGrpCateByCode(code)); } catch (Exception ex) {
											 * logger.error("Internal Server Error"); throw new
											 * LenderBorGrpCateServiceException("internal server error"); }
											 * 
											 * }
											 */

}
