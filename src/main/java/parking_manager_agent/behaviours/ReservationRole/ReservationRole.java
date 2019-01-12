package parking_manager_agent.behaviours.ReservationRole;

import jade.core.behaviours.ParallelBehaviour;
import parking_manager_agent.NotifiableBehaviour;
import parking_manager_agent.ParkingAgent;
import parking_manager_agent.behaviours.ReservationRole.subbehaviours.Reservation;

import static parking_manager_agent.DataStoreTypes.*;

public class ReservationRole extends ParallelBehaviour implements NotifiableBehaviour {

    private final ParkingAgent parkingAgent;

    public ReservationRole(ParkingAgent a, int endCondition) {
        super(a, endCondition);
        this.parkingAgent = a;
        updateDataStore();
        this.addSubBehaviour(new Reservation(this));
    }

    private void updateDataStore() {
        getDataStore().put(PRICE_IN_DOLLARS, parkingAgent.getDataRepository().getPriceInDollars());
        getDataStore().put(LATITUDE, parkingAgent.getDataRepository().getLocalization().getLatitude());
        getDataStore().put(LONGITUDE, parkingAgent.getDataRepository().getLocalization().getLongitude());
        getDataStore().put(CAPACITY, parkingAgent.getDataRepository().getCapacity());
        getDataStore().put(N_OCCUPIED_PLACES, parkingAgent.getDataRepository().getnOccupiedPlaces());
    }

    @Override
    public void onDataChanged() {
        updateDataStore();
    }

    public void bookParkingPlace() {
        parkingAgent.bookParkingPlace();
    }
}
