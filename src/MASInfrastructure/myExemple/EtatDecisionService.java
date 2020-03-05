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
    private AgentService myService;

    public EtatDecisionService(String name, int type, AgentService a) {
        this.name = name;
        this.agentType = type;
        this.myService = a;
    }

    public void setCommunication(ICommunication communication) {
        this.communication = communication;
    }

    public void setCommunicationAgt(InfraAgent communicationAgt) {
        this.communicationAgt = communicationAgt;
    }

    public void setInfraAgent(InfraAgent agt) {
        this.agt = agt;
    }

    private IState nextState;

    public void setNextState(IState nextState) {
        this.nextState = nextState;
    }

    @Override
    public void execute(LifeCycle c) {
        if (c.getSharedData("decision") != null && c.getSharedData("decision").size() > 0) {
            String[] lastInfo = (String[])c.getSharedData("decision").get(0);
            switch (lastInfo[0]) {
                //J'ai perçu une demande de broadcast de mon composant donc.......
                case "Broadcast":
                    System.out.println(name + " dit : Je vais broadcast et demander l'interface " + myService.getMonInterface());
                    Message m = new Message(agt.getInfraAgentReference(),
                            null,
                            "Broadcast",myService.getMonInterface());
                    if (communication != null) {
                        //..... J'envoie un message en broadcast
                        communication.sendMessageBroadcast(m);
                    }
                    break;
                // j'ai percu un broadcast donc si je fournis l'interface demandée je transfmet la demande à mon composant
                case "Receive_Broadcast":
                    if(lastInfo[1] == myService.getMonInterface()) {
                        System.out.println(name + " dit qu'il fournit bien l'interface "+lastInfo[1] + " et donc qu'il propage la demande à son composant");
                        Message m1 = new Message(agt.getInfraAgentReference(),
                                myService.getComposant().getInfraAgent().getInfraAgentReference(),
                                "Propage_Broadcast", null);
                        if (communication != null) {
                            communication.sendMessage(m1);
                        }
                    }else{
                        System.out.println(name + " dit qu'il ne fournit pas l'interface "+lastInfo[1] + " et donc qu'il propage pas la demande à son composant");
                    }
                    break;
                //Le service percois que son composant peut etre connecté, donc il repond positivement au service qui à fais le broadcast
                case "ReponsePositiveService":
                    System.out.println(name + " dit que son composant peut etre connecté donc il repond positivement");
                    Message m2 = new Message(agt.getInfraAgentReference(),
                            myService.getBroadcaster(),
                            "ReponsePositiveService",null);
                    if (communication != null) {
                        communication.sendMessage(m2);
                    }
                    break;
                //En tant que service qui à réalisé le broadcast, je me connecte à c2 car il m'a repondu
                case "okpourconnexion":
                    System.out.println(name + " dit qu'il se connecte à S2 et il dit à son composant qu'il est connecté");
                    //ETAPE 1 : repondre à son composant pour lui dire que c'est bon
                    Message m3 = new Message(agt.getInfraAgentReference(),
                            myService.getComposant().getInfraAgent().getInfraAgentReference(),
                            "jesuiscobbecte",null);
                    if (communication != null) {
                        communication.sendMessage(m3);
                    }
                    //ETAPE 2 : Etablir la connexion avec le service
                    Message m4 = new Message(agt.getInfraAgentReference(),
                            myService.getConnecteur(),
                            "okpourconnexion",null);
                    if (communication != null) {
                        communication.sendMessage(m3);
                    }
                    myService.setEstConnecte(true);
                    break;

            }
            c.getSharedData("decision").remove(0);

        }
        c.setCurrentState(this.nextState);
    }
}
