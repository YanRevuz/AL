package MASInfrastructure.Scheduler;

import MASInfrastructure.Agent.InfraAgent;
import MASInfrastructure.Directory.IReferenceAgentListener;

import java.util.List;

public class ClassicStrategy implements ISchedulingStrategies {

    private List<InfraAgent> listAgentsToSchedule; // list of observed agents
    private List<SchedulerListener> schedulerListeners; // list of observers
    private int speed;
    private int currentAgentCycle;
    private int maxCycleAgent;
    private boolean isRunning;
    private final int defaultMaxCycleAgent = 400;

    /**
     * @param listInfraAgents
     * @param listListenerActuels
     */
    public ClassicStrategy(List<InfraAgent> listInfraAgents, List<SchedulerListener> listListenerActuels) {
        listAgentsToSchedule = listInfraAgents;
        this.currentAgentCycle = 0;
        this.maxCycleAgent = defaultMaxCycleAgent;
        this.isRunning = true;
        schedulerListeners = listListenerActuels;
        changeSpeed(EnumSpeed.CENT);
    }

    @Override
    public void startScheduling() {
        this.isRunning = true;
        this.currentAgentCycle = 0;

        InfraAgent currentInfraAgent;
        while (isRunning) {
            synchronized (this) {

                while (this.currentAgentCycle < this.maxCycleAgent && listAgentsToSchedule.size() > 0) {
                    currentInfraAgent = listAgentsToSchedule.get(0);
                    currentInfraAgent.run();
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
     *
     * @param listAgentsToSchedule :   the list of agents to schedule
     * @param numberCycles         :   the number of agent cycle to run (One cycle = Perception, Decision, Action)
     */
    @Override
    public void startSpecialScheduling(List<InfraAgent> listAgentsToSchedule, int numberCycles) {
        int currentCycle = 0;
        int numberCyclesBound = numberCycles * 3;

        InfraAgent currentInfraAgent;

        while (currentCycle < numberCyclesBound && listAgentsToSchedule.size() > 0) {
            currentInfraAgent = listAgentsToSchedule.get(0);

            currentInfraAgent.run(); //Launch the behavior of the agent in its current state
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

    public List<SchedulerListener> getSchedulerListeners() {
        return schedulerListeners;
    }

    @Override
    public void stopScheduling() {
        synchronized (this) {
            this.isRunning = false;
        }
    }

    @Override
    public void addSchedulingListener(SchedulerListener schedulerListener) {
        schedulerListeners.add(schedulerListener);
    }

    @Override
    public void addAgent(InfraAgent infraAgent) {
        listAgentsToSchedule.add(infraAgent);
    }

    public List<IReferenceAgentListener> getReferenceAgentListeners() {
        return null;
    }

    @Override
    public void deleteAgent(InfraAgent infraAgent) {
        System.out.println(" Deleting from the scheduling strategy the agent = " + infraAgent.toString());
        listAgentsToSchedule.remove(infraAgent);
    }

    @Override
    public void setMaxCycleAgent(int maxCycleAgent) {
        //OCELogger.log(Level.INFO,"Changement du nombre de cycles agent par cycle moteur, nouvelle valeur = "+ maxCycleAgent);
        this.maxCycleAgent = maxCycleAgent;
    }

    @Override
    public void resetCurrentCycleAgent() {
        synchronized (this) {
            this.currentAgentCycle = 0;
        }
    }
}
