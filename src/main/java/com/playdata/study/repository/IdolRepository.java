package com.playdata.study.repository;

import com.playdata.study.entity.Idol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdolRepository extends JpaRepository<Idol, Long> {
}
