/**
 *
 * @author stetz
 */
public class State {
    private boolean hasPit = false;
    private boolean hasWumpus = false;
    private boolean hasGold = false;
    
    public void assignWumpus(){
        hasWumpus = !hasWumpus;
    }

    public boolean hasWumpus() {
        return hasWumpus;
    }

    public void assignPit() {
        hasPit = true;
    }
    
    public boolean hasPit() {
        return hasPit;
    }

    public boolean hasGold() {
        return hasGold;
    }
    
    public void assignGold() {
        hasGold = true;
    }
    
    public String toString() {
        String s = "";
        if( this.hasPit ) 
            s += "P";
        if( this.hasWumpus )
            s += "W";
        if( this.hasGold )
            s += "G";
        return s;
    }
    
    
}
