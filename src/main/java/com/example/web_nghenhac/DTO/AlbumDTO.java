package com.example.web_nghenhac.DTO;

import lombok.Data;


import java.util.Date;
import java.util.List;

@Data
public class AlbumDTO {

    private Long id;
    private String ma;
    private String ten;
    private String ngheSiTen;
    private Long ngheSiId;
    private String hinhAnh;
    private Date ngayTao;
    private Date ngaySua;
    private List<BaiHatDTO> baiHats;
}
