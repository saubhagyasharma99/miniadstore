package com.example.adservice;

import com.example.adservice.controller.AgentController;
import com.example.adservice.exception.AgentNotFoundException;
import com.example.adservice.model.Agent;
import com.example.adservice.repository.AgentRepository;
import com.example.adservice.service.AgentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AgentControllerTest {
    @Mock
    private AgentRepository agentRepository;

    @Mock
    private AgentService agentService;

    @InjectMocks
    private AgentController agentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAgents() {
        List<Agent> agents = new ArrayList<>();
        when(agentRepository.findAll()).thenReturn(agents);

        List<Agent> result = agentController.getAllAgents();

        assertEquals(agents, result);
        verify(agentRepository, times(1)).findAll();
    }

    @Test
    void testGetAgentById() {
        Long agentId = 1L;
        Agent agent = new Agent();
        when(agentRepository.findById(agentId)).thenReturn(Optional.of(agent));

        Agent result = agentController.getAgentById(agentId);

        assertEquals(agent, result);
        verify(agentRepository, times(1)).findById(agentId);
    }

    @Test
    void testGetAgentByIdThrowsAgentNotFoundException() {
        Long agentId = 1L;
        when(agentRepository.findById(agentId)).thenReturn(Optional.empty());

        try {
            agentController.getAgentById(agentId);
        } catch (AgentNotFoundException e) {
            assertEquals("Agent not found with agentId: " + agentId, e.getMessage());
        }

        verify(agentRepository, times(1)).findById(agentId);
    }

    @Test
    void testCreateAgent() {
        Agent agent = new Agent();
        Agent createdAgent = new Agent();

        when(agentService.createAgent(agent)).thenReturn(createdAgent);

        ResponseEntity<Agent> response = agentController.createAgent(agent);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdAgent, response.getBody());
        verify(agentService, times(1)).createAgent(agent);
    }

    @Test
    void testUpdateAgent() {
        Long agentId = 1L;
        Agent agent = new Agent();
        Agent updatedAgent = new Agent();

        when(agentService.updateAgent(agentId, agent)).thenReturn(updatedAgent);

        ResponseEntity<Agent> response = agentController.updateAgent(agentId, agent);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedAgent, response.getBody());
        verify(agentService, times(1)).updateAgent(agentId, agent);
    }

    @Test
    void testUpdateAgentReturnsNotFound() {
        Long agentId = 1L;
        Agent agent = new Agent();

        when(agentService.updateAgent(agentId, agent)).thenReturn(null);

        ResponseEntity<Agent> response = agentController.updateAgent(agentId, agent);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(agentService, times(1)).updateAgent(agentId, agent);
    }

    @Test
    void testDeleteAgent() {
        Long agentId = 1L;

        agentController.deleteAgent(agentId);

        verify(agentService, times(1)).deleteAgent(agentId);
    }
}





