import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Canvas extends JPanel {
    ArrayList<Joint> joints = new ArrayList<>();
    ArrayList<Spring> springs = new ArrayList<>();
    public Canvas(ArrayList<Joint> joints, ArrayList<Spring> springs){
        this.joints = joints;
        this.springs = springs;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Joint joint: joints){
            g.setColor(joint.getColor());
            g.fillOval((int)joint.getActualX(), (int)joint.getActualY(), joint.getDrawSize(), joint.getDrawSize());
            //current force on particle
//            g.setColor(Color.GREEN);
//            g.drawLine((int)joint.getActualX()  + joint.getDrawSize()/2, (int)joint.getActualY()  + joint.getDrawSize()/2,
//                        (int)(joint.getLastVelo().getX() * 10 + joint.getActualX()), (int)(joint.getLastVelo().getY() * 10 + joint.getActualY()));
        }
        g.setColor(Color.BLACK);
        for(Spring spring: springs){
            int size = spring.joint.getDrawSize()/2;
            int size1 = spring.joint1.getDrawSize()/2;
            int xAvg = (int)((spring.joint.getPosition().getX() + spring.joint1.getPosition().getX())/2) + spring.joint.getDrawSize()/2;
            int yAvg = (int)((spring.joint.getPosition().getY() + spring.joint1.getPosition().getY())/2) + spring.joint.getDrawSize()/2;
            //normal vecotor
            g.drawLine((int)spring.joint.getActualX() + size, (int)spring.joint.getActualY() + size,
                    (int)spring.joint1.getActualX() + size1, (int)spring.joint1.getActualY() + size1);
//            g.setColor(Color.BLUE);
//            g.drawLine(xAvg, yAvg, (int)(xAvg + spring.getNormalVector().getX() * 3), (int)(yAvg + spring.getNormalVector().getY() * 3));
            g.setColor(Color.BLACK);
        }
    }
}
