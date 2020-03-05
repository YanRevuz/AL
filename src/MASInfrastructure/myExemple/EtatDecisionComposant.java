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
            String[] lastInfo = (String[])c.getSharedData("decision").get(0);
            switch (lastInfo[0]) {
                //J'ai perçu une demande de connexion donc je la propage à mon service
                case "Demande_Connexion":
                    System.out.println(name +" dit qu'il a reçu une demande de connexion. Il va donc la propager à son service");
                    Message m = new Message(agt.getInfraAgentReference(),
                            myComposant.getAgentServiceList().get(0).getInfraAgent().getInfraAgentReference(), "PropageDemande",null);
                    if (communication != null) {
                        communication.sendMessage(m);
                    }
                    break;
                //J'ai percu une demande de propagation de connexion mais je n'ai pas de service à satisfaire donc je decide de repondre postiviement à mon service
                case "ReponsePositiveComposant":
                    System.out.println(name + " dit qu'il peut etre connecté et repond positivement à "+myComposant.getAgentServiceList().get(0).getNom());
                    Message m1 = new Message(agt.getInfraAgentReference(),
                            myComposant.getAgentServiceList().get(0).getInfraAgent().getInfraAgentReference(),
                            "ReponsePositiveComposant",null);
                    if (communication != null) {
                        communication.sendMessage(m1);
                    }
                    break;
            }
            c.getSharedData("decision").remove(0);
        }

        c.setCurrentState(this.nextState);
    }
}
