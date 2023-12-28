package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.ModeTypeMst;

public interface ModeOfExitService {

	List<ModeTypeMst> getAllModeOfExit();

	public ModeTypeMst getModeOfExitById(long modeId);

	public ModeTypeMst getModeOfExitByCode(String code);
}
