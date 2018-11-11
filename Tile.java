import java.util.HashMap;

public class Tile {

    public static void claimTile(int tile, int id, HashMap<Integer, Integer> tileToID) {
        if (tile == 0)
        throw new IllegalArgumentException("Error!");

        if (tileToID.containsKey(tile))
        throw new IllegalArgumentException("Tile is already marked");

        else
        tileToID.put(tile, id);
    }

    public static void freeTile(int tile, HashMap<Integer, Integer> tileToID) {
        if (!tileToID.containsKey(tile))
        throw new IllegalArgumentException("Tile is already free");

        else
        tileToID.remove(tile);
    }
}
