package parking_manager_agent.gui;

import util_agents.DriverTestAgent;

import javax.swing.*;

public class DriverManagerGUI extends JFrame {

    private DriverTestAgent agent;

    private JPanel jPanel;

    public DriverManagerGUI(DriverTestAgent driverTestAgent) {

        this.agent = driverTestAgent;

        setSize(200, 200);

        this.jPanel = new JPanel();

        jPanel.add(new JLabel("Lon: " + agent.getLocalization().getLongitude()));
        jPanel.add(new JLabel("Lat: " + agent.getLocalization().getLatitude()));

        this.add(jPanel);

        setTitle(agent.getLocalName());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocation(0, 300);

        setVisible(true);
    }
}
