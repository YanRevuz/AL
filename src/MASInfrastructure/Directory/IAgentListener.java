package MASInfrastructure.Directory;

import MASInfrastructure.Agent.InfraAgent;

public interface IAgentListener {

    void addAgent(InfraAgent infraAgent);

    void deleteAgent(InfraAgent infraAgent);
}
