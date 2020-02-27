package MASInfrastructure.Directory;

import MASInfrastructure.Agent.InfraAgentReference;

public interface IReferenceAgentListener {

    void agentAjoute(InfraAgentReference infraAgentReference);

    void agentRetire(InfraAgentReference infraAgentReference);
}
