import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static javax.swing.JOptionPane.showInputDialog;

// fix images to fill full screen - code pixel locations based on labels x,y position - each button type for positioning of car - use percent to account for size (start as 1 - half location each time as increase), and pixel size of map

public class TrafficSimulatorGUI extends JFrame implements ActionListener {
    private int map = 0; // change to get user input between 1 - 10 for map size
    private int mapSize = 0; // gets the size of the map
    private int position = 0;
    private int currentMapSize; // current size of the map
    private int carH = 0;
    private int carW = 0;
    private int carHL = 0;
    private int carWL = 0;
    private int slowDownSpeedBottomT = 800;
    private int stopDistanceBottomT = 900;
    private int slowDown3Way = 400;
    private int stop3Way = 500;
    private int slowDownM3Way = 450;
    private int threeWayTurn = 550;
    private int roadLength = 1000;
    private int stopVehicle = 300;
    private int roadLocation = 1;
    private int min = 1;
    private int max = 2;
    private int vehicles;
    private int gameHeight;
    private int gameWidth;
    private int location;
    private int[] topMap;
    private int[] bottomMap;
    private int[] leftMap;
    private int[] rightMap;
    private byte count = 0;
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
    private String imgPath = "images/car.png";
    private final BufferedImage image = ImageIO.read(getClass().getResourceAsStream(imgPath));
    private String imgPathCarL = "images/carLeft.png";
    private final BufferedImage imageCarL = ImageIO.read(getClass().getResourceAsStream(imgPathCarL));
    private String imgPathTRed = "images/trafficLightRed.PNG";
    private final BufferedImage imageTRed = ImageIO.read(getClass().getResourceAsStream(imgPathTRed));
    private String imgPathTGreen = "images/trafficLightGreen.PNG";
    private final BufferedImage imageTGreen = ImageIO.read(getClass().getResourceAsStream(imgPathTGreen));
    private double gridSizeWidth;
    private double gridSizeHeight;
    private char roadSideR = 'r';
    private char roadSideL = 'l';
    private int max4Way = 3;
    private char down = 'd';
    private char up = 'u';
    private char left = 'l';
    private char right = 'r';
    private boolean accelerateVehicle = true;
    private boolean moveVehicle = true;
    private Random rand = new Random();
    private boolean checkMapLocation = true;
    private Timer timer;

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
        //gridEastButtons.add(trafficLightButton);
        // oneWayIcon.setIcon(iconTrafficLight);
        //   gridEastButtons.add(trafficLightIcon);
        gridEastButtons.add(rotateButton);
        gridEastButtons.add(doneButton);
        rotateButton.addActionListener(this);
        gridEastButtons.setVisible(false);
        currentMapSize = 0;
    }

    public static void main(String[] args) throws IOException {
        TrafficSimulatorGUI frame = new TrafficSimulatorGUI();
        final int WIDTH = 1000;
        final int HEIGHT = 1000;
        frame.setSize(WIDTH, HEIGHT);
        new TrafficSimulatorGUI();
        frame.setVisible(true);
    }

    // paint method not allowing new vehicle to be added?
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
            if (trafficLight.getRoadName().equals("oneWay")) {
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
            } else if (trafficLight.getRoadName().equals("oneWayTwo")) {
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
            } else if (trafficLight.getRoadName().equals("fourWay")) {
                // Traffic Light 1
                trafficLightOne(g, trafficLight);
                trafficLightTwo(g, trafficLight);
                trafficLightThree(g, trafficLight);
                trafficLightFour(g, trafficLight);
            } else if (trafficLight.getRoadName().equals("threeWayOne")) {
                trafficLightTwo(g, trafficLight);
                trafficLightThree(g, trafficLight);
                trafficLightFour(g, trafficLight);
            } else if (trafficLight.getRoadName().equals("threeWayTwo")) {
                trafficLightOne(g, trafficLight);
                trafficLightTwo(g, trafficLight);
                trafficLightThree(g, trafficLight);
            } else if (trafficLight.getRoadName().equals("threeWayThree")) {
                trafficLightOne(g, trafficLight);
                trafficLightThree(g, trafficLight);
                trafficLightFour(g, trafficLight);
            } else if (trafficLight.getRoadName().equals("threeWayFour")) {
                trafficLightOne(g, trafficLight);
                trafficLightTwo(g, trafficLight);
                trafficLightFour(g, trafficLight);
            }
        }

        vehiclesArrayList.stream().filter(vehicle -> vehicle.getLocation() >= 0 && vehicle.getLocation() <= mapSize).forEach(vehicle -> {
            roadArrayList.forEach(road -> {
                if (vehicle.getRoadDirection() == down) {
                    if ("oneWay".equals(road.getName())) {
                        if (road.getOrientation() == 1) {
                            drawCarDown(g, vehicle);
                        }
                    } else if ("fourWay".equals(road.getName())) {
                        drawCarDown(g, vehicle);
                    } else if (road.getName().contains("threeWay")) {
                        if (road.getOrientation() == 2 || road.getOrientation() == 3 || road.getOrientation() == 4) {
                            drawCarDown(g, vehicle);
                        }
                    }
                } else if (vehicle.getRoadDirection() == left) {
                    if ("oneWayTwo".equals(road.getName())) {
                        if (road.getOrientation() == 2) {
                            drawCarLeft(g, vehicle);
                        }
                    } else if ("fourWay".equals(road.getName())) {
                        drawCarLeft(g, vehicle);
                    } else if (road.getName().contains("threeWay")) {
                        if (road.getOrientation() == 1 || road.getOrientation() == 2 || road.getOrientation() == 4) {
                            drawCarLeft(g, vehicle);
                        }
                    }
                } else if (vehicle.getRoadDirection() == right) {
                    if ("oneWayTwo".equals(road.getName())) {
                        if (road.getOrientation() == 2) {
                            //drawCarRight(g, vehicle);
                        }
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
        int nextRoadLocation = (int) (vehicle.getRoadLocation() + vehicle.getSpeed());
        vehicle.setRoadLocation(nextRoadLocation);
        int yLocation = vehicle.getLocation() / map;
        if (yLocation < 0) {
            yLocation = 0;
        }
        int carX = (int) ((int) (Math.round((gridSizeWidth * 0.499)) - carW) + (gridSizeWidth * (vehicle.getLocation() % map)));
        int carY = (int) ((int) (vehicle.getRoadLocation() / map) + (gridSizeHeight * yLocation));
        vehicle.setCarY(carY);
        vehicle.setCarX(carX);
        g.drawImage(image, vehicle.getCarX(), vehicle.getCarY(), carW, carH, null);
    }

    private void drawCarLeft(Graphics g, Vehicle vehicle) {
        boolean run = true;
        int count = map;
        int locationGrid = 0;
        while (run) {
            if (vehicle.getLocation() < count) {
                locationGrid = count - vehicle.getLocation() - 1;
                run = false;
            } else {
                count = count + map;
            }
        }


        int nextRoadLocation = (int) (vehicle.getRoadLocation() + vehicle.getSpeed());
        vehicle.setRoadLocation(nextRoadLocation);
        int yLocation = vehicle.getLocation() / map;
        if (yLocation < 0) {
            yLocation = 0;
        }
        //int test = grid.getHeight()/mapSize + 50;
        int test = getHeight() - grid.getHeight();
        //System.out.println(test); 75
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
        g.drawImage(imageCarL, vehicle.getCarX(), vehicle.getCarY(), carWL, carHL, null);
    }

    private void drawCarRight(Graphics g, Vehicle vehicle) {
        int nextRoadLocation = (int) (vehicle.getRoadLocation() + vehicle.getSpeed());
        vehicle.setRoadLocation(nextRoadLocation);
        int yLocation = vehicle.getLocation() / map;
        if (yLocation < 0) {
            yLocation = 0;
        }
        int carX = (int) ((int) (vehicle.getRoadLocation() / map) + (gridSizeHeight * yLocation));
        int carY = (int) ((int) (Math.round((gridSizeHeight * 0.499)) - carW) + (gridSizeHeight * (vehicle.getLocation() / map)));
        vehicle.setCarY(carY);
        vehicle.setCarX(carX);
        g.drawImage(image, vehicle.getCarX(), vehicle.getCarY(), carW, carH, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == menuNew) {
            if (map > 0) {
                grid.removeAll();
            }
            gridEastButtons.setVisible(true);
            // Makes a list for the current map
            int[] currentMap = new int[mapSize];
            int currentSize = 0;
            createEmptyMap(mapSize, currentSize, currentMap);
            labelStatusBar.setText("New Map Selected");
            map = Integer.parseInt(showInputDialog("Enter map size"));
            mapSize = map * map;
            // change to map size
            grid = new JPanel(new GridLayout(map, map));
            MapPieces[] button = new MapPieces[mapSize];
            grid.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
            for (int i = 0; i < (mapSize); i++) {
                try {
                    button[i] = new MapPieces(oneWayRoadButton, threeWayRoadButton, fourWayRoadButton, oneWayIcon, threeWayIcon, fourWayIcon, position, roadArrayList, map, trafficLightIcon, trafficLightButton, trafficLightArrayList);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
                saveRoad(roadArrayList, trafficLightArrayList);
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
        if (source == menuPause) {
            labelStatusBar.setText("Simulation Paused");
        }
        if (source == menuStop) {
            labelStatusBar.setText("Simulation Stopped");
        }

        if (source == menuOpen) {
            String name = JOptionPane.showInputDialog("Enter the name of the map you would like to load(e.g. city1");
            position = 0;
            roadArrayList.clear();
            if (map > 0) {
                remove(grid);
                //grid.removeAll();
            }
            gridEastButtons.setVisible(false);
            mapButtons.clearSelection();
            try {
                roadArrayList = loadRoad.invoke(name);
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            for (Road road : roadArrayList) {
                map = road.getSize();
                break;
            }
            // add load trafficLights Code
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
                    button[i] = new MapPieces(oneWayRoadButton, threeWayRoadButton, fourWayRoadButton, oneWayIcon, threeWayIcon, fourWayIcon, position, roadArrayList, map, trafficLightIcon, trafficLightButton, trafficLightArrayList);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
            MapPiecesRun[] label = new MapPiecesRun[mapSize];
            grid.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
            for (int i = 0; i < (mapSize); i++) {
                try {
                    label[i] = new MapPiecesRun(position, roadArrayList, map, trafficLightArrayList);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                //button[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                grid.add(label[i]);
                position++;
            }

            add(grid, BorderLayout.CENTER);
            // number of Cars
            int numberOfCars = 20;//Integer.parseInt(showInputDialog("Enter the number of Cars for the simulation"));
            // number of Buses
            int numberOfBus = 0;//Integer.parseInt(showInputDialog("Enter the number of Buses for the simulation"));
            // number of Bikes
            int numberOfBike = 0;//Integer.parseInt(showInputDialog("Enter the number of bikes for the simulation "));
            for (int i = 0; i < numberOfCars; ) {
                Vehicle car = new Vehicle(67, 0, mapSize + 1, 0, 0, 'n', 0, count, 'u', "Car", 0);
                vehiclesArrayList.add(car);
                vehicles++;
                i++;
                count++;
            }
            for (int i = 0; i < numberOfBus; ) {
                Vehicle car = new Vehicle(200, 0, mapSize + 1, 0, 0, 'n', 0, count, 'u', "Bus", 0);
                vehiclesArrayList.add(car);
                vehicles++;
                i++;
            }
            for (int i = 0; i < numberOfBike; ) {
                Vehicle car = new Vehicle(33, 0, mapSize + 1, 0, 0, 'n', 0, count, 'u', "Bike", 0);
                vehiclesArrayList.add(car);
                vehicles++;
                i++;
            }
            // Makes a list for top of the map (vehicles entering)
            topMap = new int[map + 1];
            bottomMap = new int[map + 1];
            leftMap = new int[map + 1];
            rightMap = new int[map + 1];
            getTopMap(map, roadArrayList, topMap, currentMapSize); // gets the top of the map (for cars entering the map)
            getBottomMap(map, roadArrayList, bottomMap, currentMapSize); // gets the bottom of the map (for cars entering the map)
            getLeftMap(map, roadArrayList, leftMap, currentMapSize); // gets the left of the map (for cars entering the map)
            getRightMap(map, roadArrayList, rightMap, currentMapSize); // gets the right of the map (for cars entering the map)
            timer = new Timer(30, ex -> {
                gridSizeWidth = grid.getWidth();
                gridSizeWidth = gridSizeWidth / map;
                gridSizeHeight = grid.getHeight();
                gridSizeHeight = gridSizeHeight / map;
                changeTrafficLightColour(rand);

                carH = (int) gridSizeHeight / 10;
                carW = (int) gridSizeWidth / 19;

                carHL = (int) gridSizeHeight / 19;
                carWL = (int) gridSizeWidth / 10;

                if (vehicles > 0) {
                    enterTopMap(roadSideR, roadLocation);
                    enterBottomMap(roadSideL, roadLocation);
                    enterLeftMap(roadSideL, roadLocation);
                    enterRightMap(roadSideR, roadLocation);
                }
                vehiclesArrayList.forEach(vehicle -> {
                    // System.out.println("Direction :" + vehicle.getRoadDirection() + " Location :" + vehicle.getLocation() + " RoadLocation :" + vehicle.getRoadLocation());
                    roadArrayList.stream().filter(r -> r.getLocation() == vehicle.getLocation()).forEach(r -> {
                        vehiclesArrayList.forEach(v2 -> {
                            double nextRoadLocation = vehicle.getRoadLocation() + vehicle.getSpeed();
                            if (r.getName().contains("oneWay")) {
                                oneWayCheckInFront(vehicle, r, v2, nextRoadLocation);
                            } else if (r.getName().contains("threeWay")) { // fix traffic light code to match changes made to classes
                                if (vehicle.getRoadDirection() == down) {
                                    // check if car is in front
                                    if (vehicle.getRoadLocation() < v2.getRoadLocation() && vehicle.getRoadSide() == v2.getRoadSide() && vehicle.getLocation() == v2.getLocation()) {
                                        checkVehicleInFront(vehicle, nextRoadLocation, r, v2);
                                    } else {
                                        // else no cars in front, check for traffic lights.
                                        // 0 is red
                                        trafficLightArrayList.stream().filter(t -> t.getLocation() == vehicle.getLocation()).filter(t -> t.getColour() == 0).filter(t -> t.getTrafficLightNumber() == 1 || t.getTrafficLightNumber() == 2 || t.getTrafficLightNumber() == 3).filter(t -> vehicle.getLocation() == t.getLocation()).forEach(t -> decelerateTrafficLight(vehicle, nextRoadLocation, slowDown3Way, stop3Way, slowDownM3Way));
                                        // fix traffic light slection code with each light controlling a direction - 1-D	2 - L	3-R3	4-U
                                        // make code into a method then replace directions with entrances
                                    }
                                }
                                if (accelerateVehicle) {
                                    if (r.getOrientation() == 4) { // is incorrect orientation
                                        if (nextRoadLocation >= slowDown3Way && nextRoadLocation <= stop3Way) {
                                            getDirection(min, max, rand, vehicle);
                                            if (vehicle.getChosenDirection() == 1) {
                                                if (v2.getLocation() == vehicle.getLocation()) {
                                                    if (nextRoadLocation >= slowDownM3Way && v2.getRoadLocation() <= stop3Way && v2.getRoadDirection() != down) {
                                                        accelerateVehicle = false;
                                                        vehicle.stopVehicle();
                                                    } else {
                                                        vehicle.setRoadLocation(11);
                                                        vehicle.setRoadSide(left);
                                                        vehicle.setRoadDirection(right);
                                                    }
                                                }

                                            } else if (vehicle.getChosenDirection() == 2) {
                                                if (v2.getLocation() == vehicle.getLocation()) {
                                                    if (v2.getRoadLocation() + vehicle.getSpeed() >= slowDownM3Way && v2.getRoadLocation() <= stop3Way && v2.getRoadDirection() == right) {
                                                        accelerateVehicle = false;
                                                        vehicle.stopVehicle();
                                                    } else {
                                                        vehicle.setRoadLocation(threeWayTurn);
                                                        vehicle.setRoadSide(right);
                                                        vehicle.setRoadDirection(right);
                                                    }
                                                }
                                            }
                                        } else if (nextRoadLocation >= slowDown3Way && nextRoadLocation < slowDownM3Way) {
                                            // slow down vehicle
                                            vehicle.decelerateVehicle();
                                            vehicle.moveVehicleRoadLocation();
                                            accelerateVehicle = false;
                                        }
                                    } else if (r.getOrientation() == 3) {
                                        if (nextRoadLocation >= slowDownM3Way && nextRoadLocation <= stop3Way) {
                                            getDirection(1, 2, rand, vehicle);
                                            if (vehicle.getChosenDirection() == 1) {
                                                if (v2.getLocation() == vehicle.getLocation()) {
                                                    if (v2.getRoadLocation() + vehicle.getSpeed() >= slowDown3Way && v2.getRoadLocation() <= stop3Way && v2.getRoadDirection() == up) {
                                                        accelerateVehicle = false;
                                                        vehicle.stopVehicle();
                                                    } else {
                                                        vehicle.setRoadLocation(threeWayTurn);
                                                        vehicle.setRoadSide(left);
                                                        vehicle.setRoadDirection(left);
                                                    }
                                                }
                                            }
                                        } else if (nextRoadLocation >= slowDown3Way && nextRoadLocation < slowDownM3Way) {
                                            // slow down vehicle
                                            vehicle.decelerateVehicle();
                                            vehicle.moveVehicleRoadLocation();
                                            accelerateVehicle = false;
                                        }
                                    } else if (r.getOrientation() == 2) {
                                        if (nextRoadLocation >= slowDownM3Way && nextRoadLocation <= stop3Way) {
                                            getDirection(1, 2, rand, vehicle);
                                            if (vehicle.getChosenDirection() == 1) {
                                                vehicle.setRoadLocation(1);
                                                vehicle.setRoadSide('l');
                                                vehicle.setRoadDirection('r');
                                            } else if (vehicle.getChosenDirection() == 2) {
                                            }
                                        } else if (nextRoadLocation >= slowDown3Way && nextRoadLocation < slowDownM3Way) {
                                            vehicle.decelerateVehicle();
                                            vehicle.moveVehicleRoadLocation();
                                            accelerateVehicle = false;
                                        }
                                    } else if (r.getOrientation() == 1) {
                                        if (vehicle.getSpeed() + vehicle.getRoadLocation() >= 9) {
                                            setRoadLocation(stop3Way, vehicle, map);
                                            vehicle.moveVehicleRoadLocation();
                                            accelerateVehicle = true;
                                        }
                                    }
                                }
                            } else if (r.getName().contains("fourWay")) {
                                // System.out.println("Location :" + vehicle.getLocation() + " RoadLocation :" + vehicle.getRoadLocation() + " RoadDirection :" + vehicle.getRoadDirection());
                                moveVehicle = true;
                                if (vehicle.getRoadDirection() == left) {
                                    fourWayCheckInFront(vehicle, r, v2, nextRoadLocation, left, down, left, up, right);
                                } else if (vehicle.getRoadDirection() == down) {
                                    fourWayCheckInFront(vehicle, r, v2, nextRoadLocation, right, right, down, left, up);
                                }
                            }
                            if (accelerateVehicle) { // runs if nothing is causing the vehicle to decelerate
                                int nextLocation;
                                if (vehicle.getLocation() <= mapSize && vehicle.getRoadLocation() > 0) {
                                    vehicle.accelerateVehicle();
                                    if (vehicle.getRoadDirection() == down) {
                                        nextLocation = vehicle.getLocation() + map;
                                        setRoadLocation(roadLength, vehicle, nextLocation);
                                    } else if (vehicle.getRoadDirection() == left) {
                                        nextLocation = vehicle.getLocation() - 1;
                                        for (int leftMap : rightMap) {
                                            if (vehicle.getLocation() == leftMap) {
                                                nextLocation = mapSize + 1;
                                                break;
                                            }
                                        }
                                        setRoadLocation(roadLength, vehicle, nextLocation);
                                    } else if (vehicle.getRoadDirection() == right) {
                                        nextLocation = vehicle.getLocation() + 1;
                                        for (int rightMap : leftMap) {
                                            if (vehicle.getLocation() == rightMap) {
                                                nextLocation = mapSize + 1;
                                                break;
                                            }
                                        }
                                        setRoadLocation(roadLength, vehicle, nextLocation);
                                    } else if (vehicle.getRoadDirection() == up) {
                                        nextLocation = vehicle.getLocation() - map;
                                        for (int topMap : bottomMap) {
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
                    System.out.println("Simulation ended");
                    System.out.println("No cars left on map");
                    timer.stop();
                }
                repaint();
                validate();

            });
            timer.start();

        }
    }

    private void fourWayCheckInFront(Vehicle vehicle, Road r, Vehicle v2, double nextRoadLocation, char directions, char exit1, char exit2, char exit3, char entrance) {
        checkInFront(vehicle, r, v2, nextRoadLocation);
        if (accelerateVehicle) {
            // 4 way intersection movement code
            // if(nextRoadLocation > 5 && nextRoadLocation < 10){slow down vehicle}
            if (nextRoadLocation > slowDown3Way && nextRoadLocation < slowDownM3Way) {
                vehicle.decelerateVehicle();
                vehicle.moveVehicleRoadLocation();
                accelerateVehicle = false;
            } else if (nextRoadLocation >= slowDownM3Way && nextRoadLocation <= threeWayTurn)// add code above to slow down vehicle - think it is skipping it ( goes from 6 to 11) - make this code else if
            {
                getDirection(min, max4Way, rand, vehicle);
                vehicle.setRoadLocation(stop3Way);
                if (nextRoadLocation <= stop3Way && vehicle.getSpeed() != 0) {
                    vehicle.setRoadLocation(stop3Way);
                    vehicle.setSpeed(0);
                    accelerateVehicle = false;
                    moveVehicle = false;
                } else if (vehicle.getRoadLocation() >= stop3Way) {
                    if (vehicle.getChosenDirection() == 1) {
                        for (Vehicle v3 : vehiclesArrayList) {
                            if (v3.getRoadLocation() == stop3Way || v3.getRoadLocation() == threeWayTurn && vehicle.getRoadDirection() == exit1 && v3.getId() != vehicle.getId()) {
                                if (v3.getRoadDirection() == exit1) {
                                    vehicle.stopVehicle();
                                    accelerateVehicle = false;
                                    moveVehicle = false;
                                }
                            }
                        }
                        if (moveVehicle) {
                            vehicle.setRoadLocation(threeWayTurn + 1);
                            vehicle.setRoadDirection(exit1);
                        }
                    } else if (vehicle.getChosenDirection() == 2) {
                        for (Vehicle v3 : vehiclesArrayList) {
                            if (v3.getRoadLocation() == stop3Way || v3.getRoadLocation() == threeWayTurn && v3.getId() != vehicle.getId()) {
                                if (v3.getRoadDirection() == exit1 || v3.getRoadDirection() == exit3) {
                                    vehicle.stopVehicle();
                                    accelerateVehicle = false;
                                    moveVehicle = false;
                                }
                            }
                        }
                        if (moveVehicle) {
                            vehicle.setRoadLocation(threeWayTurn + 1);
                            vehicle.setRoadDirection(exit2);
                            //vehicle.setRoadSide(right);
                        }
                    } else if (vehicle.getChosenDirection() == 3) {
                        for (Vehicle v3 : vehiclesArrayList) {
                            if (v3.getRoadLocation() == stop3Way || v3.getRoadLocation() == threeWayTurn && vehicle.getRoadDirection() == exit3 && v3.getId() != vehicle.getId()) {
                                if (v3.getRoadDirection() == exit1 || v3.getRoadDirection() == entrance) {
                                    vehicle.stopVehicle();
                                    accelerateVehicle = false;
                                    moveVehicle = false;
                                }
                            }
                        }
                        if (moveVehicle) {
                            vehicle.setRoadLocation(threeWayTurn + 1);
                            vehicle.setRoadDirection(exit3);
                        }
                    }
                    accelerateVehicle = false;
                }
            }
        }
    }

    private void oneWayCheckInFront(Vehicle vehicle, Road r, Vehicle v2, double nextRoadLocation) {
        if (vehicle.getRoadLocation() < v2.getRoadLocation() && vehicle.getRoadSide() == v2.getRoadSide() && vehicle.getLocation() == v2.getLocation()) {
            checkVehicleInFront(vehicle, nextRoadLocation, r, v2);
        } else {
            // else no cars in front, check for traffic lights.
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
        }
    }

    private void checkInFront(Vehicle vehicle, Road r, Vehicle v2, double nextRoadLocation) {
        if (vehicle.getRoadLocation() < v2.getRoadLocation() && vehicle.getRoadSide() == v2.getRoadSide() && vehicle.getLocation() == v2.getLocation() && vehicle.getId() != v2.getId()) {
            checkVehicleInFront(vehicle, nextRoadLocation, r, v2);
        } else {
            for (TrafficLight t : trafficLightArrayList) {
                if (t.getLocation() == vehicle.getLocation()) {
                    if (t.getColour() == 1) { // 0 is red
                        if (vehicle.getRoadDirection() == down && t.getTrafficLightNumber() == 1) {
                            if (vehicle.getRoadLocation() >= slowDown3Way && vehicle.getRoadLocation() <= stop3Way) {
                                decelerateTrafficLight(vehicle, nextRoadLocation, slowDown3Way, stop3Way, slowDownM3Way);
                            }
                        } else if (vehicle.getRoadDirection() == left && t.getTrafficLightNumber() == 2) {
                            if (vehicle.getRoadLocation() >= slowDown3Way && vehicle.getRoadLocation() <= stop3Way) {
                                decelerateTrafficLight(vehicle, nextRoadLocation, slowDown3Way, stop3Way, slowDownM3Way);
                            }
                        } else if (vehicle.getRoadDirection() == up && t.getTrafficLightNumber() == 3) {
                            if (vehicle.getRoadLocation() >= slowDown3Way && vehicle.getRoadLocation() <= stop3Way) {
                                decelerateTrafficLight(vehicle, nextRoadLocation, slowDown3Way, stop3Way, slowDownM3Way);
                            }
                        } else if (vehicle.getRoadDirection() == left && t.getTrafficLightNumber() == 4) {
                            if (vehicle.getRoadLocation() >= slowDown3Way && vehicle.getRoadLocation() <= stop3Way) {
                                decelerateTrafficLight(vehicle, nextRoadLocation, slowDown3Way, stop3Way, slowDownM3Way);
                            }
                        }
                    }
                }
            }
        }
    }

    private void changeTrafficLightColour(Random rand) {
        int trafficLight;
        int trafficLight3Way = rand.nextInt(((100 - 1) + 1)) + 1;
        int trafficLight4Way = rand.nextInt(((100 - 1) + 1)) + 1;
        for (TrafficLight t : trafficLightArrayList) {
            trafficLight = rand.nextInt(((100 - 1) + 1)) + 1;
            if ("oneWay".equals(t.getRoadName()) || "oneWayTwo".equals(t.getRoadName())) {
                if (t.getChangedColourTimer() == 0) {
                    if (trafficLight > 50) {
                        t.changeColour();
                        t.colourTimer();
                    }
                } else {
                    t.colourTimer();
                }
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

    private void setRoadLocation(int roadLength, Vehicle v, int nextLocation) {
        boolean exitMap = true;
        boolean test = true;
        double nextRoadLocation = v.getRoadLocation() + v.getSpeed();
        if (nextRoadLocation < roadLength) {
            v.setRoadLocation(nextRoadLocation);
        } else if (nextRoadLocation >= roadLength) {
            for (Road r2 : roadArrayList) {
                if (r2.getLocation() == nextLocation) {// place nextLocation over map
                    for (TrafficLight trafficLight : trafficLightArrayList) {
                        if (v.getRoadDirection() == down && trafficLight.getRoadName().contains("oneWay")) {
                            if (trafficLight.getLocation() == nextLocation && trafficLight.getRoadLocation() == 0 && trafficLight.getColour() == 1) {
                                exitMap = false;
                                v.stopVehicle();
                                accelerateVehicle = false;
                                moveVehicle = false;
                                test = false;
                            }
                        } else if (v.getRoadDirection() == left && trafficLight.getRoadName().contains("oneWay")) {
                            if (trafficLight.getLocation() == nextLocation && trafficLight.getRoadLocation() == 1 && trafficLight.getColour() == 1) {
                                exitMap = false;
                                v.stopVehicle();
                                accelerateVehicle = false;
                                moveVehicle = false;
                                test = false;
                            }
                        } else if (v.getRoadDirection() == right && trafficLight.getRoadName().contains("oneWay")) {
                            if (trafficLight.getLocation() == nextLocation && trafficLight.getRoadLocation() == 0 && trafficLight.getColour() == 1) {
                                exitMap = false;
                                v.stopVehicle();
                                accelerateVehicle = false;
                                moveVehicle = false;
                                test = false;
                            }
                        } else if (v.getRoadDirection() == up && trafficLight.getRoadName().contains("oneWay")) {
                            if (trafficLight.getLocation() == nextLocation && trafficLight.getRoadLocation() == 1 && trafficLight.getColour() == 1) {
                                exitMap = false;
                                v.stopVehicle();
                                accelerateVehicle = false;
                                moveVehicle = false;
                                test = false;
                            }
                        }
                    }
                    if (test) {
                        v.setLocation(nextLocation);// for loop check for road pieces at new place, if none and code to turn around
                        v.setRoadLocation(nextRoadLocation - roadLength); // set based on mps+ roadLocation - roadLength
                        exitMap = false;
                    }
                }
            }
            if (exitMap) {
                v.setLocation(mapSize + 1);
                v.setRoadLocation(roadLength + 1);
            }
        }
    }

    private void getDirection(int min, int max, Random rand, Vehicle v) {
        int cd;
        cd = rand.nextInt(max) + 1;
        v.setChosenDirection(cd);

    }

    private void stopVehicleStraightTrafficLight(int roadLength, Vehicle v, TrafficLight t) {
        if (t.getRoadLocation() + map == v.getLocation()) {
            if (roadLength - v.getRoadLocation() + v.getSpeed() <= 50) {
                if (t.getColour() >= 1) {
                    accelerateVehicle = false;
                    v.stopVehicle();
                }
            } else if (roadLength - v.getRoadLocation() <= 100) {
                if (t.getColour() > 1) {
                    accelerateVehicle = false;
                    v.decelerateVehicle();
                }

            }
        }
    }

    private void decelerateTrafficLight(Vehicle v, double nextRoadLocation, int i, int i2, int i3) {
        if (v.getRoadLocation() >= i && v.getRoadLocation() <= i2) {
            if (nextRoadLocation < i3) {
                if (v.getSpeed() > 2) {
                    v.decelerateVehicle();
                }
                v.moveVehicleRoadLocation();
                accelerateVehicle = false;
            } else if (nextRoadLocation >= i3) {
                v.setRoadLocation(i3);
                v.stopVehicle();
                accelerateVehicle = false;
            }
        }
    }


    private void checkVehicleInFront(Vehicle v, double nextRoadLocation, Road r, Vehicle v2) {
        if (v2.getRoadLocation() + v2.getLength() - nextRoadLocation <= 350) {
            System.out.println(v.getName() + " " + v.getId() + " has stopped due to a car in front " + r.getName() + " at Road Location " + v.getRoadLocation() + " , map Location " + v.getLocation());
            v.stopVehicle();
            accelerateVehicle = false;
            moveVehicle = false;
        } else if (v2.getRoadLocation() - v2.getLength() - nextRoadLocation <= stopVehicle) {
            if (v.getId() != v2.getId() && v.getLocation() == v2.getLocation()) {
                if (v.getSpeed() > 1) {
                    v.setSpeed(v.getSpeed() - 1);
                    v.setRoadLocation(nextRoadLocation);
                    accelerateVehicle = false;
                }
            }
        }
    }

    private void enterTopMap(char roadSide, int roadLocation) { // add code to show direction cars are facing
        checkMapLocation = true;
        char roadDirection = 'd';
        for (int topMap : topMap) { // search through top of map
            if (topMap >= 0) { // select a piece that has road on it --- add to check for orientation / entrances of road pieces
                for (Road road : roadArrayList) {
                    if (topMap == road.getLocation()) {
                        if ("oneWay".equals(road.getName())) {
                            if (road.getOrientation() == 1) {
                                checkVehicleList(roadSide, roadLocation, topMap, roadDirection);
                            }
                        } else if ("fourWay".equals(road.getName())) {
                            checkVehicleList(roadSide, roadLocation, topMap, roadDirection);
                        } else if (road.getName().contains("threeWay")) {
                            if (road.getOrientation() == 2 || road.getOrientation() == 3 || road.getOrientation() == 4) {
                                checkVehicleList(roadSide, roadLocation, topMap, roadDirection);
                            }
                        }
                        // add code for 3way intersection - first fix up map making , orientation, and options for redundant orientations
                        // add code for car class to have direction (char u(up) d (down) l(left) r(right), use to move cars
                    }
                }
            }
        }
    }

    private void enterBottomMap(char roadSide, int roadLocation) {
        checkMapLocation = true;
        char roadDirection = 'u';
        for (int bottomMap : bottomMap) { // search through top of map
            if (bottomMap > 0) { // select a piece that has road on it --- add to check for orientation / entrances of road pieces
                for (Road road : roadArrayList) {
                    if (bottomMap == road.getLocation()) { // TOP OF MAP - repeat code for all 4 sides road.getName().equals("4-way intersection")
                        switch (road.getName()) {
                            case "oneWay":
                                if (road.getOrientation() == 1) {
                                    checkVehicleList(roadSide, roadLocation, bottomMap, roadDirection);
                                }

                                break;
                            case "fourWay":
                                checkVehicleList(roadSide, roadLocation, bottomMap, roadDirection);
                                break;
                            case "threeWay":
                                if (road.getOrientation() == 1 || road.getOrientation() == 3 || road.getOrientation() == 4) {
                                    checkVehicleList(roadSide, roadLocation, bottomMap, roadDirection);
                                }
                                break;
                        }
                        // add code for 3way intersection - first fix up map making , orientation, and options for redundant orientations
                        // add code for car class to have direction (char u(up) d (down) l(left) r(right), use to move cars
                    }
                }
            }
        }
    }

    private void enterLeftMap(char roadSide, int roadLocation) {
        char roadDirection = 'r';
        checkMapLocation = true;
        for (int leftMap : leftMap) { // search through top of map
            if (leftMap > 0) { // select a piece that has road on it --- add to check for orientation / entrances of road pieces
                for (Road road : roadArrayList) {
                    if (leftMap == road.getLocation()) { // TOP OF MAP - repeat code for all 4 sides road.getName().equals("4-way intersection")
                        if ("oneWay".equals(road.getName())) {
                            if (road.getOrientation() == 1) { /// might not be correct orientation
                                checkVehicleList(roadSide, roadLocation, leftMap, roadDirection);
                            }
                        } else if ("fourWay".equals(road.getName())) {
                            checkVehicleList(roadSide, roadLocation, leftMap, roadDirection);
                        } else if (road.getName().contains("threeWay")) {
                            if (road.getOrientation() == 2 || road.getOrientation() == 3 || road.getOrientation() == 4) {
                                checkVehicleList(roadSide, roadLocation, leftMap, roadDirection);
                            }
                        }
                    }
                }
            }
        }
    }

    private void enterRightMap(char roadSide, int roadLocation) {
        checkMapLocation = true;
        char roadDirection = left;

        for (int i : rightMap) { // search through top of map
            if (i > 0) { // select a piece that has road on it --- add to check for orientation / entrances of road pieces
                for (Road road : roadArrayList) {
                    // TOP OF MAP - repeat code for all 4 sides road.getName().equals("4-way intersection")
                    if (i == road.getLocation()) {
                        // System.out.println(i);
                        if ("oneWayTwo".equals(road.getName())) {
                            checkVehicleList(roadSide, roadLocation, i, roadDirection);
                        } else if ("fourWay".equals(road.getName())) {
                            checkVehicleList(roadSide, roadLocation, i, roadDirection);
                        } else if (road.getName().contains("threeWay")) {
                            if (road.getOrientation() == 1 || road.getOrientation() == 2 || road.getOrientation() == 4) {
                                checkVehicleList(roadSide, roadLocation, i, roadDirection);
                            }
                        }
                        // add code for 3way intersection - first fix up map making , orientation, and options for redundant orientations
                        // add code for car class to have direction (char u(up) d (down) l(left) r(right), use to move cars
                    }

                }
            }
        }
    }

    private void checkVehicleList(char roadSide, int roadLocation, int i, char roadDirection) {

        for (Vehicle v : vehiclesArrayList) {
            if (i == v.getLocation() && v.getRoadLocation() < 150 + v.getLength()) {
                checkMapLocation = false;
                break;
            }
        }
        if (checkMapLocation) {
            addVehicle(roadSide, roadLocation, i, roadDirection);

            checkMapLocation = true;
        }
        checkMapLocation = true;
    }

    private void addVehicle(char roadSide, int roadLocation, int i, char roadDirection) {
        Collections.shuffle(vehiclesArrayList);
        for (Vehicle vehicle : vehiclesArrayList) {
            if (vehicle.getRoadDirection() == 'u') {
                vehicle.setLocation(i);
                vehicle.setRoadSide(roadSide);
                vehicle.setRoadLocation(roadLocation);
                vehicle.setRoadDirection(roadDirection);
                break;
            }
        }
        //carsOnMap++;
    }


    private static void createEmptyMap(int mapSize, int currentSize, int[] currentMap) {
        // creates a empty map
        for (int i = 1; i < mapSize + 1; ) {
            currentMap[currentSize] = i;
            i = i + 1;
            currentSize += 1;
        }
    }

    private static void saveRoad(ArrayList<Road> roadArrayList, ArrayList<TrafficLight> trafficLightArrayList) throws
            IOException { // change to be able to save as new map
        // saves map to to txt file
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

    private static class loadRoad { // crashes program if map.txt not found, redesign so you can choose between maps and different map sizes
        // loads map.txt for simulation
        private static ArrayList<Road> invoke(String name) throws IOException, ClassNotFoundException {
            ArrayList<Road> roadArrayList;
            FileInputStream readRoad = new FileInputStream(name);
            ObjectInputStream ois = new ObjectInputStream(readRoad);
            roadArrayList = (ArrayList<Road>) ois.readObject();
            readRoad.close();
            return roadArrayList;
        }
    }

    private static class loadTrafficLight { // crashes program if map.txt not found, redesign so you can choose between maps and different map sizes
        // loads map.txt for simulation
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
