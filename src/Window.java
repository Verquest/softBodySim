import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Window extends JFrame {
    private final int HEIGHT = 800;
    private final int WIDTH = 800;
    private final int particleSize = 5;
    private ArrayList<Spring> springs = new ArrayList<>();
    private ArrayList<Joint> joints = new ArrayList<>();
    private Canvas canvas;
    private Joint heldJoint;
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
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(Spring s: springs)
                    s.update();
                for (Joint joint : joints) {
                    if (joint != heldJoint) {
                        joint.update();
                    }
                }
                this.repaint();
            }
        });
        t.start();
        this.addMouseListener(new BallDragger(joints, this));
    }

    public void setStopper(Joint held) {
        heldJoint = held;
    }
    public void jointAndSpringCreator(){
        int size = 10;
        int width = 30;
        int height = 30;
        Joint j = new Joint(100, 100, 20);
        Joint j1 = new Joint(300, 100, 20);
        Joint j2 = new Joint(500, 100, 20);
        Joint j3 = new Joint(100, 300, 20);
        Joint j4 = new Joint(300, 300, 20);
        Joint j5 = new Joint(500, 300, 20);
        Joint j6 = new Joint(100, 500, 20);
        Joint j7 = new Joint(300, 500, 20);
        Joint j8 = new Joint(500, 500, 20);
        Spring spring = new Spring(j, j1, 0.3f);
        Spring spring1 = new Spring(j1, j2,  0.3f);
        Spring spring2 = new Spring(j2, j5,  0.3f);
        Spring spring3 = new Spring(j5, j8,  0.3f);
        Spring spring4 = new Spring(j8, j7,  0.3f);
        Spring spring5 = new Spring(j7, j6,  0.3f);
        Spring spring6 = new Spring(j6, j3,  0.3f);
        Spring spring7 = new Spring(j3, j,  0.3f);
        Spring spring8 = new Spring(j1, j4,  0.5f);
        Spring spring9 = new Spring(j4, j7,  0.5f);
        Spring spring10 = new Spring(j4, j5,  0.5f);
        Spring spring11 = new Spring(j4, j3,  0.5f);
        Spring spring12 = new Spring(j, j4,  0.3f);
        Spring spring13 = new Spring(j4, j2,  0.3f);
        Spring spring14 = new Spring(j4, j8,  0.3f);
        Spring spring15 = new Spring(j4, j6,  0.3f);
        Spring spring16 = new Spring(j1, j3,  0.3f);
        Spring spring17 = new Spring(j1, j5,  0.3f);
        Spring spring18 = new Spring(j5, j7,  0.3f);
        Spring spring19 = new Spring(j3, j7,  0.3f);
        springs.add(spring);
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
        springs.add(spring17);
        springs.add(spring18);
        springs.add(spring19);

        joints.add(j);
        joints.add(j1);
        joints.add(j2);
        joints.add(j3);
        joints.add(j4);
        joints.add(j5);
        joints.add(j6);
        joints.add(j7);
        joints.add(j8);
    }
}
