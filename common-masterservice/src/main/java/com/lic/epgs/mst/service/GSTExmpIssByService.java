package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.GSTExmpIssBy;
import com.lic.epgs.mst.exceptionhandling.GSTServiceException;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;

public interface GSTExmpIssByService {

	List<GSTExmpIssBy> getAllGst() throws ResourceNotFoundException, GSTServiceException;

	public GSTExmpIssBy getGSTById(long gstId);

	public GSTExmpIssBy getGSTByCode(String gstCode);

}
