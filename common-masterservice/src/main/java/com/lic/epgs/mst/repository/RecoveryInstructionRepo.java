package com.lic.epgs.mst.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lic.epgs.mst.entity.RecoveryInstruction;


@Repository
public interface RecoveryInstructionRepo extends JpaRepository<RecoveryInstruction, Long> {
	Optional<RecoveryInstruction> findByRecoveryCode(String code);
}

