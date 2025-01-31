import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MapPiecesRun extends JLabel implements ComponentListener {
    private String name = "";

    MapPiecesRun(int position, ArrayList<Road> roadArrayList) {
        // declares icons for map pieces
        ImageIcon oneWay = new ImageIcon(this.getClass().getResource("images/oneWay.png"));
        ImageIcon oneWayTwo = new ImageIcon(this.getClass().getResource("images/oneWayTwo.png"));
        ImageIcon threeWayOne = new ImageIcon(this.getClass().getResource("images/threeWayOne.png"));
        ImageIcon threeWayTwo = new ImageIcon(this.getClass().getResource("images/threeWayTwo.png"));
        ImageIcon threeWayThree = new ImageIcon(this.getClass().getResource("images/threeWayThree.png"));
        ImageIcon threeWayFour = new ImageIcon(this.getClass().getResource("images/threeWayFour.png"));
        ImageIcon fourWay = new ImageIcon(this.getClass().getResource("images/fourWay.png"));
        Toolkit.getDefaultToolkit().setDynamicLayout(true);
        this.addComponentListener(this);
        for (Road road : roadArrayList) {
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
            int w = getWidth();
            int h = getHeight();
            assert img != null;
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
