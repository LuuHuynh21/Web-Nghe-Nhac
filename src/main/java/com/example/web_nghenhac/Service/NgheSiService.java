package com.example.web_nghenhac.Service;

import com.example.web_nghenhac.entity.NgheSi;
import com.example.web_nghenhac.repository.NgheSiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NgheSiService {

    @Autowired
    private NgheSiRepository ngheSiRepo;

    public List<NgheSi> getAll(){
        return ngheSiRepo.findAll();
    }

    public NgheSi addNS(NgheSi ngheSi){
        return ngheSiRepo.save(ngheSi);
    }

    public NgheSi getById(Long id){
        return ngheSiRepo.findById(id).orElse(null);
    }

    public void delete(Long id){
        ngheSiRepo.deleteById(id);
    }
    public NgheSi update(Long id , NgheSi ngheSi){
        Optional<NgheSi> optional = ngheSiRepo.findById(id);
        return optional.map(o ->{
            o.setMa(ngheSi.getMa());
            o.setTen(ngheSi.getTen());
            o.setMoTa(ngheSi.getMoTa());
            o.setHinhAnh(ngheSi.getHinhAnh());
            o.setNgayTao(ngheSi.getNgayTao());
            o.setNgaySua(ngheSi.getNgaySua());
            return ngheSiRepo.save(ngheSi);
        }).orElse(null);
    }
    public Optional<NgheSi> getByTen(String ten) {
        return ngheSiRepo.findByTen(ten);
    }
}

