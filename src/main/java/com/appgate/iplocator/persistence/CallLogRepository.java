package com.appgate.iplocator.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appgate.iplocator.dto.CallLog;

public interface CallLogRepository extends JpaRepository<CallLog, Long>{

}
