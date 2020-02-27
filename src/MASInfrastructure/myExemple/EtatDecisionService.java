package MASInfrastructure.myExemple;

import MASInfrastructure.Agent.InfraAgent;
import MASInfrastructure.Communication.ICommunication;
import MASInfrastructure.State.IState;
import MASInfrastructure.State.LifeCycle;
import MASInfrastructure.exemple.Message;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class EtatDecisionService implements IState {

    private InfraAgent agt;
    private InfraAgent communicationAgt;
    private String name;
    private int agentType;
    private ICommunication communication;
    public void setCommunication(ICommunication communication) {
        this.communication = communication;
    }

    private AgentService myService;


    public EtatDecisionService(String name, int type, AgentService a) {
        this.name = name;
        this.agentType  = type;
        this.myService = a;
    }

    public void setCommunicationAgt(InfraAgent communicationAgt) {
        this.communicationAgt = communicationAgt;
    }

    public void setInfraAgent(InfraAgent agt) {
        this.agt=agt;
    }

    private IState nextState;

    public void setNextState(IState nextState) {
        this.nextState = nextState;
    }

    @Override
    public void execute(LifeCycle c) {
        if(c.getSharedData("decision") != null && c.getSharedData("decision").size() > 0 ){
            String lastInfo = c.getSharedData("decision").get(0).toString();

            switch (lastInfo){
                case "Brodcast":
                    System.out.println(name +" dit : Je vais brodcast");
                    Message m = new Message(agt.getInfraAgentReference(),null,"Brodcast");
                    if (communication != null) {
                        communication.sendMessageBroadcast(m);
                    }
                    break;
                case "Receive_Brodcast":
                    System.out.println(name +" dit : je vais transmettre le brodcast a mon compo");
                    Message m1 = new Message(agt.getInfraAgentReference(),myService.getComposant().getInfraAgent().getInfraAgentReference(),"Propage_Brodcast");
                    if (communication != null) {
                        communication.sendMessage(m1);
                    }
                    break;
                case "ReponsePositiveService":
                    System.out.println(name +" dit : je vais transmettre la rep positive  au service requies");
                    Message m2 = new Message(agt.getInfraAgentReference(),myService.getBrodcaster(),"ReponsePositiveService");
                    if (communication != null) {
                        communication.sendMessage(m2);
                    }
                    break;
                case "okpourconnexion":
                    System.out.println(name +" dit : je dis ok pour connexion et dis ok a mon composant");
                    Message m3 = new Message(agt.getInfraAgentReference(),myService.getComposant().getInfraAgent().getInfraAgentReference(),"jesuiscobbecte");
                    if (communication != null) {
                        communication.sendMessage(m3);
                    }
                    Message m4 = new Message(agt.getInfraAgentReference(),myService.getConnecteur(),"okpourconnexion");
                    if (communication != null) {
                        communication.sendMessage(m3);
                    }
                    myService.setEstConnecte(true);
                    break;

            }
            c.getSharedData("decision").remove(0);

        }else{
            System.out.println("je n'ai pas de perception (" +name+")");
        }
        c.setCurrentState(this.nextState);

    }

}
