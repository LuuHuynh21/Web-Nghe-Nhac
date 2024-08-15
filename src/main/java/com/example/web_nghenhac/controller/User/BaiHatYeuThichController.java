package com.example.web_nghenhac.controller.User;

import com.example.web_nghenhac.Service.BaiHatService;
import com.example.web_nghenhac.Service.BaiHatYeuThichService;
import com.example.web_nghenhac.Service.NguoiDungService;
import com.example.web_nghenhac.entity.BaiHat;
import com.example.web_nghenhac.entity.BaiHatYeuThich;
import com.example.web_nghenhac.entity.NguoiDung;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bhyt")
public class BaiHatYeuThichController {

    @Autowired
    private BaiHatYeuThichService baiHatYeuThichService;

    @Autowired
    private BaiHatService baiHatService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping
    public ResponseEntity<List<BaiHat>> getFavoriteSongs(@RequestParam Long IdNguoiDung) {
        NguoiDung nguoiDung = nguoiDungService.getById(IdNguoiDung);

        if (nguoiDung == null) {
            return ResponseEntity.notFound().build();
        }
        List<BaiHat> favoriteSongs = baiHatYeuThichService.getBaiHatByNguoiDung(nguoiDung);
        return ResponseEntity.ok(favoriteSongs);
    }

    @PostMapping
    public ResponseEntity<BaiHatYeuThich> addBH(@RequestParam Long IdNguoiDung ,
                                                @RequestParam Long IdBaiHat){
        NguoiDung nguoiDung = nguoiDungService.getById(IdNguoiDung);
        BaiHat baiHat = baiHatService.getByID(IdBaiHat);
        if (nguoiDung == null || baiHat == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BaiHatYeuThich baiHatYeuThich = baiHatYeuThichService.addBHYT(nguoiDung, baiHat);
        return new ResponseEntity<>(baiHatYeuThich, HttpStatus.CREATED);
    }
}
