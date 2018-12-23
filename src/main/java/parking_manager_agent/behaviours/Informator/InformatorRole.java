package parking_manager_agent.behaviours.Informator;

import jade.core.behaviours.ParallelBehaviour;
import parking_manager_agent.ParkingManagerAgent;
import parking_manager_agent.behaviours.Informator.subbehaviours.Informator;

public class InformatorRole extends ParallelBehaviour {
    public InformatorRole(ParkingManagerAgent a, int endCondition) {
        super(a, endCondition);
        this.addSubBehaviour(new Informator());
        initDataStore(a);
    }

    private void initDataStore(ParkingManagerAgent a) {
        getDataStore().put("price_in_dollars", a.getDataRepository().getPriceInDollars());
        getDataStore().put("lat", a.getDataRepository().getLocalization().getLatitude());
        getDataStore().put("lon", a.getDataRepository().getLocalization().getLongitude());
    }

    //todo: update data store
}
