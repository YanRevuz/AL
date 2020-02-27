package MASInfrastructure.Communication;

import MASInfrastructure.Agent.InfraAgentReference;

import java.util.ArrayList;

public interface IMessage {

    InfraAgentReference getEmitter();

    void setEmitter(InfraAgentReference emitter);

    ArrayList<InfraAgentReference> getReceivers();

    void setReceivers(ArrayList<InfraAgentReference> receivers);

}
