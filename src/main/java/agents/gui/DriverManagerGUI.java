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

        this.add(jPanel);

        setTitle(agent.getLocalName());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocation(0, 250);

        setVisible(true);

    }

}
