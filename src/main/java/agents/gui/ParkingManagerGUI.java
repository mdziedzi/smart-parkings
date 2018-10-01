package agents.gui;

import agents.ParkingManagerAgent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParkingManagerGUI extends JFrame implements ActionListener {

    private ParkingManagerAgent myAgent;

    private JPanel jPanel;

    public ParkingManagerGUI(ParkingManagerAgent parkingManagerAgent) {

        myAgent = parkingManagerAgent;

        setSize(200, 200);

        jPanel = new JPanel();

        jPanel.add(new JLabel("Num of total palces: " + myAgent.getCapacity()));
        jPanel.add(new JLabel("Num of occupied palces: " + myAgent.getNumOfOccupiedPlaces()));
        jPanel.add(new JLabel("Price: " + myAgent.getPrice()));

        this.add(jPanel);

        setTitle(myAgent.getLocalName());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

    }
}
