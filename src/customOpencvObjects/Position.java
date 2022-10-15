package customOpencvObjects;

public class Position {
    int x,y,z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getDistanceTo(Position obj) {
        double distance = Math.sqrt(Math.pow((x- obj.x),2) + Math.pow((y- obj.y),2) + Math.pow((z- obj.z),2));

        return distance;

    }
}
