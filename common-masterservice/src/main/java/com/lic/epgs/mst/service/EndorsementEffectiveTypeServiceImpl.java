package com.lic.epgs.mst.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lic.epgs.mst.entity.EndorsementEffectiveType;
import com.lic.epgs.mst.exceptionhandling.LoggingUtil;
import com.lic.epgs.mst.exceptionhandling.ResourceNotFoundException;
import com.lic.epgs.mst.repository.EndorsementEffectiveTypeRepository;

@Service
@Transactional
public class EndorsementEffectiveTypeServiceImpl implements EndorsementEffectiveTypeService {
	@Autowired
	EndorsementEffectiveTypeRepository endorsementEffectiveTypeRepository;
	String className = this.getClass().getSimpleName();

	@Override
	public List<EndorsementEffectiveType> getAllEndorsementEffectiveType() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		LoggingUtil.logInfo(className, methodName, "Started");
		return endorsementEffectiveTypeRepository.findAll();
	}

	@Override
	public EndorsementEffectiveType getEndorsementEffectiveTypeById(long EndorsementETId) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Optional<EndorsementEffectiveType> EndorsementEffectiveTypeDb = this.endorsementEffectiveTypeRepository
				.findById(EndorsementETId);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName, "Search for EndorsementET type By Id" + EndorsementETId);
		if (EndorsementEffectiveTypeDb.isPresent()) {
			return EndorsementEffectiveTypeDb.get();
		} else {
			throw new ResourceNotFoundException("EndorsementET type not found with id:" + EndorsementETId);
		}
	}

	@Override
	public EndorsementEffectiveType findByEndorsementETCode(String EndorsementETCode) {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Object endorsementetDb = this.endorsementEffectiveTypeRepository.findByEndorsementETCode(EndorsementETCode);
		LoggingUtil.logInfo(className, methodName, "Started");
		LoggingUtil.logInfo(className, methodName,
				"Search EndorsementET details By endorsementetCode:" + EndorsementETCode);

		if (endorsementetDb != null) {

			return (EndorsementEffectiveType) endorsementetDb;
		} else {
			throw new ResourceNotFoundException("EndorsementET type code not found:" + EndorsementETCode);
		}
	}

}
