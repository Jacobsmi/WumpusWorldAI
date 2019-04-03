import java.util.ArrayList;

public class Node {
    int posX;
    int posY;
    int eval;
    Node parent;
    ArrayList<Node> children = new ArrayList<>();
    public Node(int x, int y, Node p ){
        posX = x;
        posY = y;
        Node parent = p;
        if(parent == null)
            eval = 0;
        else
            eval = parent.eval;
    }
}
