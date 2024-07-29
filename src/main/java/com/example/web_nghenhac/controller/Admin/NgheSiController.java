package com.example.web_nghenhac.controller.Admin;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/nghe-si")
public class NgheSiController {

    @Autowired
    private NgheSiService ngheSiService;

    @Value("${app.upload-dir}") // Đọc đường dẫn thư mục từ cấu hình
    private String uploadDir;

    @GetMapping
    public List<NgheSi> getAll(){
        return ngheSiService.getAll();
    }

    @Autowired
    private StorageClient storageClient;

    @Value("${app.firebase.bucket.name}")
    private String bucketName;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addNgheSi(@RequestParam("ma") String ma,
                                       @RequestParam("ten") String ten,
                                       @RequestParam("moTa") String moTa,
                                       @RequestParam("hinhAnh") MultipartFile hinhAnh) throws IOException {
        NgheSi ngheSi = new NgheSi();
        ngheSi.setMa(ma);
        ngheSi.setTen(ten);
        ngheSi.setMoTa(moTa);
        ngheSi.setNgayTao(new Date(System.currentTimeMillis()));

        try {
            if (!hinhAnh.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + hinhAnh.getOriginalFilename();
                InputStream inputStream = hinhAnh.getInputStream();
                Blob blob = storageClient.bucket(bucketName).create(fileName, inputStream, hinhAnh.getContentType());

                // Lấy URL tải xuống của hình ảnh
                String imageUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucketName, fileName);
                ngheSi.setHinhAnh(imageUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        NgheSi addNS = ngheSiService.addNS(ngheSi);
        if (addNS != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(addNS);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
