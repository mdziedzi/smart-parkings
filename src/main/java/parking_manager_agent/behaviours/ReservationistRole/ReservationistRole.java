package parking_manager_agent.behaviours.ReservationistRole;

import jade.core.behaviours.ParallelBehaviour;
import parking_manager_agent.NotifiableBehaviour;
import parking_manager_agent.ParkingAgent;
import parking_manager_agent.behaviours.ReservationistRole.subbehaviours.Reservationist;

import static parking_manager_agent.DataStoreTypes.*;

public class ReservationistRole extends ParallelBehaviour implements NotifiableBehaviour {

    private final ParkingAgent parkingAgent;

    public ReservationistRole(ParkingAgent a, int endCondition) {
        super(a, endCondition);
        this.parkingAgent = a;
        updateDataStore();
        this.addSubBehaviour(new Reservationist(this));
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
        parkingAgent.getDataRepository().setnOccupiedPlaces(parkingAgent.getDataRepository().getnOccupiedPlaces() + 1);
        calculateNewPrice();
        parkingAgent.bookParkingPlace();
    }

    private void calculateNewPrice() {
        double newPrice = parkingAgent.getPriceAlgorithm().calculatePrice((int) parkingAgent.getDataRepository().getnOccupiedPlaces(), (int) parkingAgent.getDataRepository().getCapacity());
        parkingAgent.getDataRepository().setPriceInDollars(newPrice);
    }
}
