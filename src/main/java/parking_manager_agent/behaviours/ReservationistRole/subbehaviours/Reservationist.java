package parking_manager_agent.behaviours.ReservationistRole.subbehaviours;

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import ontology.ReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parking_manager_agent.behaviours.ReservationistRole.ReservationistRole;

import static parking_manager_agent.DataStoreTypes.CAPACITY;
import static parking_manager_agent.DataStoreTypes.N_OCCUPIED_PLACES;

/**
 * Subbehaviour of ReservationistRole.
 * It listens for specific type of message - request for reservation.
 * After specific request is received it books a parking place and send confirmation.
 * If the safeness responsibilities will be violated agent sends rejection
 * Communication i based on Request Protocol.
 */
public class Reservationist extends OneShotBehaviour {

    private static final Logger log = LoggerFactory.getLogger(Reservationist.class);

    private final ReservationistRole parentBehaviour;

    public Reservationist(ReservationistRole reservationistRole) {
        parentBehaviour = reservationistRole;
    }

    @Override
    public void action() {
        System.out.println("Agent " + getAgent().getLocalName() + " waiting for reservation requests...");
        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST), MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), new MessageTemplate((MessageTemplate.MatchExpression) matchMsg -> {
                    ContentElement content = null;
                    try {
                        log.debug("inside match message template");
                        content = Reservationist.this.getAgent().getContentManager().extractContent(matchMsg);
                    } catch (Codec.CodecException | OntologyException e1) {
                        e1.printStackTrace();
                    }
                    return content instanceof ReservationRequest;
                })));

        parentBehaviour.addSubBehaviour(new AchieveREResponder(getAgent(), template) {
            protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
                System.out.println("Agent " + getAgent().getLocalName() + ": REQUEST reservation received from " + request.getSender().getName() + ". Action is " + request.getContent());
                if (isReservationPossible()) {
                    // We agree to perform the action. Note that in the FIPA-Request
                    // protocol the AGREE message is optional. Return null if you
                    // don't want to send it.
                    System.out.println("Agent " + getAgent().getLocalName() + ": Agree");
                    ACLMessage agree = request.createReply();
                    agree.setPerformative(ACLMessage.AGREE);
                    performReservation();
                    return agree;
                } else {
                    // We refuse to perform the action
                    System.out.println("Agent " + getAgent().getLocalName() + ": Refuse");
                    throw new RefuseException("check-failed");
                }
            }

            protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
                System.out.println("Agent " + getAgent().getLocalName() + ": Action successfully performed");
                ACLMessage inform = request.createReply();
                inform.setPerformative(ACLMessage.INFORM);
//                prepareMsg(inform);
                return inform;
            }
        });
    }

    /**
     * Preforms reservation
     */
    private void performReservation() {
        parentBehaviour.bookParkingPlace();
        //todo
    }

    /**
     * Checks if the reservation is possible - chcecks if he num of occupied price is less than capacity.
     *
     * @return True if the reservation is possible.
     */
    private boolean isReservationPossible() {
        int capacity = (int) parentBehaviour.getDataStore().get(CAPACITY);
        int nOccupiedPlaces = (int) parentBehaviour.getDataStore().get(N_OCCUPIED_PLACES);
        return nOccupiedPlaces < capacity;
    }

}
