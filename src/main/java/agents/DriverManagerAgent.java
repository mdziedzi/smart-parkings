package agents;

import agents.gui.DriverManagerGUI;
import agents.util.Localization;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;

public class DriverManagerAgent extends GuiAgent {

    private DriverManagerGUI driverManagerGUI;

    private Localization localization;

    private AID choosenParking;

    @Override
    protected void setup() {

        Object[] args = getArguments();
        if (args != null) {
            if (args.length > 0) this.localization = (Localization) args[0];
            System.out.println("Created DriverManagerAgent " + getAID().getName() + " with lat: " + this.localization.getLatitude()
                    + " lon: " + this.localization.getLongitude());
        }

        this.driverManagerGUI = new DriverManagerGUI(this);

        // Register the parking service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("driver-info");
        sd.setName("JADE-driver-info");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        this.addBehaviour(new ChooseParking());

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

    private class ChooseParking extends OneShotBehaviour {
        public void action() {
            //todo
        }
    }
}
