package parking_manager_agent.behaviours.ParkingPlacesAdministrator;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;

public class ParkingPlacesAdministratorRole extends ParallelBehaviour {


    private int parkingPlaces; // w agencie?

    public ParkingPlacesAdministratorRole(Agent a, int endCondition) {
        super(a, endCondition);
        this.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                switch (receive()) {
                    case 1:
                        this.myAgent.addBehaviour(new Rezerwuj());
                    case 2:
                        this.myAgent.addBehaviour(new Odrzuc());

                }

            }
        });
    }

    public int getParkingPlaces() {
        return parkingPlaces;
    }
}
