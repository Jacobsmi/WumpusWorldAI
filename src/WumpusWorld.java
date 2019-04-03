import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author stetz
 */
public class WumpusWorld {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        World w = new World(37, 4);
        w.printWorld();
        Agent AI = new Agent(w);
        /*Random rng = new Random(30);
        for( int i = 0 ; i < 100 ; i++ ){
            int x = rng.nextInt(4);
            int y = rng.nextInt(4);
            System.out.print(y + ", " + x + ": ");
            printPercept(w.perceive(x, y));
        }*/
    }
    
    private static void printPercept(boolean[] per ){
        System.out.print("[");
        for( int i = 0 ; i < per.length ; i++ )
        {
            System.out.print(per[i] + "  ");
        }
        System.out.println("]");
    }
    
}
