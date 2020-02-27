package MASInfrastructure.exemple;

import MASInfrastructure.Agent.InfraAgentReference;
import MASInfrastructure.Communication.IMessage;

import java.util.ArrayList;

public class Message implements IMessage {
    private InfraAgentReference emitter;
    private ArrayList<InfraAgentReference> receivers = new ArrayList();
    private String content;


    public Message(InfraAgentReference emitter, InfraAgentReference receiver,String message) {
        this.emitter = emitter;
        this.receivers.add(receiver);
        this.content = message;
    }

    @Override
    public InfraAgentReference getEmitter() {
        return emitter;
    }

    @Override
    public void setEmitter(InfraAgentReference emitter) {

    }


    @Override
    public ArrayList<InfraAgentReference> getReceivers() {
        return receivers;
    }

    @Override
    public void setReceivers(ArrayList<InfraAgentReference> receivers) {

    }

    public String getContent() {
        return content;
    }
}
