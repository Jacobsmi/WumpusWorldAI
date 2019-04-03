import java.util.ArrayList;
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
     * For final board:
     * 8= Gold
     * 9= Pit
     * 3= Wumpus
     */
    
    public Agent(World w){
        Tree tree = new Tree();
        Node start = new Node(0,0,null);
        tree.root = start;
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
        for(int i = 0; i < hasWumpus.length; i++ ) {
            for(int j = 0; j < hasWumpus.length; j++){
                if(hasWumpus[i][j]==3){
                    fullboard[i][j] = 3;
                }
            }
        }
        for(int i = 0; i < hasPit.length; i++ ) {
            for(int j = 0; j < hasPit.length; j++){
                if(hasPit[i][j]==3){
                    fullboard[i][j] = 9;
                }
            }
        }
        for(int i = 0; i < hasGold.length; i++ ) {
            for(int j = 0; j < hasGold.length; j++){
                if(hasGold[i][j]==3){
                    fullboard[i][j] = 8;
                }
            }
        }
        System.out.println("Full Board: ");
        for (Integer[] row : fullboard){
            System.out.println(Arrays.toString(row));
        }
        ArrayList<Node> current = new ArrayList<>();
        ArrayList<Node> next = new ArrayList<>();
        current.add(tree.root);
        boolean spotHasGold = false;
        spotHasGold = pathing(fullboard, tree.root);
        while(!spotHasGold) {
            for (int i = 0; i < current.size() - 1; i++) {
                for (Node child : current.get(i).children) {
                    spotHasGold = pathing(fullboard, child);
                    next.add(child);
                }
            }
            current = next;
            next.clear();
        }

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
    public Boolean pathing(Integer[][] board, Node node){
        boolean hasGold = false;
        if(node.posX > 0){
            Node left = new Node((node.posX -1), node.posY, node);
            int here= board[node.posX-1][node.posY];
            left.eval--;
            if(here==9){
                left.eval -=1000;
            }
            if(here == 8){
                left.eval +=1000;
                hasGold = true;
            }
            if(here != 3){
                node.children.add(left);
            }
        }

        if(node.posY > 0){
            Node down = new Node(node.posX, (node.posY-1), node);
            int here= board[node.posX][node.posY-1];
            down.eval--;
            if(here==9){
                down.eval -=1000;
            }
            if(here == 8){
                down.eval +=1000;
                hasGold = true;
            }
            if(here != 3){
                node.children.add(down);
            }
        }

        if(node.posX < 3){
            Node right = new Node((node.posX+1), node.posY, node);
            int here= board[node.posX+1][node.posY];
            right.eval--;
            if(here==9){
                right.eval -=1000;
            }
            if(here == 8){
                right.eval +=1000;
                hasGold = true;
            }
            if(here != 3){
                node.children.add(right);
            }
        }

        if(node.posY < 3 ){
            Node up = new Node(node.posX, (node.posY+1), node);
            int here= board[node.posX][node.posY+1];
            up.eval--;
            if(here==9){
                up.eval -=1000;
            }
            if(here == 8){
                up.eval +=1000;
                hasGold = true;
            }
            if(here != 3){
                node.children.add(up);
            }
        }
        return hasGold;
    }

}
