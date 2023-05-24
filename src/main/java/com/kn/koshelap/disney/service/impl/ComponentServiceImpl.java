package com.kn.koshelap.disney.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kn.koshelap.disney.domain.Component;
import com.kn.koshelap.disney.domain.Ticket;
import com.kn.koshelap.disney.dto.ComponentDto;
import com.kn.koshelap.disney.dto.ComponentDtoList;
import com.kn.koshelap.disney.dts.ComponentDts;
import com.kn.koshelap.disney.repository.ComponentRepository;
import com.kn.koshelap.disney.repository.TicketRepository;
import com.kn.koshelap.disney.service.ComponentService;

@Service
public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository repository;
    private final TicketRepository ticketRepository;
    private final MappingService mapper;

    public ComponentServiceImpl(ComponentRepository repository, TicketRepository ticketRepository, MappingService mapper) {
        this.repository = repository;
        this.ticketRepository = ticketRepository;
        this.mapper = mapper;
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ComponentDts save(ComponentDts componentDts) {
        return mapper.map(repository.save(mapper.map(componentDts, Component.class)), ComponentDts.class);

    }

    @Override
    public ComponentDtoList findAll() {
        List<Component> list = repository.findAll();
        return ComponentDtoList.builder()
                .componentDtoList(mapper.mapAsList(list, ComponentDto.class)).build();
    }

    @Override
    public boolean checkVisitorQuantityAgainstMaximum(String name) {
        Optional<Component> component = repository.findByName(name);
        if(component.isPresent()) {
            List<Ticket> tickets = ticketRepository.findActiveTicketForSiteParamsNative(component.get().getId());
            return tickets.size() <= component.get().getMaxVisitors();
        }
        return false;
    }
}
