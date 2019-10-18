import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static javax.swing.JOptionPane.showInputDialog;

// fix images to fill full screen - code pixel locations based on

public class TrafficSimulatorGUI extends JFrame implements ActionListener {
    int map = 0; // change to get user input between 1 - 10 for map size
    int mapSize; // gets the size of the map
    int currentSize = 0;
    int numberOfCars = 2;  // number of Cars
    int numberOfBus = 2;  // number of Buses
    int numberOfBike = 2;  // number of Bikes
    int position = 0;
    int[] currentMap; // Makes a list for the current map
    int[] topMap = new int[map + 1];  // Makes a list for top of the map (vehicles entering)
    int[] bottomMap = new int[map + 1]; // Makes a list for bottom of the map (vehicles entering)
    int[] leftMap = new int[map + 1]; // Makes a list for left of the map (vehicles entering)
    int[] rightMap = new int[map + 1]; // Makes a list for right of the map (vehicles entering)
    int currentMapSize = 0; // current size of the map
    byte count = 0;
    ArrayList<Road> roadArrayList = new ArrayList<>(); // creates a array list for road pieces
    ArrayList<Vehicle> vehiclesArrayList = new ArrayList<>(); // creates a array list for vehicles
    ArrayList<TrafficLight> trafficLightArrayList = new ArrayList<>(); // creates a array list for traffic lights
    private JMenuBar menuBar = new JMenuBar();
    private JMenuItem menuNew = new JMenuItem("New");
    private JMenu menuCity = new JMenu("City");
    private JMenu menuSimulation = new JMenu("Simulation");
    private JMenuItem menuOpen = new JMenuItem("Open");
    private JMenuItem menuSave = new JMenuItem("Save");
    private JMenuItem menuEdit = new JMenuItem("Edit");
    private JMenuItem menuExit = new JMenuItem("Exit");
    private JMenuItem menuRun = new JMenuItem("Run");
    private JMenuItem menuPause = new JMenuItem("Pause");
    private JMenuItem menuStop = new JMenuItem("Stop");
    private JLabel labelStatusBar = new JLabel("Status Bar");
    private ImageIcon iconOneWay = createImageIcon("images/oneWayAdd.png");
    private ImageIcon iconOneWayTwo = createImageIcon("images/oneWayTwoAdd.png");
    private ImageIcon iconThreeWayOne = createImageIcon("images/threeWayOneAdd.png");
    private ImageIcon iconThreeWayTwo = createImageIcon("images/threeWayTwoAdd.png");
    private ImageIcon iconThreeWayThree = createImageIcon("images/threeWayThreeAdd.png");
    private ImageIcon iconThreeWayFour = createImageIcon("images/threeWayFourAdd.png");

    private ImageIcon car;
    private JLabel carLabel;

    private JRadioButton oneWayRoadButton = new JRadioButton("One Way Road", true);
    private JRadioButton threeWayRoadButton = new JRadioButton("Three Way Road");
    private JRadioButton fourWayRoadButton = new JRadioButton("Four Way Road");
    private boolean carDraw = false;
    private Image carImage;

    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private JPanel gridEastButtons = new JPanel(new GridLayout(4, 1,4,4));// change to map size
    private JLabel oneWayIcon = new JLabel();
    private JLabel threeWayIcon = new JLabel();
    private JLabel fourWayIcon = new JLabel();
    private JButton rotateButton = new JButton("Rotate");
    private JButton doneButton = new JButton("Done");
    private ButtonGroup mapButtons = new ButtonGroup();
    private JPanel grid;
    String name;
    int xPoints[] = {10,11,12,10};
    int yPoints[] = {10,11,12,10};
    String imgPath = "images/car.png";
    final BufferedImage image = ImageIO.read(getClass().getResourceAsStream(imgPath));





    private TrafficSimulatorGUI() throws IOException {

        setTitle("Traffic Simulator");
        Font font = new Font("Arial", Font.BOLD, 14);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setJMenuBar(menuBar);
        menuBar.add(menuCity);
        menuBar.add(menuSimulation);
        menuCity.add(menuNew);
        menuCity.add(menuOpen);
        menuCity.add(menuSave);
        menuCity.add(menuEdit);
        menuCity.add(menuExit);
        menuSimulation.add(menuRun);
        menuSimulation.add(menuPause);
        menuSimulation.add(menuStop);
        add(labelStatusBar, BorderLayout.PAGE_END);
        labelStatusBar.setFont(font);
        menuNew.addActionListener(this);
        doneButton.addActionListener(this);
        menuSave.addActionListener(this);
        menuOpen.addActionListener(this);
        menuEdit.addActionListener(this);
        menuExit.addActionListener(this);
        menuRun.addActionListener(this);

        //... Create a button group and add the buttons.
        mapButtons.add(oneWayRoadButton);
        mapButtons.add(threeWayRoadButton);
        mapButtons.add(fourWayRoadButton);
        JPanel wrapper = new JPanel();
        wrapper.add(gridEastButtons);
        add(wrapper, BorderLayout.EAST);
        gridEastButtons.add(oneWayRoadButton);
        oneWayIcon.setIcon(iconOneWay);
        gridEastButtons.add(oneWayIcon);
        gridEastButtons.add(threeWayRoadButton);
        threeWayIcon.setIcon(iconThreeWayOne);
        gridEastButtons.add(threeWayIcon);
        gridEastButtons.add(fourWayRoadButton);
        ImageIcon iconFourWay = createImageIcon("images/fourWayAdd.png");
        fourWayIcon.setIcon(iconFourWay);
        gridEastButtons.add(fourWayIcon);
        gridEastButtons.add(rotateButton);
        gridEastButtons.add(doneButton);
        rotateButton.addActionListener(this);
        gridEastButtons.setVisible(false);

    }


    private void printCar() {
        car = new ImageIcon(getClass().getResource("/images/car.png"));
        carLabel = new JLabel(car);
        add(carLabel,BorderLayout.CENTER);
       // carLabel.setLocation(10,10);
        //carLabel.setBounds(10,10,10,10);
    }

    public static void main(String[] args) throws IOException {
        TrafficSimulatorGUI frame = new TrafficSimulatorGUI();
        final int WIDTH = 1500;
        final int HEIGHT = 900;
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        new TrafficSimulatorGUI();
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        if (carDraw){
            g.drawImage(image,0,0,null);

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == menuNew) {
            if (map > 0) {
                grid.removeAll();
            }
            gridEastButtons.setVisible(true);
            currentMap = new int[mapSize];
            createEmptyMap(mapSize, currentSize, currentMap);
            // JOption Pane for map size
            labelStatusBar.setText("New Map Selected");
            map = Integer.parseInt(showInputDialog("Enter map size"));
            mapSize = map * map;
            // change to map size
            grid = new JPanel(new GridLayout(map, map));
            MapPieces[] button = new MapPieces[mapSize];
            grid.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
            for (int i = 0; i < (mapSize); i++) {
                button[i] = new MapPieces(oneWayRoadButton, threeWayRoadButton, fourWayRoadButton, oneWayIcon, threeWayIcon, fourWayIcon, position, roadArrayList, map);
                button[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                grid.add(button[i]);
                position++;
            }
            add(grid, BorderLayout.CENTER);
            validate();
            //setLayout(null);
            validate();
        }
        if (source == rotateButton) {
            //add code for cycling through images - possibly use a number system
            if (count == 0) {
                oneWayIcon.setIcon(iconOneWayTwo);
                threeWayIcon.setIcon(iconThreeWayTwo);
            } else if (count == 1) {
                oneWayIcon.setIcon(iconOneWay);
                threeWayIcon.setIcon(iconThreeWayThree);
            } else if (count == 2) {
                oneWayIcon.setIcon(iconOneWayTwo);
                threeWayIcon.setIcon(iconThreeWayFour);
            } else if (count == 3) {
                oneWayIcon.setIcon(iconOneWay);
                threeWayIcon.setIcon(iconThreeWayOne);
            }
            count++;
            if (count >= 4) {
                count = 0;
            }
        }
        if (source == doneButton) {
            gridEastButtons.setVisible(false);
            mapButtons.clearSelection();

        }
        if (source == menuSave) {
            try {
                saveRoad(roadArrayList);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (source == menuEdit) {
            gridEastButtons.setVisible(true);
        }
        if (source == menuExit) {
            System.exit(0);
        }
        if (source == menuOpen) {
            position = 0;
            roadArrayList.clear();
            if (map > 0) {
                remove(grid);
                 //grid.removeAll();
            }

            gridEastButtons.setVisible(false);
            mapButtons.clearSelection();

            try {
                roadArrayList = loadRoad.invoke();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }


            for (Road road : roadArrayList) {
                map = road.getSize();
                break;
            }

            mapSize = map * map;
            // change to map size
            grid = new JPanel(new GridLayout(map, map));
            MapPieces[] button = new MapPieces[mapSize];
            grid.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
            for (int i = 0; i < (mapSize); i++) {
                button[i] = new MapPieces(oneWayRoadButton, threeWayRoadButton, fourWayRoadButton, oneWayIcon, threeWayIcon, fourWayIcon, position, roadArrayList, map);
                button[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                grid.add(button[i]);
                position++;
            }
            add(grid, BorderLayout.CENTER);

            validate();
            //grid.repaint();
        }
        if (source == menuRun) {
            position = 0;
            if (map > 0) {
                remove(grid);
            }
            gridEastButtons.setVisible(false);
            mapButtons.clearSelection();
            for (Road road : roadArrayList) {
                map = road.getSize();
                break;
            }
            mapSize = map * map;
            // change to map size
            grid = new JPanel(new GridLayout(map, map));
            MapPiecesRun[] button = new MapPiecesRun[mapSize];
            grid.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
            for (int i = 0; i < (mapSize); i++) {
                button[i] = new MapPiecesRun(oneWayRoadButton, threeWayRoadButton, fourWayRoadButton, oneWayIcon, threeWayIcon, fourWayIcon, position, roadArrayList, map);
                //button[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                grid.add(button[i]);
                position++;
            }
            add(grid, BorderLayout.CENTER);

            validate();
            carDraw = true;
            repaint();
        }
    }

    private static void createEmptyMap(int mapSize, int currentSize, int[] currentMap) {
        // creates a empty map
        for (int i = 1; i < mapSize + 1; ) {
            currentMap[currentSize] = i;
            i = i + 1;
            currentSize += 1;
        }
    }

    private static void saveRoad(ArrayList<Road> roadArrayList) throws IOException { // change to be able to save as new map
        // saves map to to txt file
        String name = JOptionPane.showInputDialog("Enter a name for the map");
        FileOutputStream foutRoad = new FileOutputStream(name);
        ObjectOutputStream oosRoad = new ObjectOutputStream(foutRoad);
        oosRoad.writeObject(roadArrayList);
        foutRoad.close();

    }

    private static class loadRoad { // crashes program if map.txt not found, redesign so you can choose between maps and different map sizes
        // loads map.txt for simulation
        private static ArrayList<Road> invoke() throws IOException, ClassNotFoundException {
            ArrayList<Road> roadArrayList;
            String name = JOptionPane.showInputDialog("Enter the name of the map you would like to load(e.g. city1");
            FileInputStream readRoad = new FileInputStream(name);
            ObjectInputStream ois = new ObjectInputStream(readRoad);
            roadArrayList = (ArrayList<Road>) ois.readObject();
            readRoad.close();
            return roadArrayList;
        }
    }


    public static int readInt(FileInputStream in) {
        Scanner scanner = new Scanner(in);
        return scanner.nextInt();
    }
}
