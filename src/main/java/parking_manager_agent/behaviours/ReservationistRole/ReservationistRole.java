package parking_manager_agent.behaviours.ReservationistRole;

import jade.core.behaviours.ParallelBehaviour;
import parking_manager_agent.NotifiableBehaviour;
import parking_manager_agent.ParkingAgent;
import parking_manager_agent.behaviours.ReservationistRole.behaviours.CalculatePrice;
import parking_manager_agent.behaviours.ReservationistRole.behaviours.StartListeningForReservation;

import static parking_manager_agent.DataStoreTypes.*;

public class ReservationistRole extends ParallelBehaviour implements NotifiableBehaviour {

    private final ParkingAgent parkingAgent;

    public ReservationistRole(ParkingAgent a, int endCondition) {
        super(a, endCondition);
        parkingAgent = a;
        updateDataStore();
        this.addSubBehaviour(new StartListeningForReservation(this));
    }

    private void updateDataStore() {
        getDataStore().put(PRICE_IN_DOLLARS, parkingAgent.getDataRepository().getPriceInDollars());
        getDataStore().put(LATITUDE, parkingAgent.getDataRepository().getLocalization().getLatitude());
        getDataStore().put(LONGITUDE, parkingAgent.getDataRepository().getLocalization().getLongitude());
        getDataStore().put(N_OCCUPIED_PLACES, parkingAgent.getDataRepository().getnOccupiedPlaces());
        getDataStore().put(CAPACITY, parkingAgent.getDataRepository().getCapacity());
    }

    @Override
    public void onDataChanged() {
        updateDataStore();
    }

    public void bookParkingPlace() {
        parkingAgent.bookParkingPlace();
        addSubBehaviour(new CalculatePrice(this));
    }

    public ParkingAgent getParkingAgent() {
        return parkingAgent;
    }

}
