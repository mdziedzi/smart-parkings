package parking_manager_agent.gui;

import parking_manager_agent.ParkingAgent;
import parking_manager_agent.util.WindowsCounter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI of parking agent. Shows the the state of parking in real time.
 */
public class ParkingGUI extends JFrame implements ActionListener {

    private ParkingAgent agent;

    private JPanel jPanel;

    private JLabel numOfTotalPlaces;
    private JLabel numOfOccupiedPlaces;
    private JLabel lon;
    private JLabel lat;
    private JLabel price;

    public ParkingGUI(ParkingAgent parkingAgent) {

        this.agent = parkingAgent;

        setSize(200, 200);

        jPanel = new JPanel();

        numOfTotalPlaces = new JLabel("Num of total palces: " + agent.getDataRepository().getCapacity());
        numOfOccupiedPlaces = new JLabel("Num of occupied palces: " + agent.getDataRepository().getnOccupiedPlaces());
        lon = new JLabel("Lon: " + agent.getDataRepository().getLocalization().getLongitude());
        lat = new JLabel("Lat: " + agent.getDataRepository().getLocalization().getLatitude());
        price = new JLabel("Price: " + agent.getDataRepository().getPrice() + "$");

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

    /**
     * Used for refresh view after the agent state has changed.
     */
    public void refreshView() {
        numOfOccupiedPlaces.setText("Num of occupied palces: " + String.valueOf(this.agent.getDataRepository().getnOccupiedPlaces()));
        price.setText("Price: " + String.valueOf(agent.getDataRepository().getPrice()) + "$");
    }
}
