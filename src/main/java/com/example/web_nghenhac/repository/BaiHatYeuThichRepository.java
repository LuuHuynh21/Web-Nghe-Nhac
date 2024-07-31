package com.example.web_nghenhac.repository;

import com.example.web_nghenhac.entity.BaiHatYeuThich;
import com.example.web_nghenhac.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaiHatYeuThichRepository extends JpaRepository<BaiHatYeuThich,Long> {
    List<BaiHatYeuThich> findByNguoiDung(NguoiDung nguoiDung);
}
