import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Window extends JFrame {
    private final int HEIGHT = 800;
    private final int WIDTH = 800;
    private final int PARTICLE_SIZE = 10;
    private final int MAX_PRESSURE = 800;
    private final int MAX_MESH_DIST = 50;
    private final float STIFFNESS = 0.2f;
    private float pressure = 0, volume;
    private ArrayList<Spring> springs = new ArrayList<>();
    private ArrayList<Joint> joints = new ArrayList<>();
    private Canvas canvas;
    private Joint heldJoint;
    public boolean stopper;

    public Window() {
        circleGen(0.5f);
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
                    pressure += MAX_PRESSURE / 30;
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
            current_spring.joint.setVelocity(
                    new Vector(current_spring.getNormalVector().getX() * pressureVec + current_spring.joint.getVelocity().getX(),
                            current_spring.getNormalVector().getY() * pressureVec  + current_spring.joint.getVelocity().getY()));
            current_spring.joint1.setVelocity(
                    new Vector(current_spring.getNormalVector().getX() * pressureVec + current_spring.joint1.getVelocity().getX(),
                            current_spring.getNormalVector().getY() * pressureVec  + current_spring.joint1.getVelocity().getY()));
        }
    }
    private void meshify(ArrayList<Joint> outsideJoints){
        HashMap<Joint, Joint> connectedPairs = new HashMap<>();

        for(Joint joint: outsideJoints){

                for (Joint checked : joints) {

                    if (connectedPairs.containsKey(joint))
                        if (connectedPairs.get(joint) == checked)
                            continue;

                    if (connectedPairs.containsKey(checked))
                        if (connectedPairs.get(checked) == joint)
                            continue;
                    if(joint == checked)
                        continue;
                    if (Math.sqrt(Math.pow(joint.getActualX() - checked.getActualX(), 2) +
                                  Math.pow(joint.getActualY() - checked.getActualY(), 2)) < MAX_MESH_DIST) {
                        springs.add(new Spring(joint, checked, STIFFNESS));
                        connectedPairs.put(joint, checked);
                    }
                }
            
        }
    }
    public void circleGen(float scale){
        ArrayList<Joint> insideJoints = new ArrayList<>();
        //obramowka
        //gorny
        Joint j1 = new Joint(300*scale, 100*scale, PARTICLE_SIZE);
        Joint j2 = new Joint(400*scale, 140*scale, PARTICLE_SIZE);
        Joint j3 = new Joint(460*scale, 200*scale, PARTICLE_SIZE);
        //prawy
        Joint j4 = new Joint(500*scale, 300*scale, PARTICLE_SIZE);
        Joint j5 = new Joint(460*scale, 400*scale, PARTICLE_SIZE);
        Joint j6 = new Joint(400*scale, 460*scale, PARTICLE_SIZE);
        //dolny
        Joint j7 = new Joint(300*scale, 500*scale, PARTICLE_SIZE);
        Joint j8 = new Joint(200*scale, 460*scale, PARTICLE_SIZE);
        Joint j9 = new Joint(140*scale, 400*scale, PARTICLE_SIZE);
        //lewy
        Joint j10 = new Joint(100*scale, 300*scale, PARTICLE_SIZE);
        Joint j11 = new Joint(140*scale, 200*scale, PARTICLE_SIZE);
        Joint j12 = new Joint(200*scale, 140*scale, PARTICLE_SIZE);
        //filling
        //top
        Joint j13 = new Joint(260*scale, 180*scale, PARTICLE_SIZE);
        Joint j14 = new Joint(340*scale, 180*scale, PARTICLE_SIZE);
        //left
        Joint j15 = new Joint(180*scale, 260*scale, PARTICLE_SIZE);
        Joint j16 = new Joint(180*scale, 340*scale, PARTICLE_SIZE);
        //bottom
        Joint j17 = new Joint(260*scale, 420*scale, PARTICLE_SIZE);
        Joint j18 = new Joint(340*scale, 420*scale, PARTICLE_SIZE);
        //right
        Joint j19 = new Joint(420*scale, 260*scale, PARTICLE_SIZE);
        Joint j20 = new Joint(420*scale, 340*scale, PARTICLE_SIZE);
        //diagonals
        //left bottom
        Joint j21 = new Joint(200*scale, 400*scale, PARTICLE_SIZE);
        //left top
        Joint j22 = new Joint(200*scale, 200*scale, PARTICLE_SIZE);
        //right top
        Joint j23 = new Joint(400*scale, 200*scale, PARTICLE_SIZE);
        //right bottom
        Joint j24 = new Joint(400*scale, 400*scale, PARTICLE_SIZE);

        Joint j25 = new Joint(250*scale, 350*scale, PARTICLE_SIZE);
        Joint j26 = new Joint(250*scale, 250*scale, PARTICLE_SIZE);
        Joint j27 = new Joint(350*scale, 250*scale, PARTICLE_SIZE);
        Joint j28 = new Joint(350*scale, 350*scale, PARTICLE_SIZE);

        Joint j29 = new Joint(300*scale, 220*scale, PARTICLE_SIZE);
        Joint j30 = new Joint(300*scale, 380*scale, PARTICLE_SIZE);
        Joint j31 = new Joint(220*scale, 300*scale, PARTICLE_SIZE);
        Joint j32 = new Joint(380*scale, 300*scale, PARTICLE_SIZE);
        //mid
        Joint j33 = new Joint(300*scale, 270*scale, PARTICLE_SIZE);
        Joint j34 = new Joint(300*scale, 330*scale, PARTICLE_SIZE);
        Joint j35 = new Joint(270*scale, 300*scale, PARTICLE_SIZE);
        Joint j36 = new Joint(330*scale, 300*scale, PARTICLE_SIZE);
        

        joints.add(j1);
        joints.add(j2);
        joints.add(j3);
        joints.add(j4);
        joints.add(j5);
        joints.add(j6);
        joints.add(j7);
        joints.add(j8);
        joints.add(j9);
        joints.add(j10);
        joints.add(j11);
        joints.add(j12);
        joints.add(j13);
        joints.add(j14);
        joints.add(j15);
        joints.add(j16);
        joints.add(j17);
        joints.add(j18);
        joints.add(j19);
        joints.add(j20);
        joints.add(j21);
        joints.add(j22);
        joints.add(j23);
        joints.add(j24);
        joints.add(j25);
        joints.add(j26);
        joints.add(j27);
        joints.add(j28);
        joints.add(j29);
        joints.add(j30);
        joints.add(j31);
        joints.add(j32);
        joints.add(j33);
        joints.add(j34);
        joints.add(j35);
        joints.add(j36);
        //-------------------
        insideJoints.add(j13);
        insideJoints.add(j14);
        insideJoints.add(j15);
        insideJoints.add(j16);
        insideJoints.add(j17);
        insideJoints.add(j18);
        insideJoints.add(j19);
        insideJoints.add(j20);
        insideJoints.add(j21);
        insideJoints.add(j22);
        insideJoints.add(j23);
        insideJoints.add(j24);
        insideJoints.add(j25);
        insideJoints.add(j26);
        insideJoints.add(j27);
        insideJoints.add(j28);
        insideJoints.add(j29);
        insideJoints.add(j30);
        insideJoints.add(j31);
        insideJoints.add(j32);
        insideJoints.add(j33);
        insideJoints.add(j34);
        insideJoints.add(j35);
        insideJoints.add(j36);
        springs.add(new Spring(j2, j1, STIFFNESS, true));
        springs.add(new Spring(j3, j2, STIFFNESS, true));
        springs.add(new Spring(j4, j3, STIFFNESS, true));
        springs.add(new Spring(j5, j4, STIFFNESS, true));
        springs.add(new Spring(j6, j5, STIFFNESS, true));
        springs.add(new Spring(j7, j6, STIFFNESS, true));
        springs.add(new Spring(j8, j7, STIFFNESS, true));
        springs.add(new Spring(j9, j8, STIFFNESS, true));
        springs.add(new Spring(j10, j9, STIFFNESS, true));
        springs.add(new Spring(j11, j10, STIFFNESS, true));
        springs.add(new Spring(j12, j11, STIFFNESS, true));
        springs.add(new Spring(j1, j12, STIFFNESS, true));
        meshify(insideJoints);

    }

    public void dickGen(){
        ArrayList<Joint> insideJoints = new ArrayList<>();
        Joint j = new Joint(50, 100, PARTICLE_SIZE);
        Joint j1 = new Joint(80, 100, PARTICLE_SIZE);
        Joint j2 = new Joint(110, 100, PARTICLE_SIZE);
        Joint j3 = new Joint(140, 100, PARTICLE_SIZE);
        Joint j4 = new Joint(170, 100, PARTICLE_SIZE);
        Joint j5 = new Joint(200, 100, PARTICLE_SIZE);
        Joint j6 = new Joint(230, 100, PARTICLE_SIZE);
        Joint j7 = new Joint(260, 100, PARTICLE_SIZE);
        Joint j8 = new Joint(290, 100, PARTICLE_SIZE);
        Joint j9 = new Joint(320, 100, PARTICLE_SIZE);
        Joint j10 = new Joint(350, 100, PARTICLE_SIZE);
        //glowa
        Joint j11 = new Joint(350, 70, PARTICLE_SIZE);
        Joint j12 = new Joint(380, 70, PARTICLE_SIZE);
        Joint j13 = new Joint(410, 75, PARTICLE_SIZE);
        Joint j14 = new Joint(440, 85, PARTICLE_SIZE);
        Joint j15 = new Joint(460, 100, PARTICLE_SIZE);
        Joint j16 = new Joint(470, 120, PARTICLE_SIZE);
        Joint j17 = new Joint(470, 150, PARTICLE_SIZE);
        Joint j18 = new Joint(460, 170, PARTICLE_SIZE);
        Joint j19 = new Joint(440, 180, PARTICLE_SIZE);
        Joint j20 = new Joint(410, 180, PARTICLE_SIZE);
        Joint j21 = new Joint(380, 180, PARTICLE_SIZE);
        Joint j22 = new Joint(360, 170, PARTICLE_SIZE);
        Joint j23 = new Joint(330, 170, PARTICLE_SIZE);
        Joint j24 = new Joint(300, 170, PARTICLE_SIZE);
        Joint j25 = new Joint(270, 170, PARTICLE_SIZE);
        Joint j26 = new Joint(240, 170, PARTICLE_SIZE);
        Joint j27 = new Joint(210, 170, PARTICLE_SIZE);
        Joint j28 = new Joint(180, 170, PARTICLE_SIZE);
        Joint j29 = new Joint(150, 170, PARTICLE_SIZE);
        //bolls
        Joint j30 = new Joint(50, 130, PARTICLE_SIZE);
        Joint j31 = new Joint(50, 160, PARTICLE_SIZE);
        Joint j32 = new Joint(50, 190, PARTICLE_SIZE);
        Joint j33 = new Joint(50, 220, PARTICLE_SIZE);
        Joint j34 = new Joint(60, 240, PARTICLE_SIZE);
        Joint j35 = new Joint(75, 260, PARTICLE_SIZE);
        Joint j36 = new Joint(90, 270, PARTICLE_SIZE);
        Joint j37 = new Joint(110, 270, PARTICLE_SIZE);
        Joint j38 = new Joint(130, 260, PARTICLE_SIZE);
        Joint j39 = new Joint(145, 240, PARTICLE_SIZE);
        Joint j40 = new Joint(150, 210, PARTICLE_SIZE);
        Joint j41 = new Joint(150, 180, PARTICLE_SIZE);
        //srodek
        Joint j42 = new Joint(70, 120, PARTICLE_SIZE);
        Joint j43 = new Joint(100, 120, PARTICLE_SIZE);
        Joint j44 = new Joint(130, 120, PARTICLE_SIZE);
        Joint j45 = new Joint(160, 120, PARTICLE_SIZE);
        Joint j46 = new Joint(190, 120, PARTICLE_SIZE);
        Joint j47 = new Joint(220, 120, PARTICLE_SIZE);
        Joint j48 = new Joint(250, 120, PARTICLE_SIZE);
        Joint j49 = new Joint(280, 120, PARTICLE_SIZE);
        Joint j50 = new Joint(310, 120, PARTICLE_SIZE);
        Joint j51 = new Joint(340, 120, PARTICLE_SIZE);
        Joint j52 = new Joint(370, 120, PARTICLE_SIZE);
        Joint j53 = new Joint(400, 120, PARTICLE_SIZE);
        Joint j54 = new Joint(430, 120, PARTICLE_SIZE);
        Joint j55 = new Joint(450, 120, PARTICLE_SIZE);
        //2 row
        Joint j56 = new Joint(90, 150, PARTICLE_SIZE);
        Joint j57 = new Joint(120, 150, PARTICLE_SIZE);
        Joint j58 = new Joint(150, 150, PARTICLE_SIZE);
        Joint j59 = new Joint(180, 150, PARTICLE_SIZE);
        Joint j60 = new Joint(210, 150, PARTICLE_SIZE);
        Joint j61 = new Joint(240, 150, PARTICLE_SIZE);
        Joint j62 = new Joint(270, 150, PARTICLE_SIZE);
        Joint j63 = new Joint(300, 150, PARTICLE_SIZE);
        Joint j64 = new Joint(330, 150, PARTICLE_SIZE);
        Joint j65 = new Joint(360, 150, PARTICLE_SIZE);
        Joint j66 = new Joint(390, 150, PARTICLE_SIZE);
        Joint j67 = new Joint(420, 150, PARTICLE_SIZE);
        Joint j68 = new Joint(450, 150, PARTICLE_SIZE);
        Joint j69 = new Joint(60, 150, PARTICLE_SIZE);
        //jajco fill
        Joint j70 = new Joint(70, 180, PARTICLE_SIZE);
        Joint j71 = new Joint(100, 180, PARTICLE_SIZE);
        Joint j72 = new Joint(130, 180, PARTICLE_SIZE);
        Joint j73 = new Joint(90, 210, PARTICLE_SIZE);
        Joint j74 = new Joint(120, 210, PARTICLE_SIZE);
        Joint j75 = new Joint(80, 240, PARTICLE_SIZE);
        Joint j76 = new Joint(110, 240, PARTICLE_SIZE);
        //glowa gora fill
        Joint j77 = new Joint(380, 100, PARTICLE_SIZE);
        Joint j78 = new Joint(410, 100, PARTICLE_SIZE);

        joints.add(j);
        joints.add(j1);
        joints.add(j2);
        joints.add(j3);
        joints.add(j4);
        joints.add(j5);
        joints.add(j6);
        joints.add(j7);
        joints.add(j8);
        joints.add(j9);
        joints.add(j10);
        joints.add(j11);
        joints.add(j12);
        joints.add(j13);
        joints.add(j14);
        joints.add(j15);
        joints.add(j16);
        joints.add(j17);
        joints.add(j18);
        joints.add(j19);
        joints.add(j20);
        joints.add(j21);
        joints.add(j22);
        joints.add(j23);
        joints.add(j24);
        joints.add(j25);
        joints.add(j26);
        joints.add(j27);
        joints.add(j28);
        joints.add(j29);
        joints.add(j30);
        joints.add(j31);
        joints.add(j32);
        joints.add(j33);
        joints.add(j34);
        joints.add(j35);
        joints.add(j36);
        joints.add(j37);
        joints.add(j38);
        joints.add(j39);
        joints.add(j40);
        joints.add(j41);
        joints.add(j42);
        joints.add(j43);
        joints.add(j44);
        joints.add(j45);
        joints.add(j46);
        joints.add(j47);
        joints.add(j48);
        joints.add(j49);
        joints.add(j50);
        joints.add(j51);
        joints.add(j52);
        joints.add(j53);
        joints.add(j54);
        joints.add(j55);
        joints.add(j56);
        joints.add(j57);
        joints.add(j58);
        joints.add(j59);
        joints.add(j60);
        joints.add(j61);
        joints.add(j62);
        joints.add(j63);
        joints.add(j64);
        joints.add(j65);
        joints.add(j66);
        joints.add(j67);
        joints.add(j68);
        joints.add(j69);
        joints.add(j70);
        joints.add(j71);
        joints.add(j72);
        joints.add(j73);
        joints.add(j74);
        joints.add(j75);
        joints.add(j76);
        joints.add(j77);
        joints.add(j78);
        insideJoints.add(j42);
        insideJoints.add(j43);
        insideJoints.add(j44);
        insideJoints.add(j45);
        insideJoints.add(j46);
        insideJoints.add(j47);
        insideJoints.add(j48);
        insideJoints.add(j49);
        insideJoints.add(j50);
        insideJoints.add(j51);
        insideJoints.add(j52);
        insideJoints.add(j53);
        insideJoints.add(j54);
        insideJoints.add(j55);
        insideJoints.add(j56);
        insideJoints.add(j57);
        insideJoints.add(j58);
        insideJoints.add(j59);
        insideJoints.add(j60);
        insideJoints.add(j61);
        insideJoints.add(j62);
        insideJoints.add(j63);
        insideJoints.add(j64);
        insideJoints.add(j65);
        insideJoints.add(j66);
        insideJoints.add(j67);
        insideJoints.add(j68);
        insideJoints.add(j69);
        insideJoints.add(j70);
        insideJoints.add(j71);
        insideJoints.add(j72);
        insideJoints.add(j73);
        insideJoints.add(j74);
        insideJoints.add(j75);
        insideJoints.add(j76);
        insideJoints.add(j77);
        insideJoints.add(j78);
        meshify(insideJoints);
        Spring s1 = new Spring(j1, j, STIFFNESS, true);
        Spring s2 = new Spring(j2, j1, STIFFNESS, true);
        Spring s3 = new Spring(j3, j2, STIFFNESS, true);
        Spring s4 = new Spring(j4, j3, STIFFNESS, true);
        Spring s5 = new Spring(j5, j4, STIFFNESS, true);
        Spring s6 = new Spring(j6, j5, STIFFNESS, true);
        Spring s7 = new Spring(j7, j6, STIFFNESS, true);
        Spring s8 = new Spring(j8, j7, STIFFNESS, true);
        Spring s9 = new Spring(j9, j8, STIFFNESS, true);
        Spring s10 = new Spring(j10, j9, STIFFNESS, true);
        Spring s11 = new Spring(j11, j10, STIFFNESS, true);
        Spring s12 = new Spring(j12, j11, STIFFNESS, true);
        Spring s13 = new Spring(j13, j12, STIFFNESS, true);
        Spring s14 = new Spring(j14, j13, STIFFNESS, true);
        Spring s15 = new Spring(j15, j14, STIFFNESS, true);
        Spring s16 = new Spring(j16, j15, STIFFNESS, true);
        Spring s17 = new Spring(j17, j16, STIFFNESS, true);
        Spring s18 = new Spring(j18, j17, STIFFNESS, true);
        Spring s19 = new Spring(j19, j18, STIFFNESS, true);
        Spring s20 = new Spring(j20, j19, STIFFNESS, true);
        Spring s21 = new Spring(j21, j20, STIFFNESS, true);
        Spring s22 = new Spring(j22, j21, STIFFNESS, true);
        Spring s23 = new Spring(j23, j22, STIFFNESS, true);
        Spring s24 = new Spring(j24, j23, STIFFNESS, true);
        Spring s25 = new Spring(j25, j24, STIFFNESS, true);
        Spring s26 = new Spring(j26, j25, STIFFNESS, true);
        Spring s27 = new Spring(j27, j26, STIFFNESS, true);
        Spring s28 = new Spring(j28, j27, STIFFNESS, true);
        Spring s29 = new Spring(j29, j28, STIFFNESS, true);
        Spring s30 = new Spring(j, j30, STIFFNESS, true);
        Spring s31 = new Spring(j30, j31, STIFFNESS, true);
        Spring s32 = new Spring(j31, j32, STIFFNESS, true);
        Spring s33 = new Spring(j32, j33, STIFFNESS, true);
        Spring s34 = new Spring(j33, j34, STIFFNESS, true);
        Spring s35 = new Spring(j34, j35, STIFFNESS, true);
        Spring s36 = new Spring(j35, j36, STIFFNESS, true);
        Spring s37 = new Spring(j36, j37, STIFFNESS, true);
        Spring s38 = new Spring(j37, j38, STIFFNESS, true);
        Spring s39 = new Spring(j38, j39, STIFFNESS, true);
        Spring s40 = new Spring(j39, j40, STIFFNESS, true);
        Spring s41 = new Spring(j40, j41, STIFFNESS, true);
        Spring s42 = new Spring(j41, j29, STIFFNESS, true);

        springs.add(s1);
        springs.add(s2);
        springs.add(s3);
        springs.add(s4);
        springs.add(s5);
        springs.add(s6);
        springs.add(s7);
        springs.add(s8);
        springs.add(s9);
        springs.add(s10);
        springs.add(s11);
        springs.add(s12);
        springs.add(s13);
        springs.add(s14);
        springs.add(s15);
        springs.add(s16);
        springs.add(s17);
        springs.add(s18);
        springs.add(s19);
        springs.add(s20);
        springs.add(s21);
        springs.add(s22);
        springs.add(s23);
        springs.add(s24);
        springs.add(s25);
        springs.add(s26);
        springs.add(s27);
        springs.add(s28);
        springs.add(s29);
        springs.add(s30);
        springs.add(s31);
        springs.add(s32);
        springs.add(s33);
        springs.add(s34);
        springs.add(s35);
        springs.add(s36);
        springs.add(s37);
        springs.add(s38);
        springs.add(s39);
        springs.add(s40);
        springs.add(s41);
        springs.add(s42);
    }
    public void jointAndSpringCreator(){
        int offset = 50;
        float multiplier = 0.75f;
        Joint j = new Joint(WIDTH/2, HEIGHT/2, PARTICLE_SIZE);
        Joint j1 = new Joint(WIDTH/2 + offset, HEIGHT/2, PARTICLE_SIZE);
        Joint j2 = new Joint(WIDTH/2 - offset, HEIGHT/2, PARTICLE_SIZE);
        Joint j3 = new Joint(WIDTH/2, HEIGHT/2 + offset, PARTICLE_SIZE);
        Joint j4 = new Joint(WIDTH/2, HEIGHT/2 - offset, PARTICLE_SIZE);
        Joint j5 = new Joint(WIDTH/2 + (int)(offset*multiplier), HEIGHT/2 + (int)(offset*multiplier), PARTICLE_SIZE);
        Joint j6 = new Joint(WIDTH/2 + (int)(offset*multiplier), HEIGHT/2 - (int)(offset*multiplier), PARTICLE_SIZE);
        Joint j7 = new Joint(WIDTH/2 - (int)(offset*multiplier), HEIGHT/2 + (int)(offset*multiplier), PARTICLE_SIZE);
        Joint j8 = new Joint(WIDTH/2 - (int)(offset*multiplier), HEIGHT/2 - (int)(offset*multiplier), PARTICLE_SIZE);
        joints.add(j1);
        joints.add(j2);
        joints.add(j3);
        joints.add(j4);
        joints.add(j5);
        joints.add(j6);
        joints.add(j7);
        joints.add(j8);
        joints.add(j);
        Spring spring1 = new Spring(j1, j6, STIFFNESS, true);
        Spring spring2 = new Spring(j6, j4, STIFFNESS, true);
        Spring spring3 = new Spring(j4, j8, STIFFNESS, true);
        Spring spring4 = new Spring(j8, j2, STIFFNESS, true);
        Spring spring5 = new Spring(j2, j7, STIFFNESS, true);
        Spring spring6 = new Spring(j7, j3, STIFFNESS, true);
        Spring spring7 = new Spring(j3, j5, STIFFNESS, true);
        Spring spring8 = new Spring(j5, j1, STIFFNESS, true);
        Spring spring9 = new Spring(j, j1, STIFFNESS);
        Spring spring10 = new Spring(j, j2, STIFFNESS);
        Spring spring11 = new Spring(j, j3, STIFFNESS);
        Spring spring12 = new Spring(j, j4, STIFFNESS);
        Spring spring13 = new Spring(j, j5, STIFFNESS);
        Spring spring14 = new Spring(j, j6, STIFFNESS);
        Spring spring15 = new Spring(j, j7, STIFFNESS);
        Spring spring16 = new Spring(j, j8, STIFFNESS);
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
