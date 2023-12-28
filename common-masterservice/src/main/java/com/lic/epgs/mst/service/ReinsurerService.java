package com.lic.epgs.mst.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lic.epgs.mst.entity.ReinsurerType;

@Transactional
@Service
public interface ReinsurerService {

	  public ReinsurerType addReinsurerType(ReinsurerType ReinsurerType);
	  
	  //public ReinsurerType getReinsurerTypeById(long reinsurerId);
	  
	  List<ReinsurerType> getAllReunsurer();
	  
	  public ReinsurerType searchReinsurerTypeByCode(String reinsurerCode);

	public ReinsurerType searchReinsurerTypeById(Long reinsurerId);
	  
	  //public ReinsurerType getAllReinsurer(Long id);
	  
	 

}
