package MASInfrastructure.myExemple;

import MASInfrastructure.Agent.InfraAgent;
import MASInfrastructure.Communication.ICommunication;
import MASInfrastructure.State.IState;
import MASInfrastructure.State.LifeCycle;
import MASInfrastructure.exemple.Message;

public class EtatDecisionComposant implements IState {

    private IState nextState;
    private ICommunication communication;
    private InfraAgent agt;
    private String name;
    private AgentComposant myComposant;

    public EtatDecisionComposant(String name, AgentComposant s) {
        this.name = name;
        this.myComposant = s;
    }

    public void setCommunication(ICommunication communication) {
        this.communication = communication;
    }

    public void setInfraAgent(InfraAgent agt) {
        this.agt = agt;
    }

    public void setNextState(IState nextState) {
        this.nextState = nextState;
    }

    @Override
    public void execute(LifeCycle c) {
        if (c.getSharedData("decision") != null && c.getSharedData("decision").size() > 0) {
            System.out.println(name + " " + c.getSharedData("decision"));
            String lastInfo = c.getSharedData("decision").get(0).toString();

            switch (lastInfo) {
                case "Demande_Connexion":
                    System.out.println("propagation de la demande par " + name);
                    Message m = new Message(agt.getInfraAgentReference(),
                            myComposant.getAgentServiceList().get(0).getInfraAgent().getInfraAgentReference(),
                            "PropageDemande");
                    if (communication != null) {
                        communication.sendMessage(m);
                    }
                    break;
                case "ReponsePositiveComposant":
                    System.out.println("envoie de la rep positive par " + name);
                    Message m1 = new Message(agt.getInfraAgentReference(),
                            myComposant.getAgentServiceList().get(0).getInfraAgent().getInfraAgentReference(),
                            "ReponsePositiveComposant");
                    if (communication != null) {
                        communication.sendMessage(m1);
                    }
                    break;
            }
            c.getSharedData("decision").remove(0);
        } else {
            System.out.println("je n'ai pas de perception (" + name + ")");
        }

        c.setCurrentState(this.nextState);
    }
}
