package com.kn.koshelap.disney.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kn.koshelap.disney.domain.Ticket;
import com.kn.koshelap.disney.dto.TicketDto;
import com.kn.koshelap.disney.dto.TicketDtoList;
import com.kn.koshelap.disney.dto.search.TicketSearchDto;
import com.kn.koshelap.disney.repository.TicketRepository;
import com.kn.koshelap.disney.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository repository;
    private final MappingService mapper;

    public TicketServiceImpl(TicketRepository repository, MappingService mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public TicketDtoList find(TicketSearchDto searchDto) {
        return null;
    }

    @Override
    public TicketDtoList findAll() {
        List<Ticket> list  = repository.findAll();
        return TicketDtoList.builder()
                .ticketList(mapper.mapAsList(list, TicketDto.class)).build();
    }

}
