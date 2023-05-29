package com.kn.koshelap.disney.service;

import java.util.List;

import com.kn.koshelap.disney.domain.Visitor;
import com.kn.koshelap.disney.dto.VisitorDto;

public interface VisitorService {
    Visitor save(VisitorDto visitorDto);
    List<Visitor> findVisitorOnSite(String name);
}
