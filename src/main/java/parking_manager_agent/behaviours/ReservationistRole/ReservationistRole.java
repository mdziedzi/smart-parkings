package parking_manager_agent.behaviours.ReservationistRole;

import jade.core.behaviours.ParallelBehaviour;
import parking_manager_agent.NotifiableBehaviour;
import parking_manager_agent.ParkingManagerAgent;
import parking_manager_agent.behaviours.ReservationistRole.behaviours.StartListeningForReservation;

import static parking_manager_agent.DataStoreTypes.*;

public class ReservationistRole extends ParallelBehaviour implements NotifiableBehaviour {

    private final ParkingManagerAgent parkingManagerAgent;

    public ReservationistRole(ParkingManagerAgent a, int endCondition) {
        super(a, endCondition);
        parkingManagerAgent = a;
        updateDataStore();
        this.addSubBehaviour(new StartListeningForReservation(this));
    }

    private void updateDataStore() {
        getDataStore().put(PRICE_IN_DOLLARS, parkingManagerAgent.getDataRepository().getPriceInDollars());
        getDataStore().put(LATITUDE, parkingManagerAgent.getDataRepository().getLocalization().getLatitude());
        getDataStore().put(LONGITUDE, parkingManagerAgent.getDataRepository().getLocalization().getLongitude());
        getDataStore().put(N_OCCUPIED_PLACES, parkingManagerAgent.getDataRepository().getnOccupiedPlaces());
        getDataStore().put(CAPACITY, parkingManagerAgent.getDataRepository().getCapacity());
    }

    @Override
    public void onDataChanged() {
        updateDataStore();
    }

    public void bookParkingPlace() {
        // todo
        // todo calculate new price
    }
}
