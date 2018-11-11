import java.util.HashMap;

public class Tile {

private static HashMap<Integer, Integer> tileToID = new HashMap<Integer, Integer>();

    public static void claimTile(int tile, int id) {
        if (tile == 0)
        throw new IllegalArgumentException("Error!");

        if (tileToID.containsKey(tile))
        throw new IllegalArgumentException("Tile is already marked");

        else
        tileToID.put(tile, id);
    }

    public static void freeTile(int tile) {
        if (!tileToID.containsKey(tile))
        throw new IllegalArgumentException("Tile is already free");

        else
        tileToID.remove(tile);
    }
}
