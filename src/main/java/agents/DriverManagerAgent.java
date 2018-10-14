package agents;

import agents.gui.DriverManagerGUI;
import agents.util.Localization;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;
import ontology.ProposedPrice;
import ontology.SmartParkingsOntology;

import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

public class DriverManagerAgent extends GuiAgent {

    private DriverManagerGUI driverManagerGUI;

    private Localization localization;

    private AID[] aviableParkings;

    private ACLMessage currentMessage;

    private int nResponders;

    private Codec codec = new SLCodec();
    private Ontology ontology = SmartParkingsOntology.getInstance();

    @Override
    protected void setup() {

        // Register language and ontology
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);

        Object[] args = getArguments();
        if (args != null) {
            if (args.length > 0) this.localization = (Localization) args[0];
            System.out.println("Created DriverManagerAgent " + getAID().getName() + " with lat: " + this.localization.getLatitude()
                    + " lon: " + this.localization.getLongitude());
        }

        this.driverManagerGUI = new DriverManagerGUI(this);

        // Register the parking service in the yellow pages
        DFAgentDescription myTemplate = new DFAgentDescription();
        myTemplate.setName(getAID());
        ServiceDescription myServiceDescription = new ServiceDescription();
        myServiceDescription.setType("driver-info");
        myServiceDescription.setName("JADE-driver-info");
        myTemplate.addServices(myServiceDescription);
        try {
            DFService.register(this, myTemplate);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        // Update list of parkings
        DFAgentDescription searchTemplate = new DFAgentDescription();
        ServiceDescription templateServiceDescription = new ServiceDescription();
        templateServiceDescription.setType("parking-info");
        searchTemplate.addServices(templateServiceDescription);

        DFAgentDescription[] result = new DFAgentDescription[0];
        try {
            result = DFService.search(this, searchTemplate);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        aviableParkings = new AID[result.length];
        for (int i = 0; i < result.length; ++i) {
            aviableParkings[i] = result[i].getName();
        }

        System.out.println("Driver-agent: " + getAID().getName() + "have found this parkings:");
        for (AID aid : aviableParkings) {
            System.out.println(aid.getName());
        }
        System.out.println();
        nResponders = aviableParkings.length;

        currentMessage = new ACLMessage(ACLMessage.CFP);
        for (AID aid : aviableParkings) {
            currentMessage.addReceiver(aid);
        }

        currentMessage.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
        // give 2s for reply
        currentMessage.setReplyByDate(new Date(System.currentTimeMillis() + 2000));
        currentMessage.setContent("test msg");

        addBehaviour(new ContractNetInitiator(this, currentMessage) {

            protected void handlePropose(ACLMessage propose, Vector v) {
                System.out.println("Agent " + propose.getSender().getName() + " proposed " + propose.getContent());
            }

            protected void handleRefuse(ACLMessage refuse) {
                System.out.println("Agent " + refuse.getSender().getName() + " refused");
            }

            protected void handleFailure(ACLMessage failure) {
                if (failure.getSender().equals(myAgent.getAMS())) {
                    // FAILURE notification from the JADE runtime: the receiver
                    // does not exist
                    System.out.println("Responder does not exist");
                } else {
                    System.out.println("Agent " + failure.getSender().getName() + " failed");
                }
                // Immediate failure --> we will not receive a response from this agent
                nResponders--;
            }

            protected void handleAllResponses(Vector responses, Vector acceptances) {
                if (responses.size() < nResponders) {
                    // Some responder didn't reply within the specified timeout
                    System.out.println("Timeout expired: missing " + (nResponders - responses.size()) + " responses");
                }
                // Evaluate proposals.
                float bestProposal = Float.MAX_VALUE;
                AID bestProposer = null;
                ACLMessage accept = null;

                Enumeration e = responses.elements();

                while (e.hasMoreElements()) {

                    ACLMessage msg = (ACLMessage) e.nextElement();

                    if (msg.getPerformative() == ACLMessage.PROPOSE) {

                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                        acceptances.addElement(reply);

                        ContentElement content = null;
                        try {
                            content = getContentManager().extractContent(msg);
                        } catch (Codec.CodecException e1) {
                            e1.printStackTrace();
                        } catch (OntologyException e1) {
                            e1.printStackTrace();
                        }

                        if (content instanceof ProposedPrice) {
                            float proposal = ((ProposedPrice) content).getValue();
                            if (isBetter(proposal, bestProposal)) {
                                bestProposal = proposal;
                                bestProposer = msg.getSender();
                                accept = reply;
                            }
                        } else {
                            System.out.println("err");
                        }
                    }
                }
                // Accept the proposal of the best proposer
                if (accept != null) {
                    System.out.println("Accepting proposal " + bestProposal + " from responder " + bestProposer.getName());
                    accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                }
            }

            protected void handleInform(ACLMessage inform) {
                System.out.println("Agent " + inform.getSender().getName() + " successfully performed the requested action");
            }
        });
    }

    private boolean isBetter(float proposal, float bestProposal) {
        // choose the cheapest
        return proposal < bestProposal;
    }

    public Localization getLocalization() {
        return localization;
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
        System.out.println("Driver-agent " + getAID().getName() + " terminating.");
    }

    protected void onGuiEvent(GuiEvent guiEvent) {

    }
}
