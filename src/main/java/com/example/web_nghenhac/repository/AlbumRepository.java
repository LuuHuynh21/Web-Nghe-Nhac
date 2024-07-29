package com.example.web_nghenhac.repository;

import com.example.web_nghenhac.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album,Long> {
    Album findByTen(String ten);
}
