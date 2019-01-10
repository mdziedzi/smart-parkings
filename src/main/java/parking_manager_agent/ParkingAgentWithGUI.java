package parking_manager_agent;

import jade.gui.GuiEvent;
import parking_manager_agent.gui.ParkingManagerGUI;

public class ParkingAgentWithGUI extends ParkingAgent {

    private ParkingManagerGUI parkingManagerGUI;

    @Override
    protected void setup() {
        super.setup();

        // Init GUI
        parkingManagerGUI = new ParkingManagerGUI(this);
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
    }
}
