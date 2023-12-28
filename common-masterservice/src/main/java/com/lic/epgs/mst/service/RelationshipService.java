package com.lic.epgs.mst.service;

import java.util.List;

import com.lic.epgs.mst.entity.RelationshipMst;

public interface RelationshipService {

	List<RelationshipMst> getAllRelationship();

	public RelationshipMst getRelationshipById(long relationshipId);

	public RelationshipMst findByRelationshipCode(String relationshipcode);

}
