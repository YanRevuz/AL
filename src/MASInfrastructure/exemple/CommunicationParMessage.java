package MASInfrastructure.exemple;

import MASInfrastructure.Agent.InfraAgentReference;
import MASInfrastructure.Communication.ICommunication;
import MASInfrastructure.Communication.IMessage;
import MASInfrastructure.Infrastructure;

import java.util.ArrayList;
import java.util.Optional;

public class CommunicationParMessage implements ICommunication {

    private Infrastructure i;

    @Override
    public void sendMessageBroadcast(IMessage message) {

    }

    public CommunicationParMessage(Infrastructure i) {

        this.i = i;
    }

    @Override
    public void sendMessage(IMessage message) {

        i.sendMessage(message);

    }

    @Override
    public Optional<IMessage> receiveMessage(InfraAgentReference reciever) {
        return Optional.empty();
    }


    @Override
    public ArrayList<IMessage> receiveMessages(InfraAgentReference reciever) {
        return i.receiveMessages(reciever);
    }

    public Infrastructure getI() {
        return i;
    }
}
