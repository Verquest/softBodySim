import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Canvas extends JPanel {
    ArrayList<Joint> joints = new ArrayList<>();
    ArrayList<Spring> springs = new ArrayList<>();
    public Canvas(ArrayList<Joint> joints, ArrayList<Spring> springs){
        this.joints = joints;
        this.springs = springs;
        for(Joint j: joints)
            this.add(j);
        for(Spring s: springs)
            this.add(s);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Joint joint: joints){
            g.fillOval((int)joint.getActualX(), (int)joint.getActualY(), joint.getDrawSize(), joint.getDrawSize());
            joint.repaint();
        }
        for(Spring spring: springs){
            int size = spring.joint.getDrawSize()/2;
            int size1 = spring.joint1.getDrawSize()/2;
            g.drawLine((int)spring.joint.getActualX() + size, (int)spring.joint.getActualY() + size,
                    (int)spring.joint1.getActualX() + size1, (int)spring.joint1.getActualY() + size1);
        }
    }
}
