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
import parking_manager_agent.behaviours.Informator.InformatorRole;
import parking_manager_agent.behaviours.ParkingPlacesAdministrator.ParkingPlacesAdministratorRole;
import price_algorithm.PriceAlgorithm;

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
        sd.setType("parking-info");
        sd.setName("JADE-parking-info");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new InformatorRole(this, ParallelBehaviour.WHEN_ALL));
        addBehaviour(new ParkingPlacesAdministratorRole(this, ParallelBehaviour.WHEN_ALL));
//        addBehaviour(new ParkingMarketMonitorRole(this, ParallelBehaviour.WHEN_ALL));
//        addBehaviour(new PriceDecisionMakerRole(this, ParallelBehaviour.WHEN_ALL));


//        System.out.println("Agent " + getLocalName() + " waiting for CFP...");
//        MessageTemplate template = MessageTemplate.and(
//                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
//                MessageTemplate.MatchPerformative(ACLMessage.CFP));
//
//        addBehaviour(new ContractNetResponder(this, template) {
//            protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
//                System.out.println("Agent " + getLocalName() + ": CFP received from " + cfp.getSender().getName() + ". Action is " + cfp.getContent());
//                if (cfp.getContent() != null) { // todo
//                    // We provide a proposal
//                    System.out.println("Agent " + getLocalName() + ": Proposing " + price);
//                    ACLMessage proposePriceMsg = cfp.createReply();
//                    proposePriceMsg.setPerformative(ACLMessage.PROPOSE);
//                    prepareProposePriceMsg(proposePriceMsg);
//                    return proposePriceMsg;
//                } else {
//                    // We refuse to provide a proposal
//                    System.out.println("Agent " + getLocalName() + ": Refuse");
//                    throw new RefuseException("evaluation-failed");
//                }
//            }
//
//            protected ACLMessage prepareResultNotification(ACLMessage cfp, ACLMessage propose, ACLMessage accept) {
//                ACLMessage reply = accept.createReply();
//                if (bookParkingPlace(accept)) {
//                    System.out.println("Agent " + getLocalName() + ": Proposal accepted");
//                    System.out.println("Agent " + getLocalName() + ": Action successfully performed");
//                    reply.setPerformative(ACLMessage.INFORM);
//                    return reply;
//                } else {
//                    reply.setPerformative(ACLMessage.REFUSE);
//                    return reply;
//                }
//
//            }
//
//            protected void handleRejectProposal(ACLMessage reject) {
//                System.out.println("Agent " + getLocalName() + ": Proposal rejected");
//            }
//        });
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

//    private boolean bookParkingPlace(ACLMessage accept) {
//        if (numOfOccupiedPlaces >= capacity) {
//            return false;
//        } else {
//            numOfOccupiedPlaces++;
//            price = priceAlgorithm.calculatePrice(numOfOccupiedPlaces, capacity);
////            parkingManagerGUI.refreshView();
//            return true;
//        }
//    }

//    private void prepareProposePriceMsg(ACLMessage msg) {
//        msg.setLanguage(codec.getName());
//        msg.setOntology(ontology.getName());
//
//        ParkingOffer parkingOffer = new ParkingOffer();
//        parkingOffer.setPrice((float) price);
//        parkingOffer.setLat((float) localization.getLatitude());
//        parkingOffer.setLon((float) localization.getLongitude());
//        try {
//            getContentManager().fillContent(msg, parkingOffer);
//        } catch (Codec.CodecException e) {
//            e.printStackTrace();
//        } catch (OntologyException e) {
//            e.printStackTrace();
//        }
//    }

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
}
