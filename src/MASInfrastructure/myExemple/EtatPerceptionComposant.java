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
        ArrayList<String[]> info = new ArrayList<>();
        ArrayList<Message> infraMessages = new ArrayList<>(this.agt.readMessages().stream().map(x -> (Message) x).collect(Collectors.toList()));
        //Si en tant que composant je perçois une demande de connexion, je stocke pour mon état décision qu'il faut que je demande une connexion
        if (infraMessages != null && infraMessages.size() != 0 && infraMessages.get(0).getContent() == "Demande_Connexion") {
            String[] information = new String[2];
            information[0] = "Demande_Connexion";
            information[1] = name;
            info.add(information);
            c.shareVariable("decision", info);
        //Si en tant que composant je perçois une demande de propagation de connexion de la part de mon service, je stocke pour mon état décision qu'il faut que je propage une connexion
        } else if (infraMessages != null && infraMessages.size() != 0 && infraMessages.get(0).getContent() == "Propage_Broadcast") {
            String[] information = new String[2];
            information[0] = "ReponsePositiveComposant";
            information[1] = name;
            info.add(information);
            c.shareVariable("decision", info);
        //Si en tant que composant initial je perçois que je suis connécté alors il y a une application
        } else if (infraMessages != null && infraMessages.size() != 0 && infraMessages.get(0).getContent() == "jesuiscobbecte") {
            System.out.println("Une application a été trouvée");
        }
        c.setCurrentState(this.nextState);
    }
}



