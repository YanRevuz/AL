package MASInfrastructure.Scheduler;

import MASInfrastructure.Agent.InfraAgentReference;
import MASInfrastructure.State.IState;


public interface SchedulerListener {

    void changementEtat(InfraAgentReference infraAgentReference, IState etatAbstract);
}
