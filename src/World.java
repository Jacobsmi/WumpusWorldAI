import java.util.Random;

public class World {

    private Random rng;
    private boolean wumpusDead = false;
    private final int size;
    State[][] world;

    public final int NORTH = 0;
    public final int EAST = 1;
    public final int SOUTH = 2;
    public final int WEST = 3;
    private int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public World(int seed, int size) {
        this.size = size;
        world = new State[this.size][this.size];
        this.rng = new Random(seed);
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                world[i][j] = new State();
            }
        }

        // Randomly generate cave
        // Assign the wumpus
        int x = rng.nextInt(this.size);
        int y = rng.nextInt(this.size);
        world[x][y].assignWumpus();

        // Assign gold
        do {
            x = rng.nextInt(this.size);
            y = rng.nextInt(this.size);
        } while (world[x][y].hasWumpus());
        world[x][y].assignGold();

        // Create pits
        for (int i = 0; i < this.size; i++) {
            x = rng.nextInt(this.size);
            y = rng.nextInt(this.size);
            if (!world[x][y].hasWumpus() && !world[x][y].hasGold()) {
                world[x][y].assignPit();
            }
        }
    }

    /**
     * Shoots an arrow in a direction from location x, y
     *
     * @param x The X coordinate the arrow is shot from
     * @param y The Y coordinate the arrow is shot from
     * @param direction The direction (according to NORTH, EAST, WEST, or SOUTH)
     * the arrow is shot in
     */
    public void shoot(int x, int y, int direction) {
        while (x >= 0 && x < this.size && y >= 0 && y < this.size) {
            if (world[x][y].hasWumpus()) {
                wumpusDead = true;
                world[x][y].assignWumpus();
            }
            x += dirs[direction][0];
            y += dirs[direction][1];
        }
    }

    /**
     * Perceives at location x,y
     *
     * @param x X location of the agent
     * @param y Y location of the agent
     * @return A 5-tuple (including false for a wall location)
     */
    public boolean[] perceive(int x, int y) {
        boolean[] percept = {hasStench(x, y), hasBreeze(x, y), hasGold(x, y),
            false, this.wumpusDead};

        return percept;
    }

    private boolean hasStench(int x, int y) {
        if (x > 0 && world[x - 1][y].hasWumpus()) {
            return true;
        }
        if (x < this.size - 1 && world[x + 1][y].hasWumpus()) {
            return true;
        }
        if (y > 0 && world[x][y - 1].hasWumpus()) {
            return true;
        }
        if (y < this.size - 1 && world[x][y + 1].hasWumpus()) {
            return true;
        }
        return false;

    }

    private boolean hasBreeze(int x, int y) {
        if (x > 0 && world[x - 1][y].hasPit()) {
            return true;
        }
        if (x < this.size - 1 && world[x + 1][y].hasPit()) {
            return true;
        }
        if (y > 0 && world[x][y - 1].hasPit()) {
            return true;
        }
        if (y < this.size - 1 && world[x][y + 1].hasPit()) {
            return true;
        }
        return false;
    }

    private boolean hasGold(int x, int y) {
        return world[x][y].hasGold();
    }

    /**
     * Prints the rows top to bottom but the columns left to right, so 1,1 is in
     * the bottom left.
     */
    public void printWorld() {
        for (int i = this.size - 1; i >= 0; i--) {
            for (int j = 0; j < this.size; j++) {
                System.out.printf("|%5s|", world[i][j].toString());
            }
            System.out.println();
        }
    }

}
