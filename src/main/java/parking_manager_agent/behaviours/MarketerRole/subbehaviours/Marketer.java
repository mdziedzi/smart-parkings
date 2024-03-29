package parking_manager_agent.behaviours.MarketerRole.subbehaviours;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import ontology.ParkingOffer;
import ontology.SmartParkingsOntology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parking_manager_agent.behaviours.MarketerRole.MarketerRole;

import static parking_manager_agent.DataStoreTypes.*;

/**
 * Subbehaviour of MarketerRole.
 * It listens for specific type of message - request for parking offer.
 * After specific request is received it sends the current offer.
 * Communication i based on CNP. Agent do not send offer if the parking is full.
 */
public class Marketer extends OneShotBehaviour {

    private static final Logger log = LoggerFactory.getLogger(Marketer.class);

    private final MarketerRole marketerRole;

    public Marketer(MarketerRole marketerRole) {
        super();
        this.marketerRole = marketerRole;
    }

    @Override
    public void action() {
        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
                MessageTemplate.MatchPerformative(ACLMessage.CFP));

        ((ParallelBehaviour) getParent()).addSubBehaviour(new ContractNetResponder(myAgent, template) {
            protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
                System.out.println("Agent " + myAgent.getLocalName() + ": CFP received from " + cfp.getSender().getName() + ". Action is " + cfp.getContent());
                if (cfp.getContent() != null) { // todo
                    // We provide a proposal
                    System.out.println("Agent " + myAgent.getLocalName() + ": Proposing " + getParent().getDataStore().get(PRICE_IN_DOLLARS));
                    ACLMessage proposePriceMsg = cfp.createReply();
                    proposePriceMsg.setPerformative(ACLMessage.PROPOSE);
                    prepareProposePriceMsg(proposePriceMsg);
                    return proposePriceMsg;
                } else {
                    // We refuse to provide a proposal
                    System.out.println("Agent " + myAgent.getLocalName() + ": Refuse");
                    throw new RefuseException("evaluation-failed");
                }
            }

            protected ACLMessage prepareResultNotification(ACLMessage cfp, ACLMessage propose, ACLMessage accept) {
                ACLMessage reply = accept.createReply();
                if (isBookingPermitted(accept)) {
                    System.out.println("Agent " + myAgent.getLocalName() + ": Proposal accepted");
                    System.out.println("Agent " + myAgent.getLocalName() + ": Action successfully performed");
                    reply.setPerformative(ACLMessage.INFORM);
                    return reply;
                } else {
                    reply.setPerformative(ACLMessage.REFUSE);
                    return reply;
                }

            }

            protected void handleRejectProposal(ACLMessage reject) {
                System.out.println("Agent " + myAgent.getLocalName() + ": Proposal rejected");
            }
        });
    }

    /**
     * Fill the message with offer data.
     *
     * @param msg Message to fill.
     * @see ParkingOffer
     */
    private void prepareProposePriceMsg(ACLMessage msg) {
        msg.setLanguage(new SLCodec().getName());
        msg.setOntology(SmartParkingsOntology.getInstance().getName());

        log.debug(String.valueOf((double) marketerRole.getDataStore().get(PRICE_IN_DOLLARS)));
        log.debug(String.valueOf((double) marketerRole.getDataStore().get(LATITUDE)));
        log.debug(String.valueOf((double) marketerRole.getDataStore().get(LONGITUDE)));

        ParkingOffer parkingOffer = new ParkingOffer();
        parkingOffer.setPrice((double) marketerRole.getDataStore().get(PRICE_IN_DOLLARS));
        parkingOffer.setLat((double) marketerRole.getDataStore().get(LATITUDE));
        parkingOffer.setLon((double) marketerRole.getDataStore().get(LONGITUDE));
        try {
            getAgent().getContentManager().fillContent(msg, parkingOffer);
        } catch (Codec.CodecException | OntologyException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the supposed reservation is possible.
     * @param accept Message used to make decision.
     * @return True - if parking is permitted, if not it returns false.
     */
    private boolean isBookingPermitted(ACLMessage accept) {
        marketerRole.isBookingPermitted();
        return (int) marketerRole.getDataStore().get(N_OCCUPIED_PLACES) + 1 < (int) marketerRole.getDataStore().get(CAPACITY);
    }
}
