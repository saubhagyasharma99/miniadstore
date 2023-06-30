package com.example.adservice.service;

import com.example.adservice.model.Agent;
import com.example.adservice.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    @Override
    public Agent getAgentById(Long id) {
        Optional<Agent> agentOptional = agentRepository.findById(id);
        return agentOptional.orElse(null);
    }

    @Override
    public Agent createAgent(Agent agent) {
        return agentRepository.save(agent);
    }

    @Override
    public Agent updateAgent(Long id, Agent agent) {
        Optional<Agent> agentOptional = agentRepository.findById(id);
        if (agentOptional.isPresent()) {
            Agent existingAgent = agentOptional.get();
            existingAgent.setName(agent.getName());
            existingAgent.setService(agent.getService());
            return agentRepository.save(existingAgent);
        }
        return null;
    }

    @Override
    public void deleteAgent(Long id) {
        agentRepository.deleteById(id);
    }
}