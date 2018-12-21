package parking_manager_agent.behaviours.Informator.subbehaviours;

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import ontology.ParkingOffer;
import ontology.SmartParkingsOntology;

public class Informator extends CyclicBehaviour {

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), new MessageTemplate((MessageTemplate.MatchExpression) matchMsg -> {
            ContentElement content = null;
            try {
                content = Informator.this.getAgent().getContentManager().extractContent(matchMsg);
            } catch (Codec.CodecException | OntologyException e1) {
                e1.printStackTrace();
            }
            return content instanceof ParkingOffer;
        }));
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            System.out.println(msg);
            ACLMessage replyMsg = msg.createReply();
            prepareMsg(replyMsg);
            getAgent().send(replyMsg);
        } else {
            block();
        }
    }

    private void prepareMsg(ACLMessage msg) {
        msg.setLanguage(new SLCodec().getName());
        msg.setOntology(SmartParkingsOntology.getInstance().getName());

        ParkingOffer parkingOffer = new ParkingOffer();

        parkingOffer.setPrice((float) getDataStore().get("price_in_dollars"));
        parkingOffer.setLat((float) getDataStore().get("lat"));
        parkingOffer.setLon((float) getDataStore().get("lon"));
        try {
            getAgent().getContentManager().fillContent(msg, parkingOffer);
        } catch (Codec.CodecException | OntologyException e) {
            e.printStackTrace();
        }
    }

}
