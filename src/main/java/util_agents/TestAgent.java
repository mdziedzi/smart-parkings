package util_agents;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import ontology.ParkingOffer;
import ontology.SmartParkingsOntology;

import static parking_manager_agent.util.Constants.SD_TYPE;

/**
 * Agent used for testing purposes.
 */
public class TestAgent extends Agent {

    private AID[] parkings;

    private Codec codec = new SLCodec();
    private Ontology ontology = SmartParkingsOntology.getInstance();

    @Override
    protected void setup() {

        // Register language and ontology
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);

        searchForAgents();

        addBehaviour(new ParallelBehaviour() {
            @Override
            public void onStart() {
                super.onStart();
                addSubBehaviour(new OneShotBehaviour() {
                    @Override
                    public void action() {
                        System.out.println("1");

                        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                        for (AID parking : parkings)
                            msg.addReceiver(parking);
//                msg.setConversationId ("test");
//                msg.setReplyWith("test-reply");
                        prepareMsg(msg);
                        myAgent.send(msg);
                    }
                });
                addSubBehaviour(new CyclicBehaviour() {
                    @Override
                    public void action() {
                        ACLMessage receiveMsg = myAgent.receive();
                        if (receiveMsg != null) {
                            System.out.println(receiveMsg);
                        } else {
                            block();
                        }
                    }
                });
            }
        });

    }

    private void searchForAgents() {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(SD_TYPE);
        template.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(this, template);
            parkings = new AID[result.length];
            for (int i = 0; i < result.length; ++i) {
                parkings[i] = result[i].getName();
            }
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }

    private void prepareMsg(ACLMessage msg) {

        msg.setLanguage(codec.getName());
        msg.setOntology(ontology.getName());

        ParkingOffer parkingOffer = new ParkingOffer();

//        parkingOffer.setPrice((float) getDataStore().get("PRICE_IN_DOLLARS"));
//        parkingOffer.setLat((float) getDataStore().get(LATITUDE));
//        parkingOffer.setLon((float) getDataStore().get(LONGITUDE));
        try {
            this.getContentManager().fillContent(msg, parkingOffer);
        } catch (Codec.CodecException | OntologyException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void takeDown() {
        super.takeDown();
    }
}
