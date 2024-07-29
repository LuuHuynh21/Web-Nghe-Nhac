package com.example.web_nghenhac.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "NguoiDung")
public class NguoiDung{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Ma")
    private String ma;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "Email")
    private String email;

    @Column(name = "MatKhau")
    private String matKhau;

    @Column(name = "HoatDong")
    private Boolean hoatDong;

    @Column(name = "NgaySinh")
    private Date ngaySinh;

    @Column(name = "GioiTinh")
    private String gioiTinh;

    @Column(name = "NgayDangKi")
    private Date ngayDangKi;

    @Column(name = "NgayTao")
    private Date ngayTao;

    @Column(name = "NgaySua")
    private Date ngaySua;

    @ManyToMany
    @JoinTable(name = "NguoiDungVaiTro",
              joinColumns = @JoinColumn(name = "IdNguoiDung"),
              inverseJoinColumns = @JoinColumn(name = "IdVaiTro"))
    private Set<VaiTro> vaiTro;

}
