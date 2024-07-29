package com.example.web_nghenhac.repository;

import com.example.web_nghenhac.entity.TheLoai;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheLoaiRepository extends JpaRepository<TheLoai,Long> {
    TheLoai findByTen(String ten);
}
