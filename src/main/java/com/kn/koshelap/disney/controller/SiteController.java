package com.kn.koshelap.disney.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kn.koshelap.disney.dto.search.SiteSearchDto;
import com.kn.koshelap.disney.dts.SiteDts;
import com.kn.koshelap.disney.service.SiteService;

@RestController
@RequestMapping("/api")
public class SiteController {

    private final SiteService service;

    public SiteController(SiteService service) {
        this.service = service;
    }

    @DeleteMapping("/site/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/site")
    public ResponseEntity<?> save(@RequestBody SiteDts siteDts) {
        return new ResponseEntity<>(service.save(siteDts), HttpStatus.OK);
    }

    @GetMapping("/site/all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping("/site/search")
    public ResponseEntity<?> find(@RequestBody SiteSearchDto searchDto) {
        return new ResponseEntity<>(service.find(searchDto), HttpStatus.OK);
    }

}
