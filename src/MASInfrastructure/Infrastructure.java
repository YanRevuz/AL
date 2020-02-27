
package MASInfrastructure;

import MASInfrastructure.Agent.InfraAgent;
import MASInfrastructure.Agent.InfraAgentReference;
import MASInfrastructure.Directory.AgentDirectory;
import MASInfrastructure.Directory.IAgentDirectory;
import MASInfrastructure.Communication.ICommunication;
import MASInfrastructure.Communication.IMessage;
import MASInfrastructure.State.LifeCycle;
import MASInfrastructure.Factory.IInfraAgentFactory;
import MASInfrastructure.Factory.ISuicideService;
import MASInfrastructure.Factory.InfraAgentFactory;
import MASInfrastructure.Scheduler.*;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Infrastructure implements IInfraAgentFactory, ISuicideService, ICommunication, IScheduler, PropertyChangeListener {

    private InfraAgentFactory infraAgentFactory;
    private IScheduler scheduler;
    private IAgentDirectory annuaire;

    public Infrastructure() {
        scheduler = new Scheduler(new ClassicStrategy(new ArrayList<>(), new ArrayList<>()));
//        scheduler = new Scheduler(new CycleByCycleStrategy(new ArrayList<>(), new ArrayList<>()));
        annuaire = AgentDirectory.getInstance();
        infraAgentFactory = new InfraAgentFactory(annuaire, scheduler);
    }

    @Override
    public void suicide(InfraAgent agent) {
        infraAgentFactory.suicide(agent);
    }

    @Override
    public void startScheduling() {
        scheduler.startScheduling();
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
        this.scheduler.startSpecialScheduling(listAgentsToSchedule, numberCycles);
    }

    @Override
    public void changeSpeed(EnumSpeed newSpeed) {
        scheduler.changeSpeed(newSpeed);
    }

    @Override
    public void changeSchedulingStrategy(ISchedulingStrategies schedulingStrategy) {
        scheduler.changeSchedulingStrategy(schedulingStrategy);
    }

    @Override
    public void stopScheduling() {
        scheduler.stopScheduling();
    }

    @Override
    public void sendMessageBroadcast(IMessage message) {
        annuaire.sendMessageBroadcast(message);
    }

    @Override
    public void sendMessage(IMessage message) {
        annuaire.sendMessage(message);
    }

    @Override
    public Optional<IMessage> receiveMessage(InfraAgentReference reciever) {
        return annuaire.receiveMessage(reciever);
    }

    @Override
    public ArrayList<IMessage> receiveMessages(InfraAgentReference reciever) {
        return annuaire.receiveMessages(reciever);
    }

    @Override
    public InfraAgent createInfrastructureAgent(LifeCycle lifeCycle, ICommunication myMailBoxManager) {
        InfraAgent infraAgent = infraAgentFactory.createInfrastructureAgent(lifeCycle, myMailBoxManager);
        return infraAgent;
    }

    public IAgentDirectory getAnnuaire() {
        return annuaire;
    }

    public IScheduler getScheduler() {
        return scheduler;
    }

    @Override
    public void addAgentToScheduler(InfraAgent infraAgent) {

        scheduler.addAgentToScheduler(infraAgent);
    }

    @Override
    public void deleteAgentFromScheduler(InfraAgent infraAgent) {

        scheduler.deleteAgentFromScheduler(infraAgent);
    }

    /**
     * Set the value of the number of agent cycle per OCE Cycle
     * @param maxCycleAgent
     */
    @Override
    public void setMaxCycleAgent(int maxCycleAgent) {
       this.scheduler.setMaxCycleAgent(maxCycleAgent);
    }


    @Override
    public void resetCurrentCycleAgent() {
        this.scheduler.resetCurrentCycleAgent();
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //Depending on the name of the property we do the corresponding operation

    }
}
