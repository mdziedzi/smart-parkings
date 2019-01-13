package parking_manager_agent.behaviours.MarketerRole.subbehaviours;

import jade.core.behaviours.OneShotBehaviour;
import parking_manager_agent.behaviours.MarketerRole.MarketerRole;

import static parking_manager_agent.DataStoreTypes.CAPACITY;
import static parking_manager_agent.DataStoreTypes.N_OCCUPIED_PLACES;

public class CalculatePrice extends OneShotBehaviour {
    private final MarketerRole marketerRole;

    public CalculatePrice(MarketerRole marketerRole) {
        super();
        this.marketerRole = marketerRole;
    }

    @Override
    public void action() {
        marketerRole.getParkingAgent().getPriceAlgorithm().calculatePrice((int) getParent()
                .getDataStore().get(N_OCCUPIED_PLACES), (int) getParent().getDataStore().get(CAPACITY));
    }
}
