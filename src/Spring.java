import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class Spring extends JPanel {
    private final float restLength;
     final Joint joint;
     final Joint joint1;
    private float k;
    public Spring(Joint joint, Joint joint1, float restLength, float k) {
        this.joint = joint;
        this.joint1 = joint1;
        this.restLength = restLength;
        this.k = k;
        this.setOpaque(true);
    }
    public Spring(Joint joint, Joint joint1, float k) {
        this.joint = joint;
        this.joint1 = joint1;
        this.restLength = (float) Math.sqrt(Math.pow(joint.getActualX()- joint1.getActualX(),2) + Math.pow(joint.getActualY()- joint1.getActualY(),2));
        //this.restLength = Math.abs((int)Math.hypot(joint.getActualX()- joint1.getActualX(), joint.getActualY())- joint1.getActualY());
        System.out.println(restLength);
        this.k = k;
        this.setOpaque(true);
    }
    public void update(){
        Vector force = Vector.sub(joint1.getPosition(), joint.getPosition());
        float x = force.mag() - restLength;
        force.normalize();
        force = force.mult(k*x);
        joint.applyForce(force);
        force = force.mult(-1);
        joint1.applyForce(force);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        System.out.println("DRAWSIZE " + joint1.getDrawSize());
        g.drawLine((int)joint.getActualX() + joint1.getDrawSize()/2, (int)joint.getActualY() + joint1.getDrawSize()/2,
                (int)joint1.getActualX() + joint1.getDrawSize()/2, (int)joint1.getActualY() + joint1.getDrawSize()/2);
    }
}
