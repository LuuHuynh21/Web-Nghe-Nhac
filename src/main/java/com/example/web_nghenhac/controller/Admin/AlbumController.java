package com.example.web_nghenhac.controller.Admin;


import com.example.web_nghenhac.DTO.AlbumDTO;
import com.example.web_nghenhac.Service.AlbumService;
import com.example.web_nghenhac.Service.NgheSiService;
import com.example.web_nghenhac.entity.Album;
import com.example.web_nghenhac.entity.NgheSi;
import com.google.cloud.storage.Blob;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

//    @Value("${app.upload-dir}") // Đọc đường dẫn thư mục từ cấu hình
//    private String uploadDir;

    @GetMapping
    public List<Album> getAll() {
        return albumService.getAll();
    }

    @GetMapping("/{id}")
    public Album getById(@PathVariable("id")Long id){
        return albumService.getById(id);
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


}
