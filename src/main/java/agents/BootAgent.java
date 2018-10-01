package agents;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import static com.sun.activation.registries.LogSupport.log;

public class BootAgent extends Agent {

    protected void setup() {
        log("I'm started.");

        this.addBehaviour(new OneShotBehaviour(this) {

            @Override
            public void action() {

                ContainerController cc = getContainerController();
                AgentController ac;

                // new agent
                try {
                    int capacity = 20;
                    int numOfOccupiedPlaces = 10;
                    float price = 2;
                    Object[] args = {capacity, numOfOccupiedPlaces, price};
                    ac = cc.createNewAgent("p0", "agents.ParkingManagerAgent", args);
                    ac.start();
                } catch (StaleProxyException e) {
                    e.printStackTrace();
                }

                // new agent
                try {
                    int capacity = 30;
                    int numOfOccupiedPlaces = 12;
                    float price = 3.2f;
                    Object[] args = {capacity, numOfOccupiedPlaces, price};
                    ac = cc.createNewAgent("p1", "agents.ParkingManagerAgent", args);
                    ac.start();
                } catch (StaleProxyException e) {
                    e.printStackTrace();
                }

                // new agent
                try {
                    int capacity = 10;
                    int numOfOccupiedPlaces = 8;
                    float price = 1.2f;
                    Object[] args = {capacity, numOfOccupiedPlaces, price};
                    ac = cc.createNewAgent("p2", "agents.ParkingManagerAgent", args);
                    ac.start();
                } catch (StaleProxyException e) {
                    e.printStackTrace();
                }

                // new agent
                try {
                    int capacity = 35;
                    int numOfOccupiedPlaces = 26;
                    float price = 2.5f;
                    Object[] args = {capacity, numOfOccupiedPlaces, price};
                    ac = cc.createNewAgent("p3", "agents.ParkingManagerAgent", args);
                    ac.start();
                } catch (StaleProxyException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
