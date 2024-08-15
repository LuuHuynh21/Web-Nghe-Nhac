package com.example.web_nghenhac.controller.User;

import com.example.web_nghenhac.Service.AlbumService;
import com.example.web_nghenhac.Service.BaiHatService;
import com.example.web_nghenhac.Service.TheLoaiService;
import com.example.web_nghenhac.Service.TimKiemService;
import com.example.web_nghenhac.entity.Album;
import com.example.web_nghenhac.entity.BaiHat;
import com.example.web_nghenhac.entity.TheLoai;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    private BaiHatService baiHatService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private TheLoaiService theLoaiService;

    @Autowired
    private TimKiemService timKiemService;

    @GetMapping("/search")
    public List<Object> search(@RequestParam String ten) {
        return timKiemService.search(ten);
    }

    @GetMapping("/bai-hat")
    public Page<BaiHat> hienThiTheoLuotNghe(@RequestParam(defaultValue = "0")int page ,
                                            @RequestParam(defaultValue = "100")int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"luotNghe"));
        return baiHatService.hienThiTheoTrangThai(pageable);
    }
    @GetMapping("/bai-hat/random")
    public Page<BaiHat> hienThiNgauNhien(@RequestParam(defaultValue = "0")int page ,
                                            @RequestParam(defaultValue = "100")int size){
        Pageable pageable = PageRequest.of(page, size);
        return baiHatService.findBaiHatsRandom(pageable);
    }

    @GetMapping("/album/{id}")
    public List<BaiHat> getSongsByAlbum(@PathVariable Long id) {
        return baiHatService.getBaiHatsByAlbum(id);
    }

    @GetMapping("/album")
    public Page<Album> hienThiTheoTrangThai(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10")int size) {
        Pageable pageable = PageRequest.of(page,size);
        return albumService.hienThiABTrangThai(pageable);
    }


    @GetMapping("/the-loai/{theLoaiId}")
    public List<BaiHat> getBaiHatsByTheLoai(@PathVariable Long theLoaiId) {
        return baiHatService.getBaiHatsByTheLoai(theLoaiId);
    }
    @GetMapping("/the-loai")
    public Page<TheLoai> findByTL(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10")int size) {
        Pageable pageable = PageRequest.of(page,size);
        return theLoaiService.findByTrangThai(pageable);
    }
}
