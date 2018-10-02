package agents;

import agents.gui.ParkingManagerGUI;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

public class ParkingManagerAgent extends GuiAgent {

    private AID[] parkings;

    private ParkingManagerGUI parkingManagerGUI;

    // model

    private int capacity;

    private int numOfOccupiedPlaces;

    private ArrayList<Boolean> parkingPlaces;

    private float price;

    @Override
    protected void setup() {

        Object[] args = getArguments();
        if (args != null) {
            if (args.length > 0) this.capacity = (Integer) args[0];
            if (args.length > 1) this.numOfOccupiedPlaces = (Integer) args[1];
            if (args.length > 2) this.price = (Float) args[2];
            System.out.println("Created ParkingManagerAgent " + getAID().getName() + " with capacity " + this.capacity + " liczba zajetych miejsc " + this.numOfOccupiedPlaces + " price " + this.price);
        }

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

        addBehaviour(new TickerBehaviour(this, 10000) {
            @Override
            protected void onTick() {
                // Update list of parkings
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription serviceDescription = new ServiceDescription();
                serviceDescription.setType("parking-info");
                template.addServices(serviceDescription);

                try {
                    DFAgentDescription[] result = DFService.search(myAgent, template);
                    parkings = new AID[result.length];
                    for (int i = 0; i < result.length; ++i) {
                        parkings[i] = result[i].getName();
                    }

                    System.out.println("Parking-agent: " + getAID().getName() + "have found this parkings:");
                    for (AID aid : parkings) {
                        System.out.println(aid.getName());
                    }
                    System.out.println();

                    // sending msg
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    for (AID aid : parkings) {
                        msg.addReceiver(new AID(aid.getLocalName(), AID.ISLOCALNAME));
                    }
                    msg.setLanguage("English");
                    msg.setOntology("parking-ontology");
                    msg.setContent("My price is 2$");
                    send(msg);

                } catch (FIPAException e) {
                    e.printStackTrace();
                }
            }
        });

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = myAgent.receive();
                if (msg != null) {
                    // Message received. Process it
                    String msgContent = msg.getContent();
                    System.out.println(this.getAgent().getLocalName() + " Otrzymalem wiadomosc: " + msgContent + " od " + msg.getSender().getLocalName());
                } else {
                    block();
                }
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

    public float getPrice() {
        return price;
    }
}
