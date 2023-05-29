package com.kn.koshelap.disney.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.kn.koshelap.disney.domain.enums.StateEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="ticket_number")
    private String ticketNumber;

    @Column(name="time_purchase")
    private Timestamp timePurchase;

    @Column(name="time_end")
    private Timestamp  timeEnd;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private StateEnum state;

    @OneToOne(targetEntity = Component.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "component_id")
    private Component component;

    @OneToOne(targetEntity = Visitor.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

}
