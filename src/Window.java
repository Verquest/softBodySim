import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Window extends JFrame {
    private final int HEIGHT = 800;
    private final int WIDTH = 800;
    private final int particleSize = 5;
    private float pressure = 0, volume;
    private final int MAX_PRESSURE = 700;
    private ArrayList<Spring> springs = new ArrayList<>();
    private ArrayList<Joint> joints = new ArrayList<>();
    private Canvas canvas;
    private Joint heldJoint;
    private Joint middleJoint;
    public boolean stopper;

    public Window() {
        jointAndSpringCreator();
        canvas = new Canvas(joints, springs);
        canvas.setLayout(null);
        this.add(canvas);
        this.setContentPane(canvas);
        this.setLocationRelativeTo(null);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        Thread t = new Thread(() -> {
            while (true) {
                for(Spring s: springs)
                    s.update();
                for (Joint joint : joints) {
                    joint.update();
                }
                calculateVolume();
                applyPressure();
                if(pressure < MAX_PRESSURE){
                    pressure += MAX_PRESSURE / 3;
                }
                this.repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        this.addMouseListener(new BallDragger(joints, this));
    }

    public void setStopper(Joint held) {
        heldJoint = held;
    }
    public void calculateVolume(){
        volume = 0;
        for(int i = 0; i < springs.size(); i++){
            float springLength = springs.get(i).getCurrentLength();
            volume += Math.abs(springs.get(i).joint.getActualX() - springs.get(i).joint1.getActualX())*Math.abs(springs.get(i).getNormalVector().getX())*springLength;
        }
        volume/=2;
    }
    public void applyPressure(){
        float pressureVec;
        for(int i = 0; i < springs.size(); i++){
            Spring current_spring = springs.get(i);
            float springLength = springs.get(i).getCurrentLength();
            pressureVec = springLength * pressure * (1/volume);
            if(current_spring.joint.id == middleJoint.id) {
                current_spring.joint.setVelocity(new Vector(current_spring.getNormalVector().getX() * pressureVec + current_spring.joint.getVelocity().getX(),
                                                            current_spring.getNormalVector().getY() * pressureVec  + current_spring.joint.getVelocity().getY()));
                System.out.println("applying pressure");
            }
            if(current_spring.joint1.id == middleJoint.id) {
                current_spring.joint1.setVelocity(new Vector(current_spring.getNormalVector().getX() * pressureVec + current_spring.joint1.getVelocity().getX(),
                                                            current_spring.getNormalVector().getY() * pressureVec  + current_spring.joint1.getVelocity().getY()));
                System.out.println("applying pressure");
            }
        }
    }
    public void jointAndSpringCreator(){
        int size = 10;
        int offset = 50;
        float multiplier = 0.75f;
        float stiffness = 0.1f;
        Joint j = new Joint(WIDTH/2, HEIGHT/2, 10);
        middleJoint = j;
        Joint j1 = new Joint(WIDTH/2 + offset, HEIGHT/2, 10);
        Joint j2 = new Joint(WIDTH/2 - offset, HEIGHT/2, 10);
        Joint j3 = new Joint(WIDTH/2, HEIGHT/2 + offset, 10);
        Joint j4 = new Joint(WIDTH/2, HEIGHT/2 - offset, 10);
        Joint j5 = new Joint(WIDTH/2 + (int)(offset*multiplier), HEIGHT/2 + (int)(offset*multiplier), 10);
        Joint j6 = new Joint(WIDTH/2 + (int)(offset*multiplier), HEIGHT/2 - (int)(offset*multiplier), 10);
        Joint j7 = new Joint(WIDTH/2 - (int)(offset*multiplier), HEIGHT/2 + (int)(offset*multiplier), 10);
        Joint j8 = new Joint(WIDTH/2 - (int)(offset*multiplier), HEIGHT/2 - (int)(offset*multiplier), 10);
        joints.add(j1);
        joints.add(j2);
        joints.add(j3);
        joints.add(j4);
        joints.add(j5);
        joints.add(j6);
        joints.add(j7);
        joints.add(j8);
        joints.add(j);
        Spring spring1 = new Spring(j1, j6, stiffness, true);
        Spring spring2 = new Spring(j6, j4, stiffness, true);
        Spring spring3 = new Spring(j4, j8, stiffness, true);
        Spring spring4 = new Spring(j8, j2, stiffness, true);
        Spring spring5 = new Spring(j2, j7, stiffness, true);
        Spring spring6 = new Spring(j7, j3, stiffness, true);
        Spring spring7 = new Spring(j3, j5, stiffness, true);
        Spring spring8 = new Spring(j5, j1, stiffness, true);
        Spring spring9 = new Spring(j, j1, stiffness);
        Spring spring10 = new Spring(j, j2, stiffness);
        Spring spring11 = new Spring(j, j3, stiffness);
        Spring spring12 = new Spring(j, j4, stiffness);
        Spring spring13 = new Spring(j, j5, stiffness);
        Spring spring14 = new Spring(j, j6, stiffness);
        Spring spring15 = new Spring(j, j7, stiffness);
        Spring spring16 = new Spring(j, j8, stiffness);
        springs.add(spring1);
        springs.add(spring2);
        springs.add(spring3);
        springs.add(spring4);
        springs.add(spring5);
        springs.add(spring6);
        springs.add(spring7);
        springs.add(spring8);
        springs.add(spring9);
        springs.add(spring10);
        springs.add(spring11);
        springs.add(spring12);
        springs.add(spring13);
        springs.add(spring14);
        springs.add(spring15);
        springs.add(spring16);
    }
}
