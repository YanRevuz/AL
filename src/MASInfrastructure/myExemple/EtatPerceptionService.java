package MASInfrastructure.myExemple;

import MASInfrastructure.Agent.InfraAgent;
import MASInfrastructure.Agent.InfraAgentReference;
import MASInfrastructure.Communication.ICommunication;
import MASInfrastructure.State.IState;
import MASInfrastructure.State.LifeCycle;
import MASInfrastructure.exemple.Message;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class EtatPerceptionService implements IState {

    private InfraAgent agt;
    private InfraAgent communicationAgt;
    private String name;
    private int agentType;
    private ICommunication communication;
    private AgentService myService;

    public EtatPerceptionService(String name, int type, AgentService a) {
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

        ArrayList<String[]> info = new ArrayList<>();
        ArrayList<Message> infraMessages = new ArrayList<>(this.agt.readMessages().stream().map(x -> (Message) x).collect(Collectors.toList()));
        if (myService.type() == 0) {
            if (infraMessages != null && infraMessages.size() != 0 && infraMessages.get(0).getContent() == "PropageDemande") {
                String[] information = new String[2];
                information[0] = "Broadcast";
                information[1] = myService.getMonInterface();
                info.add(information);
                c.shareVariable("decision", info);
            } else if (infraMessages != null && infraMessages.size() != 0 && infraMessages.get(0).getContent() == "ReponsePositiveService") {
                myService.setConnecteur(infraMessages.get(0).getEmitter());
                String[] information = new String[2];
                information[0] = "okpourconnexion";
                information[1] = name;
                info.add(information);
                c.shareVariable("decision", info);
            }
        } else {
            if (infraMessages != null && infraMessages.size() != 0 && infraMessages.get(0).getContent() == "Broadcast") {
                myService.setBroadcaster(infraMessages.get(0).getEmitter());
                String[] information = new String[2];
                information[0] = "Receive_Broadcast";
                information[1] = infraMessages.get(0).getId();
                info.add(information);
                c.shareVariable("decision", info);
            } else if (infraMessages != null && infraMessages.size() != 0 && infraMessages.get(0).getContent() == "ReponsePositiveComposant") {
                String[] information = new String[2];
                information[0] = "ReponsePositiveService";
                information[1] = name;
                info.add(information);
                c.shareVariable("decision", info);
            } else if (infraMessages != null && infraMessages.size() != 0 && infraMessages.get(0).getContent() == "okpourconnexion") {
                myService.setConnecteur(infraMessages.get(0).getEmitter());
                myService.setEstConnecte(true);
            }
        }
        c.setCurrentState(this.nextState);
    }
}
