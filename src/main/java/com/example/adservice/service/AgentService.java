package com.example.adservice.service;

import com.example.adservice.model.Agent;

import java.util.List;

public interface AgentService {
    List<Agent> getAllAgents();
    Agent getAgentById(Long id);
    Agent createAgent(Agent agent);
    Agent updateAgent(Long id, Agent agent);
    void deleteAgent(Long id);
}
