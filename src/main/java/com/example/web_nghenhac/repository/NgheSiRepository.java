package com.example.web_nghenhac.repository;

import com.example.web_nghenhac.entity.NgheSi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NgheSiRepository extends JpaRepository<NgheSi,Long> {
    @Query(value = "SELECT * FROM NgheSi ns WHERE ns.Ten LIKE %:ten%",nativeQuery = true)
    List<NgheSi> searchTen(String ten);

    @Query("SELECT COUNT(ns) FROM NgheSi ns")
    Long tongNgheSi();
    Page<NgheSi> findAll(Pageable pageable);
}
