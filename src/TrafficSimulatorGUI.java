import javax.swing.*;
import java.awt.*;

public class TrafficSimulatorGUI extends JFrame {
    private TrafficSimulatorGUI(){
        setTitle("Traffic Simulator");
        Font font = new Font("Arial", Font.BOLD, 14);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuCity = new JMenu("City");
        menuBar.add(menuCity);
        JMenu menuSimulation = new JMenu("Simulation");
        menuBar.add(menuSimulation);

        JMenuItem menuNew = new JMenuItem("New");
        menuCity.add(menuNew);
        JMenuItem menuOpen = new JMenuItem("Open");
        menuCity.add(menuOpen);
        JMenuItem menuSave = new JMenuItem("City");
        menuCity.add(menuSave);
        JMenuItem menuExit = new JMenuItem("Exit");
        menuCity.add(menuExit);

        JMenuItem menuRun = new JMenuItem("Run");
        menuSimulation.add(menuRun);
        JMenuItem menuPause = new JMenuItem("Pause");
        menuSimulation.add(menuPause);
        JMenuItem menuStop = new JMenuItem("Stop");
        menuSimulation.add(menuStop);

        JLabel label1 = new JLabel("Status Bar");
        add(label1,BorderLayout.PAGE_END);
        label1.setFont(font);

    }

    public static void main(String[] args) {


        TrafficSimulatorGUI frame = new TrafficSimulatorGUI();
        JPanel grid = new JPanel( new GridLayout(3, 4) );
        JPanel wrapper = new JPanel();
        wrapper.add(grid);
        frame.add(wrapper, BorderLayout.CENTER);

        JLabel a;
        a = new JLabel("Test 1");
        grid.add(a);

        final int WIDTH = 800;
        final int HEIGHT = 750;
        frame.setSize(WIDTH,HEIGHT);
        frame.setVisible(true);
        new TrafficSimulatorGUI();
    }
}
