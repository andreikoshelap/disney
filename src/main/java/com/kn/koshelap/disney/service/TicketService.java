package com.kn.koshelap.disney.service;

import com.kn.koshelap.disney.dto.TicketDtoList;
import com.kn.koshelap.disney.dto.search.TicketSearchDto;

public interface TicketService {
    TicketDtoList find(TicketSearchDto searchDto);

    TicketDtoList findAll();
}
