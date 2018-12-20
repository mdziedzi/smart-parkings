package parking_manager_agent.behaviours.Informator;

import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import parking_manager_agent.behaviours.Informator.subbehaviours.Informator;

public class InformatorRole extends ParallelBehaviour {
    public InformatorRole(Agent a, int endCondition) {
        super(a, endCondition);
        this.addSubBehaviour(new Informator());
    }
}
