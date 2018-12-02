package agents.gui;

import agents.ParkingManagerAgent;
import agents.util.WindowsCounter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParkingManagerGUI extends JFrame implements ActionListener {

    private ParkingManagerAgent agent;

    private JPanel jPanel;

    private JLabel numOfTotalPlaces;
    private JLabel numOfOccupiedPlaces;
    private JLabel lon;
    private JLabel lat;
    private JLabel price;

    public ParkingManagerGUI(ParkingManagerAgent parkingManagerAgent) {

        this.agent = parkingManagerAgent;

        setSize(200, 200);

        jPanel = new JPanel();

        numOfTotalPlaces = new JLabel("Num of total palces: " + agent.getCapacity());
        numOfOccupiedPlaces = new JLabel("Num of occupied palces: " + agent.getNumOfOccupiedPlaces());
        lon = new JLabel("Lon: " + agent.getLocalization().getLongitude());
        lat = new JLabel("Lat: " + agent.getLocalization().getLatitude());
        price = new JLabel("Price: " + agent.getPrice() + "$");

        jPanel.add(numOfTotalPlaces);
        jPanel.add(numOfOccupiedPlaces);
        jPanel.add(lon);
        jPanel.add(lat);
        jPanel.add(price);

        jPanel.setBackground(new Color(33, 123, 5));

        this.add(jPanel);

        setTitle(agent.getLocalName());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocation(WindowsCounter.getInstance().getCounter() * 200, 0);
        WindowsCounter.getInstance().increment();

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

    }

    public void refreshView() {
        numOfOccupiedPlaces.setText("Num of occupied palces: " + String.valueOf(this.agent.getNumOfOccupiedPlaces()));
        price.setText("Price: " + String.valueOf(agent.getPrice()) + "$");
    }
}
