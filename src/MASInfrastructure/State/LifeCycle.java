package MASInfrastructure.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LifeCycle {
    private IState currentState;
    private Map<String, ArrayList> sharedMemory;

    /**
     * Constructor of the class
     * @param initialState : the first state of the cycle
     */
    public LifeCycle(IState initialState) {
        this.currentState = initialState;
        this.sharedMemory = new HashMap<>();
    }

    /**
     * Change the current state of the agent
     * @param newState : the new state in the life cycle of the agent
     */
    public void setCurrentState(IState newState) {
        this.currentState = newState;
    }

    /***
     * Get the current state
     * @return the current state of the agent
     */
    public IState getCurrentState() {
        return currentState;
    }

    /**
     * This function allow to share data in a labeled area
     * @param variableName : the name of the are, its used to distinguish the different areas
     * @param dataToShare  : the data to share between the states
     */
    public void shareVariable(String variableName, ArrayList dataToShare) {
        if (this.sharedMemory.containsKey(variableName)) {
            this.sharedMemory.replace(variableName, dataToShare);
        } else {
            this.sharedMemory.put(variableName, dataToShare);
        }
    }

    public ArrayList getSharedData(String variableName) {
        return this.sharedMemory.get(variableName);
    }

    /**
     * This function runs the current state of an agent
     */
    public void run() {
        this.currentState.execute(this);
    }
}
