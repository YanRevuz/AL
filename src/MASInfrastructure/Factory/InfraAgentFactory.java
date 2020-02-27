package MASInfrastructure.Factory;

import MASInfrastructure.Agent.InfraAgent;
import MASInfrastructure.Directory.IAgentDirectory;
import MASInfrastructure.Communication.ICommunication;
import MASInfrastructure.State.LifeCycle;
import MASInfrastructure.Scheduler.IScheduler;

public class InfraAgentFactory implements IInfraAgentFactory, ISuicideService {
    private final IAgentDirectory annuaire;
    private final IScheduler scheduler;

    public InfraAgentFactory(IAgentDirectory annuaire, IScheduler scheduler) {
        this.annuaire = annuaire;
        this.scheduler = scheduler;
    }

    @Override
    public InfraAgent createInfrastructureAgent(LifeCycle lifeCycle, ICommunication myMailBoxManager) {
        InfraAgent infraAgent = new InfraAgent(lifeCycle, myMailBoxManager);
        annuaire.addAgent(infraAgent);
        scheduler.addAgentToScheduler(infraAgent);
        return infraAgent;
    }

    @Override
    public void suicide(InfraAgent infraAgent) {
        annuaire.removeAgent(infraAgent.getInfraAgentReference());
        scheduler.deleteAgentFromScheduler(infraAgent);
    }

}
