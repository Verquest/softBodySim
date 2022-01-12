import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class BallDragger implements MouseListener {
    private Joint chosenJoint;
    private ArrayList<Joint> joints;
    private Window invoker;
    private boolean stopper = false;
    private DragAndDropHandler dragHandler;
    public BallDragger(ArrayList<Joint> joints, Window invoker){
        this.joints = joints;
        this.invoker = invoker;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        while (stopper){
            chosenJoint.setActualX(e.getX());
            chosenJoint.setActualY(e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(Joint joint: joints) {
            if (Math.hypot(e.getX() - joint.getActualX() - joint.getDrawSize() / 2, e.getY() - joint.getActualY() - joint.getDrawSize()) < joint.getDrawSize()) {
                stopper = true;
                invoker.setStopper(joint);
                dragHandler = new DragAndDropHandler(joint, invoker);
                new Thread(dragHandler).start();
                System.out.println("mouse over the thing.");
                chosenJoint = joint;
            }
        }
        System.out.println(e.getX() + "  " + e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(stopper) {
            stopper = false;
            dragHandler.setRun(false);
            invoker.setStopper(null);
        }
        System.out.println("Mouse released");
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
