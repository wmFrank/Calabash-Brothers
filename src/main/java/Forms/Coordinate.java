package Forms;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate() {
        x = -1;
        y = -1;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void add(Coordinate a, Coordinate b){
        x = a.getX() + b.getX();
        y = a.getY() + b.getY();
    }
}