package MASInfrastructure.exemple;

import MASInfrastructure.Agent.InfraAgent;
import MASInfrastructure.State.IState;
import MASInfrastructure.State.LifeCycle;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class EtatPerception implements IState {

    private InfraAgent agt;
    private String name;

    public EtatPerception(String name) {
        this.name = name;
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

   ArrayList<Message> infraMessages = new ArrayList<>(this.agt.readMessages().stream().map(x -> (Message)x).collect(Collectors.toList()));
        if (infraMessages !=null && infraMessages.size() !=0) System.out.println(name+ " a reçu "+infraMessages.get(0).toString());
        else System.out.println(name+" n'a pas reçu de message");
        System.out.println(name+ " : perception partage " +c.getSharedData("perception"));
        ArrayList<String> info=new ArrayList();
        info.add("info de "+name);
        c.shareVariable("decision", info);
        c.setCurrentState(this.nextState);

    }
}
