package parking_manager_agent;

import exceptions.EffectorsConnectionFailure;
import exceptions.SensorsConnectionFailure;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import ontology.SmartParkingsOntology;
import parking_devices.ConnectionCallback;
import parking_devices.effectors.EffectorsInterface;
import parking_devices.sensors.SensorsInterface;
import parking_manager_agent.behaviours.InformatorRole.InformatorRole;
import parking_manager_agent.behaviours.ReservationistRole.ReservationistRole;
import price_algorithm.PriceAlgorithm;

import static parking_manager_agent.util.Constants.SD_NAME;
import static parking_manager_agent.util.Constants.SD_TYPE;

public class ParkingManagerAgent extends GuiAgent {

    private AID[] parkings;

//    private ParkingManagerGUI parkingManagerGUI;

    private Codec codec = new SLCodec();
    private Ontology ontology = SmartParkingsOntology.getInstance();

    // model
    private ParkingAgentDataRepository dataRepisitory;

    private SensorsInterface sensorsInterface;
    private EffectorsInterface effectorsInterface;

    private PriceAlgorithm priceAlgorithm;

    @Override
    protected void setup() {

        // Register language and ontology
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);

        // set args
        Object[] args = getArguments();
        if (args != null) {
            if (args.length > 1) {
                this.sensorsInterface = (SensorsInterface) args[0];
                this.effectorsInterface = (EffectorsInterface) args[1];
            }
            if (args.length > 2) {
                this.priceAlgorithm = (PriceAlgorithm) args[2];
            }
        }

        this.dataRepisitory = initParkingData();

        this.dataRepisitory.setPriceInDollars(priceAlgorithm.calculatePrice(dataRepisitory.getnOccupiedPlaces(),
                dataRepisitory.getCapacity()));


        // init GUI
//        parkingManagerGUI = new ParkingManagerGUI(this);

        // Register the parking service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(SD_TYPE);
        sd.setName(SD_NAME);
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new InformatorRole(this, ParallelBehaviour.WHEN_ALL));
//        addBehaviour(new ParkingPlacesAdministratorRole(this, ParallelBehaviour.WHEN_ALL));
        addBehaviour(new ReservationistRole(this, ParallelBehaviour.WHEN_ALL));
    }

    private ParkingAgentDataRepository initParkingData() {
        checkSensorsConnection();
        checkEffectorsConnection();
        return new ParkingAgentDataRepository(
                sensorsInterface.getCapacity(),
                sensorsInterface.getNOccupiedPlaces(),
                sensorsInterface.getPriceInDollars(),
                sensorsInterface.getLocalization());

    }

    private void checkEffectorsConnection() {
        effectorsInterface.checkConnection(new ConnectionCallback() {

            @Override
            public void onConnectionSuccess() {
                //do nothing
            }

            @Override
            public void onConnectionFailure() throws EffectorsConnectionFailure {
                throw new EffectorsConnectionFailure();
            }
        });
    }

    private void checkSensorsConnection() {
        sensorsInterface.checkConnection(new ConnectionCallback() {
            @Override
            public void onConnectionSuccess() {
                //do nothing
            }

            @Override
            public void onConnectionFailure() throws SensorsConnectionFailure {
                throw new SensorsConnectionFailure();
            }
        });

    }

    @Override
    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        // Printout a dismissal message
        System.out.println("Parking-parking_manager_agent " + getAID().getName() + " terminating.");
    }

    protected void onGuiEvent(GuiEvent guiEvent) {

    }

    public ParkingAgentDataRepository getDataRepository() {
        return dataRepisitory;
    }

    public void bookParkingPlace() {
        effectorsInterface.blockParkingPlace();
    }

    public PriceAlgorithm getPriceAlgorithm() {
        return priceAlgorithm;
    }

    public void setNewPrice(double newPrice) {
        dataRepisitory.setPriceInDollars(newPrice);
    }
}
