package MASInfrastructure.Directory;

import MASInfrastructure.Agent.InfraAgent;
import MASInfrastructure.Agent.InfraAgentReference;
import MASInfrastructure.Communication.ICommunication;
import MASInfrastructure.Communication.IMessage;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public interface IAgentDirectory extends ICommunication, IAgentDirectoryManager {

    ConcurrentMap<InfraAgentReference, ConcurrentLinkedQueue<IMessage>> getAgentsMessagesQueues();

    ConcurrentMap<InfraAgentReference, InfraAgent> getAgents();

}
