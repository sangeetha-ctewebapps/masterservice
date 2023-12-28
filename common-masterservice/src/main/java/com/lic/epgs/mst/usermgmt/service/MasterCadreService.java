package com.lic.epgs.mst.usermgmt.service;

import java.util.List;

import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.usermgmt.entity.MasterCadre;
import com.lic.epgs.mst.usermgmt.exceptionhandling.MasterCadreServiceException;

public interface MasterCadreService {
	
	List<MasterCadre> getAllCadres() throws ResourceNotFoundException, MasterCadreServiceException;

}

