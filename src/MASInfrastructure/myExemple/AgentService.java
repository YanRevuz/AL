package MASInfrastructure.myExemple;

import MASInfrastructure.Agent.InfraAgent;
import MASInfrastructure.Agent.InfraAgentReference;

public class AgentService {

    private InfraAgent infraAgent;

    private EtatPerceptionService attente;
    private EtatDecisionService action;

    private AgentComposant composant;

    private int type;

    private int nombreDeReponseMax;

    private InfraAgentReference brodcaster;

    private InfraAgentReference connecteur;


    private String nom;

    private boolean estConnecte = false;

    public EtatPerceptionService getAttente() {
        return attente;
    }

    public EtatDecisionService getAction() {
        return action;
    }

    public AgentService(String nom,int type,int nbReponseMax) {
        this.nombreDeReponseMax = nbReponseMax;
        this.type = type;
        this.nom = nom;
        action=new EtatDecisionService(nom,type,this);
        attente=new EtatPerceptionService(nom,type,this);
        attente.setNextState(action);
        action.setNextState(attente);

    }

    public AgentComposant getComposant() {
        return composant;
    }

    public void setComposant(AgentComposant composant) {
        this.composant = composant;
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

    public int type() {
        return type;
    }

    public InfraAgentReference getBrodcaster() {
        return brodcaster;
    }

    public void setBrodcaster(InfraAgentReference brodcaster) {
        this.brodcaster = brodcaster;
    }

    public InfraAgentReference getConnecteur() {
        return connecteur;
    }

    public void setConnecteur(InfraAgentReference connecteur) {
        this.connecteur = connecteur;
    }

    public boolean isEstConnecte() {
        return estConnecte;
    }

    public void setEstConnecte(boolean estConnecte) {
        this.estConnecte = estConnecte;
    }
}
