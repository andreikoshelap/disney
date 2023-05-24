package com.kn.koshelap.disney.service;

import com.kn.koshelap.disney.dto.ComponentDtoList;
import com.kn.koshelap.disney.dts.ComponentDts;

public interface ComponentService {
    void delete(Long id);

    ComponentDts save(ComponentDts componentDts);

    ComponentDtoList findAll();

    boolean checkVisitorQuantityAgainstMaximum(String name);
}
