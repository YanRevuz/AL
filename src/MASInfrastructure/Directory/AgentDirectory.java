package MASInfrastructure.Directory;

import MASInfrastructure.Agent.InfraAgent;
import MASInfrastructure.Agent.InfraAgentReference;
import MASInfrastructure.Communication.IMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

public class AgentDirectory implements IAgentDirectory {

    private List<IAgentListener> agentListeners;
    private List<IReferenceAgentListener> referenceAgentListeners;
    private List<IMessageAgentListener> messageAgentListeners;
    private ConcurrentMap<InfraAgentReference, InfraAgent> agents; // references des agents à l'instant t
    private ConcurrentMap<InfraAgentReference, ConcurrentLinkedQueue<IMessage>> agentsMessagesQueues; // references des agents associés aux messages reçus
    private ConcurrentMap<InfraAgentReference, ReadWriteLock> agentsLocks;

    private AgentDirectory() {
        referenceAgentListeners = Collections.synchronizedList(new ArrayList<>());
        agentListeners = Collections.synchronizedList(new ArrayList<>());
        messageAgentListeners = Collections.synchronizedList(new ArrayList<>());
        agents = new ConcurrentHashMap<>();
        agentsMessagesQueues = new ConcurrentHashMap<>();
        agentsLocks = new ConcurrentHashMap<>();
    }

    private static class AgentDirectoryHolder {
        private final static AgentDirectory instance = new AgentDirectory();
    }

    public static AgentDirectory getInstance() {
        return AgentDirectoryHolder.instance;
    }

    public ConcurrentMap<InfraAgentReference, InfraAgent> getAgents() {
        return agents;
    }

    @Override
    public void addAgent(InfraAgent infraAgent) {
        agentsLocks.put(infraAgent.getInfraAgentReference(), new ReentrantReadWriteLock());
        lockAgentEcriture(infraAgent.getInfraAgentReference());
        agents.put(infraAgent.getInfraAgentReference(), infraAgent);
        agentsMessagesQueues.put(infraAgent.getInfraAgentReference(), new ConcurrentLinkedQueue<>());
        unlockAgentEcriture(infraAgent.getInfraAgentReference());
        referenceAgentListeners.forEach(agentListener -> agentListener.agentAjoute(infraAgent.getInfraAgentReference()));
        agentListeners.forEach(agentListener -> agentListener.addAgent(infraAgent));
    }

    @Override
    public void removeAgent(InfraAgentReference infraAgentReference) {

        agentListeners.forEach(agentListener -> agentListener.deleteAgent(agents.get(infraAgentReference)));
        lockAgentEcriture(infraAgentReference);
        agents.remove(infraAgentReference);
        agentsMessagesQueues.remove(infraAgentReference);
        unlockAgentEcriture(infraAgentReference);
        referenceAgentListeners.forEach(agentListener -> agentListener.agentRetire(infraAgentReference));
    }


    public ConcurrentMap<InfraAgentReference, ConcurrentLinkedQueue<IMessage>> getAgentsMessagesQueues() {
        return agentsMessagesQueues;
    }

    @Override
    public void sendMessage(IMessage message) {
        int index;
        for (index = 0; index < message.getReceivers().size(); index++) {
            lockAgentLecture(message.getReceivers().get(index));
            if (agentsMessagesQueues.containsKey(message.getReceivers().get(index))) {
                agentsMessagesQueues.get(message.getReceivers().get(index)).add(message);
                int finalIndex = index;
                messageAgentListeners.forEach(messageAgentListener -> messageAgentListener.messageEnvoye(message.getEmitter(),
                        message.getReceivers().get(finalIndex), message));
            }
            unlockAgentLecture(message.getReceivers().get(index));
        }
    }


    @Override
    public void sendMessageBroadcast(IMessage message) {
        agentsMessagesQueues.keySet().forEach(this::lockAgentLecture);
        agentsMessagesQueues.entrySet().forEach(referenceAgentEntry -> {
            if (referenceAgentEntry.getKey() != message.getEmitter()) {
                referenceAgentEntry.getValue().add(message);
                notifierMessageAgentListeners(message.getEmitter(), message, referenceAgentEntry.getKey());
            }
        });
        agentsMessagesQueues.keySet().forEach(this::unlockAgentLecture);
    }

    private void notifierMessageAgentListeners(InfraAgentReference expediteur, IMessage IMessage,
                                               InfraAgentReference infraAgentReference) {
        messageAgentListeners.forEach(
                messageAgentListener -> messageAgentListener.messageEnvoye(expediteur, infraAgentReference, IMessage));
    }


    @Override
    public Optional<IMessage> receiveMessage(InfraAgentReference reciever) {
        lockAgentLecture(reciever);
        Optional<IMessage> message = Optional.ofNullable(agentsMessagesQueues.get(reciever))
                .map(ConcurrentLinkedQueue::poll);
        message.ifPresent(messageAgent -> notifierMessageAgentListeners(messageAgent.getEmitter(), messageAgent,
                reciever));
        unlockAgentLecture(reciever);
        return message;
    }

    @Override
    public ArrayList<IMessage> receiveMessages(InfraAgentReference reciever) {
        ArrayList messages = new ArrayList<>(agentsMessagesQueues.get(reciever));
        agentsMessagesQueues.get(reciever).clear();
        return messages;
    }


    private void lockAgentEcriture(InfraAgentReference infraAgentReference) {
        executeIfPresent(agentsLocks.get(infraAgentReference), readWriteLock -> readWriteLock.writeLock().lock());
    }

    private void lockAgentLecture(InfraAgentReference infraAgentReference) {
        executeIfPresent(agentsLocks.get(infraAgentReference), readWriteLock -> readWriteLock.readLock().lock());
    }

    private void unlockAgentEcriture(InfraAgentReference infraAgentReference) {
        executeIfPresent(agentsLocks.get(infraAgentReference), readWriteLock -> readWriteLock.writeLock().unlock());
    }

    private void unlockAgentLecture(InfraAgentReference infraAgentReference) {
        executeIfPresent(agentsLocks.get(infraAgentReference), readWriteLock -> readWriteLock.readLock().unlock());
    }

    private <T> void executeIfPresent(T object, Consumer<T> objectConsumer) {
        if (object != null) {
            objectConsumer.accept(object);
        }
    }

    public List<IMessageAgentListener> getMessageAgentListeners() {
        return messageAgentListeners;
    }

}