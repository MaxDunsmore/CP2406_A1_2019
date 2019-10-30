import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static javax.swing.JOptionPane.showInputDialog;

public class TrafficSimulatorGUI extends JFrame implements ActionListener {
    private int map = 0; // change to get user input between 1 - 10 for map size
    private int mapSize = 0; // gets the size of the map
    private int position = 0;
    private int currentMapSize; // current size of the map
    private int carH = 0;
    private int carW = 0;
    private int carHL = 0;
    private int carWL = 0;
    private int busH = 0;
    private int busWL = 0;
    private int bikeWL = 0;
    int bikeH = 0;

    int carHCalc;
    int carWCalc;

    private int slowDownSpeedBottomT = 800;
    private int stopDistanceBottomT = 900;
    private int slowDownIntersection = 400;
    private int stopIntersection = 500;
    private int slowDownSecond3Way = 450;
    private int intersectionTurn = 550;
    private int roadLength = 1000;
    private int stopVehicle = 300;
    private int roadLocation = 1;
    private int max3Way = 2;
    private int vehicles;
    private int vehiclesOnMap;
    private int exitIntersection = 600;
    private int numberOfCars = 8;
    private int numberOfBus = 0;
    private int numberOfBike = 0;
    private int enterMapSpeed;
    private int enterMapSpeedCount = 0;
    private int avgSpeed;
    int vehicleNextLocation;
    int nextLocationBrake;
    private int[] topMap;
    private int[] bottomMap;
    private int[] leftMap;
    private int[] rightMap;
    private byte rotateButtonCount = 0;
    private ArrayList<Road> roadArrayList = new ArrayList<>(); // creates a array list for road pieces
    private ArrayList<Vehicle> vehiclesArrayList = new ArrayList<>(); // creates a array list for vehicles
    private ArrayList<TrafficLight> trafficLightArrayList = new ArrayList<>(); // creates a array list for traffic lights
    private JMenuItem menuNew = new JMenuItem("New");
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
    private JRadioButton oneWayRoadButton = new JRadioButton("One Way Road", true);
    private JRadioButton threeWayRoadButton = new JRadioButton("Three Way Road");
    private JRadioButton fourWayRoadButton = new JRadioButton("Four Way Road");
    private JRadioButton trafficLightButton = new JRadioButton("Traffic Light");

    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private JPanel gridEastButtons = new JPanel(new GridLayout(6, 1, 4, 4));// change to map size
    private JLabel oneWayIcon = new JLabel();
    private JLabel threeWayIcon = new JLabel();
    private JLabel fourWayIcon = new JLabel();
    private JLabel trafficLightIcon = new JLabel();
    private JButton rotateButton = new JButton("Rotate");
    private JButton doneButton = new JButton("Done");
    private ButtonGroup mapButtons = new ButtonGroup();
    private JPanel grid;
    private String imgPathCarD = "images/carDown.png";
    private final BufferedImage imageCarD = ImageIO.read(getClass().getResourceAsStream(imgPathCarD));
    private String imgPathBusD = "images/busDown.png";
    private final BufferedImage imageBusD = ImageIO.read(getClass().getResourceAsStream(imgPathBusD));
    private String imgPathBikeD = "images/bikeDown.png";
    private final BufferedImage imageBikeD = ImageIO.read(getClass().getResourceAsStream(imgPathBikeD));
    private String imgPathCarL = "images/carLeft.png";
    private final BufferedImage imageCarL = ImageIO.read(getClass().getResourceAsStream(imgPathCarL));
    private String imgPathBusL = "images/busLeft.png";
    private final BufferedImage imageBusL = ImageIO.read(getClass().getResourceAsStream(imgPathBusL));
    private String imgPathBikeL = "images/bikeLeft.png";
    private final BufferedImage imageBikeL = ImageIO.read(getClass().getResourceAsStream(imgPathBikeL));
    private String imgPathCarR = "images/carRight.png";
    private final BufferedImage imageCarR = ImageIO.read(getClass().getResourceAsStream(imgPathCarR));
    private String imgPathBusR = "images/busRight.png";
    private final BufferedImage imageBusR = ImageIO.read(getClass().getResourceAsStream(imgPathBusR));
    private String imgPathBikeR = "images/bikeRight.png";
    private final BufferedImage imageBikeR = ImageIO.read(getClass().getResourceAsStream(imgPathBikeR));
    private String imgPathCarU = "images/carUp.png";
    private final BufferedImage imageCarU = ImageIO.read(getClass().getResourceAsStream(imgPathCarU));
    private String imgPathBusU = "images/busUp.png";
    private final BufferedImage imageBusU = ImageIO.read(getClass().getResourceAsStream(imgPathBusU));
    private String imgPathBikeU = "images/bikeUp.png";
    private final BufferedImage imageBikeU = ImageIO.read(getClass().getResourceAsStream(imgPathBikeU));
    private String imgPathTRed = "images/trafficLightRed.PNG";
    private final BufferedImage imageTRed = ImageIO.read(getClass().getResourceAsStream(imgPathTRed));
    private String imgPathTGreen = "images/trafficLightGreen.PNG";
    private final BufferedImage imageTGreen = ImageIO.read(getClass().getResourceAsStream(imgPathTGreen));
    private String name;
    private double gridSizeWidth;
    private double gridSizeHeight;
    private int max4WayRandom = 3;
    private char down = 'd';
    private char up = 'u';
    private char left = 'l';
    private char right = 'r';
    private char start = 's';
    private char finish = 'c';
    private boolean accelerateVehicle = true;
    private boolean moveVehicle = true;
    private Random rand = new Random();
    private boolean checkMapLocation = true;
    private Timer timer;
    double nextRoadLocation;

    private TrafficSimulatorGUI() throws IOException {
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
        menuStop.addActionListener(this);
        menuPause.addActionListener(this);
        //... Create a button group and add the buttons.
        mapButtons.add(oneWayRoadButton);
        mapButtons.add(threeWayRoadButton);
        mapButtons.add(fourWayRoadButton);
        mapButtons.add(trafficLightButton);
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
        gridEastButtons.add(trafficLightButton);
        ImageIcon iconTrafficLight = createImageIcon("images/trafficLightAdd.png");
        trafficLightIcon.setIcon(iconTrafficLight);
        trafficLightIcon.setHorizontalAlignment(JLabel.CENTER);
        gridEastButtons.add(trafficLightIcon);
        gridEastButtons.add(rotateButton);
        gridEastButtons.add(doneButton);
        rotateButton.addActionListener(this);
        gridEastButtons.setVisible(false);
        currentMapSize = 0;
    }

    public static void main(String[] args) throws IOException {
        TrafficSimulatorGUI frame = new TrafficSimulatorGUI();
        final int WIDTH = 1200;
        final int HEIGHT = 1000;
        frame.setSize(WIDTH, HEIGHT);
        new TrafficSimulatorGUI();
        frame.setBackground(Color.GREEN);
        frame.setVisible(true);
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        BufferedImage bf = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        try {
            animation(bf.getGraphics());
            g.drawImage(bf, 0, 0, null);
        } catch (Exception ex) {
            System.err.println("Could not Draw Car");
        }
    }

    private void animation(Graphics g) {
        super.paint(g);
        for (TrafficLight trafficLight : trafficLightArrayList) {
            switch (trafficLight.getRoadName()) {
                case "oneWay": {
                    int tX = (int) ((Math.round((gridSizeWidth * 0.37))) + (gridSizeWidth * (trafficLight.getLocation() % map)));
                    int tY = 0;
                    if (trafficLight.getRoadLocation() == 0) {
                        tY = (int) ((Math.round(gridSizeHeight * 0.01)) + (gridSizeHeight * (trafficLight.getLocation() / map)) + 54);
                    } else if (trafficLight.getRoadLocation() == 1) {
                        tY = (int) ((Math.round(gridSizeHeight * 0.99)) + (gridSizeHeight * (trafficLight.getLocation() / map)));
                    }
                    if (trafficLight.getColour() == 1) {
                        g.drawImage(imageTRed, tX, tY, null);
                    } else if (trafficLight.getColour() == 0) {
                        g.drawImage(imageTGreen, tX, tY, null);
                    }
                    break;
                }
                case "oneWayTwo": {
                    int tY = (int) ((Math.round((gridSizeHeight * 0.4))) + (gridSizeHeight * (trafficLight.getLocation() / map)));
                    int tX = 0;
                    if (trafficLight.getRoadLocation() == 0) {
                        tX = (int) ((Math.round(gridSizeWidth * 0.01)) + (gridSizeWidth * (trafficLight.getLocation() % map)));
                    } else if (trafficLight.getRoadLocation() == 1) {
                        tX = (int) ((Math.round(gridSizeWidth * 0.99)) + (gridSizeWidth * (trafficLight.getLocation() % map)) - 24);
                    }
                    if (trafficLight.getColour() == 1) {
                        g.drawImage(imageTRed, tX, tY, null);
                    } else if (trafficLight.getColour() == 0) {
                        g.drawImage(imageTGreen, tX, tY, null);
                    }
                    break;
                }
                case "fourWay":
                    // Traffic Light 1
                    trafficLightOne(g, trafficLight);
                    trafficLightTwo(g, trafficLight);
                    trafficLightThree(g, trafficLight);
                    trafficLightFour(g, trafficLight);
                    break;
                case "threeWayOne":
                    trafficLightTwo(g, trafficLight);
                    trafficLightThree(g, trafficLight);
                    trafficLightFour(g, trafficLight);
                    break;
                case "threeWayTwo":
                    trafficLightOne(g, trafficLight);
                    trafficLightTwo(g, trafficLight);
                    trafficLightThree(g, trafficLight);
                    break;
                case "threeWayThree":
                    trafficLightOne(g, trafficLight);
                    trafficLightThree(g, trafficLight);
                    trafficLightFour(g, trafficLight);
                    break;
                case "threeWayFour":
                    trafficLightOne(g, trafficLight);
                    trafficLightTwo(g, trafficLight);
                    trafficLightFour(g, trafficLight);
                    break;
            }
        }
        vehiclesArrayList.stream().filter(vehicle -> vehicle.getLocation() >= 0 && vehicle.getLocation() <= mapSize).forEach(vehicle -> {
            roadArrayList.forEach(road -> {
                if (vehicle.getRoadDirection() == down) {
                    if ("oneWay".equals(road.getName())) {
                        drawCarDown(g, vehicle);
                    } else if ("fourWay".equals(road.getName())) {
                        drawCarDown(g, vehicle);
                    } else if ("threeWayTwo".equals(road.getName()) || "threeWayThree".equals(road.getName()) || "threeWayFour".equals(road.getName()) || "threeWayOne".equals(road.getName())) {
                        drawCarDown(g, vehicle);
                    }
                } else if (vehicle.getRoadDirection() == left) {
                    if ("oneWayTwo".equals(road.getName())) {
                        drawCarLeft(g, vehicle);
                    } else if ("fourWay".equals(road.getName())) {
                        drawCarLeft(g, vehicle);
                    } else if ("threeWayTwo".equals(road.getName()) || "threeWayOne".equals(road.getName()) || "threeWayFour".equals(road.getName()) || "threeWayThree".equals(road.getName())) {
                        drawCarLeft(g, vehicle);
                    }
                } else if (vehicle.getRoadDirection() == right) {

                    if ("oneWayTwo".equals(road.getName())) {
                        drawCarRight(g, vehicle);
                    } else if ("fourWay".equals(road.getName())) {
                        drawCarRight(g, vehicle);
                    } else if ("threeWayTwo".equals(road.getName()) || "threeWayThree".equals(road.getName()) || "threeWayOne".equals(road.getName()) || "threeWayFour".equals(road.getName())) {
                        drawCarRight(g, vehicle);
                    }
                } else if (vehicle.getRoadDirection() == up) {
                    if ("oneWay".equals(road.getName())) {
                        drawCarUp(g, vehicle);
                    } else if ("fourWay".equals(road.getName())) {
                        drawCarUp(g, vehicle);
                    } else if ("threeWayOne".equals(road.getName()) || "threeWayThree".equals(road.getName()) || "threeWayFour".equals(road.getName()) || "threeWayTwo".equals(road.getName())) {
                        drawCarUp(g, vehicle);
                    }
                }
            });
        });
    }

    private void trafficLightFour(Graphics g, TrafficLight trafficLight) {
        int tX = (int) ((Math.round((gridSizeWidth * 0.6))) + (gridSizeWidth * (trafficLight.getLocation() % map)));
        int tY = (int) ((Math.round((gridSizeHeight * 0.6))) + (gridSizeHeight * (trafficLight.getLocation() / map)) + 54);
        if (trafficLight.getColour() == 1 && trafficLight.getTrafficLightNumber() == 4) {
            g.drawImage(imageTRed, tX, tY, null);
        } else if (trafficLight.getColour() == 0 && trafficLight.getTrafficLightNumber() == 4) {
            g.drawImage(imageTGreen, tX, tY, null);
        }
    }

    private void trafficLightThree(Graphics g, TrafficLight trafficLight) {
        int tX = (int) ((Math.round((gridSizeWidth * 0.4))) + (gridSizeWidth * (trafficLight.getLocation() % map)) - 24);
        int tY = (int) ((Math.round((gridSizeHeight * 0.6))) + (gridSizeHeight * (trafficLight.getLocation() / map)) + 54);
        if (trafficLight.getColour() == 1 && trafficLight.getTrafficLightNumber() == 3) {
            g.drawImage(imageTRed, tX, tY, null);
        } else if (trafficLight.getColour() == 0 && trafficLight.getTrafficLightNumber() == 3) {
            g.drawImage(imageTGreen, tX, tY, null);
        }
    }

    private void trafficLightTwo(Graphics g, TrafficLight trafficLight) {
        int tX = (int) ((Math.round((gridSizeWidth * 0.6))) + (gridSizeWidth * (trafficLight.getLocation() % map)));
        int tY = (int) ((Math.round((gridSizeHeight * 0.4))) + (gridSizeHeight * (trafficLight.getLocation() / map)));
        if (trafficLight.getColour() == 1 && trafficLight.getTrafficLightNumber() == 2) {
            g.drawImage(imageTRed, tX, tY, null);
        } else if (trafficLight.getColour() == 0 && trafficLight.getTrafficLightNumber() == 2) {
            g.drawImage(imageTGreen, tX, tY, null);
        }
    }

    private void trafficLightOne(Graphics g, TrafficLight trafficLight) {
        int tX = (int) ((Math.round((gridSizeWidth * 0.4))) + (gridSizeWidth * (trafficLight.getLocation() % map)) - 24);
        int tY = (int) ((Math.round((gridSizeHeight * 0.4))) + (gridSizeHeight * (trafficLight.getLocation() / map)));
        if (trafficLight.getColour() == 1 && trafficLight.getTrafficLightNumber() == 1) {
            g.drawImage(imageTRed, tX, tY, null);
        } else if (trafficLight.getColour() == 0 && trafficLight.getTrafficLightNumber() == 1) {
            g.drawImage(imageTGreen, tX, tY, null);
        }
    }

    private void drawCarDown(Graphics g, Vehicle vehicle) {
        vehicle.moveVehicleRoadLocation();
        int yLocation = vehicle.getLocation() / map;
        if (yLocation < 0) {
            yLocation = 0;
        }
        int carX = (int) ((int) (Math.round((gridSizeWidth * 0.499)) - carW) + (gridSizeWidth * (vehicle.getLocation() % map)));
        int carY = (int) ((int) (vehicle.getRoadLocation() / map) + (gridSizeHeight * yLocation));
        vehicle.setCarY(carY);
        vehicle.setCarX(carX);
        switch (vehicle.getName()) {
            case "Car":
                g.drawImage(imageCarD, vehicle.getCarX(), vehicle.getCarY(), carW, carH, null);
                break;
            case "Bus":
                g.drawImage(imageBusD, vehicle.getCarX(), vehicle.getCarY(), carW, busH, null);
                break;
            case "Bike":
                g.drawImage(imageBikeD, vehicle.getCarX(), vehicle.getCarY(), carW, carH, null);
                break;
        }
    }

    private void drawCarLeft(Graphics g, Vehicle vehicle) {
        boolean run = true;
        int countLocation = map;
        int locationGrid = 0;
        while (run) {
            if (vehicle.getLocation() < countLocation) {
                locationGrid = countLocation - vehicle.getLocation() - 1;
                run = false;
            } else {
                countLocation = countLocation + map;
            }
        }

        vehicle.moveVehicleRoadLocation();
        int yLocation = vehicle.getLocation() / map;
        if (yLocation < 0) {
            yLocation = 0;
        }
        int test = getHeight() - grid.getHeight();
        int test2 = grid.getHeight();
        test2 = (test2 / map) + test;
        int carX = grid.getWidth() - (int) ((int) (vehicle.getRoadLocation() / map) + (gridSizeWidth * locationGrid));
        int carY = (int) (Math.round((test2 * 0.49)) + (gridSizeHeight * yLocation));
        if (gridSizeHeight < 50) {
            carY = carY + 15;
        } else if (gridSizeHeight < 100) {
            carY = carY + 14;
        } else if (gridSizeHeight < 200) {
            carY = carY + 8;
        } else if (gridSizeHeight < 300) {
            carY = carY + 2;
        }
        vehicle.setCarY(carY);
        vehicle.setCarX(carX);
        switch (vehicle.getName()) {
            case "Car":
                g.drawImage(imageCarL, vehicle.getCarX(), vehicle.getCarY(), carWL, carHL, null);
                break;
            case "Bus":
                g.drawImage(imageBusL, vehicle.getCarX(), vehicle.getCarY(), busWL, carHL, null);
                break;
            case "Bike":
                g.drawImage(imageBikeL, vehicle.getCarX(), vehicle.getCarY(), bikeWL, carHL, null);
                break;
        }
    }

    private void drawCarRight(Graphics g, Vehicle vehicle) {
        vehicle.moveVehicleRoadLocation();
        int yLocation = vehicle.getLocation() / map;
        if (yLocation < 0) {
            yLocation = 0;
        }
        int test = getHeight() - grid.getHeight();
        int test2 = grid.getHeight();
        test2 = (test2 / map) + test;
        int carX = (int) ((int) (vehicle.getRoadLocation() / map) + (gridSizeWidth * (vehicle.getLocation() % map)));
        int carY = (int) (Math.round((test2 * 0.51)) + (gridSizeHeight * yLocation));
        if (gridSizeHeight < 50) {
            carY = carY + 15;
        } else if (gridSizeHeight < 100) {
            carY = carY + 14;
        } else if (gridSizeHeight < 200) {
            carY = carY + 13;
        } else if (gridSizeHeight < 300) {
            carY = carY + 12;
        } else if (gridSizeHeight < 400) {
            carY = carY + 11;
        }
        vehicle.setCarY(carY);
        vehicle.setCarX(carX);
        switch (vehicle.getName()) {
            case "Car":
                g.drawImage(imageCarR, vehicle.getCarX(), vehicle.getCarY(), carWL, carHL, null);
                break;
            case "Bus":
                g.drawImage(imageBusR, vehicle.getCarX(), vehicle.getCarY(), busWL, carHL, null);
                break;
            case "Bike":
                g.drawImage(imageBikeR, vehicle.getCarX(), vehicle.getCarY(), bikeWL, carHL, null);
                break;
        }
    }

    private void drawCarUp(Graphics g, Vehicle vehicle) {
        vehicle.moveVehicleRoadLocation();
        int yLocation = vehicle.getLocation() / map;
        if (yLocation < 0) {
            yLocation = 0;
        }
        int carX = (int) ((int) (Math.round((gridSizeWidth * 0.51)) + carW) + (gridSizeWidth * (vehicle.getLocation() % map)));
        int carY = (int) (gridSizeHeight - (vehicle.getRoadLocation() / map) + (gridSizeHeight * yLocation));
        vehicle.setCarY(carY);
        vehicle.setCarX(carX);
        //System.out.println("Speed : " + vehicle.getSpeed() + "   RLocation : " + vehicle.getRoadLocation());
        switch (vehicle.getName()) {
            case "Car":
                g.drawImage(imageCarU, vehicle.getCarX(), vehicle.getCarY(), carW, carH, null);
                break;
            case "Bus":
                g.drawImage(imageBusU, vehicle.getCarX(), vehicle.getCarY(), carW, busH, null);
                break;
            case "Bike":
                g.drawImage(imageBikeU, vehicle.getCarX(), vehicle.getCarY(), carW, carH, null);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == menuNew) {
            labelStatusBar.setText("New map selected");
            if (map > 0) {
                remove(grid);
            }
            roadArrayList.clear();
            trafficLightArrayList.clear();
            gridEastButtons.setVisible(true);

            // Makes a list for the current map
            int[] currentMap = new int[mapSize];
            int currentSize = 0;
            createEmptyMap(mapSize, currentSize, currentMap);
            boolean error = true;
            while (error) {
                try {
                    map = Integer.parseInt(showInputDialog("Enter map size between 2 and 10"));
                    error = map < 2 || map > 10;
                } catch (NumberFormatException nfe) {
                    labelStatusBar.setText("Please enter a Number");
                }
            }
            mapSize = map * map;
            // change to map size
            grid = new JPanel(new GridLayout(map, map));
            MapPieces[] button = new MapPieces[mapSize];
            grid.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
            for (int i = 0; i < (mapSize); i++) {
                try {
                    button[i] = new MapPieces(oneWayRoadButton, threeWayRoadButton, fourWayRoadButton, oneWayIcon, threeWayIcon, position, roadArrayList, map, trafficLightButton, trafficLightArrayList);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                button[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                grid.add(button[i]);
                position++;
            }
            add(grid, BorderLayout.CENTER);
            validate();
        }
        if (source == rotateButton) {
            labelStatusBar.setText("Icons Rotated");
            //add code for cycling through images - possibly use a number system
            if (rotateButtonCount == 0) {
                oneWayIcon.setIcon(iconOneWayTwo);
                threeWayIcon.setIcon(iconThreeWayTwo);
            } else if (rotateButtonCount == 1) {
                oneWayIcon.setIcon(iconOneWay);
                threeWayIcon.setIcon(iconThreeWayThree);
            } else if (rotateButtonCount == 2) {
                oneWayIcon.setIcon(iconOneWayTwo);
                threeWayIcon.setIcon(iconThreeWayFour);
            } else if (rotateButtonCount == 3) {
                oneWayIcon.setIcon(iconOneWay);
                threeWayIcon.setIcon(iconThreeWayOne);
            }
            rotateButtonCount++;
            if (rotateButtonCount >= 4) {
                rotateButtonCount = 0;
            }
        }
        if (source == doneButton) {
            labelStatusBar.setText("Editing Complete");
            gridEastButtons.setVisible(false);
            mapButtons.clearSelection();
        }
        if (source == menuSave) {
            try {
                saveRoad(roadArrayList, trafficLightArrayList);
                labelStatusBar.setText("Map Saved");
            } catch (IOException ex) {
                ex.printStackTrace();
                labelStatusBar.setText("Error could not save map");
            }
        }
        if (source == menuEdit) {
            labelStatusBar.setText("Editing map");
            gridEastButtons.setVisible(true);
        }
        if (source == menuExit) {
            System.exit(0);
        }
        if (source == menuPause) {
            labelStatusBar.setText("Simulation Paused");

            if (timer.isRunning()) {
                timer.stop();
                menuPause.setText("Resume");
            } else {
                timer.restart();
                menuPause.setText("Pause");
            }
        }
        if (source == menuStop) {
            labelStatusBar.setText("Simulation Stopped");
            timer.stop();
        }
        if (source == menuOpen) {
            name = "";
            boolean error = true;
            while (error) {
                try {
                    name = JOptionPane.showInputDialog("Enter the name of the map you would like to load(e.g. city1");
                    position = 0;
                    roadArrayList.clear();
                    trafficLightArrayList.clear();
                    if (map > 0) {
                        remove(grid);
                    }
                    gridEastButtons.setVisible(false);
                    mapButtons.clearSelection();
                    if (name != null) {
                        roadArrayList = loadRoad.invoke(name);
                        labelStatusBar.setText("Map Loaded :" + name);
                    }

                    error = false;
                } catch (IOException | ClassNotFoundException ex) {
                    labelStatusBar.setText("File not found");
                    //ex.printStackTrace();
                }
            }
            // add load trafficLights Code
            if (name != null) {
                for (Road road : roadArrayList) {
                    map = road.getSize();
                    break;
                }
                try {
                    trafficLightArrayList = loadTrafficLight.invoke(name);
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
                    try {
                        button[i] = new MapPieces(oneWayRoadButton, threeWayRoadButton, fourWayRoadButton, oneWayIcon, threeWayIcon, position, roadArrayList, map, trafficLightButton, trafficLightArrayList);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    button[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    grid.add(button[i]);
                    position++;
                }
                add(grid, BorderLayout.CENTER);
                validate();
                labelStatusBar.setText("Map Loaded: " + name);
            }
            labelStatusBar.setText("");
        }
        if (source == menuRun) {
            position = 0;
            if (map > 0) {
                remove(grid);
            }
            vehiclesArrayList.clear();
            gridEastButtons.setVisible(false);
            mapButtons.clearSelection();
            for (Road road : roadArrayList) {
                map = road.getSize();
                break;
            }
            mapSize = map * map;
            // change to map size
            grid = new JPanel(new GridLayout(map, map));
            MapPiecesRun[] label = new MapPiecesRun[mapSize];
            grid.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
            for (int i = 0; i < (mapSize); i++) {
                try {
                    label[i] = new MapPiecesRun(position, roadArrayList);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                grid.add(label[i]);
                position++;
            }

            add(grid, BorderLayout.CENTER);
            JOptionPane optionPane = new JOptionPane();
            JSlider jSlider = getSlider(optionPane);
            optionPane.setMessage(new Object[]{"Select a number: ", jSlider});
            optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
            optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
            JDialog dialog = optionPane.createDialog("Please select the entry rate for vehicles");
            dialog.setVisible(true);
            if (optionPane.getInputValue() == "uninitializedValue") {
                enterMapSpeed = 0;
            } else {
                enterMapSpeed = (int) optionPane.getInputValue();
            }
/*
            boolean error = true;
            while (error){
                try {
                    numberOfCars = Integer.parseInt(showInputDialog("Enter the number of Cars for the simulation"));
                    error = false;
                } catch (NumberFormatException nfe) {
                    labelStatusBar.setText("Please enter a Number");
                }
            }
            error = true;
            while (error){
                try {
                    numberOfBus = Integer.parseInt(showInputDialog("Enter the number of Buses for the simulation"));
                    error = false;
                } catch (NumberFormatException nfe) {
                    labelStatusBar.setText("Please enter a Number");
                }
            }
            error = true;
            while (error){
                try {
                    numberOfCars = Integer.parseInt(showInputDialog("Enter the number of Bikes for the simulation"));
                    error = false;
                } catch (NumberFormatException nfe) {
                    labelStatusBar.setText("Please enter a Number");
                }
            }
*/
            for (int i = 0; i < numberOfCars; ) {
                Vehicle car = new Vehicle(67, 0, mapSize + 1, 0, 0, 0, rotateButtonCount, 's', "Car", 0);
                vehiclesArrayList.add(car);
                vehicles++;
                i++;
                rotateButtonCount++;
            }
            for (int i = 0; i < numberOfBus; ) {
                Vehicle car = new Vehicle(200, 0, mapSize + 1, 0, 0, 0, rotateButtonCount, 's', "Bus", 0);
                vehiclesArrayList.add(car);
                vehicles++;
                i++;
            }
            for (int i = 0; i < numberOfBike; ) {
                Vehicle car = new Vehicle(33, 0, mapSize + 1, 0, 0, 0, rotateButtonCount, 's', "Bike", 0);
                vehiclesArrayList.add(car);
                vehicles++;
                i++;
            }
            topMap = new int[map + 1];
            bottomMap = new int[map + 1];
            leftMap = new int[map + 1];
            rightMap = new int[map + 1];
            getTopMap(map, roadArrayList, topMap, currentMapSize); // gets the top of the map (for cars entering the map)
            getBottomMap(map, roadArrayList, bottomMap, currentMapSize); // gets the bottom of the map (for cars entering the map)
            getLeftMap(map, roadArrayList, leftMap, currentMapSize); // gets the left of the map (for cars entering the map)
            getRightMap(map, roadArrayList, rightMap, currentMapSize); // gets the right of the map (for cars entering the map)

            timer = new Timer(10, ex -> {
                gridSizeWidth = grid.getWidth();
                gridSizeWidth = gridSizeWidth / map;
                gridSizeHeight = grid.getHeight();
                gridSizeHeight = gridSizeHeight / map;
                changeTrafficLightColour(rand);
                carH = (int) gridSizeHeight / 10;
                carW = (int) gridSizeWidth / 19;
                carHL = (int) gridSizeHeight / 19;
                carWL = (int) gridSizeWidth / 10;

                busH = (int) gridSizeHeight / 5;
                busWL = (int) gridSizeWidth / 5;
                bikeWL = (int) gridSizeWidth / 20;
                if (vehicles > 0) {
                    enterTopMap(roadLocation);
                    enterBottomMap(roadLocation);
                    enterLeftMap(roadLocation);
                    enterRightMap(roadLocation);
                }
                vehiclesArrayList.forEach(vehicle -> {
                    roadArrayList.stream().filter(r -> r.getLocation() == vehicle.getLocation()).forEach(road -> {
                        vehiclesArrayList.forEach(v2 -> {

                            nextRoadLocation = vehicle.getRoadLocation() + vehicle.getSpeed();

                            if (road.getName().contains("oneWay")) {
                                oneWayCheckInFront(vehicle, v2, nextRoadLocation); // move check to up here - traffic lihght code is moving the vehicle when it is looking for the correct v2
                                nextRoadLocation = vehicle.getRoadLocation() + vehicle.getSpeed();
                            } else if (road.getName().contains("threeWay")) { // fix traffic light code to match changes made to classes
                                if (vehicle.getRoadDirection() == down) {
                                    trafficLightIntersection(vehicle, nextRoadLocation);
                                    if (accelerateVehicle) {
                                        threeWayIntersection(vehicle, road, v2, nextRoadLocation, 4, 3, 2, 1, right, down, left, up);
                                    }
                                } else if (vehicle.getRoadDirection() == left) {
                                    trafficLightIntersection(vehicle, nextRoadLocation);
                                    if (accelerateVehicle) {
                                        threeWayIntersection(vehicle, road, v2, nextRoadLocation, 1, 2, 4, 3, down, left, up, right);
                                    }
                                } else if (vehicle.getRoadDirection() == right) {
                                    trafficLightIntersection(vehicle, nextRoadLocation);
                                    if (accelerateVehicle) {
                                        threeWayIntersection(vehicle, road, v2, nextRoadLocation, 2, 1, 3, 4, up, right, down, left);
                                    }
                                } else if (vehicle.getRoadDirection() == up) {
                                    trafficLightIntersection(vehicle, nextRoadLocation);
                                    if (accelerateVehicle) {
                                        threeWayIntersection(vehicle, road, v2, nextRoadLocation, 1, 2, 3, 4, left, right, up, down);
                                    }
                                }
                            } else if (road.getName().contains("fourWay")) {
                                // System.out.println("Location :" + vehicle.getLocation() + " RoadLocation :" + vehicle.getRoadLocation() + " RoadDirection :" + vehicle.getRoadDirection());
                                moveVehicle = true;
                                if (vehicle.getRoadDirection() == left) {
                                    fourWayCheckInFront(vehicle, v2, nextRoadLocation, down, left, up, right);
                                } else if (vehicle.getRoadDirection() == down) {
                                    fourWayCheckInFront(vehicle, v2, nextRoadLocation, right, down, left, up);
                                } else if (vehicle.getRoadDirection() == right) {
                                    fourWayCheckInFront(vehicle, v2, nextRoadLocation, up, right, down, left);
                                } else if (vehicle.getRoadDirection() == up) {
                                    fourWayCheckInFront(vehicle, v2, nextRoadLocation, left, up, right, down);
                                }
                            }
                            if (accelerateVehicle) { // runs if nothing is causing the vehicle to decelerate

                                int nextLocation;
                                if (vehicle.getLocation() <= mapSize && vehicle.getRoadLocation() > 0) {
                                    vehicle.accelerateVehicle();
                                    if (vehicle.getRoadDirection() == down) {
                                        nextLocation = vehicle.getLocation() + map;
                                        for (int bottomMap : bottomMap) {
                                            if (vehicle.getLocation() == bottomMap) {
                                                nextLocation = mapSize + 1;
                                                break;
                                            }
                                        }
                                        setRoadLocation(roadLength, vehicle, nextLocation);
                                    } else if (vehicle.getRoadDirection() == left) {
                                        nextLocation = vehicle.getLocation() - 1;
                                        for (int leftMap : leftMap) {
                                            if (vehicle.getLocation() == leftMap) {
                                                nextLocation = mapSize + 1;
                                                break;
                                            }
                                        }
                                        setRoadLocation(roadLength, vehicle, nextLocation);
                                    } else if (vehicle.getRoadDirection() == right) {
                                        nextLocation = vehicle.getLocation() + 1;
                                        for (int rightMap : rightMap) {
                                            if (vehicle.getLocation() == rightMap) {
                                                nextLocation = mapSize + 1;
                                                break;
                                            }
                                        }
                                        setRoadLocation(roadLength, vehicle, nextLocation);
                                    } else if (vehicle.getRoadDirection() == up) {
                                        nextLocation = vehicle.getLocation() - map;
                                        for (int topMap : topMap) {
                                            if (vehicle.getLocation() == topMap) {
                                                nextLocation = mapSize + 1;
                                                break;
                                            }
                                        }
                                        setRoadLocation(roadLength, vehicle, nextLocation);
                                    }
                                }
                            }
                        });
                    });
                    accelerateVehicle = true;
                });
                boolean endSimulation = true;
                for (Vehicle v : vehiclesArrayList) {
                    if (v.getRoadLocation() <= roadLength) {
                        endSimulation = false;
                        break;
                    }
                }
                if (endSimulation) {
                    labelStatusBar.setText("Simulation Ended");
                    timer.stop();
                }
                avgSpeed = 0;
                for (Vehicle vehicle : vehiclesArrayList) {
                    if (vehicle.getRoadDirection() != finish && vehicle.getRoadDirection() != start) {
                        avgSpeed += vehicle.getSpeed();
                    }
                }
                avgSpeed = avgSpeed / vehiclesArrayList.size();
                if (name == null) {
                    name = "Map not saved";
                }
                labelStatusBar.setText("Map name: " + name + "    " + "Vehicles on Map: " + vehiclesOnMap + "    " + "Average Speed: " + avgSpeed);
                repaint();
                validate();
            });
            timer.start();
        }
    }

    private void threeWayIntersection(Vehicle vehicle, Road r, Vehicle v2, double nextRoadLocation, int rule1, int rule2, int rule3, int rule4, char exit1, char exit2, char exit3, char exit4) {
        checkInFront(vehicle, v2, nextRoadLocation);
        if (accelerateVehicle) {
            if (r.getOrientation() == rule1) { // rule 1
                if (nextRoadLocation >= slowDownIntersection && nextRoadLocation <= stopIntersection) {
                    getDirection(max3Way, rand, vehicle);
                    if (vehicle.getChosenDirection() == 1) {
                        if (v2.getLocation() == vehicle.getLocation()) {
                            if (nextRoadLocation >= slowDownSecond3Way && v2.getRoadLocation() <= stopIntersection && v2.getRoadDirection() == exit4) {
                                accelerateVehicle = false;
                                vehicle.stopVehicle();
                            }
                        }
                        if (accelerateVehicle) {
                            vehicle.setRoadLocation(exitIntersection);
                            vehicle.setRoadDirection(exit1);
                        }
                    } else if (vehicle.getChosenDirection() == 2) {
                        vehicle.setRoadLocation(exitIntersection);
                        vehicle.setRoadDirection(exit2);
                    }
                }
            } else if (r.getOrientation() == rule2) {
                if (nextRoadLocation >= slowDownSecond3Way && nextRoadLocation <= stopIntersection) {
                    getDirection(2, rand, vehicle);
                    if (vehicle.getChosenDirection() == 1) {
                        vehicle.setRoadLocation(exitIntersection);
                        vehicle.setRoadDirection(exit3);
                    } else if (vehicle.getChosenDirection() == 2) {
                        vehicle.setRoadLocation(exitIntersection);
                        vehicle.setRoadDirection(exit2);
                    }
                }
            } else if (r.getOrientation() == rule3) {
                if (nextRoadLocation >= slowDownIntersection && nextRoadLocation <= stopIntersection) {
                    getDirection(max3Way, rand, vehicle);
                    if (vehicle.getChosenDirection() == 1) {
                        if (v2.getLocation() == vehicle.getLocation()) {
                            if (nextRoadLocation >= slowDownSecond3Way && v2.getRoadLocation() <= stopIntersection && v2.getRoadDirection() == exit3 && v2.getRoadDirection() == exit1) {
                                accelerateVehicle = false;
                                vehicle.stopVehicle();
                            }
                        }
                        if (accelerateVehicle) {
                            vehicle.setRoadLocation(exitIntersection);
                            vehicle.setRoadDirection(exit1);
                        }
                    } else if (vehicle.getChosenDirection() == 2) {
                        if (v2.getLocation() == vehicle.getLocation()) {
                            if (nextRoadLocation >= slowDownSecond3Way && v2.getRoadLocation() <= stopIntersection && v2.getRoadDirection() == exit3) {
                                accelerateVehicle = false;
                                vehicle.stopVehicle();
                            }
                        }
                        if (accelerateVehicle) {
                            vehicle.setRoadLocation(exitIntersection);
                            vehicle.setRoadDirection(exit3);
                        }
                    }
                }
            } else if (r.getOrientation() == rule4) {
                if (vehicle.getChosenDirection() == 1) {
                    vehicle.moveVehicleRoadLocation();
                } else if (vehicle.getChosenDirection() == 2) {
                    vehicle.setRoadDirection(exit4);
                    vehicle.setRoadLocation(exitIntersection);
                }

            }
        }
    }

    private static JSlider getSlider(final JOptionPane optionPane) {
        JSlider slider = new JSlider(0, 50);
        slider.setMajorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        ChangeListener changeListener = changeEvent -> {
            JSlider theSlider = (JSlider) changeEvent.getSource();
            if (!theSlider.getValueIsAdjusting()) {
                optionPane.setInputValue(theSlider.getValue());
            }
        };
        slider.addChangeListener(changeListener);
        return slider;
    }

    private void trafficLightIntersection(Vehicle vehicle, double nextRoadLocation) {
        for (TrafficLight t : trafficLightArrayList) {
            if (t.getLocation() == vehicle.getLocation()) {
                if (t.getColour() == 1) { // 0 is red
                    if (vehicle.getRoadDirection() == down && t.getTrafficLightNumber() == 1) {
                        if (vehicle.getRoadLocation() >= slowDownIntersection && vehicle.getRoadLocation() <= stopIntersection) {
                            decelerateTrafficLight(vehicle, nextRoadLocation, slowDownIntersection, stopIntersection, slowDownSecond3Way);
                            accelerateVehicle = false;
                        }

                    } else if (vehicle.getRoadDirection() == left && t.getTrafficLightNumber() == 2) {
                        if (vehicle.getRoadLocation() >= slowDownIntersection && vehicle.getRoadLocation() <= stopIntersection) {
                            decelerateTrafficLight(vehicle, nextRoadLocation, slowDownIntersection, stopIntersection, slowDownSecond3Way);
                            accelerateVehicle = false;
                        }
                    } else if (vehicle.getRoadDirection() == up && t.getTrafficLightNumber() == 3) {
                        if (vehicle.getRoadLocation() >= slowDownIntersection && vehicle.getRoadLocation() <= stopIntersection) {
                            decelerateTrafficLight(vehicle, nextRoadLocation, slowDownIntersection, stopIntersection, slowDownSecond3Way);
                            accelerateVehicle = false;
                        }
                    } else if (vehicle.getRoadDirection() == left && t.getTrafficLightNumber() == 4) {
                        if (vehicle.getRoadLocation() >= slowDownIntersection && vehicle.getRoadLocation() <= stopIntersection) {
                            decelerateTrafficLight(vehicle, nextRoadLocation, slowDownIntersection, stopIntersection, slowDownSecond3Way);
                            accelerateVehicle = false;
                        }
                    }
                }
            }
        }
    }

    private void fourWayCheckInFront(Vehicle vehicle, Vehicle v2, double nextRoadLocation, char exit1, char exit2, char exit3, char entrance) {
        checkInFront(vehicle, v2, nextRoadLocation);
        if (accelerateVehicle) {
            if (nextRoadLocation > slowDownIntersection && nextRoadLocation < slowDownSecond3Way) {
                vehicle.decelerateVehicle();
                vehicle.moveVehicleRoadLocation();
                accelerateVehicle = false;
            } else if (nextRoadLocation >= slowDownSecond3Way && nextRoadLocation <= intersectionTurn) { // add code above to slow down vehicle - think it is skipping it ( goes from 6 to 11) - make this code else if
                getDirection(max4WayRandom, rand, vehicle);
                vehicle.setRoadLocation(stopIntersection);
                if (nextRoadLocation <= stopIntersection && vehicle.getSpeed() != 0) {
                    vehicle.setRoadLocation(stopIntersection);
                    vehicle.setSpeed(0);
                    accelerateVehicle = false;
                    moveVehicle = false;
                } else if (vehicle.getRoadLocation() >= stopIntersection) {
                    if (vehicle.getChosenDirection() == 1) {
                        for (Vehicle v3 : vehiclesArrayList) {
                            if (v3.getRoadLocation() == stopIntersection || v3.getRoadLocation() == intersectionTurn && vehicle.getRoadDirection() == exit1 && v3.getId() != vehicle.getId()) {
                                if (v3.getRoadDirection() == exit1) {
                                    vehicle.stopVehicle();
                                    accelerateVehicle = false;
                                    moveVehicle = false;
                                }
                            }
                        }
                        if (moveVehicle) {
                            vehicle.setRoadLocation(exitIntersection);
                            vehicle.setRoadDirection(exit1);
                        }
                    } else if (vehicle.getChosenDirection() == 2) {
                        for (Vehicle v3 : vehiclesArrayList) {
                            if (v3.getRoadLocation() == stopIntersection || v3.getRoadLocation() == intersectionTurn && v3.getId() != vehicle.getId()) {
                                if (v3.getRoadDirection() == exit1 || v3.getRoadDirection() == exit3) {
                                    vehicle.stopVehicle();
                                    accelerateVehicle = false;
                                    moveVehicle = false;
                                }
                            }
                        }
                        if (moveVehicle) {
                            vehicle.setRoadLocation(exitIntersection);
                            vehicle.setRoadDirection(exit2);
                        }
                    } else if (vehicle.getChosenDirection() == 3) {
                        for (Vehicle v3 : vehiclesArrayList) {
                            if (v3.getRoadLocation() == stopIntersection || v3.getRoadLocation() == intersectionTurn && vehicle.getRoadDirection() == exit3 && v3.getId() != vehicle.getId()) {
                                if (v3.getRoadDirection() == exit1 || v3.getRoadDirection() == entrance) {
                                    vehicle.stopVehicle();
                                    accelerateVehicle = false;
                                    moveVehicle = false;
                                }
                            }
                        }
                        if (moveVehicle) {
                            vehicle.setRoadLocation(exitIntersection);
                            vehicle.setRoadDirection(exit3);
                        }
                    }
                    accelerateVehicle = false;
                }
            }
        }
    }

    private void oneWayCheckInFront(Vehicle vehicle, Vehicle v2, double nextRoadLocation) {
        trafficLightArrayList.stream().filter(t -> t.getLocation() == vehicle.getLocation()).forEach(trafficLight -> {
            if (vehicle.getRoadDirection() == down && trafficLight.getRoadLocation() == 1 && trafficLight.getColour() == 1) {
                decelerateTrafficLight(vehicle, nextRoadLocation, slowDownSpeedBottomT, roadLength, stopDistanceBottomT);
            } else if (vehicle.getRoadDirection() == left && trafficLight.getRoadLocation() == 0 && trafficLight.getColour() == 1) {
                decelerateTrafficLight(vehicle, nextRoadLocation, slowDownSpeedBottomT, roadLength, stopDistanceBottomT);
            } else if (vehicle.getRoadDirection() == right && trafficLight.getRoadLocation() == 1 && trafficLight.getColour() == 1) {
                decelerateTrafficLight(vehicle, nextRoadLocation, slowDownSpeedBottomT, roadLength, stopDistanceBottomT);
            } else if (vehicle.getRoadDirection() == up && trafficLight.getRoadLocation() == 0 && trafficLight.getColour() == 1) {
                decelerateTrafficLight(vehicle, nextRoadLocation, slowDownSpeedBottomT, roadLength, stopDistanceBottomT);
            }
        });
        checkVehicleInFront(vehicle, nextRoadLocation, v2);
    }

    private void checkInFront(Vehicle vehicle, Vehicle v2, double nextRoadLocation) {
        if (vehicle.getRoadLocation() < v2.getRoadLocation() && vehicle.getLocation() == v2.getLocation() && vehicle.getId() != v2.getId()) {
            checkVehicleInFront(vehicle, nextRoadLocation, v2);
        } else {
            trafficLightIntersection(vehicle, nextRoadLocation);
        }
    }

    private void changeTrafficLightColour(Random rand) {
        int trafficLight;
        int trafficLight3Way = rand.nextInt(((100 - 1) + 1)) + 1;
        int trafficLight4Way = rand.nextInt(((100 - 1) + 1)) + 1;
        for (TrafficLight t : trafficLightArrayList) {
            trafficLight = rand.nextInt(((100 - 1) + 1)) + 1;
            if ("oneWay".equals(t.getRoadName()) || "oneWayTwo".equals(t.getRoadName())) {
                t.setColour(1);
                /*
                if (t.getChangedColourTimer() == 0) {
                    if (trafficLight > 50) {
                        t.changeColour();
                        t.colourTimer();
                    }
                } else {
                    t.colourTimer();
                }
                 */
            } else if ("threeWayOne".equals(t.getRoadName()) || "threeWayTwo".equals(t.getRoadName()) || "threeWayThree".equals(t.getRoadName()) || "threeWayFour".equals(t.getRoadName())) {
                if (trafficLight3Way > 35) {
                    t.threeWayCycle();
                }
            } else if ("fourWay".equals(t.getRoadName())) {
                if (trafficLight4Way > 35) {
                    t.fourWayCycle();
                }
            }
        }
    }

    private void setRoadLocation(int roadLength, Vehicle vehicle, int nextLocation) {
        boolean exitMap = true;
        boolean test = true;
        double nextRoadLocation = vehicle.getRoadLocation() + vehicle.getSpeed();
        if (nextRoadLocation >= roadLength) {
            for (Road road : roadArrayList) {
                if (road.getLocation() == nextLocation) {// place nextLocation over map
                    if (vehicle.getRoadDirection() == down) {
                        if (road.getName().equals("oneWayTwo") || road.getName().equals("threeWayOne")) {
                            vehiclesOnMap--;
                            exitMap = true;
                            moveVehicle = false;
                            test = false;
                        }
                    } else if (vehicle.getRoadDirection() == up) {
                        if (road.getName().equals("oneWayTwo") || road.getName().equals("threeWayTwo")) {
                            exitMap = true;
                            moveVehicle = false;
                            test = false;
                        }
                    } else if (vehicle.getRoadDirection() == left) {
                        if (road.getName().equals("oneWay") || road.getName().equals("threeWayThree")) {
                            exitMap = true;
                            moveVehicle = false;
                            test = false;
                        }
                    } else if (vehicle.getRoadDirection() == right) {
                        if (road.getName().equals("oneWay") || road.getName().equals("threeWayFour")) {
                            exitMap = true;
                            moveVehicle = false;
                            test = false;
                        }
                    }
                    for (TrafficLight trafficLight : trafficLightArrayList) {
                        if (vehicle.getRoadDirection() == down && trafficLight.getRoadName().contains("oneWay")) {
                            if (trafficLight.getLocation() == nextLocation && trafficLight.getRoadLocation() == 0 && trafficLight.getColour() == 1) {
                                exitMap = false;
                                vehicle.stopVehicle();
                                accelerateVehicle = false;
                                moveVehicle = false;
                                test = false;
                            }
                        } else if (vehicle.getRoadDirection() == left && trafficLight.getRoadName().contains("oneWay")) {
                            if (trafficLight.getLocation() == nextLocation && trafficLight.getRoadLocation() == 1 && trafficLight.getColour() == 1) {
                                exitMap = false;
                                vehicle.stopVehicle();
                                accelerateVehicle = false;
                                moveVehicle = false;
                                test = false;
                            }
                        } else if (vehicle.getRoadDirection() == right && trafficLight.getRoadName().contains("oneWay")) {
                            if (trafficLight.getLocation() == nextLocation && trafficLight.getRoadLocation() == 0 && trafficLight.getColour() == 1) {
                                exitMap = false;
                                vehicle.stopVehicle();
                                accelerateVehicle = false;
                                moveVehicle = false;
                                test = false;
                            }
                        } else if (vehicle.getRoadDirection() == up && trafficLight.getRoadName().contains("oneWay")) {
                            if (trafficLight.getLocation() == nextLocation && trafficLight.getRoadLocation() == 1 && trafficLight.getColour() == 1) {
                                exitMap = false;
                                vehicle.stopVehicle();
                                accelerateVehicle = false;
                                moveVehicle = false;
                                test = false;
                            }
                        }
                    }// possibly add code to slow down for next vehicle
                    if (test) {
                        vehicle.setLocation(nextLocation);// for loop check for road pieces at new place, if none and code to turn around
                        vehicle.setRoadLocation(nextRoadLocation - roadLength); // set based on mps+ roadLocation - roadLength
                        exitMap = false;
                    }
                }
            }
            if (exitMap) {
                vehicle.setLocation(mapSize + 1);
                vehicle.setRoadLocation(roadLength + 1);
                vehicle.setRoadDirection(finish);
            }
        }
    }

    private void getDirection(int max, Random rand, Vehicle v) {
        int cd;
        cd = rand.nextInt(max) + 1;
        v.setChosenDirection(cd);
    }

    private void decelerateTrafficLight(Vehicle v, double nextRoadLocation, int i, int i2, int i3) {
        if (v.getRoadLocation() >= i && v.getRoadLocation() <= i2) {
            if (nextRoadLocation < i3) {
                if (v.getSpeed() > 2) {
                    v.decelerateVehicle();
                }
                //v.moveVehicleRoadLocation();
                accelerateVehicle = false;
            } else if (nextRoadLocation >= i3) {
                //v.setRoadLocation(i3);
                v.stopVehicle();
                accelerateVehicle = false;
            }
        }
    }

    private void checkVehicleInFront(Vehicle v, double nextRoadLocation, Vehicle v2) {
        // System.out.println(v.getLocation() + "  " + v2.getLocation());
        if (v.getRoadLocation() < v2.getRoadLocation() && v.getLocation() == v2.getLocation()) {
            if (v2.getRoadLocation() - v2.getLength() - nextRoadLocation <= 350) {
                if (v.getLocation() == v2.getLocation()) {
                    v.stopVehicle();
                    accelerateVehicle = false;
                    moveVehicle = false;
                }

            } else if (v2.getRoadLocation() - v2.getLength() - nextRoadLocation <= 450) {
                if (v.getLocation() == v2.getLocation()) {
                    if (v.getSpeed() > 1) {
                        v.setSpeed(v.getSpeed() - 1);
                        //v.setRoadLocation(nextRoadLocation);
                        accelerateVehicle = false;
                    }
                }
            }
        }
        if (v.getRoadDirection() == up){
            vehicleNextLocation = v2.getLocation() + map;
        }
        else  if (v.getRoadDirection() == down){
            vehicleNextLocation = v2.getLocation() - map;
        }
        else  if (v.getRoadDirection() == right){
            vehicleNextLocation = v2.getLocation() - 1;
        }
        else  if (v.getRoadDirection() == left){
            vehicleNextLocation = v2.getLocation() + 1;
        }


        nextLocationBrake = (int) (v.getRoadLocation() - 650);

        if (v.getLocation() == vehicleNextLocation) {
            if (v.getRoadLocation() + 350 >roadLength){
                if (nextLocationBrake > v2.getRoadLocation() ) {
                    v.stopVehicle();
                    accelerateVehicle = false;
                    moveVehicle = false;
                }
            }

        }

    }

    private void enterTopMap(int roadLocation) { // add code to show direction cars are facing
        checkMapLocation = true;
        char roadDirection = down;
        for (int topMap : topMap) { // search through top of map
            if (topMap >= 0) { // select a piece that has road on it --- add to check for orientation / entrances of road pieces
                for (Road road : roadArrayList) {
                    if (topMap == road.getLocation()) {
                        if ("oneWay".equals(road.getName())) {
                            if (road.getOrientation() == 1) {
                                checkVehicleList(roadLocation, topMap, roadDirection);
                            }
                        } else if ("fourWay".equals(road.getName())) {
                            checkVehicleList(roadLocation, topMap, roadDirection);
                        } else if (road.getName().contains("threeWay")) {
                            if (road.getOrientation() == 2 || road.getOrientation() == 3 || road.getOrientation() == 4) {
                                checkVehicleList(roadLocation, topMap, roadDirection);
                            }
                        }
                    }
                }
            }
        }
    }

    private void enterBottomMap(int roadLocation) {
        checkMapLocation = true;
        char roadDirection = up;
        for (int bottomMap : bottomMap) { // search through top of map
            if (bottomMap > 0) { // select a piece that has road on it --- add to check for orientation / entrances of road pieces
                for (Road road : roadArrayList) {
                    if (bottomMap == road.getLocation()) { // TOP OF MAP - repeat code for all 4 sides road.getName().equals("4-way intersection")
                        if ("oneWay".equals(road.getName())) {
                            checkVehicleList(roadLocation, bottomMap, roadDirection);
                        } else if ("fourWay".equals(road.getName())) {
                            checkVehicleList(roadLocation, bottomMap, roadDirection);
                        } else if (road.getName().contains("threeWay")) {
                            if (road.getOrientation() == 1 || road.getOrientation() == 3 || road.getOrientation() == 4) {
                                checkVehicleList(roadLocation, bottomMap, roadDirection);
                            }
                        }
                        // add code for 3way intersection - first fix up map making , orientation, and options for redundant orientations
                        // add code for car class to have direction (char u(up) d (down) l(left) r(right), use to move cars
                    }
                }
            }
        }
    }

    private void enterLeftMap(int roadLocation) {
        char roadDirection = right;
        checkMapLocation = true;
        for (int leftMap : leftMap) { // search through top of map
            if (leftMap > 0) { // select a piece that has road on it --- add to check for orientation / entrances of road pieces
                for (Road road : roadArrayList) {
                    if (leftMap == road.getLocation()) { // TOP OF MAP - repeat code for all 4 sides road.getName().equals("4-way intersection")
                        if ("oneWayTwo".equals(road.getName())) {
                            checkVehicleList(roadLocation, leftMap, roadDirection);
                        } else if ("fourWay".equals(road.getName())) {
                            checkVehicleList(roadLocation, leftMap, roadDirection);
                        } else if (road.getName().contains("threeWay")) {
                            if (road.getOrientation() == 1 || road.getOrientation() == 2 || road.getOrientation() == 3) {
                                checkVehicleList(roadLocation, leftMap, roadDirection);
                            }
                        }
                    }
                }
            }
        }
    }

    private void enterRightMap(int roadLocation) {
        checkMapLocation = true;
        char roadDirection = left;
        for (int i : rightMap) { // search through top of map
            if (i > 0) { // select a piece that has road on it --- add to check for orientation / entrances of road pieces
                for (Road road : roadArrayList) {
                    if (i == road.getLocation()) {
                        if ("oneWayTwo".equals(road.getName())) {
                            checkVehicleList(roadLocation, i, roadDirection);
                        } else if ("fourWay".equals(road.getName())) {
                            checkVehicleList(roadLocation, i, roadDirection);
                        } else if (road.getName().contains("threeWay")) {
                            if (road.getOrientation() == 1 || road.getOrientation() == 2 || road.getOrientation() == 4) {
                                checkVehicleList(roadLocation, i, roadDirection);
                            }
                        }
                    }

                }
            }
        }
    }

    private void checkVehicleList(int roadLocation, int i, char roadDirection) {
        if (enterMapSpeed <= enterMapSpeedCount) {
            for (Vehicle v : vehiclesArrayList) {
                if (i == v.getLocation() && v.getRoadLocation() - v.getLength() < 350 && v.getRoadDirection() == up) {
                    checkMapLocation = false;
                    break;
                } else if (i == v.getLocation() && v.getRoadLocation() < 350 && v.getRoadDirection() == down) {
                    checkMapLocation = false;
                    break;
                }
                else if (i == v.getLocation() && v.getRoadLocation() - v.getLength() < 350 && v.getRoadDirection() == right) {
                    checkMapLocation = false;
                    break;
                }
                else if (i == v.getLocation() && v.getRoadLocation() - v.getLength() < 350 && v.getRoadDirection() == left) {
                    checkMapLocation = false;
                    break;
                }
            }
            if (checkMapLocation) {
                addVehicle(roadLocation, i, roadDirection);
                checkMapLocation = true;
            }
            checkMapLocation = true;
            enterMapSpeedCount = 0;
        }
        enterMapSpeedCount++;
    }

    private void addVehicle(int roadLocation, int i, char roadDirection) {
        Collections.shuffle(vehiclesArrayList);
        for (Vehicle vehicle : vehiclesArrayList) {
            if (vehicle.getRoadDirection() == start) {
                vehicle.setLocation(i);
                vehicle.setRoadLocation(roadLocation);
                vehicle.setRoadDirection(roadDirection);
                vehicles--;
                vehiclesOnMap++;
                break;
            }
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

    private static void saveRoad(ArrayList<Road> roadArrayList, ArrayList<TrafficLight> trafficLightArrayList) throws IOException {
        String name = JOptionPane.showInputDialog("Enter a name for the map");
        FileOutputStream foutRoad = new FileOutputStream(name);
        ObjectOutputStream oosRoad = new ObjectOutputStream(foutRoad);
        oosRoad.writeObject(roadArrayList);
        foutRoad.close();
        String nameTrafficLight = name + "TrafficLight";
        FileOutputStream foutTrafficLight = new FileOutputStream(nameTrafficLight);
        ObjectOutputStream oosTrafficLight = new ObjectOutputStream(foutTrafficLight);
        oosTrafficLight.writeObject(trafficLightArrayList);
        foutTrafficLight.close();
    }

    private static class loadRoad {
        private static ArrayList<Road> invoke(String name) throws IOException, ClassNotFoundException {
            ArrayList<Road> roadArrayList;
            FileInputStream readRoad = new FileInputStream(name);
            ObjectInputStream ois = new ObjectInputStream(readRoad);
            roadArrayList = (ArrayList<Road>) ois.readObject();
            readRoad.close();
            return roadArrayList;
        }
    }


    private static class loadTrafficLight {
        private static ArrayList<TrafficLight> invoke(String name) throws IOException, ClassNotFoundException {
            ArrayList<TrafficLight> trafficLightArrayList;
            FileInputStream readRoad = new FileInputStream(name + "TrafficLight");
            ObjectInputStream ois = new ObjectInputStream(readRoad);
            trafficLightArrayList = (ArrayList<TrafficLight>) ois.readObject();
            readRoad.close();
            return trafficLightArrayList;
        }
    }

    private static void getRightMap(int map, ArrayList<Road> roadArrayList, int[] rightMap, int currentMapSize) {
        // goes through map and gets the right  map side (vehicles entering)
        int mapSize = map * map;// remove to pass in variable
        for (int i = map - 1; i <= mapSize; currentMapSize++) {
            for (Road road : roadArrayList) {
                if (road.getLocation() == i) {
                    rightMap[currentMapSize] = i;
                }
            }
            i = i + map;
        }
    }

    private static void getLeftMap(int map, ArrayList<Road> roadArrayList, int[] leftMap, int currentMapSize) {
        // goes through map and gets the left  map side (vehicles entering)
        int mapSize = map * map;// remove to pass in variable
        for (int i = 0; i <= mapSize; currentMapSize++) {
            for (Road v : roadArrayList) {
                if (v.getLocation() == i) {
                    leftMap[currentMapSize] = i;
                }
            }
            i = i + map;
        }
    }

    private static void getBottomMap(int map, ArrayList<Road> roadArrayList, int[] topMap, int currentMapSize) {
        // goes through map and gets the left  map side (vehicles entering)
        int mapSize = map * map;// remove to pass in variable
        int mapStop = mapSize - map;
        for (int i = mapSize; i > mapStop; i--, currentMapSize++) {
            for (Road v : roadArrayList) {
                if (v.getLocation() == i) {
                    topMap[currentMapSize] = i;
                }
            }
        }
    }

    private static void getTopMap(int map, ArrayList<Road> roadArrayList, int[] topMap, int currentMapSize) {
        // goes through map and gets the top  map side (vehicles entering)
        for (int i = 0; i < map; i++, currentMapSize++) {
            for (Road v : roadArrayList) {
                if (v.getLocation() == i) {
                    topMap[currentMapSize] = i;
                }
            }
        }
    }
}