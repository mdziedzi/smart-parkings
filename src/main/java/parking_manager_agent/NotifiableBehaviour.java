package parking_manager_agent;

/**
 * Represents the behaviour that can be notified.
 */
public interface NotifiableBehaviour {

    /**
     * Method invoked while data of an agent has hanged.
     */
    void onDataChanged();

}
