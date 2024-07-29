package com.example.web_nghenhac.Service;

import com.example.web_nghenhac.entity.TheLoai;
import com.example.web_nghenhac.repository.TheLoaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheLoaiService {

    @Autowired
    private TheLoaiRepository theLoaiRepo;

    public List<TheLoai> getAll(){
        return theLoaiRepo.findAll();
    }

    public TheLoai getById(Long id){
        return theLoaiRepo.findById(id).orElse(null);
    }

    public TheLoai add(TheLoai theLoai){
        return theLoaiRepo.save(theLoai);
    }

    public void delete(Long id){
        theLoaiRepo.deleteById(id);
    }
    public TheLoai update(Long id, TheLoai theLoai){
        Optional<TheLoai> optional = theLoaiRepo.findById(id);
        return optional.map(o ->{
            o.setMa(theLoai.getMa());
            o.setTen(theLoai.getTen());
            o.setMoTa(theLoai.getMoTa());
            o.setNgaySua(theLoai.getNgaySua());
            return theLoaiRepo.save(o);
        }).orElse(null);
    }
    public TheLoai getByName(String ten) {
        return theLoaiRepo.findByTen(ten);
    }
}
