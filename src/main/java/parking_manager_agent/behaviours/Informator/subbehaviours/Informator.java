package parking_manager_agent.behaviours.Informator.subbehaviours;

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import ontology.ParkingOffer;
import ontology.SmartParkingsOntology;
import parking_manager_agent.behaviours.Informator.InformatorRole;

import static parking_manager_agent.DataStoreTypes.*;

public class Informator extends OneShotBehaviour {

    private final InformatorRole parentBehaviour;

    public Informator(InformatorRole informatorRole) {
        parentBehaviour = informatorRole;
    }

    @Override
    public void action() {
//        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), new MessageTemplate((MessageTemplate.MatchExpression) matchMsg -> {
//            ContentElement content = null;
//            try {
//                content = Informator.this.getAgent().getContentManager().extractContent(matchMsg);
//            } catch (Codec.CodecException | OntologyException e1) {
//                e1.printStackTrace();
//            }
//            return content instanceof ParkingOffer;
//        }));
//        ACLMessage msg = myAgent.receive(mt);
//        if (msg != null) {
//            System.out.println(msg);
//            ACLMessage replyMsg = msg.createReply();
//            prepareMsg(replyMsg);
//            getAgent().send(replyMsg);
//        } else {
//            block();
//        }

        System.out.println("Agent " + getAgent().getLocalName() + " waiting for requests...");
        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST), MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), new MessageTemplate((MessageTemplate.MatchExpression) matchMsg -> {
            ContentElement content = null;
            try {
                content = Informator.this.getAgent().getContentManager().extractContent(matchMsg);
            } catch (Codec.CodecException | OntologyException e1) {
                e1.printStackTrace();
            }
            return content instanceof ParkingOffer;
                })));

        parentBehaviour.addSubBehaviour(new AchieveREResponder(getAgent(), template) {
            protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
                System.out.println("Agent " + getAgent().getLocalName() + ": REQUEST received from " + request.getSender().getName() + ". Action is " + request.getContent());
                if (true) {
                    // We agree to perform the action. Note that in the FIPA-Request
                    // protocol the AGREE message is optional. Return null if you
                    // don't want to send it.
                    System.out.println("Agent " + getAgent().getLocalName() + ": Agree");
                    ACLMessage agree = request.createReply();
                    agree.setPerformative(ACLMessage.AGREE);
                    return agree;
                } else {
                    // We refuse to perform the action
                    System.out.println("Agent " + getAgent().getLocalName() + ": Refuse");
                    throw new RefuseException("check-failed");
                }
            }

            protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
//                if (performAction()) {
                System.out.println("Agent " + getAgent().getLocalName() + ": Action successfully performed");
                ACLMessage inform = request.createReply();
                inform.setPerformative(ACLMessage.INFORM);
                prepareMsg(inform);
                return inform;
//                }
//                else {
//                    System.out.println("Agent "+getAgent().getLocalName()+": Action failed");
//                    throw new FailureException("unexpected-error");
//                }
            }
        });


    }

    private void prepareMsg(ACLMessage msg) {
        msg.setLanguage(new SLCodec().getName());
        msg.setOntology(SmartParkingsOntology.getInstance().getName());

        ParkingOffer parkingOffer = new ParkingOffer();

        parkingOffer.setPrice((double) parentBehaviour.getDataStore().get(PRICE_IN_DOLLARS));
        parkingOffer.setLat((double) parentBehaviour.getDataStore().get(LATITUDE));
        parkingOffer.setLon((double) parentBehaviour.getDataStore().get(LONGITUDE));
        try {
            getAgent().getContentManager().fillContent(msg, parkingOffer);
        } catch (Codec.CodecException | OntologyException e) {
            e.printStackTrace();
        }
    }

}
