package MASInfrastructure.myExemple;

import MASInfrastructure.Agent.InfraAgent;
import MASInfrastructure.Communication.ICommunication;
import MASInfrastructure.State.IState;
import MASInfrastructure.State.LifeCycle;
import MASInfrastructure.exemple.Message;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class EtatPerceptionComposant implements IState {
    private IState nextState;
    private ICommunication communication;
    private InfraAgent agt;
    private String name;

    public EtatPerceptionComposant(String name) {
        this.name = name;
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
        ArrayList<String> info = new ArrayList<>();
        ArrayList<Message> infraMessages = new ArrayList<>(this.agt.readMessages().stream().map(x -> (Message) x).collect(Collectors.toList()));
        if (infraMessages != null && infraMessages.size() != 0 && infraMessages.get(0).getContent() =="Demande_Connexion") {
            info.add("Demande_Connexion");
            c.shareVariable("decision",info);
        }else if (infraMessages != null && infraMessages.size() != 0 && infraMessages.get(0).getContent() =="Propage_Brodcast"){
            info.add("ReponsePositiveComposant");
            c.shareVariable("decision",info);
        }else if (infraMessages != null && infraMessages.size() != 0 && infraMessages.get(0).getContent() =="jesuiscobbecte"){
            System.out.println(name + " EST UNE APPLI");
        }
        c.setCurrentState(this.nextState);
    }


}



