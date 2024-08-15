package com.example.web_nghenhac.repository;

import com.example.web_nghenhac.entity.TheLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TheLoaiRepository extends JpaRepository<TheLoai,Long> {
    @Query(value = "SELECT * FROM TheLoai tl WHERE tl.Ten LIKE %:ten%",nativeQuery = true)
    List<TheLoai> searchTen(String ten);

    Page<TheLoai> findAll(Pageable pageable);

    @Query("SELECT b FROM TheLoai b WHERE b.trangThai = true")
    Page<TheLoai> findByTrangThai(Pageable pageable);

}
