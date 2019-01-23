package parking_manager_agent;

import exceptions.EffectorsConnectionFailure;
import exceptions.SensorsConnectionFailure;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import ontology.SmartParkingsOntology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parking_devices.ConnectionCallback;
import parking_devices.effectors.EffectorsInterface;
import parking_devices.sensors.SensorsInterface;
import parking_manager_agent.behaviours.InformatorRole.InformatorRole;
import parking_manager_agent.behaviours.MarketerRole.MarketerRole;
import parking_manager_agent.behaviours.ReservationistRole.ReservationistRole;
import price_algorithm.PriceAlgorithm;

import static parking_manager_agent.util.Constants.SD_NAME;
import static parking_manager_agent.util.Constants.SD_TYPE;

/**
 * Agent that manages physical parking environment.
 * todo: type something more
 */
public class ParkingAgent extends GuiAgent {

    private static final Logger log = LoggerFactory.getLogger(ParkingAgent.class);

    private Codec codec = new SLCodec();
    private Ontology ontology = SmartParkingsOntology.getInstance();

    private ParkingAgentDataRepository dataRepository;

    private SensorsInterface sensorsInterface;
    private EffectorsInterface effectorsInterface;
    private PriceAlgorithm priceAlgorithm;

    @Override
    protected void setup() {
        // Register language and ontology.
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);

        // Set fields from agent arguments.
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

        this.dataRepository = initParkingData();

        // set initial price
        this.dataRepository.setPrice(priceAlgorithm.calculatePrice(dataRepository.getnOccupiedPlaces(),
                dataRepository.getCapacity()));

        // Register the parking service in the yellow pages
        registerParkingAgentInDF();

        // Add GAIA project roles as a subbehaviours to agent.
        addBehaviour(new InformatorRole(this, ParallelBehaviour.WHEN_ALL));
        addBehaviour(new MarketerRole(this, ParallelBehaviour.WHEN_ALL));
        addBehaviour(new ReservationistRole(this, ParallelBehaviour.WHEN_ALL));
    }

    /**
     * Registers parking service in Directory Facilitator aka Yellow Pages.
     */
    private void registerParkingAgentInDF() {
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
    }

    /**
     * Checks connection with sensors and effectors.
     *
     * @return Initialized ParkingAgentDataRepository.
     */
    private ParkingAgentDataRepository initParkingData() {
        checkSensorsConnection();
        checkEffectorsConnection();
        return new ParkingAgentDataRepository(
                sensorsInterface.getCapacity(),
                sensorsInterface.getNOccupiedPlaces(),
                sensorsInterface.getPrice(),
                sensorsInterface.getLocalization());
    }

    /**
     * Checks connection with effectors and handle callback result.
     */
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

    /**
     * Checks connection with sensors and handle callback result.
     */
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


    public void isBookingPermitted() {
        effectorsInterface.isBookingPermitted();
        log.debug("isBookingPermitted");
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

    /**
     * Simple getter method returning ParkingAgentDataRepository
     * @return ParkingAgentDataRepository
     */
    public ParkingAgentDataRepository getDataRepository() {
        return dataRepository;
    }

    /**
     * Simple getter method returning PriceAlgorithm
     * @return PriceAlgorithm
     */
    public PriceAlgorithm getPriceAlgorithm() {
        return priceAlgorithm;
    }

    public void setNewPrice(double newPrice) {
        dataRepository.setPrice(newPrice);
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {

    }

    public void bookParkingPlace() {
        effectorsInterface.blockParkingPlace();
        updateView();
    }

    protected void updateView() {
    }
}
