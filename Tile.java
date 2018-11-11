import java.util.HashMap;

public class Tile {

    private static HashMap<Integer, Point> tileToCoords = new HashMap<Integer, Point>();

    private static class Point {

        private double x;
        private double y;

        public Point(double xCoor, double yCoor) {
            this.x = xCoor;
            this.y = yCoor;
        }

        public double getX(Point p) {
            return p.x;
        }

        public double getY(Point p) {
            return p.y;
        }
    }

    public static void claimTile(int tile, double xCoor, double yCoor) {
        if (tile == 0)
        throw new IllegalArgumentException("Error!");

        if (tileToCoords.containsKey(tile))
        throw new IllegalArgumentException("Tile is already marked");

        else {
            Point p = new Point(xCoor, yCoor);
            tileToCoords.put(tile, p);
        }
    }

    public static void freeTile(int tile) {
        if (tileToCoords.containsKey(tile))
        throw new IllegalArgumentException("Tile is already free");

        else
        tileToCoords.remove(tile);
    }
}
