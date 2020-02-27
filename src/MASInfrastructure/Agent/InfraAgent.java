package MASInfrastructure.Agent;

import MASInfrastructure.Communication.ICommunication;
import MASInfrastructure.Communication.IMessage;
import MASInfrastructure.State.IState;
import MASInfrastructure.State.LifeCycle;

import java.util.ArrayList;
import java.util.Optional;

public class InfraAgent {

    private final InfraAgentReference infraAgentReference;
    private LifeCycle lifeCycle;
    private ICommunication myMailBoxManager;

    public InfraAgent(LifeCycle lifeCycle, ICommunication myMailBoxManager) {

        this.infraAgentReference = new InfraAgentReference();
        this.lifeCycle = lifeCycle;
        this.myMailBoxManager = myMailBoxManager;
    }

    public void run() {
        this.lifeCycle.run();
    }


    public InfraAgentReference getInfraAgentReference() {
        return infraAgentReference;
    }

    public IState getState() {
        return lifeCycle.getCurrentState();
    }

    public ArrayList<IMessage> readMessages() {
        return this.myMailBoxManager.receiveMessages(this.infraAgentReference);
    }

    public Optional<IMessage> readMessage() {
        return this.myMailBoxManager.receiveMessage(this.infraAgentReference);
    }

    @Override
    public String toString() {
        return "INFRA.IDAgent{" + infraAgentReference + '}';
    }
}
