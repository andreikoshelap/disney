package com.kn.koshelap.disney.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.kn.koshelap.disney.dto.SiteDto;
import com.kn.koshelap.disney.dto.SiteDtoList;
import com.kn.koshelap.disney.service.impl.SiteServiceImpl;

@WebMvcTest(SiteController.class)
public class SiteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SiteServiceImpl siteService;

    @BeforeEach
    void setup() {
        SiteDtoList allSites = getAllSites();

        when(siteService.findAll()).thenReturn(allSites);

    }

    private static SiteDtoList getAllSites() {
        List<SiteDto> sites = new ArrayList<>(List.of(new SiteDto("West", "West"),
                new SiteDto("North", "North"),
                new SiteDto("Ost", "Ost"),
                new SiteDto("South", "South")));
        return new SiteDtoList(sites);
    }

    @Test
    public void outputAllSites() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/site/all"))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        assertThat(jsonObject.get("siteList")).isNotNull();

    }

}
