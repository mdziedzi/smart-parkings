package agents;

import agents.util.Localization;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.Random;

import static com.sun.activation.registries.LogSupport.log;

public class BootAgent extends Agent {

    protected void setup() {
        log("I'm started.");

        this.addBehaviour(new OneShotBehaviour(this) {

            @Override
            public void action() {

                ContainerController cc = getContainerController();
                AgentController ac;

                // produce ParkingManagerAgents
                for (int i = 0; i < 5; i++) {
                    // new agent
                    try {
                        int capacity = generateCapacity();
                        int numOfOccupiedPlaces = generateNumOfOccupiedPlaces();
                        double basePrice = generateBasePrice();
                        Localization localization = generateLocalization();
                        Object[] args = {capacity, numOfOccupiedPlaces, basePrice, localization};
                        ac = cc.createNewAgent("p" + i, "agents.ParkingManagerAgent", args);
                        ac.start();
                    } catch (StaleProxyException e) {
                        e.printStackTrace();
                    }
                }

                // wait for asynchronous producing of Parkings
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // proudce DriverManagerAgents
                for (int i = 0; i < 20; i++) {
                    // new agent
                    try {
                        Localization localization = generateLocalization();
                        Object[] args = {localization};
                        ac = cc.createNewAgent("d" + i, "agents.DriverManagerAgent", args);
                        ac.start();
                    } catch (StaleProxyException e) {
                        e.printStackTrace();
                    }

                    // give some time
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private Localization generateLocalization() {
        Random rand = new Random();
        double lon = Math.floor(rand.nextDouble() * (100 - 0) * 1e2) / 1e2;
        double lat = Math.floor(rand.nextDouble() * (100 - 0) * 1e2) / 1e2;
        return new Localization(lon, lat);
    }

    private double generateBasePrice() {
        Random rand = new Random();
        // 10 - 30
        return Math.floor(1 + rand.nextDouble() * (10 - 1) * 1e2) / 1e2;
    }

    // todo: delete
    private int generateNumOfOccupiedPlaces() {
        return 0;
    }

    private int generateCapacity() {
        Random rand = new Random();
        // 10 - 30
        return rand.nextInt(31) + 10;
    }
}
