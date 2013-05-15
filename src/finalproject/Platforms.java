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




public class Platforms {
    
    int width = 72;
    int height = 30;
    
    // Location of the platform
    Posn loc;
    
    // Constructor for the class
    public Platforms() {
        this.loc = loc;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Sets the location for the platform.
     * Location is based on the given location of a target.
     * The platform is aligned with the bottom edge of the target.
     */
    public void setLoc(Posn pos) {
        
        int x = pos.x;
        int y = pos.y + 52;
        
        this.loc = new Posn(x,y);
        
    }
    
    /**
     * Draws the platform image.
     */
    public WorldImage showPlatform () {
        return new FromFileImage(this.loc, "Images/ice.png");
    }
    
    
}
