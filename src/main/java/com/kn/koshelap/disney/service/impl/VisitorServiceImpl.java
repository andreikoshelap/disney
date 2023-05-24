package com.kn.koshelap.disney.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kn.koshelap.disney.domain.Component;
import com.kn.koshelap.disney.domain.Site;
import com.kn.koshelap.disney.domain.Ticket;
import com.kn.koshelap.disney.domain.Visitor;
import com.kn.koshelap.disney.dto.VisitorDto;
import com.kn.koshelap.disney.repository.ComponentRepository;
import com.kn.koshelap.disney.repository.SiteRepository;
import com.kn.koshelap.disney.repository.TicketRepository;
import com.kn.koshelap.disney.repository.TicketRepositoryCustom;
import com.kn.koshelap.disney.repository.VisitorRepository;
import com.kn.koshelap.disney.service.VisitorService;

@Service
public class VisitorServiceImpl implements VisitorService {
    @Autowired
    private  VisitorRepository repository;
    @Autowired
    ComponentRepository componentRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    TicketRepositoryCustom customRepository;
    @Autowired
    SiteRepository siteRepository;
    @Autowired
    private  MappingService mapper;



    @Override
    public Visitor save(VisitorDto visitorDto) {
        return repository.save(mapper.map(visitorDto, Visitor.class));
    }

    @Override
    public List<Visitor> findVisitorOnSite(String name) {
        Optional<Site> site = siteRepository.findByName(name);
        if(site.isPresent()) {
            List<Ticket> ticketsOnSite = new ArrayList<>();
            List<Component> components = componentRepository.findActiveComponentBySiteId(site.get().getId());
            if(!components.isEmpty()) {
                components.forEach(component -> {
                    List<Ticket> tickets = ticketRepository.findActiveTicketForSiteParamsNative(component.getId());
                    ticketsOnSite.addAll(tickets);
                });
            }
            if(!ticketsOnSite.isEmpty()) {
                List<Long> visitorsIds = ticketsOnSite.stream().map(Ticket::getVisitor).map(Visitor::getId).collect(Collectors.toList());
                return repository.findAllById(visitorsIds);
            }
        }
        return null;
    }
}
