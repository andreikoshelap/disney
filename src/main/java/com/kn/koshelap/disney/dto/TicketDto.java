package com.kn.koshelap.disney.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TicketDto {

    private String ticketNumber;
    private Timestamp timePurchase;
    private Timestamp timeEnd;

}
