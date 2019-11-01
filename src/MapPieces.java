import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MapPieces extends JButton implements ActionListener, ComponentListener {
    // declares icons for map pieces
    private JLabel oneWayIcon, threeWayIcon;
    private JRadioButton oneWayRoadButton;
    private JRadioButton threeWayRoadButton;
    private JRadioButton fourWayRoadButton;
    private JRadioButton trafficLightButton;
    private int position; // position of road pieces
    private int map; // size of map
    private int location; // location of traffic lights
    private String name = ""; // name of road piece
    private ArrayList<Road> roadArrayList;
    private ArrayList<TrafficLight> trafficLightArrayList;
    private int w = getWidth(); // height of gridLayout
    private int h = getHeight(); // width of gridLayout
    private int gameHeight;// height of map piece
    private int gameWidth;;// width of map piece
    // boolean value to determine if to draw traffic lights
    private boolean oneWayDraw = false;
    private boolean oneWayTwoDraw = false;
    private boolean threeWayOneDraw = false;
    private boolean threeWayTwoDraw = false;
    private boolean threeWayThreeDraw = false;
    private boolean threeWayFourDraw = false;
    private boolean fourWayDraw = false;
    private boolean remove = true;
    private String imgPath = "images/trafficLight.png"; // traffic light image path
    private final BufferedImage image = ImageIO.read(getClass().getResourceAsStream(imgPath)); // load traffic light as image

    MapPieces(JRadioButton oneWayRoadButton, JRadioButton threeWayRoadButton, JRadioButton fourWayRoadButton, JLabel oneWayIcon, JLabel threeWayIcon, int position, ArrayList<Road> roadArrayList, int map, JRadioButton trafficLightButton, ArrayList<TrafficLight> trafficLightArrayList) throws IOException {
        ImageIcon oneWay = new ImageIcon(this.getClass().getResource("images/oneWay.png"));
        ImageIcon oneWayTwo = new ImageIcon(this.getClass().getResource("images/oneWayTwo.png"));
        ImageIcon threeWayOne = new ImageIcon(this.getClass().getResource("images/threeWayOne.png"));
        ImageIcon threeWayTwo = new ImageIcon(this.getClass().getResource("images/threeWayTwo.png"));
        ImageIcon threeWayThree = new ImageIcon(this.getClass().getResource("images/threeWayThree.png"));
        ImageIcon threeWayFour = new ImageIcon(this.getClass().getResource("images/threeWayFour.png"));
        ImageIcon fourWay = new ImageIcon(this.getClass().getResource("images/fourWay.png"));
        Toolkit.getDefaultToolkit().setDynamicLayout(true);
        this.addActionListener(this);
        this.addComponentListener(this);
        this.oneWayRoadButton = oneWayRoadButton;
        this.threeWayRoadButton = threeWayRoadButton;
        this.fourWayRoadButton = fourWayRoadButton;
        this.trafficLightButton = trafficLightButton;
        this.oneWayIcon = oneWayIcon;
        this.threeWayIcon = threeWayIcon;
        this.position = position;
        this.roadArrayList = roadArrayList;
        this.trafficLightArrayList = trafficLightArrayList;
        this.map = map;
        for (TrafficLight trafficLight : trafficLightArrayList) { // goes through trafficLightArrayList to see if a traffic light should be drawn
            if (trafficLight.getLocation() == position) {
                switch (trafficLight.getRoadName()) {
                    case "oneWay":
                        oneWayDraw = true;
                        location = trafficLight.getRoadLocation();
                        break;
                    case "oneWayTwo":
                        oneWayTwoDraw = true;
                        location = trafficLight.getRoadLocation();
                        break;
                    case "threeWayOne":
                        threeWayOneDraw = true;
                        break;
                    case "threeWayTwo":
                        threeWayTwoDraw = true;
                        break;
                    case "threeWayThree":
                        threeWayThreeDraw = true;
                        break;
                    case "threeWayFour":
                        threeWayFourDraw = true;
                        break;
                    case "fourWay":
                        fourWayDraw = true;
                        break;
                }
            }
            repaint();
        }
        for (Road road : roadArrayList) {// goes through roadArrayList to see if a road icon should be added to the button
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
            switch (name) {
                case "oneWay": {
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
                    break;
                }
                case "oneWayTwo": {
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
                    break;
                }
                case "threeWayOne":
                    oneWayDraw = false;
                    oneWayTwoDraw = false;
                    threeWayOneDraw = true;
                    threeWayTwoDraw = false;
                    threeWayThreeDraw = false;
                    threeWayFourDraw = false;
                    fourWayDraw = false;
                    addTrafficLightThreeWay("threeWayOne");
                    repaint();
                    break;
                case "threeWayTwo":
                    oneWayDraw = false;
                    oneWayTwoDraw = false;
                    threeWayOneDraw = false;
                    threeWayTwoDraw = true;
                    threeWayThreeDraw = false;
                    threeWayFourDraw = false;
                    fourWayDraw = false;
                    addTrafficLightThreeWay("threeWayTwo");
                    repaint();
                    break;
                case "threeWayThree":
                    oneWayDraw = false;
                    oneWayTwoDraw = false;
                    threeWayOneDraw = false;
                    threeWayTwoDraw = false;
                    threeWayThreeDraw = true;
                    threeWayFourDraw = false;
                    fourWayDraw = false;
                    addTrafficLightThreeWay("threeWayThree");
                    repaint();
                    break;
                case "threeWayFour":
                    oneWayDraw = false;
                    oneWayTwoDraw = false;
                    threeWayOneDraw = false;
                    threeWayTwoDraw = false;
                    threeWayThreeDraw = false;
                    threeWayFourDraw = true;
                    fourWayDraw = false;
                    addTrafficLightThreeWay("threeWayFour");
                    repaint();
                    break;
                case "fourWay":
                    oneWayDraw = false;
                    oneWayTwoDraw = false;
                    threeWayOneDraw = false;
                    threeWayTwoDraw = false;
                    threeWayThreeDraw = false;
                    threeWayFourDraw = false;
                    fourWayDraw = true;
                    addTrafficLightFourWay();
                    repaint();
                    break;
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
        assert img != null; // Check how this line works
        Image scaledImage = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledImage));

    }

    private void addTrafficLightThreeWay(String s) {
        TrafficLight trafficLight41 = new TrafficLight(position, 0, 't', 0, 1, s, 0);
        TrafficLight trafficLight42 = new TrafficLight(position, 0, 't', 0, 2, s, 0);
        TrafficLight trafficLight43 = new TrafficLight(position, 0, 't', 0, 3, s, 0);
        TrafficLight trafficLight44 = new TrafficLight(position, 0, 't', 0, 4, s, 0);
        trafficLightArrayList.add(trafficLight41);
        trafficLightArrayList.add(trafficLight42);
        trafficLightArrayList.add(trafficLight43);
        trafficLightArrayList.add(trafficLight44);
    }

    private void addTrafficLightFourWay() {
        TrafficLight trafficLight41 = new TrafficLight(position, 1, 't', 0, 1, "fourWay", 0);
        TrafficLight trafficLight42 = new TrafficLight(position, 1, 't', 0, 2, "fourWay", 0);
        TrafficLight trafficLight43 = new TrafficLight(position, 1, 't', 0, 3, "fourWay", 0);
        TrafficLight trafficLight44 = new TrafficLight(position, 1, 't', 0, 4, "fourWay", 0);
        trafficLightArrayList.add(trafficLight41);
        trafficLightArrayList.add(trafficLight42);
        trafficLightArrayList.add(trafficLight43);
        trafficLightArrayList.add(trafficLight44);
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
        double positionW = 0.4;
        double positionH = 0.4;
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
            assert img != null;
            Image scaledImg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(scaledImg));
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
