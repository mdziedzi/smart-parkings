package util_agents;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import parking.ParkingDevices;
import parking.ParkingDevicesImpl;
import parking_manager_agent.PriceAlgorithm;
import parking_manager_agent.PriceAlgorithmConstant;
import parking_manager_agent.PriceAlgorithmMutable;
import parking_manager_agent.util.Localization;

import java.util.Random;

import static com.sun.activation.registries.LogSupport.log;
import static parking_manager_agent.util.Constants.*;

public class BootAgent extends Agent {

    protected void setup() {
        log("I'm started.");

        this.addBehaviour(new OneShotBehaviour(this) {

            @Override
            public void action() {

                ContainerController cc = getContainerController();
                AgentController ac;

                // produce ParkingManagerAgents (Mutable price)
                for (int i = 0; i < N_GENERATED_PARKINGS; i++) {
                    // new parking_manager_agent
                    try {
                        int capacity = generateCapacity();
                        int numOfOccupiedPlaces = generateNumOfOccupiedPlaces();
                        double basePrice = generateBasePrice();
                        Localization localization = generateLocalization();
                        ParkingDevices parkingDevices = new ParkingDevicesImpl(capacity, numOfOccupiedPlaces, localization);
                        PriceAlgorithm priceAlgorithm = new PriceAlgorithmMutable();
                        Object[] args = {parkingDevices, priceAlgorithm};
                        ac = cc.createNewAgent("pmp" + i, "parking_manager_agent.ParkingManagerAgent", args);
                        ac.start();

                        try {
                            Thread.sleep(SLEEP_BEFORE_PARKINGS_PRODUCTION);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (StaleProxyException e) {
                        e.printStackTrace();
                    }
                }

                // produce ParkingManagerAgents (Constant price)
                for (int i = 0; i < N_GENERATED_PARKINGS; i++) {
                    // new parking_manager_agent
                    try {
                        int capacity = generateCapacity();
                        int numOfOccupiedPlaces = generateNumOfOccupiedPlaces();
                        double basePrice = generateBasePrice();
                        Localization localization = generateLocalization();
                        ParkingDevices parkingDevices = new ParkingDevicesImpl(capacity, numOfOccupiedPlaces, localization);
                        PriceAlgorithm priceAlgorithm = new PriceAlgorithmConstant();
                        Object[] args = {parkingDevices, priceAlgorithm};
                        ac = cc.createNewAgent("pcp" + i, "parking_manager_agent.ParkingManagerAgent", args);
                        ac.start();

                        try {
                            Thread.sleep(SLEEP_BEFORE_PARKINGS_PRODUCTION);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (StaleProxyException e) {
                        e.printStackTrace();
                    }
                }

//                // wait for asynchronous producing of Parkings
//                try {
//                    Thread.sleep(SLEEP_BEFORE_DRIVERS_PRODUCTION);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                // proudce DriverManagerAgents
//                for (int i = 0; i < N_GENERATED_DRIVERS; i++) {
//                    // new parking_manager_agent
//                    try {
//                        Localization localization = generateLocalization();
//                        Object[] args = {localization};
//                        ac = cc.createNewAgent("d" + i, "util_agents.DriverManagerAgent", args);
//                        ac.start();
//                    } catch (StaleProxyException e) {
//                        e.printStackTrace();
//                    }
//
//                    // give some time
//                    try {
//                        Thread.sleep(SLEEP_BETWEEN_EACH_DRIVER_PRODUCTION);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }

            }
        });
    }

    private Localization generateLocalization() {
        Random rand = new Random();
        double lat = Math.floor(rand.nextDouble() * (MAX_LONGITUDE - MIN_LONGITUDE) * LOCALIZATION_FLOOR_FACTOR) / LOCALIZATION_FLOOR_FACTOR + MIN_LATITUDE;
        double lon = Math.floor(rand.nextDouble() * (MAX_LATITUDE - MIN_LATITUDE) * LOCALIZATION_FLOOR_FACTOR) / LOCALIZATION_FLOOR_FACTOR + MIN_LONGITUDE;
        return new Localization(lat, lon);
    }

    private double generateBasePrice() {
        Random rand = new Random();
        return Math.floor(MIN_BASE_PRICE + rand.nextDouble() * (MAX_BASE_PRICE - MIN_BASE_PRICE) * FLOOR_FACTOR) / FLOOR_FACTOR;
    }

    // todo: delete
    private int generateNumOfOccupiedPlaces() {
        return OCCUPIED_PLACES;
    }

    private int generateCapacity() {
//        Random rand = new Random();
//        return rand.nextInt(MAX_CAPACITY + 1) + MIN_CAPACITY;
        return CAPACITY;
    }
}
