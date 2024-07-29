package com.example.web_nghenhac.repository;

import com.example.web_nghenhac.entity.NgheSi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NgheSiRepository extends JpaRepository<NgheSi,Long> {
    Optional<NgheSi> findByTen(String ten);
}
