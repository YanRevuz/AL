package MASInfrastructure.Scheduler;

import MASInfrastructure.Agent.InfraAgent;


import java.util.List;

public class Scheduler implements IScheduler {

    private ISchedulingStrategies schedulingStrategy;

    public Scheduler(ISchedulingStrategies schedulingStrategy) {
        this.schedulingStrategy = schedulingStrategy;
    }

    @Override
    public void startScheduling() {
        schedulingStrategy.startScheduling();
    }

    @Override
    public void changeSpeed(EnumSpeed newSpeed) {
        schedulingStrategy.changeSpeed(newSpeed);
    }

    @Override
    public void changeSchedulingStrategy(ISchedulingStrategies schedulingStrategy) {
        this.schedulingStrategy = schedulingStrategy;
    }

    @Override
    public void stopScheduling() {
        schedulingStrategy.stopScheduling();
    }

    @Override
    public void addAgentToScheduler(InfraAgent infraAgent) {
        schedulingStrategy.addAgent(infraAgent);
    }

    public ISchedulingStrategies getSchedulingStrategy() {
        return schedulingStrategy;
    }

    @Override
    public void deleteAgentFromScheduler(InfraAgent infraAgent) {
        schedulingStrategy.deleteAgent(infraAgent);
    }

    /**
     * Start a special scheduling cycle with a set of agents and for a certain number of cycles.
     * An example of a use case for this method, is to treat the feedback : scheduling the agents which are supposed to treat the feedback.
     * @param listAgentsToSchedule :   the list of agents to schedule
     * @param numberCycles         :   the number of agent cycle to run (One cycle = Perception, Decision, Action)
     **/
    @Override
    public void startSpecialScheduling(List<InfraAgent> listAgentsToSchedule, int numberCycles) {
        this.schedulingStrategy.startSpecialScheduling(listAgentsToSchedule,numberCycles);
    }

    @Override
    public void setMaxCycleAgent(int maxCycleAgent) {
        this.schedulingStrategy.setMaxCycleAgent(maxCycleAgent);
    }

    @Override
    public void resetCurrentCycleAgent() {
        this.schedulingStrategy.resetCurrentCycleAgent();
    }
}
