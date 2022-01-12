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
                    Thread.sleep(1);
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
        int width = 20;
        int height = 20;
        float stiffnessCrossbrace = 0.4f;
        float stiffnessNet = 0.4f;
        Joint[][] jointTab = new Joint[20][20];
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++) {
                Joint joint = new Joint(j * width, i * height, size);
                jointTab[i][j] = joint;
                joints.add(joint);
                if(i==0) {
                    //joint.setStationary(true);
                }
            }
        }
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 20; j++) {
                if(i>0 && j < 19) {
                    springs.add(new Spring(jointTab[i][j], jointTab[i - 1][j + 1], stiffnessCrossbrace));
                    springs.add(new Spring(jointTab[i - 1][j], jointTab[i][j + 1], stiffnessCrossbrace));
                }

                if(i < 19){
                    springs.add(new Spring(jointTab[i][j], jointTab[i + 1][j], stiffnessNet));
                }
                if(j < 19){
                    springs.add(new Spring(jointTab[i][j], jointTab[i][j + 1], stiffnessNet));
                }
            }
        }
    }
}
