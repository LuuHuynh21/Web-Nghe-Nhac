package com.example.web_nghenhac.DTO;

import com.example.web_nghenhac.entity.Album;
import com.example.web_nghenhac.entity.BaiHat;

import java.util.List;

public class AlbumBaiHat {
    private Album album;
    private List<BaiHat> baiHats;

    public AlbumBaiHat(Album album, List<BaiHat> baiHats) {
        this.album = album;
        this.baiHats = baiHats;
    }

    // Getters and setters
    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<BaiHat> getBaiHats() {
        return baiHats;
    }

    public void setBaiHats(List<BaiHat> baiHats) {
        this.baiHats = baiHats;
    }
}
