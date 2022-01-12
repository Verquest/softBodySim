import javax.swing.*;
import java.awt.*;

public class Joint extends JPanel{
    private final int size;
    private Vector velocity, position, acceleration, gravity;
    private Color color;
    private final float mass, bounciness;
    private boolean stationary = false;
    Joint(int x, int y, int size){
        acceleration = new Vector(0,0);
        velocity = new Vector(0,0);
        position = new Vector(x, y);
        gravity = new Vector(0, 0.05f);
        mass = 1f;
        bounciness = 1f;
        color = Color.BLACK;
        this.size = size;
        this.setOpaque(true);
    }
    public void applyForce(Vector force){
        force.div(mass);
        acceleration = acceleration.add(force);
    }
    public void resetAcceleration(){
        acceleration = new Vector(0, 0);
    }
    public void update(){
        if(!stationary) {
            Vector pendingPosition;
            velocity = velocity.mult(0.8f);
            velocity = velocity.add(acceleration);
            velocity = velocity.add(gravity);
            pendingPosition = position.add(velocity);
            int pendingX = (int) pendingPosition.getX();
            int pendingY = (int) pendingPosition.getY();
            if (pendingX >= size / 2 && pendingX <= 770 - size) {
                position.setX(pendingPosition.getX());
            }

            if (pendingY >= size / 2 && pendingY <= 770 - size) {
                position.setY(pendingPosition.getY());
            }
            acceleration = acceleration.mult(0);
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillOval((int)position.getX(), (int)position.getY(), size, size);
    }
    public void addToVelocity(Vector vector){
        velocity = velocity.add(vector);
    }
    public Vector getPosition(){
        return position;
    }
    public int getDrawSize(){
        return size;
    }
    public Color getColor() {
        return color;
    }
    public float getActualX() {
        return position.getX();
    }

    public float getActualY() {
        return position.getY();
    }
    public void setActualX(float x) {
        position.setX(x);
    }

    public void setActualY(float y) {
        position.setY(y);
    }
    public void setGravity(Vector gravity){
        this.gravity = gravity;
    }
    public void setStationary(boolean stationary){
        this.stationary = stationary;
    }
}
