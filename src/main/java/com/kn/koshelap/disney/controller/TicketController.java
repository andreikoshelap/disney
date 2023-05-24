package com.kn.koshelap.disney.controller;

import static java.lang.String.format;
import static java.sql.Timestamp.valueOf;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kn.koshelap.disney.domain.Component;
import com.kn.koshelap.disney.domain.Ticket;
import com.kn.koshelap.disney.domain.Visitor;
import com.kn.koshelap.disney.dto.SiteTimestampRequest;
import com.kn.koshelap.disney.dto.VisitorDto;
import com.kn.koshelap.disney.dto.search.TicketSearchDto;
import com.kn.koshelap.disney.repository.ComponentRepository;
import com.kn.koshelap.disney.repository.SiteRepository;
import com.kn.koshelap.disney.repository.TicketRepository;
import com.kn.koshelap.disney.repository.TicketRepositoryCustom;
import com.kn.koshelap.disney.repository.VisitorRepository;
import com.kn.koshelap.disney.service.ComponentService;
import com.kn.koshelap.disney.service.TicketService;
import com.kn.koshelap.disney.service.VisitorService;

@RestController
@RequestMapping("/api")
public class TicketController {

    @Autowired
    private TicketService service;
    @Autowired
    private VisitorService visitorService;
    @Autowired
    private ComponentService componentService;
    @Autowired
    ComponentRepository componentRepository;
    @Autowired
    VisitorRepository visitorRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    TicketRepositoryCustom customRepository;
    @Autowired
    SiteRepository siteRepository;



    @PostMapping("/ticket/search")
    public ResponseEntity<?> find(@RequestBody TicketSearchDto searchDto) {
        return new ResponseEntity<>(service.find(searchDto), HttpStatus.OK);
    }

    @GetMapping("/ticket/all")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PutMapping("/ticket/component/{id}")
    public ResponseEntity<Ticket> addTicketToComponent(@PathVariable("id") Long id, @RequestBody VisitorDto visitorDto) {
        Optional<Component> component = componentRepository.findById(id);
        Optional<Visitor> visitor = visitorRepository.findByName(format("%s %s", visitorDto.getFirstName(), visitorDto.getLastName()));
        Visitor newVisitor;
        if (visitor.isEmpty()) {
            newVisitor = visitorService.save(visitorDto);
        } else {
            newVisitor = visitor.get();
        }
        if (component.isPresent() && componentService.checkVisitorQuantityAgainstMaximum(component.get().getName())) {
            Ticket ticket = new Ticket();
            ticket.setComponent(component.get());
            ticket.setVisitor(newVisitor);
            ticket.setTicketNumber(UUID.randomUUID().toString());
            ticket.setTimePurchase(valueOf(LocalDateTime.now()));
            ticket.setTimeEnd(valueOf(LocalDateTime.now().plusHours(1)));
            ticketRepository.save(ticket);
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ticket/site/{name}")
    public ResponseEntity<?> findActiveTicketsBySiteName(@PathVariable("name") String name) {
        List<Component> components = new ArrayList<>();
        siteRepository.findByName(name).ifPresent(site -> components.addAll(componentRepository.findActiveComponentBySiteId(site.getId())));
        List<Ticket> tickets = new ArrayList<>();
        components.forEach(component -> tickets.addAll(ticketRepository.findActiveTicketForSiteParamsNative(component.getId())));
        if (!tickets.isEmpty()) {
            return new ResponseEntity<>(tickets, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/ticket/site", method = RequestMethod.GET)
    public ResponseEntity<?> findTicketsBySiteNameAndByTime(SiteTimestampRequest request) {
        List<Component> components = new ArrayList<>();
        siteRepository.findByName(request.getSiteName())
                .ifPresent(site -> components.addAll(componentRepository.findActiveComponentBySiteId(site.getId())));
        List<Ticket> tickets = new ArrayList<>();
        components.forEach(component -> tickets.addAll(ticketRepository.findTicketByTimeParamsNative(component.getId(), request.getTimestamp())));
        if (!tickets.isEmpty()) {
            return new ResponseEntity<>(tickets, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
