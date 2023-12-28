/*
 * package com.lic.epgs.mst.utils;
 * 
 * 
 * import org.apache.logging.log4j.LogManager; import
 * org.apache.logging.log4j.Logger; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Component;
 * 
 * import com.lic.epgs.constant.Constant; import
 * com.lic.epgs.mst.service.IFSCCodeMasterService;
 * 
 * @Component public class DeDupeUtil {
 * 
 * 
 * @Autowired IFSCCodeMasterService ifscCodeMasterService;
 * 
 * private static final Logger logger = LogManager.getLogger(DeDupeUtil.class);
 * 
 * 
 * public Boolean deDupeCheckIFSCCode(Long ifscCodeId, String ifscCode) { try {
 * 
 * int count = ifscCodeMasterService.checkDupe(ifscCodeId, ifscCode);
 * 
 * logger.info(count); if (count == 0) {
 * logger.info(Constant.DUPLICATE_NOT_FOUND); return true; } else {
 * logger.info(Constant.DUPLICATE_CODE); return false; } } catch (Exception
 * exception) { logger.error(Constant.ERROR); throw exception; } }
 * 
 * }
 */