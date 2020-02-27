package MASInfrastructure.Factory;

import MASInfrastructure.Agent.InfraAgent;
import MASInfrastructure.Communication.ICommunication;
import MASInfrastructure.State.LifeCycle;

public interface IInfraAgentFactory {

    InfraAgent createInfrastructureAgent(LifeCycle lifeCycle, ICommunication myMailBoxManager);
}
