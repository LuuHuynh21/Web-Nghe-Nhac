//package com.example.web_nghenhac.controller.Admin;
//
//import com.example.web_nghenhac.Service.NguoiDungService;
//import com.example.web_nghenhac.entity.NguoiDung;
//import com.example.web_nghenhac.entity.VaiTro;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/nguoi-dung")
//public class NguoiDungController {
//
//    @Autowired
//    private NguoiDungService nguoiDungService;
//
//
//    @GetMapping
//    public List<NguoiDung> getAll(){
//        return nguoiDungService.getAll();
//    }
//
//    @PostMapping
//    public ResponseEntity<NguoiDung> addND(@RequestParam("ma") String ma,
//                                           @RequestParam("ten") String ten,
//                                           @RequestParam("email") String email,
//                                           @RequestParam("matKhau") String matKhau,
//                                           @RequestParam("ngaySinh") String ngaySinh,
//                                           @RequestParam("gioiTinh") String gioiTinh,
//                                           @RequestParam("vaiTro") Long idVaiTro) {
//
//        VaiTro vaiTro = vaiTroService.getById(idVaiTro);
//        if (vaiTro == null) {
//            return ResponseEntity.notFound().build();
//        }
//        NguoiDung nguoiDung = new NguoiDung();
//        nguoiDung.setMa(ma);
//        nguoiDung.setTen(ten);
//        nguoiDung.setEmail(email);
//        nguoiDung.setMatKhau(matKhau);
//        nguoiDung.setNgaySinh(ngaySinh);
//        nguoiDung.setGioiTinh(gioiTinh);
//        nguoiDung.setTrangThai(true);
//        nguoiDung.setNgayTao(new Date());
//        nguoiDung.setVaiTro(vaiTro);
//        NguoiDung addNguoiDung = nguoiDungService.saveNguoiDung(nguoiDung);
//        return ResponseEntity.status(HttpStatus.CREATED).body(addNguoiDung);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<NguoiDung> getDetail(@PathVariable Long id){
//        NguoiDung nguoiDung = nguoiDungService.getById(id);
//        if(nguoiDung != null){
//            return ResponseEntity.ok(nguoiDung);
//        }else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }
//
//    @PutMapping(value = "/{id}")
//    public ResponseEntity<NguoiDung> updateND(@PathVariable("id") Long id,
//                                              @RequestParam("ma") String ma,
//                                              @RequestParam("ten") String ten,
//                                              @RequestParam("email") String email,
//                                              @RequestParam("trangThai") Boolean trangThai,
//                                              @RequestParam("matKhau") String matKhau,
//                                              @RequestParam("ngaySinh") String ngaySinh,
//                                              @RequestParam("gioiTinh") String gioiTinh,
//                                              @RequestParam("vaiTro") Long idVaiTro) {
//
//        Optional<NguoiDung> opNguoiDung = nguoiDungService.findById(id);
//
//        if (!opNguoiDung.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//
//        Optional<VaiTro> opVaiTro = vaiTroService.findById(idVaiTro);
//
//        if (!opVaiTro.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//        NguoiDung nguoiDung = opNguoiDung.get();
//        VaiTro vaiTro = opVaiTro.get();
//        nguoiDung.setMa(ma);
//        nguoiDung.setTen(ten);
//        nguoiDung.setEmail(email);
//        nguoiDung.setMatKhau(matKhau);
//        nguoiDung.setNgaySinh(ngaySinh);
//        nguoiDung.setGioiTinh(gioiTinh);
//        nguoiDung.setTrangThai(trangThai);
//        nguoiDung.setNgaySua(new Date());
//        nguoiDung.setVaiTro(vaiTro);
//        NguoiDung updatedND = nguoiDungService.update(id, nguoiDung);
//        if (updatedND != null) {
//            return ResponseEntity.ok(updatedND);
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//}
