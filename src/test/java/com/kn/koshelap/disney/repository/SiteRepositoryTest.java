package com.kn.koshelap.disney.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.kn.koshelap.disney.domain.Site;

@DataJpaTest
public class SiteRepositoryTest {

        @Autowired
        private SiteRepository siteRepository;

        @BeforeEach
        public void setup() {
            List<Site> sites = new ArrayList<>(List.of(new Site(1L, "West", "West"),
                    new Site(2L, "North", "North"),
                    new Site(3L, "Ost", "Ost"),
                    new Site(4L, "South", "South")));
            siteRepository.saveAll(sites);
        }

        @AfterEach
        public void tearDown() {
            siteRepository.deleteAll();
        }

        @Test
        public void findSite() {
            Optional<Site> site = siteRepository.findByName("West");
            assertThat(site).isNotEmpty().get().extracting(Site::getDescription).isEqualTo("West");

        }



}
