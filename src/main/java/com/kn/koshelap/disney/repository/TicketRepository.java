package com.kn.koshelap.disney.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kn.koshelap.disney.domain.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {
    @Query(value = "SELECT * FROM ticket t WHERE t.component_id = :id and t.time_purchase < :timestamp AND time_end > :timestamp", nativeQuery = true)
    List<Ticket> findTicketByTimeParamsNative(@Param("id") Long id, @Param("timestamp") String timestamp);

    @Query(value = "SELECT * FROM ticket t WHERE t.component_id = :id and t.time_purchase < now() and t.time_end > now() and t.state = 'ACTIVE'", nativeQuery = true)
    List<Ticket> findActiveTicketForSiteParamsNative(@Param("id") Long id);
}
