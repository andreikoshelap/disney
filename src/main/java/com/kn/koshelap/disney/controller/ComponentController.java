package com.kn.koshelap.disney.controller;

import java.util.Optional;

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

import com.kn.koshelap.disney.domain.Component;
import com.kn.koshelap.disney.domain.Site;
import com.kn.koshelap.disney.dto.SiteDto;
import com.kn.koshelap.disney.dts.ComponentDts;
import com.kn.koshelap.disney.repository.ComponentRepository;
import com.kn.koshelap.disney.repository.SiteRepository;
import com.kn.koshelap.disney.repository.VisitorRepository;
import com.kn.koshelap.disney.service.ComponentService;

@RestController
@RequestMapping("/api")
public class ComponentController {

    private ComponentService service;
    @Autowired
    SiteRepository siteRepository;
    @Autowired
    ComponentRepository componentRepository;
    @Autowired
    VisitorRepository visitorRepository;

    public ComponentController(ComponentService service) {
        this.service = service;
    }

    @DeleteMapping("/component/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/component")
    public ResponseEntity<?> save(@RequestBody ComponentDts componentDts) {
        return new ResponseEntity<>(service.save(componentDts), HttpStatus.OK);
    }

    @PutMapping("/site/component/{id}")
    public ResponseEntity<Site> addComponentToSite(@PathVariable("id") Long id, @RequestBody SiteDto siteDto) {
        Optional<Component> component = componentRepository.findById(id);
        Optional<Site> site = siteRepository.findByName(siteDto.getName());

        if (component.isPresent() && site.isPresent()){
            component.get().setSite(site.get());
            componentRepository.save(component.get());
            return new ResponseEntity<>(site.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/component/all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }
}
