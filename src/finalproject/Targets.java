/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.util.*;
import javalib.impworld.World;
import javalib.worldimages.*;
import javalib.colors.*;
import java.lang.Math.*;


public class Targets {
    
    // Location of the target
    Posn loc;
    
    Random rand = new Random();
    
    // Indicates if the target has been hit or not
    boolean dead = false;
    
    // Constructor
    public Targets() {
        this.loc = new Posn(0,0);
    }
    
    /**
     * Sets the current location of the target.
     */
    public void setLoc() {
        // Generates a number between 200 and 750 for the x-coordinate
        int locx = rand.nextInt(551) + 200;
        // Generates a random number between 100 and 500 for the y-coordinate
        int locy = rand.nextInt(401) + 100;
        
        // Sets location of the target
        this.loc.x = locx;
        this.loc.y = locy;
    }
    
    /**
     * Draws the image of the target, before it has been shot.
     */
    public WorldImage showTarget () {
        return new FromFileImage(this.loc, "Images/penguin.png");
    }
    
    /**
     * Draws the image of the target, after it has been shot.
     */
    public WorldImage showBlood () {
        return new FromFileImage(this.loc, "Images/blood.png");
    }
    
    /**
     * Determines if a projectile is close enough to destroy the target.
     * Returns a boolean indicating this status.
     */
    public boolean destroyTarget(Posn projectile) {
        int dx = Math.abs(this.loc.x - projectile.x);
        int dy = Math.abs(this.loc.y - projectile.y);
        
        return (dx < 20 && dy < 20);
    }
    
}
