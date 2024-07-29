package com.example.web_nghenhac.DTO;

import lombok.Data;

import java.util.Date;
import java.time.LocalTime;

@Data
public class BaiHatDTO {
    private Long id;
    private String ma;
    private String ten;
    private String ngheSiTen;
    private String ngheSiHinhAnh;
    private String theLoaiTen;
    private String albumTen;
    private String thoiLuong;

    private String url;
    private Date ngayTao;
    private Date ngaySua;
}
