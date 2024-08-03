package com.example.web_nghenhac.repository;

import com.example.web_nghenhac.entity.Album;
import com.example.web_nghenhac.entity.BaiHat;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BaiHatRepository extends JpaRepository<BaiHat,Long> {

    @Query(value = "SELECT * FROM BaiHat bh WHERE bh.Ten LIKE %:ten%",nativeQuery = true)
    List<BaiHat> searchTen(String ten);

    @Query("SELECT SUM(bh.luotNghe) FROM BaiHat bh")
    Long TongLuotNghe();

    List<BaiHat> findByAlbumId(Long id);

    List<BaiHat> findByTheLoaiId(Long theLoaiId);

    Page<BaiHat> findAll(Pageable pageable);
}
