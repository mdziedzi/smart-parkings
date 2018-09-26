package agents.gui;

import agents.ParkingManagerAgent;

import javax.swing.*;
import java.awt.*;

public class ParkingManagerGUI extends JFrame {

    private ParkingManagerAgent myAgent;

    public ParkingManagerGUI(ParkingManagerAgent parkingManagerAgent) {

        myAgent = parkingManagerAgent;

        setSize(200, 200);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().add(new JLabel("testtest"), BorderLayout.CENTER);

        setTitle(myAgent.getLocalName());

        setVisible(true);
    }
}
