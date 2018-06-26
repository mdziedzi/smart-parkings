package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class ParkingManagerAgent extends Agent {

    private AID[] parkings;

    protected void setup() {
        System.out.println("Hello! Parking-agent " + getAID().getName() + " is ready.");

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
    }

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

}
