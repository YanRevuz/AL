package MASInfrastructure.exemple;

import MASInfrastructure.Agent.InfraAgentReference;
import MASInfrastructure.Communication.IMessage;

import java.util.ArrayList;

public class Message implements IMessage {
    private InfraAgentReference emitter;
    private ArrayList<InfraAgentReference> receivers = new ArrayList();
    private String content,id;

    public Message(InfraAgentReference emitter, InfraAgentReference receiver,String message, String id) {
        this.emitter = emitter;
        this.receivers.add(receiver);
        this.content = message;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Message(InfraAgentReference emitter, InfraAgentReference receiver, String message) {
       new Message(emitter,receiver,message,null);
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
