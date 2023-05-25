package com.kn.koshelap.disney.controller;

import static com.kn.koshelap.disney.domain.enums.StateEnum.ACTIVE;
import static com.kn.koshelap.disney.domain.enums.StateEnum.CANCELED;
import static com.kn.koshelap.disney.domain.enums.StateEnum.QUEUE;
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
        Optional<Visitor> visitor = visitorRepository.findByName(visitorDto.getName());
        Visitor newVisitor;
        if (!visitor.isPresent()) {
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
            ticket.setState(QUEUE);
            ticketRepository.save(ticket);
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    @PostMapping("/ticket/activate/{id}")
    public ResponseEntity<?> activateTicket(@PathVariable("id") Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isPresent() && QUEUE.equals(ticket.get().getState())) {
            ticket.get().setState(ACTIVE);
            ticketRepository.save(ticket.get());
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/ticket/cancel/{id}")
    public ResponseEntity<?> cancelTicket(@PathVariable("id") Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isPresent() && QUEUE.equals(ticket.get().getState())) {
            ticket.get().setState(CANCELED);
            ticketRepository.save(ticket.get());
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/site/util/{id}")
    public ResponseEntity<?> getSiteUtilization(@PathVariable("id") Long id) {
        List<Component> components = getComponentsBySiteId(id);
        if (!components.isEmpty()) {
            int maxSiteVisitorCount = components.stream().map(component -> component.getMaxVisitors()).reduce(0, Integer::sum);

            List<Ticket> tickets = new ArrayList<>();
            components.forEach(component -> tickets.addAll(ticketRepository.findActiveTicketForSiteParamsNative(component.getId())));
            double percentUtilisation = calculatePercentage(tickets.size(), maxSiteVisitorCount);

            return new ResponseEntity<>(percentUtilisation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/component/util/{id}")
    public ResponseEntity<?> getComponentUtilization(@PathVariable("id") Long id) {

        Optional<Component> component = componentRepository.findById(id);
        if (component.isPresent()) {
            int maxSiteVisitorCount = component.map(Component::getMaxVisitors).orElse(0);
            List<Ticket> tickets = new ArrayList<>();
            tickets.addAll(ticketRepository.findActiveTicketForSiteParamsNative(component.get().getId()));
            double percentUtilisation = calculatePercentage(tickets.size(), maxSiteVisitorCount);

            return new ResponseEntity<>(percentUtilisation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private List<Component> getComponentsBySiteId(Long id) {
        List<Component> components = new ArrayList<>();
        siteRepository.findById(id).ifPresent(site -> components.addAll(componentRepository.findActiveComponentBySiteId(site.getId())));
        return components;
    }

    private double calculatePercentage(double obtained, double total) {
        return obtained * 100 / total;
    }
}
