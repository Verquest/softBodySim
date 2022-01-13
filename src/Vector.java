public class Vector {
    private float x, y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float mag(){
        return (float)Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
    }
    public void normalize(){
        float divider = this.mag();
        this.div(divider);
    }

    public Vector add(Vector vec){
        return new Vector( x+vec.getX(), y+vec.getY());
    }
    public Vector sub(Vector vec){
        return new Vector(x-vec.getX(), y-vec.getY());
    }

    public static Vector sub(Vector vec, Vector vec1){
        return new Vector(vec.getX()-vec1.getX(), vec.getY()-vec1.getY());
    }

    public Vector mult(Vector vec){
        return new Vector(x*vec.getX(), y*vec.getY());
    }
    public Vector mult(float scalar){
        return new Vector(x*scalar, y*scalar);
    }
    public void div(Vector vec){
        x /= vec.getX();
        y /= vec.getY();
    }
    public void div(float scalar){
        x /= scalar;
        y /= scalar;
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vector x: " + getX() + " y: " + getY();
    }
}
