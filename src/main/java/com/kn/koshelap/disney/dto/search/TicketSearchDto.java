package com.kn.koshelap.disney.dto.search;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketSearchDto extends SearchDto {

    private String ticketNumber;
    private Timestamp timePurchase;
    private Timestamp timeEnd;
}
