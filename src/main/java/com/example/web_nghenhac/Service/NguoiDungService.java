package com.example.web_nghenhac.Service;

import com.example.web_nghenhac.entity.NguoiDung;
import com.example.web_nghenhac.entity.VaiTro;
import com.example.web_nghenhac.repository.NguoiDungRepository;
import com.example.web_nghenhac.repository.VaiTroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NguoiDungService {

    @Autowired
    private NguoiDungRepository nguoiDungRepo;

    @Autowired
    private VaiTroRepository vaiTroRepo;

    public Optional<NguoiDung> findByEmail(String email){
        return nguoiDungRepo.findByEmail(email);
    }

    public NguoiDung saveNguoiDung (NguoiDung nguoiDung){
        return nguoiDungRepo.save(nguoiDung);
    }

    public VaiTro saveVaiTro(VaiTro vaiTro){
        return vaiTroRepo.save(vaiTro);
    }

    public NguoiDung getById(Long id){
        return nguoiDungRepo.findById(id).get();
    }
}
