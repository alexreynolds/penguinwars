
package finalproject;

import java.util.*;
import javalib.impworld.World;
import javalib.worldimages.*;
import javalib.colors.*;


public class Shooter {
    
    // Position of the shooter
    Posn pos = new Posn (75, 540);
    
    // Constructor for the shooter class
    public Shooter () {
        this.pos = pos;
    }
    
    /**
     * Displays the image for the shooter.
     */
    public WorldImage ShowShooter() {
        return new FromFileImage(pos, "Images/evilpenguin.png");
    }
    
}
