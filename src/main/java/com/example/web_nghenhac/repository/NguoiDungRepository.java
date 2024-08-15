package com.example.web_nghenhac.repository;

import com.example.web_nghenhac.entity.NguoiDung;
import com.example.web_nghenhac.entity.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NguoiDungRepository extends JpaRepository<NguoiDung,Long> {
    Optional<NguoiDung> findByEmail(String email);

    NguoiDung findByVaiTro(VaiTro vaiTro);
}
