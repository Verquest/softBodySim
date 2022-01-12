public class DragAndDropHandler implements Runnable{
    private Joint joint;
    private boolean run = true;
    private Window invoker;
    public DragAndDropHandler(Joint joint, Window invoker){
        this.joint = joint;
        this.invoker = invoker;
    }
    public void setRun(boolean b){
        run = b;
    }
    @Override
    public void run() {
        while (run){
            joint.setActualX((float)invoker.getMousePosition().getX() - joint.getDrawSize());
            joint.setActualY((float)invoker.getMousePosition().getY() - joint.getDrawSize()/2 - 30);
            invoker.repaint();
        }
        joint.resetAcceleration();
    }
}
