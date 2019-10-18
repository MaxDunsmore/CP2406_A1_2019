import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MapPiecesRun extends JLabel implements ActionListener {
    private ImageIcon oneWay,oneWayTwo, threeWayOne, threeWayTwo, threeWayThree, threeWayFour, fourWay;
    private JLabel oneWayIcon, threeWayIcon, fourWayIcon;

    private JRadioButton oneWayRoadButton;
    private JRadioButton threeWayRoadButton;
    private JRadioButton fourWayRoadButton;
    private int position;
    private int map;
    ArrayList<Road> roadArrayList;
    int i = 0;

    MapPiecesRun(JRadioButton oneWayRoadButton, JRadioButton threeWayRoadButton, JRadioButton fourWayRoadButton, JLabel oneWayIcon, JLabel threeWayIcon, JLabel fourWayIcon, int position, ArrayList<Road> roadArrayList, int map) {
        oneWay = new ImageIcon(this.getClass().getResource("images/oneWay.png"));
        oneWayTwo = new ImageIcon(this.getClass().getResource("images/oneWayTwo.png"));

        threeWayOne = new ImageIcon(this.getClass().getResource("images/threeWayOne.png"));
        threeWayTwo = new ImageIcon(this.getClass().getResource("images/threeWayTwo.png"));
        threeWayThree = new ImageIcon(this.getClass().getResource("images/threeWayThree.png"));
        threeWayFour = new ImageIcon(this.getClass().getResource("images/threeWayFour.png"));

        fourWay = new ImageIcon(this.getClass().getResource("images/fourWay.png"));

        this.oneWayRoadButton = oneWayRoadButton;
        this.threeWayRoadButton = threeWayRoadButton;
        this.fourWayRoadButton = fourWayRoadButton;
        this.oneWayIcon = oneWayIcon;
        this.threeWayIcon = threeWayIcon;
        this.fourWayIcon = fourWayIcon;
        this.position = position;
        this.roadArrayList = roadArrayList;
        this.map = map;

        for (Road road : roadArrayList){
            if (road.getLocation() == position ){
                if (road.getName().equals("Straight")){
                    if (road.getOrientation() == 1){
                        setIcon(oneWay);
                    }
                    else if (road.getOrientation() == 2){
                        setIcon(oneWayTwo);
                    }
                }
                else if(road.getName().equals("2-Way intersection")){
                    if (road.getOrientation() == 1){
                        setIcon(threeWayOne);
                    }
                    else if (road.getOrientation() == 2){
                        setIcon(threeWayTwo);
                    }
                    else if (road.getOrientation() == 3){
                        setIcon(threeWayThree);
                    }
                    else if (road.getOrientation() == 4){
                        setIcon(threeWayFour);
                    }
                }
                else if(road.getName().equals("4-Way intersection")){
                    setIcon(fourWay);
                }
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
