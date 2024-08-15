package com.example.web_nghenhac.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BaiHat")
@Entity
public class BaiHat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Ma")
    private String ma;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "TrangThai")
    private Boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "IdNgheSi")
    private NgheSi ngheSi;

    @ManyToOne
    @JoinColumn(name = "IdTheLoai")
    private TheLoai theLoai;

    @ManyToOne
    @JoinColumn(name = "IdAlbum")
    private Album album;

    @Column(name = "ThoiLuong")
    private String thoiLuong;

    @Column(name = "LuotNghe")
    private Integer luotNghe = 0;

    @Column(name = "URL")
    private String url;

    @Column(name = "NgayTao")
    private Date ngayTao;

    @Column(name = "NgaySua")
    private Date ngaySua;
}
