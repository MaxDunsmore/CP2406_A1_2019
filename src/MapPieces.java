import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MapPieces extends JButton implements ActionListener, ComponentListener {
    private ImageIcon oneWay, oneWayTwo, threeWayOne, threeWayTwo, threeWayThree, threeWayFour, fourWay, trafficLight;
    private JLabel oneWayIcon, threeWayIcon, fourWayIcon, trafficLightIcon;
    private JRadioButton oneWayRoadButton;
    private JRadioButton threeWayRoadButton;
    private JRadioButton fourWayRoadButton;
    private JRadioButton trafficLightButton;
    private int position;
    private int map;
    private int location;
    private String name = "";
    private ArrayList<Road> roadArrayList;
    private ArrayList<TrafficLight> trafficLightArrayList;
    int i = 0;
    int newX;
    int newY;
    private int w = getWidth();
    private int h = getHeight();
    private double positionW;
    private double positionH;
    private int gameHeight;
    private int gameWidth;
    boolean trafficLightDraw = false;
    private boolean oneWayDraw = false;
    private boolean oneWayTwoDraw = false;
    private boolean threeWayOneDraw = false;
    private boolean threeWayTwoDraw = false;
    private boolean threeWayThreeDraw = false;
    private boolean threeWayFourDraw = false;
    private boolean fourWayDraw = false;
    private boolean remove = true;
    private String imgPath = "images/trafficLight.png";
    final BufferedImage image = ImageIO.read(getClass().getResourceAsStream(imgPath));

    MapPieces(JRadioButton oneWayRoadButton, JRadioButton threeWayRoadButton, JRadioButton fourWayRoadButton, JLabel oneWayIcon, JLabel threeWayIcon, JLabel fourWayIcon, int position, ArrayList<Road> roadArrayList, int map, JLabel trafficLightIcon, JRadioButton trafficLightButton, ArrayList<TrafficLight> trafficLightArrayList) throws IOException {
        oneWay = new ImageIcon(this.getClass().getResource("images/oneWay.png"));
        oneWayTwo = new ImageIcon(this.getClass().getResource("images/oneWayTwo.png"));
        threeWayOne = new ImageIcon(this.getClass().getResource("images/threeWayOne.png"));
        threeWayTwo = new ImageIcon(this.getClass().getResource("images/threeWayTwo.png"));
        threeWayThree = new ImageIcon(this.getClass().getResource("images/threeWayThree.png"));
        threeWayFour = new ImageIcon(this.getClass().getResource("images/threeWayFour.png"));
        fourWay = new ImageIcon(this.getClass().getResource("images/fourWay.png"));
        trafficLight = new ImageIcon(this.getClass().getResource("images/trafficLight.png"));
        Toolkit.getDefaultToolkit().setDynamicLayout(true);
        this.addActionListener(this);
        this.addComponentListener(this);
        this.oneWayRoadButton = oneWayRoadButton;
        this.threeWayRoadButton = threeWayRoadButton;
        this.fourWayRoadButton = fourWayRoadButton;
        this.trafficLightButton = trafficLightButton;
        this.oneWayIcon = oneWayIcon;
        this.threeWayIcon = threeWayIcon;
        this.fourWayIcon = fourWayIcon;
        this.position = position;
        this.roadArrayList = roadArrayList;
        this.trafficLightArrayList = trafficLightArrayList;
        this.map = map;
 /*       for(Road road : roadArrayList){
            BufferedImage img = null;
            String iconPath = "images/" + road.getName() + ".png";
            try {
                img = ImageIO.read(this.getClass().getResource(iconPath));
            } catch (IOException error) {
                error.printStackTrace();
            }
            w = getWidth();
            h = getHeight();
            Image dimg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(dimg));
        }
 */
        for (TrafficLight trafficLight : trafficLightArrayList) {
            if (trafficLight.getLocation() == position) {
                if (trafficLight.getRoadName().equals("oneWay")) {
                    oneWayDraw = true;
                    location = trafficLight.getRoadLocation();
                } else if (trafficLight.getRoadName().equals("oneWayTwo")) {
                    oneWayTwoDraw = true;
                    location = trafficLight.getRoadLocation();
                } else if (trafficLight.getRoadName().equals("threeWayOne")) {
                    threeWayOneDraw = true;
                } else if (trafficLight.getRoadName().equals("threeWayTwo")) {
                    threeWayTwoDraw = true;
                } else if (trafficLight.getRoadName().equals("threeWayThree")) {
                    threeWayThreeDraw = true;
                } else if (trafficLight.getRoadName().equals("threeWayFour")) {
                    threeWayFourDraw = true;
                } else if (trafficLight.getRoadName().equals("fourWay")) {
                    fourWayDraw = true;
                }
            }
            repaint();
        }
        for (Road road : roadArrayList) {
            // redesign code to match new names - figure out how to use resize code
            if (road.getLocation() == position) {
                switch (road.getName()) {
                    case "oneWay":
                        setIcon(oneWay);
                        name = "oneWay";
                        break;
                    case "oneWayTwo":
                        setIcon(oneWayTwo);
                        name = "oneWayTwo";
                        break;
                    case "threeWayOne":
                        setIcon(threeWayOne);
                        name = "threeWayOne";
                        break;
                    case "threeWayTwo":
                        setIcon(threeWayTwo);
                        name = "threeWayTwo";
                        break;
                    case "threeWayThree":
                        setIcon(threeWayThree);
                        name = "threeWayThree";
                        break;
                    case "threeWayFour":
                        setIcon(threeWayFour);
                        name = "threeWayFour";
                        break;
                    case "fourWay":
                        setIcon(fourWay);
                        name = "fourWay";
                        break;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // for loop for each piece in grid to replace image with code below - use code to get name of current image
        if (trafficLightButton.isSelected()) {
            remove = false;
        }
        if (remove) {
            roadArrayList.removeIf(road -> road.getLocation() == position);
        }
        if (oneWayRoadButton.isSelected()) {
            if (oneWayIcon.getIcon().toString().contains("oneWayAdd.png")) {
                Road road = new Road("oneWay", 1, position, map);
                roadArrayList.add(road);
                name = "oneWay";
            } else if (oneWayIcon.getIcon().toString().contains("oneWayTwoAdd.png")) {
                Road road = new Road("oneWayTwo", 2, position, map);
                roadArrayList.add(road);
                name = "oneWayTwo";
            }
        }
        if (threeWayRoadButton.isSelected()) {
            if (threeWayIcon.getIcon().toString().contains("threeWayOneAdd.png")) {
                Road road = new Road("threeWayOne", 1, position, map);
                roadArrayList.add(road);
                name = "threeWayOne";
            } else if (threeWayIcon.getIcon().toString().contains("threeWayTwoAdd.png")) {
                Road road = new Road("threeWayTwo", 2, position, map);
                roadArrayList.add(road);
                name = "threeWayTwo";
            } else if (threeWayIcon.getIcon().toString().contains("threeWayThreeAdd.png")) {
                Road road = new Road("threeWayThree", 3, position, map);
                roadArrayList.add(road);
                name = "threeWayThree";
            } else if (threeWayIcon.getIcon().toString().contains("threeWayFourAdd.png")) {
                Road road = new Road("threeWayFour", 4, position, map);
                roadArrayList.add(road);
                name = "threeWayFour";
            }
        }
        if (fourWayRoadButton.isSelected()) {
            name = "fourWay";
            Road road = new Road("fourWay", 1, position, map);
            roadArrayList.add(road);
        }
        if (trafficLightButton.isSelected()) {
            location = 0;
            if (name.equals("oneWay")) {
                String[] options = {"Top", "Bottom"};
                location = JOptionPane.showOptionDialog(null, "Choose where you would like the traffic light to go", "Traffic light position", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                oneWayDraw = true;
                oneWayTwoDraw = false;
                threeWayOneDraw = false;
                threeWayTwoDraw = false;
                threeWayThreeDraw = false;
                threeWayFourDraw = false;
                fourWayDraw = false;
                TrafficLight trafficLight = new TrafficLight(position, 1, location, 0, 1, "oneWay", 1);
                trafficLightArrayList.add(trafficLight);
                repaint();
            } else if (name.equals("oneWayTwo")) {
                String[] options = {"Left", "Right"};
                location = JOptionPane.showOptionDialog(null, "Choose where you would like the traffic light to go", "Traffic light position", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                oneWayDraw = false;
                oneWayTwoDraw = true;
                threeWayOneDraw = false;
                threeWayTwoDraw = false;
                threeWayThreeDraw = false;
                threeWayFourDraw = false;
                fourWayDraw = false;
                TrafficLight trafficLight = new TrafficLight(position, 1, location, 0, 1, "oneWayTwo", 1);
                trafficLightArrayList.add(trafficLight);
                repaint();
            } else if (name.equals("threeWayOne")) {
                oneWayDraw = false;
                oneWayTwoDraw = false;
                threeWayOneDraw = true;
                threeWayTwoDraw = false;
                threeWayThreeDraw = false;
                threeWayFourDraw = false;
                fourWayDraw = false;
                addTrafficLightFourWay(0, "threeWayOne");
                repaint();
            } else if (name.equals("threeWayTwo")) {
                oneWayDraw = false;
                oneWayTwoDraw = false;
                threeWayOneDraw = false;
                threeWayTwoDraw = true;
                threeWayThreeDraw = false;
                threeWayFourDraw = false;
                fourWayDraw = false;
                addTrafficLightFourWay(0, "threeWayTwo");
                repaint();
            } else if (name.equals("threeWayThree")) {
                oneWayDraw = false;
                oneWayTwoDraw = false;
                threeWayOneDraw = false;
                threeWayTwoDraw = false;
                threeWayThreeDraw = true;
                threeWayFourDraw = false;
                fourWayDraw = false;
                addTrafficLightFourWay(0, "threeWayThree");
                repaint();
            } else if (name.equals("threeWayFour")) {
                oneWayDraw = false;
                oneWayTwoDraw = false;
                threeWayOneDraw = false;
                threeWayTwoDraw = false;
                threeWayThreeDraw = false;
                threeWayFourDraw = true;
                fourWayDraw = false;
                addTrafficLightFourWay(0, "threeWayFour");
                repaint();
            } else if (name.equals("fourWay")) {
                oneWayDraw = false;
                oneWayTwoDraw = false;
                threeWayOneDraw = false;
                threeWayTwoDraw = false;
                threeWayThreeDraw = false;
                threeWayFourDraw = false;
                fourWayDraw = true;
                addTrafficLightFourWay(1, "fourWay");
                repaint();
            }
        }
        BufferedImage img = null;
        String iconPath = "images/" + this.name + ".png";
        try {
            img = ImageIO.read(this.getClass().getResource(iconPath));
        } catch (IOException error) {
            error.printStackTrace();
        }
        w = getWidth();
        h = getHeight();
        Image dimg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(dimg));

    }

    private void addTrafficLightFourWay(int i, String s) {
        TrafficLight trafficLight41 = new TrafficLight(position, i, 't', 0, 1, s, 0);
        TrafficLight trafficLight42 = new TrafficLight(position, 1, 't', 0, 2, s, 0);
        TrafficLight trafficLight43 = new TrafficLight(position, i, 't', 0, 3, s, 0);
        trafficLight41.printTrafficLight();
        trafficLightArrayList.add(trafficLight41);
        trafficLightArrayList.add(trafficLight42);
        trafficLightArrayList.add(trafficLight43);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (oneWayDraw) {
            w = getWidth();
            h = getHeight();

            gameWidth = (int) (Math.round(w * 0.4));
            gameWidth = gameWidth - 24;
            if (location == 0) {
                gameHeight = (int) (Math.round(h * 0.01));
            } else if (location == 1) {
                gameHeight = (int) (Math.round(h * 0.99));
                gameHeight = gameHeight - 54;
            }
            g.drawImage(image, gameWidth, gameHeight, null);
        }
        if (oneWayTwoDraw) {
            w = getWidth();
            h = getHeight();
            gameHeight = (int) (Math.round(h * 0.4));
            gameHeight = gameHeight - 54;
            if (gameHeight < 0) {
                gameHeight = 0;
            }
            if (location == 0) {
                gameWidth = (int) (Math.round(w * 0.01));
            } else if (location == 1) {
                gameWidth = (int) (Math.round(w * 0.99));
                gameWidth = gameWidth - 24;
            }
            g.drawImage(image, gameWidth, gameHeight, null);
        }
        if (fourWayDraw) {
            // Traffic Light 1
            w = getWidth();
            h = getHeight();
            trafficLightOne(g);
            trafficLightTwo(g);
            trafficLightThree(g);
            trafficLightFour(g);
        }
        if (threeWayOneDraw) {
            trafficLightOne(g);
            trafficLightThree(g);
            trafficLightFour(g);
        }
        if (threeWayTwoDraw) {
            trafficLightOne(g);
            trafficLightTwo(g);
            trafficLightThree(g);
        }
        if (threeWayThreeDraw) {
            trafficLightOne(g);
            trafficLightTwo(g);
            trafficLightFour(g);
        }
        if (threeWayFourDraw) {
            trafficLightOne(g);
            trafficLightThree(g);
            trafficLightFour(g);
        }
    }

    private void trafficLightFour(Graphics g) {
        gameWidth = (int) (Math.round(w * 0.6));
        gameHeight = (int) (Math.round(h * 0.6));
        g.drawImage(image, gameWidth, gameHeight, null);
    }

    private void trafficLightThree(Graphics g) {
        gameWidth = (int) (Math.round(w * 0.4));
        gameWidth = gameWidth - 24;
        gameHeight = (int) (Math.round(h * 0.6));
        g.drawImage(image, gameWidth, gameHeight, null);
    }

    private void trafficLightTwo(Graphics g) {
        gameWidth = (int) (Math.round(w * 0.6));
        gameHeight = (int) (Math.round(h * 0.4));
        gameHeight = gameHeight - 54;
        g.drawImage(image, gameWidth, gameHeight, null);
    }

    private void trafficLightOne(Graphics g) {
        positionW = 0.4;
        positionH = 0.4;
        gameWidth = (int) (Math.round(w * positionW));
        gameWidth = gameWidth - 24;
        gameHeight = (int) (Math.round(h * positionH));
        gameHeight = gameHeight - 54;
        g.drawImage(image, gameWidth, gameHeight, null);
    }


    @Override
    public void componentResized(ComponentEvent e) {
        if (!name.equals("")) {
            BufferedImage img = null;
            String test3 = "images/" + this.name + ".png";
            try {
                img = ImageIO.read(this.getClass().getResource(test3));
            } catch (IOException error) {
                error.printStackTrace();
            }
            w = getWidth();
            h = getHeight();
            Image dimg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(dimg));
        }

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
