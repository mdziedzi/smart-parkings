package parking_manager_agent.behaviours.ReservationistRole;

import jade.core.behaviours.ParallelBehaviour;
import parking_manager_agent.NotifiableBehaviour;
import parking_manager_agent.ParkingAgent;
import parking_manager_agent.behaviours.ReservationistRole.subbehaviours.Reservationist;

import static parking_manager_agent.DataStoreTypes.*;

/**
 * Implementation of Gaia project role - Reservationist.
 * Marketer is responsible for giving the current offer of the parking.
 */
public class ReservationistRole extends ParallelBehaviour implements NotifiableBehaviour {

    private final ParkingAgent parkingAgent;

    public ReservationistRole(ParkingAgent a, int endCondition) {
        super(a, endCondition);
        this.parkingAgent = a;
        updateDataStore();
        this.addSubBehaviour(new Reservationist(this));
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
        getDataStore().put(CAPACITY, parkingAgent.getDataRepository().getCapacity());
        getDataStore().put(N_OCCUPIED_PLACES, parkingAgent.getDataRepository().getnOccupiedPlaces());
    }

    @Override
    public void onDataChanged() {
        updateDataStore();
    }

    /**
     * Performs booking of the parking place. Increments the number of occupied places and block the physical parking place,
     * calculate new price and update state of the agent.
     */
    public void bookParkingPlace() {
        parkingAgent.getDataRepository().setnOccupiedPlaces(parkingAgent.getDataRepository().getnOccupiedPlaces() + 1);
        calculateNewPrice();
        parkingAgent.bookParkingPlace();
        updateDataStore();
    }

    /**
     * Calculates new price for the parking place and stores it in DataRepository
     * @see parking_manager_agent.ParkingAgentDataRepository
     */
    private void calculateNewPrice() {
        double newPrice = parkingAgent.getPriceAlgorithm().calculatePrice((int) parkingAgent.getDataRepository().getnOccupiedPlaces(), (int) parkingAgent.getDataRepository().getCapacity());
        parkingAgent.getDataRepository().setPrice(newPrice);
    }
}
