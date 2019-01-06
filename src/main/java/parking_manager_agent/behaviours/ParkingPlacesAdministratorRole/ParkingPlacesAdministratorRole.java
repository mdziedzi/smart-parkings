package parking_manager_agent.behaviours.ParkingPlacesAdministratorRole;

import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;

public class ParkingPlacesAdministratorRole extends ParallelBehaviour {


    private int parkingPlaces; // w agencie?

    public ParkingPlacesAdministratorRole(Agent a, int endCondition) {
        super(a, endCondition);
    }

    public int getParkingPlaces() {
        return parkingPlaces;
    }
}
