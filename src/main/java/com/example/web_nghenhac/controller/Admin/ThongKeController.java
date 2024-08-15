package com.example.web_nghenhac.controller.Admin;

import com.example.web_nghenhac.Service.AlbumService;
import com.example.web_nghenhac.Service.BaiHatService;
import com.example.web_nghenhac.Service.NgheSiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/thong-ke")
public class ThongKeController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private NgheSiService ngheSiService;

    @Autowired
    private BaiHatService baiHatService;

    @GetMapping("/tong-album")
    public ResponseEntity<Long> tongAlbum(){
        Long tongAB = albumService.tongAB();
        return ResponseEntity.ok(tongAB);
    }

    @GetMapping("/tong-nghe-si")
    public ResponseEntity<Long> tongNgheSi(){
        Long tongNS = ngheSiService.tongNgheSi();
        return ResponseEntity.ok(tongNS);
    }

    @GetMapping("/tong-luot-nghe")
    public ResponseEntity<Long> tongLuotNghe(){
        Long tongLuotNghe = baiHatService.getTongBaiHat();
        return ResponseEntity.ok(tongLuotNghe);
    }
}
