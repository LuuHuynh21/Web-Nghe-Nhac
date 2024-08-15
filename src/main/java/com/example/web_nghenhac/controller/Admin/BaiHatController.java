package com.example.web_nghenhac.controller.Admin;

import com.example.web_nghenhac.Service.AlbumService;
import com.example.web_nghenhac.Service.BaiHatService;
import com.example.web_nghenhac.Service.NgheSiService;
import com.example.web_nghenhac.Service.TheLoaiService;
import com.example.web_nghenhac.entity.Album;
import com.example.web_nghenhac.entity.BaiHat;
import com.example.web_nghenhac.entity.NgheSi;
import com.example.web_nghenhac.entity.TheLoai;
import com.google.cloud.storage.Blob;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/bai-hat")
public class BaiHatController {
//test
    @Autowired
    private BaiHatService baiHatService;

    @Autowired
    private TheLoaiService theLoaiService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private NgheSiService ngheSiService;

    @GetMapping
    public List<BaiHat> getAll() {
        return baiHatService.getAll();
    }

    @GetMapping("/phan-trang")
    public Page<BaiHat> getAll(@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size) {
        Pageable pageable = PageRequest.of(page, size);
        return baiHatService.phanTrang(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BaiHat>> searchNgheSi(@RequestParam("ten") String ten){
        List<BaiHat> result = baiHatService.getByTen(ten);
        return ResponseEntity.ok(result);
    }



    @PutMapping("/luotnghe/{id}")
    public ResponseEntity<BaiHat> LuotNghe(@PathVariable Long id) {
        BaiHat updatedBaiHat = baiHatService.LuotNghe(id);
        if (updatedBaiHat != null) {
            return ResponseEntity.ok(updatedBaiHat);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Autowired
    private StorageClient storageClient;

    @Value("${app.firebase.bucket.name}")
    private String bucketName;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addBaiHat(
            @RequestParam("ma") String ma,
            @RequestParam("ten") String ten,
            @RequestParam("ngheSi") Long IdNgheSi,
            @RequestParam("theLoai") Long IdTheLoai,
            @RequestParam("album") Long IdAlbum,
            @RequestParam("thoiLuong") String thoiLuong,
            @RequestParam("url") MultipartFile fileMp3) throws IOException {

        NgheSi ngheSi = ngheSiService.getById(IdNgheSi);
        TheLoai theLoai = theLoaiService.getById(IdTheLoai);
        Album album = albumService.getById(IdAlbum);

        if (ngheSi == null || theLoai == null || album == null) {
            return ResponseEntity.notFound().build();
        }

        BaiHat baiHat = new BaiHat();
        baiHat.setMa(ma);
        baiHat.setTen(ten);
        baiHat.setTrangThai(true);
        baiHat.setNgheSi(ngheSi);
        baiHat.setTheLoai(theLoai);
        baiHat.setAlbum(album);
        baiHat.setThoiLuong(thoiLuong);
        baiHat.setNgayTao(new Date(System.currentTimeMillis()));

        try {
            if (!fileMp3.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + fileMp3.getOriginalFilename();
                InputStream inputStream = fileMp3.getInputStream();
                Blob blob = storageClient.bucket(bucketName).create(fileName, inputStream, fileMp3.getContentType());

                // Lấy URL tải xuống của file MP3
                String fileUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucketName, fileName);
                baiHat.setUrl(fileUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        BaiHat add = baiHatService.addBaiHat(baiHat);
        if (add != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(add);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<BaiHat> getDetail(@PathVariable("id") Long id) {
        BaiHat baiHat = baiHatService.getByID(id);
        if (baiHat != null) {
            return ResponseEntity.ok(baiHat);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaiHat> update(@PathVariable("id") Long id,
                                         @RequestParam("ma") String ma,
                                         @RequestParam("ten") String ten,
                                         @RequestParam("trangThai") Boolean trangThai,
                                         @RequestParam("thoiLuong") String thoiLuong,
                                         @RequestParam("ngheSi") Long idNgheSi,
                                         @RequestParam("theLoai") Long idTheLoai,
                                         @RequestParam("album") Long idAlbum,
                                         @RequestParam("url") MultipartFile fileMp3) {
        Optional<BaiHat> optionalBaiHat = baiHatService.findById(id);

        if (!optionalBaiHat.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Optional<NgheSi> optionalNgheSi = ngheSiService.findById(idNgheSi);
        Optional<TheLoai> optionalTheLoai = theLoaiService.findById(idTheLoai);
        Optional<Album> optionalAlbum = albumService.findById(idAlbum);

        if (!optionalNgheSi.isPresent() || !optionalTheLoai.isPresent() || !optionalAlbum.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        BaiHat baiHat = optionalBaiHat.get();
        NgheSi ngheSi = optionalNgheSi.get();
        TheLoai theLoai = optionalTheLoai.get();
        Album album = optionalAlbum.get();

        baiHat.setMa(ma);
        baiHat.setTen(ten);
        baiHat.setTrangThai(trangThai);
        baiHat.setThoiLuong(thoiLuong);
        baiHat.setNgheSi(ngheSi);
        baiHat.setTheLoai(theLoai);
        baiHat.setAlbum(album);
        baiHat.setNgaySua(new java.util.Date(System.currentTimeMillis()));

        try {
            if (!fileMp3.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + fileMp3.getOriginalFilename();
                InputStream inputStream = fileMp3.getInputStream();
                Blob blob = storageClient.bucket(bucketName).create(fileName, inputStream, fileMp3.getContentType());

                // Lấy URL tải xuống của file MP3
                String fileUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucketName, fileName);
                baiHat.setUrl(fileUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


        BaiHat updatedBaiHat = baiHatService.update(id,baiHat);
        if (updatedBaiHat != null) {
            return ResponseEntity.ok(updatedBaiHat);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
