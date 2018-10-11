package agents;

import agents.gui.ParkingManagerGUI;
import agents.util.Localization;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import ontology.SmartParkingsOntology;

import java.util.ArrayList;

public class ParkingManagerAgent extends GuiAgent {

    private AID[] parkings;

    private ParkingManagerGUI parkingManagerGUI;

    private Codec codec = new SLCodec();
    private Ontology ontology = SmartParkingsOntology.getInstance();

    // model

    private int capacity;

    private int numOfOccupiedPlaces;

    private ArrayList<Boolean> parkingPlaces;

    private double price;

    private double basePrice;

    private Localization localization;

    @Override
    protected void setup() {

        // set args
        Object[] args = getArguments();
        if (args != null) {
            if (args.length > 0) this.capacity = (Integer) args[0];
            if (args.length > 1) this.numOfOccupiedPlaces = (Integer) args[1];
            if (args.length > 2) this.basePrice = (Double) args[2];
            if (args.length > 3) this.localization = (Localization) args[3];
            System.out.println("Created ParkingManagerAgent " + getAID().getName() + " with capacity " + this.capacity
                    + " liczba zajetych miejsc " + this.numOfOccupiedPlaces + " base price " + this.basePrice
                    + " lat: " + this.localization.getLatitude() + " lon: " + this.localization.getLongitude());
        }

        calculatePrice();

        // Register language and ontology
        ContentManager contentManager = getContentManager();
        contentManager.registerLanguage(codec);
        contentManager.registerOntology(ontology);

        // init GUI
        parkingManagerGUI = new ParkingManagerGUI(this);

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

//        addBehaviour(new TickerBehaviour(this, 10000) {
//            @Override
//            protected void onTick() {
//                // Update list of parkings
//                DFAgentDescription template = new DFAgentDescription();
//                ServiceDescription serviceDescription = new ServiceDescription();
//                serviceDescription.setType("parking-info");
//                template.addServices(serviceDescription);
//
//                try {
//                    DFAgentDescription[] result = DFService.search(myAgent, template);
//                    parkings = new AID[result.length];
//                    for (int i = 0; i < result.length; ++i) {
//                        parkings[i] = result[i].getName();
//                    }
//
//                    System.out.println("Parking-agent: " + getAID().getName() + "have found this parkings:");
//                    for (AID aid : parkings) {
//                        if (aid != this.myAgent.getAID()) {
//                            System.out.println(aid.getName());
//                        }
//                    }
//                    System.out.println();
//
//                    // sending msg
//                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
//                    for (AID aid : parkings) {
//                        msg.addReceiver(new AID(aid.getLocalName(), AID.ISLOCALNAME));
//                    }
//                    msg.setLanguage(codec.getName());
//                    msg.setOntology(ontology.getName());
//                    msg.setContent("My price is 2$");
//                    send(msg);
//
//                } catch (FIPAException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        addBehaviour(new CyclicBehaviour() {
//            @Override
//            public void action() {
//                ACLMessage msg = myAgent.receive();
//                if (msg != null) {
//                    if (!msg.getSender().getLocalName().equals(this.myAgent.getLocalName())) {
//                        // Message received. Process it
//                        String msgContent = msg.getContent();
//                        System.out.println(this.getAgent().getLocalName() + " Otrzymalem wiadomosc: " + msgContent + " od " + msg.getSender().getLocalName());
//                    }
//                } else {
//                    block();
//                }
//            }
//        });



    }

    private void calculatePrice() {
        price = Math.floor(basePrice * numOfOccupiedPlaces / capacity * 1e2) / 1e2;
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
        System.out.println("Parking-agent " + getAID().getName() + " terminating.");
    }

    protected void onGuiEvent(GuiEvent guiEvent) {

    }

    public int getCapacity() {
        return capacity;
    }

    public int getNumOfOccupiedPlaces() {
        return numOfOccupiedPlaces;
    }

    public double getPrice() {
        return price;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public Localization getLocalization() {
        return localization;
    }
}
