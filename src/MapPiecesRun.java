import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MapPiecesRun extends JLabel implements ComponentListener {
    private ImageIcon oneWay, oneWayTwo, threeWayOne, threeWayTwo, threeWayThree, threeWayFour, fourWay;
    private int location;
    private int map;
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

    MapPiecesRun(int position, ArrayList<Road> roadArrayList, int map,  ArrayList<TrafficLight> trafficLightArrayList) throws IOException {
        oneWay = new ImageIcon(this.getClass().getResource("images/oneWay.png"));
        oneWayTwo = new ImageIcon(this.getClass().getResource("images/oneWayTwo.png"));
        threeWayOne = new ImageIcon(this.getClass().getResource("images/threeWayOne.png"));
        threeWayTwo = new ImageIcon(this.getClass().getResource("images/threeWayTwo.png"));
        threeWayThree = new ImageIcon(this.getClass().getResource("images/threeWayThree.png"));
        threeWayFour = new ImageIcon(this.getClass().getResource("images/threeWayFour.png"));
        fourWay = new ImageIcon(this.getClass().getResource("images/fourWay.png"));
        Toolkit.getDefaultToolkit().setDynamicLayout(true);
        this.addComponentListener(this);
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
