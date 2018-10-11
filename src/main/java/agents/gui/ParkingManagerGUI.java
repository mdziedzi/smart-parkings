package agents.gui;

import agents.ParkingManagerAgent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParkingManagerGUI extends JFrame implements ActionListener {

    private ParkingManagerAgent agent;

    private JPanel jPanel;

    public ParkingManagerGUI(ParkingManagerAgent parkingManagerAgent) {

        this.agent = parkingManagerAgent;

        setSize(200, 200);

        jPanel = new JPanel();

        jPanel.add(new JLabel("Num of total palces: " + agent.getCapacity()));
        jPanel.add(new JLabel("Num of occupied palces: " + agent.getNumOfOccupiedPlaces()));
        jPanel.add(new JLabel("Base price: " + agent.getBasePrice()));
        jPanel.add(new JLabel("Lon: " + agent.getLocalization().getLongitude()));
        jPanel.add(new JLabel("Lat: " + agent.getLocalization().getLatitude()));
        jPanel.add(new JLabel("Price: " + agent.getPrice()));

        this.add(jPanel);

        setTitle(agent.getLocalName());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

    }
}
