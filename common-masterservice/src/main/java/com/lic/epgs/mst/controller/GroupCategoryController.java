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

import com.lic.epgs.mst.entity.GroupCategoryMst;
import com.lic.epgs.mst.exceptionhandling.GroupCategoryException;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.service.GroupCategoryService;

@RestController
@CrossOrigin("*")
public class GroupCategoryController {

	private static final Logger logger = LoggerFactory.getLogger(GroupCategoryController.class);

	@Autowired
	private GroupCategoryService groupcategoryService;

	String className = this.getClass().getSimpleName();

	 @GetMapping("/GroupCategory")
	public List<GroupCategoryMst> getAllGroupCategory() throws ResourceNotFoundException, GroupCategoryException {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");

		try {
			List<GroupCategoryMst> groupcategory = groupcategoryService.getAllGroupCategory();

			if (groupcategory.isEmpty()) {
				logger.debug("inside GroupCategory controller getAllGroupCategory() method");
				logger.info("GroupCategory list is empty ");
				throw new ResourceNotFoundException("GroupCategory not found");
			}
			return groupcategory;
		} catch (Exception ex) {
			logger.error("Internal Server Error");
			throw new GroupCategoryException("internal server error");
		}
	}

	@GetMapping("/GroupCategory/{id}")
	public ResponseEntity<GroupCategoryMst> getGroupCategoryById(@PathVariable long id) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + id);

		return ResponseEntity.ok().body(groupcategoryService.getGroupCategoryById(id));

	}

	@GetMapping("/GroupCategoryByCode/{code}")
	public ResponseEntity<GroupCategoryMst> getGroupCategoryByCode(@PathVariable String code) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started" + code);

		return ResponseEntity.ok().body(groupcategoryService.getGroupCategoryByCode(code));

	}

}
