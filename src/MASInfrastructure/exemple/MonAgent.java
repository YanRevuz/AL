package MASInfrastructure.exemple;

import MASInfrastructure.Agent.InfraAgent;

public class MonAgent {

    private InfraAgent infraAgent;

    private EtatPerception perception;
    private EtatDecision decision;

    private String nom;

    public EtatPerception getPerception() {
        return perception;
    }

    public EtatDecision getDecision() {
        return decision;
    }

    public MonAgent(String nom) {
        this.nom = nom;
        perception=new EtatPerception(nom);
        decision=new EtatDecision(nom);
        perception.setNextState(decision);
        decision.setNextState(perception);

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
