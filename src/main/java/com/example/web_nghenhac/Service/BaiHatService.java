package com.example.web_nghenhac.Service;

import com.example.web_nghenhac.DTO.BaiHatDTO;
import com.example.web_nghenhac.entity.BaiHat;
import com.example.web_nghenhac.repository.BaiHatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BaiHatService {

    @Autowired
    private BaiHatRepository baiHatRepo;

    public Page<BaiHat> phanTrang(Pageable pageable){
        return baiHatRepo.findAll(pageable);
    }

    public List<BaiHat> getAll() {
        return   baiHatRepo.findAll();
//        return bh.stream().map(this::baiHatDTO).collect(Collectors.toList());
    }
//    private BaiHatDTO baiHatDTO(BaiHat baiHat){
//        BaiHatDTO dto = new BaiHatDTO();
//        dto.setId(baiHat.getId());
//        dto.setMa(baiHat.getMa());
//        dto.setTen(baiHat.getTen());
//        dto.setThoiLuong(baiHat.getThoiLuong());
//        dto.setUrl(baiHat.getUrl());
////        dto.setNgayPhatHanh(baiHat.getNgayPhatHanh());
//        dto.setNgayTao(baiHat.getNgayTao());
//        dto.setNgaySua(baiHat.getNgaySua());
//        dto.setNgheSiTen(baiHat.getNgheSi().getTen());
//        dto.setNgheSiHinhAnh(baiHat.getNgheSi().getHinhAnh());
//        dto.setTheLoaiTen(baiHat.getTheLoai().getTen());
//        dto.setAlbumTen(baiHat.getAlbum().getTen());
//        return dto;
//    }

    public BaiHat addBaiHat(BaiHat baiHat) {
        return baiHatRepo.save(baiHat);
    }

    public BaiHat getByID(Long id) {
        return baiHatRepo.findById(id).orElse(null);
    }

    public void delete(Long id){
        baiHatRepo.deleteById(id);
    }

    public BaiHat update(Long id , BaiHat baiHat){
        Optional<BaiHat> optional = baiHatRepo.findById(id);
        return optional.map(s -> {
            s.setMa(baiHat.getMa());
            s.setTen(baiHat.getTen());
            s.setThoiLuong(baiHat.getThoiLuong());
//            s.setNgayPhatHanh(baiHat.getNgayPhatHanh());
            s.setUrl(baiHat.getUrl());
            s.setNgayTao(baiHat.getNgayTao());
            s.setNgaySua(baiHat.getNgaySua());
            s.setNgheSi(baiHat.getNgheSi());
            s.setTheLoai(baiHat.getTheLoai());
            s.setAlbum(baiHat.getAlbum());
            return baiHatRepo.save(baiHat);
        }).orElse(null);
    }
    public List<BaiHat> getBaiHatsByAlbum(Long id) {
        return baiHatRepo.findByAlbumId(id);
    }

    public BaiHat LuotNghe(Long id) {
        Optional<BaiHat> optionalBaiHat = baiHatRepo.findById(id);
        if (optionalBaiHat.isPresent()) {
            BaiHat baiHat = optionalBaiHat.get();
            baiHat.setLuotNghe(baiHat.getLuotNghe() + 1);
            return baiHatRepo.save(baiHat);
        }
        return null;
    }

    public List<BaiHat> getByTen( String ten) {
        return baiHatRepo.searchTen(ten);
    }

    public Long getTongBaiHat(){
        return baiHatRepo.TongLuotNghe();
    }
}
