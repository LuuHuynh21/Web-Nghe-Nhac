package com.example.web_nghenhac.Service;

import com.example.web_nghenhac.entity.BaiHat;
import com.example.web_nghenhac.entity.BaiHatYeuThich;
import com.example.web_nghenhac.entity.NguoiDung;
import com.example.web_nghenhac.repository.BaiHatYeuThichRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BaiHatYeuThichService {

    @Autowired
    private BaiHatYeuThichRepository baiHatYeuThichRepo;

    public BaiHatYeuThich addBHYT(NguoiDung nguoiDung, BaiHat baiHat){
        BaiHatYeuThich baiHatYeuThich = new BaiHatYeuThich();
        baiHatYeuThich.setNguoiDung(nguoiDung);
        baiHatYeuThich.setBaiHat(baiHat);
        baiHatYeuThich.setNgayTao(new Date());
        return baiHatYeuThichRepo.save(baiHatYeuThich);
    }

    public List<BaiHat> getBaiHatByNguoiDung(NguoiDung nguoiDung) {
        return baiHatYeuThichRepo.findByNguoiDung(nguoiDung).stream()
                .map(BaiHatYeuThich::getBaiHat)
                .collect(Collectors.toList());
    }
}
