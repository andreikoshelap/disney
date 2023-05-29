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

import com.kn.koshelap.disney.dto.ComponentDto;
import com.kn.koshelap.disney.dto.ComponentDtoList;
import com.kn.koshelap.disney.repository.ComponentRepository;
import com.kn.koshelap.disney.repository.SiteRepository;
import com.kn.koshelap.disney.repository.VisitorRepository;
import com.kn.koshelap.disney.service.impl.ComponentServiceImpl;

@WebMvcTest(ComponentController.class)
public class ComponentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComponentServiceImpl service;
    @MockBean
    SiteRepository siteRepository;
    @MockBean
    ComponentRepository componentRepository;
    @MockBean
    VisitorRepository visitorRepository;


    @BeforeEach
    void setup() {
        ComponentDtoList allComponents = getAllComponents();

        when(service.findAll()).thenReturn(allComponents);

    }

    private static ComponentDtoList getAllComponents() {
        List<ComponentDto> components = new ArrayList<>(List.of(new ComponentDto("green", "DOUBLE_SWINGS", 12),
                new ComponentDto("roller coaster", "CAROUSEL", 10),
                new ComponentDto("Wonder land", "BALL_PIT", 3),
                new ComponentDto("water slide", "SLIDE", 15)));
        return new ComponentDtoList(components);
    }

    @Test
    public void outputAllComponent() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/component/all"))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        assertThat(jsonObject.get("componentDtoList")).isNotNull();

    }

}
