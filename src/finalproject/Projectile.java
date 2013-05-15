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

public class Projectile {
    
    // Current location of the projectile
    Posn currloc;
    
    // The starting location of the projectile
    double xval = 90.0;
    double yval = 540.0;
    
    // The X and Y velocities
    double xvel;
    double yvel;
    
    // Counts how many times it has bounced off walls
    int bouncecount = 0;
    
    // Constructor for the class
    public Projectile (double dy, double dx) {
        // Determines the X and Y velocities
        this.xvel = dx / ((dx * dx) + (dy * dy)) * 700;
        this.yvel = -1*dy / ((dx * dx) + (dy * dy)) * 700;
       
        this.bouncecount = bouncecount;
        
        // The initial position of the projectile
        this.currloc = new Posn(90,540);
    }
    
    /**
     * Moves the projectile by updating its current location.
     * Movement is based on its current location and the X and Y velocities.
     */
    public void moveProj () {
        xval += this.xvel;
        yval += this.yvel;
        this.currloc.x =  (int) Math.rint(xval); //this.xvel
        this.currloc.y = (int) Math.rint(yval); // this.yvel
    }
    
    /**
     * Draws the image of the projectile. 
     */
    public WorldImage projImage() {
        return new DiskImage(currloc, 4, new Red());
    }
    
    /**
     * Determines if the projectile has hit a wall.
     * If a side wall is hit, the x velocity is negated.
     * If a top or bottom wall is hit, the y velocity is negated.
     */
    public void hitWall () {
        if (this.currloc.x <= 1 || this.currloc.x >= 800) {
            this.xvel *= -1;
            this.bouncecount++;
        } else if (this.currloc.y <= 10 || this.currloc.y >=600) {
            this.yvel *= -1;
            this.bouncecount++;
        }
    }
    

    /**
     * Determines if the projectile has hit a platform.
     * If the side of the platform is hit, the x velocity is negated.
     * If the top or bottom of the platform is hit, the y velocity is negated.
     */
    public void hitPlatform (Posn pos) {
        
        // Hitting top/bottom of platform
        if (Math.abs(this.currloc.y - pos.y) < 15 && Math.abs(this.currloc.x - pos.x) <= 36) {
            this.yvel *= -1;
        } else if (Math.abs(this.currloc.x - pos.x) < 38 && Math.abs(this.currloc.y - pos.y) <= 16) {
            this.xvel *= -1;
        }
       
            
    }
    
    
    
    
}
