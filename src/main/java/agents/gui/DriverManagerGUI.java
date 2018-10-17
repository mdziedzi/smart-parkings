package agents.gui;

import agents.DriverManagerAgent;

import javax.swing.*;

public class DriverManagerGUI extends JFrame {

    private DriverManagerAgent agent;

    private JPanel jPanel;

    public DriverManagerGUI(DriverManagerAgent driverManagerAgent) {

        this.agent = driverManagerAgent;

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
