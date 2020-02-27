/*
 * Copyright (c) 2018.  Younes Walid, IRIT, University of Toulouse
 */

package MASInfrastructure.State;

/**
 * Interface representing an anbstract state of the life cyrcle of an agent
 * @author Walid YOUNES
 * @version 1.0
 */
public interface IState {

    /**
     * This function allows to run the action associated to the current state
     * @param c the life cycle of the agent
     */
    void execute(LifeCycle c);
}