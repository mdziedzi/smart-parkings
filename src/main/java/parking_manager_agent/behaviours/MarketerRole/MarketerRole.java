package parking_manager_agent.behaviours.MarketerRole;

import jade.core.behaviours.ParallelBehaviour;
import parking_manager_agent.NotifiableBehaviour;
import parking_manager_agent.ParkingAgent;
import parking_manager_agent.behaviours.MarketerRole.subbehaviours.Marketer;

import static parking_manager_agent.DataStoreTypes.*;

/**
 * Implementation of Gaia project role - Marketer.
 * Marketer is responsible for giving the current offer of the parking.
 */
public class MarketerRole extends ParallelBehaviour implements NotifiableBehaviour {

    private final ParkingAgent parkingAgent;

    public MarketerRole(ParkingAgent a, int endCondition) {
        super(a, endCondition);
        parkingAgent = a;
        updateDataStore();
        this.addSubBehaviour(new Marketer(this));
    }

    /**
     * Updates the behaviour DataStore filling it with the values from the agent DataRepository.
     *
     * @see jade.core.behaviours.DataStore
     * @see parking_manager_agent.ParkingAgentDataRepository
     */
    private void updateDataStore() {
        getDataStore().put(PRICE_IN_DOLLARS, parkingAgent.getDataRepository().getPrice());
        getDataStore().put(LATITUDE, parkingAgent.getDataRepository().getLocalization().getLatitude());
        getDataStore().put(LONGITUDE, parkingAgent.getDataRepository().getLocalization().getLongitude());
        getDataStore().put(N_OCCUPIED_PLACES, parkingAgent.getDataRepository().getnOccupiedPlaces());
        getDataStore().put(CAPACITY, parkingAgent.getDataRepository().getCapacity());
    }

    @Override
    public void onDataChanged() {
        updateDataStore();
    }

    /**
     * Checks if the booking is permitted.
     */
    public void isBookingPermitted() {
        parkingAgent.isBookingPermitted();
        updateDataStore();

    }

    public ParkingAgent getParkingAgent() {
        return parkingAgent;
    }

}
