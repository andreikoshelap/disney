package com.kn.koshelap.disney.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

@Service
public class MappingService extends ConfigurableMapper {
    private MapperFactory factory;

    public MappingService() {
    }

    @Override
    public void configure(MapperFactory factory) {
        this.factory = factory;
    }

    private void registerCustomMappers(MapperFactory factory) {

    }

    @PostConstruct
    public void registerStuff() {
        registerCustomMappers(factory);
    }
}
