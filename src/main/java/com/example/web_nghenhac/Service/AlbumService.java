package com.example.web_nghenhac.Service;

import com.example.web_nghenhac.DTO.AlbumDTO;
import com.example.web_nghenhac.entity.Album;
import com.example.web_nghenhac.entity.NgheSi;
import com.example.web_nghenhac.repository.AlbumRepository;
import com.example.web_nghenhac.repository.NgheSiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepo;

    @Autowired
    private NgheSiRepository ngheSiRepo;

    public List<Album> getAll(){
        return albumRepo.findAll();
    }

    public Page<Album> phanTrang(Pageable pageable){
        return albumRepo.findAll(pageable);
    }

    public Page<Album> hienThiABTrangThai(Pageable pageable){
        return albumRepo.findByTrangThai(pageable);
    }

    public  Album add(Album album){
        return albumRepo.save(album);
    }

    public Album getById(Long id){
        return albumRepo.findById(id).orElse(null);
    }
    public void delete(Long id){
        albumRepo.deleteById(id);
    }
    public Album update(Long id , Album album){
        Optional<Album> optional = albumRepo.findById(id);
        return optional.map(o ->{
            o.setMa(album.getMa());
            o.setTen(album.getTen());
            o.setTrangThai(album.getTrangThai());
            o.setNgheSi(album.getNgheSi());
            o.setHinhAnh(album.getHinhAnh());
            o.setNgayTao(album.getNgayTao());
            o.setNgaySua(album.getNgaySua());
            return albumRepo.save(album);
        }).orElse(null);
    }
    public List<Album> getByName(String ten) {
        return albumRepo.searchTen(ten);
    }

    public Long tongAB(){
        return albumRepo.tongAlbum();
    }
    public Optional<Album> findById(Long id) {
        return albumRepo.findById(id);
    }
}
