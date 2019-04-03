import java.util.Arrays;

public class Agent {
    World world;
    Integer[][] hasWumpus = new Integer[4][4];
    Integer[][] hasGold = new Integer[4][4];
    Integer[][] hasPit = new Integer[4][4];
    Integer[][] fullboard = new Integer[4][4];
    
    /**
     * 0 = No
     * 1 = Not visited
     * 2 = Maybe
     * 3 = Yes
     */
    
    public Agent(World w){
        world = w;
        for(Integer[] row : hasWumpus){
            Arrays.fill(row, 1);
        }
        for(Integer[] row : hasPit){
            Arrays.fill(row, 1);
        }
        for(Integer[] row : hasGold){
            Arrays.fill(row, 0);
        }
        for(Integer[] row : fullboard){
            Arrays.fill(row, 0);
        }
        for( int r = 0; r < 4; r++ ) {
            for ( int c = 0; c < 4; c++ ) {
                getPerceptions(r,c);
            }
        }
        for(int i = 0; i < hasWumpus.length-1; i++ ) {
            for(int j = 0; i < hasWumpus.length-1; j++){
                if(hasWumpus[i][j]==3){
                    fullboard[i][j] = 3;
                }
            }
        }
        for(int i = 0; i < hasPit.length; i++ ) {
            for(int j = 0; i < hasPit.length-1; j++){
                if(hasPit[i][j]==3){
                    fullboard[i][j] = 9;
                }
            }
        }
        for(int i = 0; i < hasGold.length; i++ ) {
            for(int j = 0; i < hasGold.length; j++){
                if(hasGold[i][j]==3){
                    fullboard[i][j] = 8;
                }
            }
        }
        System.out.println("Full Board: ");
        for (Integer[] row : fullboard){
            System.out.println(Arrays.toString(row));
        }
        //getPerceptions(0, 0);
        //System.out.println("Statuses of " + hasPit[0][1] + " " + hasPit[1][0]);
    }
    public void getPerceptions(int x, int y){
        boolean[] perceptions = world.perceive(x,y);
        //System.out.println("Perceptions: " + Arrays.toString(perceptions));
        perceptionHandler(perceptions[0], hasWumpus, x, y);
        perceptionHandler(perceptions[1], hasPit, x, y);
        if( perceptions[2] ) {
            hasGold[x][y] = 3;
        }
    }
    public void perceptionHandler(boolean bool, Integer[][] percept, int x, int y){
        if(bool == false){
            if(x > 0)
                percept[x-1][y] = 0;
            if(y > 0)
                percept[x][y-1] = 0;
            if(x < 3)
                percept[x+1][y] = 0;
            if(y < 3 ){
                percept[x][y+1] = 0;
            }
        }
        if(bool == true){
            if(x > 0)
                if(percept[x-1][y]== 1)
                    percept[x-1][y] = 2;
                else if(percept[x-1][y] == 2)
                    percept[x-1][y] = 3;
            if(y > 0)
                if(percept[x][y-1]== 1)
                    percept[x][y-1] = 2;
                else if(percept[x][y-1] == 2)
                    percept[x][y-1] = 3;
            if(x < 3)
                if(percept[x+1][y]== 1)
                    percept[x+1][y] = 2;
                else if(percept[x+1][y] == 2)
                    percept[x+1][y] = 3;
            if(y < 3 ){
                if(percept[x][y+1]== 1)
                    percept[x][y+1] = 2;
                else if(percept[x][y+1] == 2)
                    percept[x][y+1] = 3;
            }
        }
    }

}
