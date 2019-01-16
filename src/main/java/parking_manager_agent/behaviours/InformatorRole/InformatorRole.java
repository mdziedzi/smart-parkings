package parking_manager_agent.behaviours.InformatorRole;

import jade.core.behaviours.ParallelBehaviour;
import parking_manager_agent.NotifiableBehaviour;
import parking_manager_agent.ParkingAgent;
import parking_manager_agent.behaviours.InformatorRole.subbehaviours.Informator;

import static parking_manager_agent.DataStoreTypes.*;

public class InformatorRole extends ParallelBehaviour implements NotifiableBehaviour {

    private final ParkingAgent parkingAgent;

    public InformatorRole(ParkingAgent a, int endCondition) {
        super(a, endCondition);
        parkingAgent = a;
        updateDataStore();
        this.addSubBehaviour(new Informator(this));
    }

    private void updateDataStore() {
        getDataStore().put(PRICE_IN_DOLLARS, parkingAgent.getDataRepository().getPriceInDollars());
        getDataStore().put(LATITUDE, parkingAgent.getDataRepository().getLocalization().getLatitude());
        getDataStore().put(LONGITUDE, parkingAgent.getDataRepository().getLocalization().getLongitude());
    }

    @Override
    public void onDataChanged() {
        updateDataStore();
    }
}
