import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class Spring{
    private Vector normalVector = new Vector(0, 0);
    private final float restLength;
    public final Joint joint, joint1;
    private float k;
    public boolean isOutsideSpring = false;
    public Spring(Joint joint, Joint joint1, float restLength, float k) {
        this.joint = joint;
        this.joint1 = joint1;
        this.restLength = restLength;
        this.k = k;
    }
    public Spring(Joint joint, Joint joint1, float k) {
        this.joint = joint;
        this.joint1 = joint1;
        this.restLength = getCurrentLength();
        System.out.println(restLength);
        this.k = k;
    }
    public Spring(Joint joint, Joint joint1, float k, boolean isOutsideSpring) {
        this.joint = joint;
        this.joint1 = joint1;
        this.restLength = getCurrentLength();
        this.isOutsideSpring = isOutsideSpring;
        System.out.println(restLength);
        this.k = k;
    }
    public void update(){
        Vector force = Vector.sub(joint1.getPosition(), joint.getPosition());
        float x = force.mag() - restLength;
        force.normalize();
        force = force.mult(k*x);
        joint.applyForce(force);
        force = force.mult(-1);
        joint1.applyForce(force);
        float currentLength = getCurrentLength();
        float normalX, normalY;
        if(isOutsideSpring) {
            normalX = Math.abs(joint.getActualX() - joint1.getActualX());
            normalY = Math.abs(joint.getActualY() - joint1.getActualY());
            if(joint.getActualX() < joint1.getActualX()) {
                if(joint.getActualY() < joint1.getActualY()) {
                    //good
                    normalVector.setX(-normalY);
                    normalVector.setY(normalX);
                }else{
                    normalVector.setX(normalY);
                    normalVector.setY(normalX);
                }
            }else{
                if(joint.getActualY() < joint1.getActualY()) {
                    //good
                    normalVector.setX(-normalY);
                    normalVector.setY(-normalX);
                }else{
                    normalVector.setX(normalY);
                    normalVector.setY(-normalX);
                }
            }
        }
    }

    public float getCurrentLength(){
        return (float) Math.sqrt(Math.pow(joint.getActualX()- joint1.getActualX(),2) + Math.pow(joint.getActualY()- joint1.getActualY(),2));
    }
    public Vector getNormalVector(){
        return normalVector;
    }
}
