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

import com.lic.epgs.mst.entity.PortfolioTypeMaster;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.PortfolioTypeServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.PortfolioTypeService;

@RestController
@CrossOrigin("*")
public class PortfolioTypeController {

	private static final Logger logger = LoggerFactory.getLogger(PortfolioTypeController.class);

	@Autowired
	private PortfolioTypeService PortfolioTypeService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/portfoliotype")
	public List<PortfolioTypeMaster> getAllPortfolioType()
			throws ResourceNotFoundException, PortfolioTypeServiceException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<PortfolioTypeMaster> portfolioType = PortfolioTypeService.getAllPortfolioType();

			if (portfolioType.isEmpty()) {
				logger.debug("inside portfolio type controller getAllPortfolio Type() method");
				logger.info("Portfolio type list is empty ");
				throw new ResourceNotFoundException("Portfolio type not found");
			}
			return portfolioType;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new PortfolioTypeServiceException("internal server error");
		}
	}

	@GetMapping("/portfoliotype/{id}")
	public ResponseEntity<PortfolioTypeMaster> getPortfolioTypeById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(PortfolioTypeService.getPortfolioTypeById(id));

	}

	@GetMapping("/portfolioTypeByCode/{code}")
	public ResponseEntity<PortfolioTypeMaster> getPortfolioTypeByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(PortfolioTypeService.getPortfolioTypeByCode(code));

	}

}
