package com.kn.koshelap.disney.service;

import com.kn.koshelap.disney.dto.SiteDtoList;
import com.kn.koshelap.disney.dto.search.SiteSearchDto;
import com.kn.koshelap.disney.dts.SiteDts;

public interface SiteService {

    void delete(Long id);

    SiteDts save(SiteDts siteDts);

    SiteDtoList findAll();
    SiteDtoList find(SiteSearchDto searchDto);

}
