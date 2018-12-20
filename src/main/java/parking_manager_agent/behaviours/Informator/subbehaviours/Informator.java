package parking_manager_agent.behaviours.Informator.subbehaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Informator extends CyclicBehaviour {


    @Override
    public void action() {
        MessageTemplate mt = new MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), MessageTemplate.Match)
        ACLMessage msg = myAgent.receive();
        if (msg != null) {
            System.out.println(msg);
            ACLMessage msgTx = msg.createReply();
            msgTx.setContent("Hello!");
//            send(msgTx);
        } else {
            block();
        }

    }

//    private void prepareMsg(ACLMessage msg) {
//        msg.setLanguage(new SLCodec().getName());
//        msg.setOntology(SmartParkingsOntology.getInstance().getName());
//
//        ParkingOffer parkingOffer = new ParkingOffer();
//        parkingOffer.setPrice((float) myAgent.getPrice());
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

}
