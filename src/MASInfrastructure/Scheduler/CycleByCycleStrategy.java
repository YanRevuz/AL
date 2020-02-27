package MASInfrastructure.Scheduler;

import MASInfrastructure.Agent.InfraAgent;

import java.util.List;

public class CycleByCycleStrategy implements ISchedulingStrategies {

    private List<InfraAgent> listAgentsToSchedule; // list of observed agents
    private List<SchedulerListener> schedulerListeners; // list of observers
    private int speed;
    private int currentAgentCycle;
    private int maxCycleAgent;
    private boolean isRunning;
    private final int defaultMaxCycleAgent = 100;

    public CycleByCycleStrategy(List<InfraAgent> listInfraAgent, List<SchedulerListener> listListenerActuels) {
        listAgentsToSchedule = listInfraAgent;
        schedulerListeners = listListenerActuels;
        this.currentAgentCycle = 0;
        this.maxCycleAgent = defaultMaxCycleAgent;
        changeSpeed(EnumSpeed.CENT);
    }


    @Override
    public void startScheduling() {
        //Initialize the parameters for the execution
        this.isRunning = true;
        this.currentAgentCycle = 0;

        InfraAgent currentInfraAgent;
        while(isRunning) {
            synchronized (this) {

                while (this.currentAgentCycle < this.maxCycleAgent && listAgentsToSchedule.size()>0 ) {
                    currentInfraAgent = listAgentsToSchedule.get(0);
                    currentInfraAgent.run(); //Launch the behavior of the agent in its PERCEPTION State
                    currentInfraAgent.run(); //Launch the behavior of the agent in its DECISION State
                    currentInfraAgent.run(); //Launch the behavior of the agent in its ACTION State
                    listAgentsToSchedule.remove(currentInfraAgent);
                    listAgentsToSchedule.add(currentInfraAgent);
                    this.currentAgentCycle++;
                }
            }
        }
    }

    /**
     * Start a special scheduling cycle with a set of agents and for a certain number of cycles.
     * An example of a use case for this method, is to treat the feedback : scheduling the agents which are supposed to treat the feedback.
     * @param listAgentsToSchedule :   the list of agents to schedule
     * @param numberCycles         :   the number of agent cycle to run (One cycle = Perception, Decision, Action)
     */
    @Override
    public void startSpecialScheduling(List<InfraAgent> listAgentsToSchedule, int numberCycles) {
        int currentCycle = 0;
        //OCELogger.log(Level.INFO, "----------------------------------------------------------------------------------------- STARTING SPECIAL SCHEDULING -------------------------------------------------------------------------------------------- \n");
        InfraAgent currentInfraAgent;

        while (currentCycle < numberCycles && listAgentsToSchedule.size()>0 ) {
            currentInfraAgent = listAgentsToSchedule.get(0);
            currentInfraAgent.run(); //Launch the behavior of the agent in its PERCEPTION State
            currentInfraAgent.run(); //Launch the behavior of the agent in its DECISION State
            currentInfraAgent.run(); //Launch the behavior of the agent in its ACTION State
            listAgentsToSchedule.remove(currentInfraAgent);
            listAgentsToSchedule.add(currentInfraAgent);
            currentCycle++;
        }
    }


    public List<InfraAgent> getListAgentsToSchedule() {
        return listAgentsToSchedule;
    }


    @Override
    public void changeSpeed(EnumSpeed speed) {
        switch (speed) {
            case CENT:
                this.speed = 10;
                break;
            case SOIXANTE_QUINZE:
                this.speed = 15;
                break;
            case CINQUANTE:
                this.speed = 20;
                break;
            case VINGT_CINQ:
                this.speed = 50;
                break;
            case DIX:
                this.speed = 100;
                break;
        }
    }

    @Override
    public void stopScheduling() {
    }

    @Override
    public void addSchedulingListener(SchedulerListener schedulerListener) {
        schedulerListeners.add(schedulerListener);
    }

    @Override
    public void addAgent(InfraAgent infraAgent) {
        listAgentsToSchedule.add(infraAgent);
    }

    @Override
    public void deleteAgent(InfraAgent infraAgent) {
        listAgentsToSchedule.remove(infraAgent);
    }


    @Override
    public void setMaxCycleAgent(int maxCycleAgent) {
        this.maxCycleAgent = maxCycleAgent;
    }

    @Override
    public void resetCurrentCycleAgent() {
        this.currentAgentCycle = 0;
    }
}
