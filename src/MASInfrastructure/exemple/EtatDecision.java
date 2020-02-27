package MASInfrastructure.exemple;

import MASInfrastructure.Agent.InfraAgent;
import MASInfrastructure.Communication.ICommunication;
import MASInfrastructure.State.IState;
import MASInfrastructure.State.LifeCycle;

import java.util.ArrayList;

public class EtatDecision implements IState {

    private IState nextState;
    private ICommunication communication;
    private InfraAgent agt;
    private String name;

    public EtatDecision(String name) {
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

        Message m = new Message(agt.getInfraAgentReference(), null,"test");
        if (communication != null) {
            communication.sendMessageBroadcast(m);
        }
            System.out.println(name+ " : décision partage " + c.getSharedData("decision") + " " + c.getSharedData("tre"));
            ArrayList<String> info = new ArrayList();
            info.add("broadcast");
            c.shareVariable("perception", info);
            c.setCurrentState(this.nextState);
        }



}
