package com.kn.koshelap.disney.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kn.koshelap.disney.domain.Site;
import com.kn.koshelap.disney.dto.SiteDto;
import com.kn.koshelap.disney.dto.SiteDtoList;
import com.kn.koshelap.disney.dts.SiteDts;
import com.kn.koshelap.disney.repository.SiteRepository;
import com.kn.koshelap.disney.service.SiteService;

@Service
public class SiteServiceImpl implements SiteService {
    @Autowired
    private  SiteRepository repository;
    @Autowired
    private  MappingService mapper;


    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public SiteDts save(SiteDts siteDts) {
        return mapper.map(repository.save(mapper.map(siteDts, Site.class)), SiteDts.class);

    }

    @Override
    public SiteDtoList findAll() {
        List<Site> list = repository.findAll();
        return SiteDtoList.builder()
                .siteList(mapper.mapAsList(list, SiteDto.class)).build();
    }

}
