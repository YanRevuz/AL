package MASInfrastructure.Communication;

import MASInfrastructure.Agent.InfraAgentReference;

import java.util.ArrayList;
import java.util.Optional;

public interface ICommunication {

    void sendMessageBroadcast(IMessage message);

    void sendMessage(IMessage message);

    Optional<IMessage> receiveMessage(InfraAgentReference reciever);

    ArrayList<IMessage> receiveMessages(InfraAgentReference reciever);

}
