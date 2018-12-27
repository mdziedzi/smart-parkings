package parking_manager_agent.behaviours.ReservationistRole;

import jade.core.behaviours.ParallelBehaviour;
import parking_manager_agent.NotifiableBehaviour;
import parking_manager_agent.ParkingManagerAgent;
import parking_manager_agent.behaviours.ReservationistRole.behaviours.ListenForReservation;

public class ReservationistRole extends ParallelBehaviour implements NotifiableBehaviour {

    private final ParkingManagerAgent parkingManagerAgent;

    public ReservationistRole(ParkingManagerAgent a, int endCondition) {
        super(a, endCondition);
        parkingManagerAgent = a;
        updateDataStore();
        this.addSubBehaviour(new ListenForReservation());
    }

    private void updateDataStore() {
        getDataStore().put("price_in_dollars", parkingManagerAgent.getDataRepository().getPriceInDollars());
        getDataStore().put("lat", parkingManagerAgent.getDataRepository().getLocalization().getLatitude());
        getDataStore().put("lon", parkingManagerAgent.getDataRepository().getLocalization().getLongitude());
    }

    @Override
    public void onDataChanged() {
        updateDataStore();
    }
}
