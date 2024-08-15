package com.example.web_nghenhac.Service;

import com.example.web_nghenhac.entity.Album;
import com.example.web_nghenhac.entity.BaiHat;
import com.example.web_nghenhac.entity.NgheSi;
import com.example.web_nghenhac.repository.AlbumRepository;
import com.example.web_nghenhac.repository.BaiHatRepository;
import com.example.web_nghenhac.repository.NgheSiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TimKiemService {

    @Autowired
    private BaiHatRepository baiHatRepository;

    @Autowired
    private NgheSiRepository ngheSiRepository;

    @Autowired
    private AlbumRepository albumRepository;

    public List<Object> search(String ten) {
        List<BaiHat> baiHats = baiHatRepository.searchTen(ten);
        List<NgheSi> ngheSis = ngheSiRepository.searchTen(ten);
        List<Album> albums = albumRepository.searchTen(ten);

        return Stream.of(baiHats, ngheSis, albums)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
