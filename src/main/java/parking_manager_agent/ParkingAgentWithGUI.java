package parking_manager_agent;

import jade.gui.GuiEvent;
import parking_manager_agent.gui.ParkingGUI;

public class ParkingAgentWithGUI extends ParkingAgent {

    private ParkingGUI parkingGUI;

    @Override
    protected void setup() {
        super.setup();

        // Init GUI
        parkingGUI = new ParkingGUI(this);
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
    }
}
