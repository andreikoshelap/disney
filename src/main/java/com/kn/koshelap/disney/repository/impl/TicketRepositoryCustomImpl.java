package com.kn.koshelap.disney.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.kn.koshelap.disney.domain.Component;
import com.kn.koshelap.disney.domain.Site;
import com.kn.koshelap.disney.domain.Ticket;
import com.kn.koshelap.disney.repository.TicketRepositoryCustom;

@Repository
public class TicketRepositoryCustomImpl implements TicketRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

//    @Override
//    public List<Ticket> findAllTicketsBySite(Site site) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Ticket> query = cb.createQuery(Ticket.class);
//        Root<Ticket> ticket = query.from(Ticket.class);
//        Join<Ticket, Component> componentJoin = ticket.join(Ticket_.component);
//        Join<Component, Site> siteJoin = componentJoin.join(Component_.site);
//        List<Predicate> predicates = new ArrayList<>();
//        predicates.add(cb.equal(siteJoin.get(Site_.id), site));
//
//        query.select(ticket)
//                .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
//
//        return entityManager.createQuery(query)
//                .getResultList();
//    }
}
