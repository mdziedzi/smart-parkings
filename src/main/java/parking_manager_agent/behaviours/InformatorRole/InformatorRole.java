package parking_manager_agent.behaviours.InformatorRole;

import jade.core.behaviours.ParallelBehaviour;
import parking_manager_agent.NotifiableBehaviour;
import parking_manager_agent.ParkingManagerAgent;
import parking_manager_agent.behaviours.InformatorRole.subbehaviours.Informator;

import static parking_manager_agent.DataStoreTypes.*;

public class InformatorRole extends ParallelBehaviour implements NotifiableBehaviour {

    private final ParkingManagerAgent parkingManagerAgent;

    public InformatorRole(ParkingManagerAgent a, int endCondition) {
        super(a, endCondition);
        parkingManagerAgent = a;
        updateDataStore();
        this.addSubBehaviour(new Informator(this));
    }

    private void updateDataStore() {
        getDataStore().put(PRICE_IN_DOLLARS, parkingManagerAgent.getDataRepository().getPriceInDollars());
        getDataStore().put(LATITUDE, parkingManagerAgent.getDataRepository().getLocalization().getLatitude());
        getDataStore().put(LONGITUDE, parkingManagerAgent.getDataRepository().getLocalization().getLongitude());
    }

    @Override
    public void onDataChanged() {
        updateDataStore();
    }

}
