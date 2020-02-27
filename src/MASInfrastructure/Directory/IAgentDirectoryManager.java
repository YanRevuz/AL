package MASInfrastructure.Directory;

import MASInfrastructure.Agent.InfraAgent;
import MASInfrastructure.Agent.InfraAgentReference;


public interface IAgentDirectoryManager {

    void addAgent(InfraAgent infraAgent);

    void removeAgent(InfraAgentReference infraAgentReference);
}
