package com.example.web_nghenhac.controller.Admin;



import com.example.web_nghenhac.Service.AlbumService;
import com.example.web_nghenhac.Service.BaiHatService;
import com.example.web_nghenhac.Service.NgheSiService;
import com.example.web_nghenhac.entity.Album;
import com.example.web_nghenhac.entity.BaiHat;
import com.example.web_nghenhac.entity.NgheSi;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private NgheSiService ngheSiService;

    @Autowired
    private BaiHatService baiHatService;

//    @Value("${app.upload-dir}") // Đọc đường dẫn thư mục từ cấu hình
//    private String uploadDir;

    @GetMapping
    public List<Album> getAll(){
        return albumService.getAll();
    }

    @GetMapping("/phan-trang")
    public Page<Album> phanTrang(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "10")int size) {
        Pageable pageable = PageRequest.of(page,size);
        return albumService.phanTrang(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Album>> searchAlbum(@RequestParam("ten") String ten){
        List<Album> result = albumService.getByName(ten);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/tong-album")
    public ResponseEntity<Long> tongAlbum(){
        Long tongAB = albumService.tongAB();
        return ResponseEntity.ok(tongAB);
    }

    @GetMapping("/{id}")
    public List<BaiHat> getSongsByAlbum(@PathVariable Long id) {
        return baiHatService.getBaiHatsByAlbum(id);
    }
    
    @Autowired
    private StorageClient storageClient;

    @Value("${app.firebase.bucket.name}")
    private String bucketName;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addAlbum(@RequestParam("ma") String ma,
                                      @RequestParam("ten") String ten,
                                      @RequestParam("ngheSi") Long ngheSiId,
                                      @RequestParam("hinhAnh") MultipartFile hinhAnh) throws IOException {
        NgheSi ngheSi = ngheSiService.getById(ngheSiId);
        if (ngheSi == null) {
            return ResponseEntity.notFound().build();
        }

        Album album = new Album();
        album.setMa(ma);
        album.setTen(ten);
        album.setNgheSi(ngheSi);
        album.setNgayTao(new Date(System.currentTimeMillis()));

        try {
            if (!hinhAnh.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + hinhAnh.getOriginalFilename();
                InputStream inputStream = hinhAnh.getInputStream();
                Blob blob = storageClient.bucket(bucketName).create(fileName, inputStream, hinhAnh.getContentType());

                // Lấy URL tải xuống của hình ảnh
                String imageUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucketName, fileName);
                album.setHinhAnh(imageUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Album addedAlbum = albumService.add(album);
        if (addedAlbum != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(addedAlbum);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Album> getDetail(@PathVariable("id") Long id) {
        Album album = albumService.getById(id);
        if (album != null) {
            return ResponseEntity.ok(album);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Album> update(@PathVariable("id") Long id,
                                        @RequestParam("ma") String ma,
                                        @RequestParam("ten") String ten,
                                        @RequestParam("ngheSi") Long idNgheSi,
                                        @RequestParam(value = "hinhAnh", required = false) MultipartFile hinhAnh) {
        Optional<Album> optionalAlbum = albumService.findById(id);

        if (!optionalAlbum.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Optional<NgheSi> optionalNgheSi = ngheSiService.findById(idNgheSi);

        if (!optionalNgheSi.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Album album = optionalAlbum.get();
        NgheSi ngheSi = optionalNgheSi.get();

        album.setMa(ma);
        album.setTen(ten);
        album.setNgheSi(ngheSi);
        album.setNgaySua(new Date(System.currentTimeMillis()));

        try {
            if (hinhAnh != null && !hinhAnh.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + hinhAnh.getOriginalFilename();
                InputStream inputStream = hinhAnh.getInputStream();
                Blob blob = storageClient.bucket(bucketName).create(fileName, inputStream, hinhAnh.getContentType());

                // Get the download URL of the image
                String imageUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucketName, fileName);
                album.setHinhAnh(imageUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Album updatedAlbum = albumService.update(id, album);
        if (updatedAlbum != null) {
            return ResponseEntity.ok(updatedAlbum);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
