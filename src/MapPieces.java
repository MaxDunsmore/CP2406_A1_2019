import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MapPieces extends JButton implements ActionListener {
    private ImageIcon oneWay,oneWayTwo, threeWayOne, threeWayTwo, threeWayThree, threeWayFour, fourWay;
    private JLabel oneWayIcon, threeWayIcon, fourWayIcon;

    private JRadioButton oneWayRoadButton;
    private JRadioButton threeWayRoadButton;
    private JRadioButton fourWayRoadButton;
    private int position;
    private int map;
    ArrayList<Road> roadArrayList;
    int i = 0;

    MapPieces (JRadioButton oneWayRoadButton,JRadioButton threeWayRoadButton,JRadioButton fourWayRoadButton, JLabel oneWayIcon, JLabel threeWayIcon, JLabel fourWayIcon, int position,ArrayList<Road> roadArrayList, int map) {
        oneWay = new ImageIcon(this.getClass().getResource("images/oneWay.png"));
        oneWayTwo = new ImageIcon(this.getClass().getResource("images/oneWayTwo.png"));

        threeWayOne = new ImageIcon(this.getClass().getResource("images/threeWayOne.png"));
        threeWayTwo = new ImageIcon(this.getClass().getResource("images/threeWayTwo.png"));
        threeWayThree = new ImageIcon(this.getClass().getResource("images/threeWayThree.png"));
        threeWayFour = new ImageIcon(this.getClass().getResource("images/threeWayFour.png"));

        fourWay = new ImageIcon(this.getClass().getResource("images/fourWay.png"));


        this.addActionListener(this);

        this.oneWayRoadButton = oneWayRoadButton;
        this.threeWayRoadButton = threeWayRoadButton;
        this.fourWayRoadButton = fourWayRoadButton;
        this.oneWayIcon = oneWayIcon;
        this.threeWayIcon = threeWayIcon;
        this.fourWayIcon = fourWayIcon;
        this.position = position;
        this.roadArrayList = roadArrayList;
        this.map = map;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        roadArrayList.removeIf(road -> road.getLocation() == position);
        if (oneWayRoadButton.isSelected()){
            if (oneWayIcon.getIcon().toString().contains("oneWayAdd.png")){
                Road road = new Road("Straight",1,position, map);
                setIcon(oneWay);
                roadArrayList.add(road);
            }
            else if (oneWayIcon.getIcon().toString().contains("oneWayTwoAdd.png")){
                Road road = new Road("Straight",2,position, map);
                setIcon(oneWayTwo);
                roadArrayList.add(road);
            }
        }
        if (threeWayRoadButton.isSelected()){
            if (threeWayIcon.getIcon().toString().contains("threeWayOneAdd.png")){
                Road road = new Road("2-Way intersection",1,position, map);
                setIcon(threeWayOne);
                roadArrayList.add(road);
            }
            else if (threeWayIcon.getIcon().toString().contains("threeWayTwoAdd.png")){
                Road road = new Road("2-Way intersection",2,position, map);
                setIcon(threeWayTwo);
                roadArrayList.add(road);
            }
            else if (threeWayIcon.getIcon().toString().contains("threeWayThreeAdd.png")){
                Road road = new Road("2-Way intersection",3,position, map);
                setIcon(threeWayThree);
                roadArrayList.add(road);
            }
            else if (threeWayIcon.getIcon().toString().contains("threeWayFourAdd.png")){
                Road road = new Road("2-Way intersection",4,position, map);
                setIcon(threeWayFour);
                roadArrayList.add(road);
            }
        }
        if (fourWayRoadButton.isSelected()){
            Road road = new Road("4-Way intersection",1,position, map);
            setIcon(fourWay);
            roadArrayList.add(road);
        }

    }
}
