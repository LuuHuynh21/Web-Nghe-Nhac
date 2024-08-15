package com.example.web_nghenhac.repository;

import com.example.web_nghenhac.entity.Album;
import com.example.web_nghenhac.entity.TheLoai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album,Long> {
    @Query(value = "SELECT * FROM Album ab WHERE ab.Ten LIKE %:ten%",nativeQuery = true)
    List<Album> searchTen(String ten);

    @Query("SELECT COUNT(ab) FROM Album ab")
    Long tongAlbum();
    Page<Album> findAll(Pageable pageable);

    @Query("SELECT b FROM Album b WHERE b.trangThai = true")
    Page<Album> findByTrangThai(Pageable pageable);
}
