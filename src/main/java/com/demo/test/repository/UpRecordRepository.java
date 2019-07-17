package com.demo.test.repository;

import com.demo.test.domain.UpRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpRecordRepository extends JpaRepository<UpRecord,Long> {
}
