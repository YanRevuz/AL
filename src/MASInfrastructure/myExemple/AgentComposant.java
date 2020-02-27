package MASInfrastructure.myExemple;

import MASInfrastructure.Agent.InfraAgent;

import java.util.ArrayList;
import java.util.List;

public class AgentComposant {

    private InfraAgent infraAgent;
    private EtatDecisionComposant decisionComposant;
    private EtatPerceptionComposant perceptionComposant;
    private List<AgentService> agentServiceList;
    private String nom;

    public AgentComposant(String nom) {
        this.nom = nom;
        this.agentServiceList = new ArrayList<AgentService>();
        decisionComposant = new EtatDecisionComposant(nom, this);
        perceptionComposant = new EtatPerceptionComposant(nom);
        perceptionComposant.setNextState(decisionComposant);
        decisionComposant.setNextState(perceptionComposant);
    }

    public void addAgent(AgentService agent) {
        agentServiceList.add(agent);
    }

    public EtatDecisionComposant getDecisionComposant() {
        return decisionComposant;
    }

    public EtatPerceptionComposant getPerceptionComposant() {
        return perceptionComposant;
    }

    public List<AgentService> getAgentServiceList() {
        return agentServiceList;
    }

    public InfraAgent getInfraAgent() {
        return infraAgent;
    }

    public void setInfraAgent(InfraAgent infraAgent) {
        this.infraAgent = infraAgent;
    }

    public String getNom() {
        return nom;
    }
}
