package parking_manager_agent.behaviours.ReservationistRole.behaviours;

import jade.core.behaviours.OneShotBehaviour;
import parking_manager_agent.behaviours.ReservationistRole.ReservationistRole;

import static parking_manager_agent.DataStoreTypes.CAPACITY;
import static parking_manager_agent.DataStoreTypes.N_OCCUPIED_PLACES;

public class CalculatePrice extends OneShotBehaviour {
    private final ReservationistRole reservationistRole;

    public CalculatePrice(ReservationistRole reservationistRole) {
        super();
        this.reservationistRole = reservationistRole;
    }

    @Override
    public void action() {
        reservationistRole.getParkingManagerAgent().getPriceAlgorithm().calculatePrice((double) getParent()
                .getDataStore().get(N_OCCUPIED_PLACES), (double) getParent().getDataStore().get(CAPACITY));
    }
}
