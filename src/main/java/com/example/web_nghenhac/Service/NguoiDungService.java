package com.example.web_nghenhac.Service;

import com.example.web_nghenhac.entity.NguoiDung;
import com.example.web_nghenhac.entity.VaiTro;
import com.example.web_nghenhac.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NguoiDungService {

    @Autowired
    private NguoiDungRepository nguoiDungRepo;



    public Optional<NguoiDung> findByEmail(String email){
        return nguoiDungRepo.findByEmail(email);
    }

    public List<NguoiDung> getAll(){
        return nguoiDungRepo.findAll();
    }

    public NguoiDung saveNguoiDung (NguoiDung nguoiDung){
        return nguoiDungRepo.save(nguoiDung);
    }


    public NguoiDung getById(Long id){
        return nguoiDungRepo.findById(id).orElse(null);
    }

    public Optional<NguoiDung> findById(Long id){
        return nguoiDungRepo.findById(id);
    }

    public NguoiDung update(Long id , NguoiDung nguoiDung){
        Optional<NguoiDung> optional = nguoiDungRepo.findById(id);
        return optional.map(o ->{
            o.setMa(nguoiDung.getMa());
            o.setTen(nguoiDung.getTen());
            o.setEmail(nguoiDung.getEmail());
            o.setMatKhau(nguoiDung.getMatKhau());
            o.setTrangThai(nguoiDung.getTrangThai());
            o.setNgaySinh(nguoiDung.getNgaySinh());
            o.setGioiTinh(nguoiDung.getGioiTinh());
            o.setNgaySua(nguoiDung.getNgaySua());
            o.setVaiTro(nguoiDung.getVaiTro());
            return nguoiDungRepo.save(nguoiDung);
        }).orElse(null);
    }

    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return nguoiDungRepo.findByEmail(username)
                        .orElseThrow(()-> new UsernameNotFoundException("User not found"));
            }
        };
    }

}
