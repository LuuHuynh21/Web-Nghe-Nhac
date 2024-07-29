package com.example.web_nghenhac.controller.Admin;


import com.example.web_nghenhac.Service.TheLoaiService;
import com.example.web_nghenhac.entity.TheLoai;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/the-loai")
public class TheLoaiController {

    @Autowired
    private TheLoaiService theLoaiService;

    @GetMapping
    public List<TheLoai> getAll(){
        return theLoaiService.getAll();
    }

    @PostMapping("")
    public ResponseEntity<TheLoai> createTL(@RequestBody TheLoai theLoai){
        TheLoai createTL = theLoaiService.add(theLoai);
        return ResponseEntity.status(HttpStatus.CREATED).body(createTL);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TheLoai> update(@PathVariable("id")Long id, @RequestBody TheLoai theLoai){
        TheLoai update = theLoaiService.update(id, theLoai);
        return ResponseEntity.ok(update);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<TheLoai> delete(@PathVariable("id")Long id){
        TheLoai theLoai = theLoaiService.getById(id);
        if(theLoai != null){
            theLoaiService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
