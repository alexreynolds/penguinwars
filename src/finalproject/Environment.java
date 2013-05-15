/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.util.*;
import java.awt.Color;
import javalib.impworld.*;
import javalib.colors.*;
import javalib.worldcanvas.*;
import javalib.worldimages.*;
import java.awt.*;
import sun.audio.*;
import java.io.*;
import java.text.DecimalFormat;


public class Environment extends World {
    
    // Size of the canvas
    int width = 800;
    int height = 600;
    
    // Contains the positions of the various projectiles
    ArrayList<Projectile> shots;
    
    // Contains the positions of the various targets
    ArrayList<Targets> targets;
    
    // Contains the positions of the various platforms
    ArrayList<Platforms> platforms;
    
    // Tracks time in world
    int counter = 1;
    
    // Tracks number of targets left
    int targetcounter = 5;
    
    // Tracks number of projectiles shot per round
    int shotcounter = 0;
    
    // Tracks number of targets destroyed
    int destroycounter = 0;
    
    // Tracks whether or not the player has won
    boolean done = false;
    
    // Initializes the shooter
    Shooter shotty = new Shooter();
    
    // Tracks if the player has clicked anywhere yet
    boolean mouseClicked = false;
    
    // A popup that is shown when the player kills all of the targets
    Victory window = new Victory(this);
    
    // Indicates if this is the first round of play
    boolean first = true;
    
    // Constructor for the environment class
    public Environment (Shooter shotty) {
        super();
        this.shots = new ArrayList<Projectile>();
        this.targets = new ArrayList<Targets>();
        this.platforms = new ArrayList<Platforms>();
        
        // Adds targets to the arraylist of targets
        for (int i = 0; i<targetcounter;i++){
            Targets temptarget = new Targets();
            temptarget.setLoc();
            for (int j = 0; j<targets.size(); j++){
                Targets temp = targets.get(j);
                if (Math.abs(temptarget.loc.x - temp.loc.x) < 60 || Math.abs(temptarget.loc.y - temp.loc.y) < 60)
                    temptarget.setLoc();
            }
            targets.add(temptarget);
            
            // Adds platforms to the arraylist of platforms
            Platforms tempplat = new Platforms();
            tempplat.setLoc(temptarget.loc);
            platforms.add(tempplat);
        }
        this.shotty = shotty;
        
        
    }
    
    /**
     * Performed on every tick of the timer.
     * Moves the projectiles, checks to see if the targets have been destroyed.
     */
    public void onTick() { 
        
        // Increments the time counter by 1
        counter++;
        
        // Moves the projectiles, tests to see if they're hitting the wall
       for (int i = 0; i<shots.size();i++){
           Projectile pro = shots.get(i);
           pro.moveProj();
           pro.hitWall();
           
           // If it has bounced 5 times, take projectile off the screen
           if (pro.bouncecount >= 5) {
               shots.remove(i);
               i--;
           }
           
       }
       
       // Steps through arrays of projectiles and targets to determine if the
       // projectile has hit a target
       for (int i = 0; i<targets.size(); i++) { 
           for (int j = 0; j<shots.size(); j++) {
               Targets temp = targets.get(i);
               Projectile tempshot = shots.get(j);
               // Plays a squish noise when target is destroyed
               if (temp.destroyTarget(tempshot.currloc) && temp.dead == false) {
                   try{
                        InputStream in = new FileInputStream("Sounds/memo.au");
                        AudioStream as = new AudioStream(in); 
                        AudioPlayer.player.start(as);  
                     }
                     catch(FileNotFoundException e){
                         //exception if the sound file cannot be found
                         System.out.println( "exception can't find file" + e);
                     }
                     //exception if the sound file cannot be read
                     catch(IOException e){
                         System.out.println( "can't read file" + e);

                     }
                   // The target has been destroyed, updates counters appropriately
                   temp.dead = true;
                   targetcounter--;
                   destroycounter++;
               }
           }
       }
       
       // Steps through array of platforms to determine if the projectile has hit a platform
       for (int i = 0;i<platforms.size();i++) {
           for (int j = 0;j<shots.size();j++) {
               Platforms temp = platforms.get(i);
               Projectile tempshot = shots.get(j);
               tempshot.hitPlatform(temp.loc);
           }
       }
       
       // If all targets are destroyed but the round hasn't officially finished
       if (targetcounter == 0 && !done) {
           
           // Plays a victory noise
           try{
                        InputStream in = new FileInputStream("Sounds/victory.au");
                        AudioStream as = new AudioStream(in); 
                        AudioPlayer.player.start(as);  
                     }
                     catch(FileNotFoundException e){
                         //exception if the sound file cannot be found
                         System.out.println( "exception can't find file" + e);
                     }
                     //exception if the sound file cannot be read
                     catch(IOException e){
                         System.out.println( "can't read file" + e);

                     }
           
           // Opens the victory pop up window
           window.setVisible(true);
           
           // The round is done
           done = true;
       }
       
       
    }
    
    /**
     * Performed when the player clicks the canvas.
     * Calls for a new projectile to be added to the screen, and increments the
     * shot counter.
     */
    public void onMouseClicked(Posn loc) {
        
        addProjectile(getY(loc), getX(loc)); 
        
        // Adds one to the shot counter
        shotcounter++;
        
        
    }
    
    /**
     * Adds a projectile to the ArrayList of projectiles.
     * Does so given the dy and dx of the respective projectile.
     */
    public void addProjectile (double dy, double dx) {
        Projectile temp = new Projectile(dy, dx);
        shots.add(temp);
    }
    
    /**
     * Draws the projectile image for each item in the projectile ArrayList.
     */
    public WorldImage showProjectiles (ArrayList<Projectile> shots) {
        WorldImage temp = new DiskImage(new Posn(0,0), 1, new Black());
        
        for (int i = 0; i<shots.size();i++){
            Projectile temppro = shots.get(i);
            temp = new OverlayImages(temp, temppro.projImage());
        }
        
        return temp;
    }
    
    /** Draws the target images for each item in the target ArrayList.
     */
    public WorldImage showTargets(ArrayList<Targets> targets) {
        WorldImage temp = new DiskImage(new Posn(0,0), 1, new Black());
        
        for (int i = 0; i<targets.size();i++){
            Targets temptarget = targets.get(i);
            if (temptarget.dead)
                temp = new OverlayImages(temp, temptarget.showBlood());
            else
                temp = new OverlayImages(temp, temptarget.showTarget());
        }
        
        return temp;
    }
    
    /**
     * Draws the platform images for each item in the platform ArrayList.
     * Each platform is drawn directly beneath a target.
     */
    public WorldImage showPlatforms(ArrayList<Platforms> platforms) {
        WorldImage temp = new DiskImage(new Posn(0,0), 1, new Black());
        
        for (int i = 0; i<platforms.size();i++){
            Platforms tempplat = platforms.get(i);
            temp = new OverlayImages(temp, tempplat.showPlatform());
        }
        
        return temp;
    }
    
    /** Calculates dx for the projectile's path.
     * Does this based on the location of the mouse click.
     */
    public double getX(Posn loc){
        double dx = loc.x - 90; //50
        
        return dx;
    }    
    
    /** Calculates dy for the projectile's path.
     * Does this based on the location of the mouse click.
     */
    public double getY(Posn loc) {
        double dy = 540 - loc.y; //550
        
        return dy;
    }
    
    /**
     * Draws the WorldImage for the canvas.
     */
    public WorldImage makeImage() {
        String shotsShot = shotcounter + " shots fired this round";
        String penguinskilled = destroycounter + " penguins obliterated so far";
        
        // Displays entry image for the game
        while (counter < 120 && first) {
            return new FromFileImage(new Posn(this.width/2, this.height/2), "Images/startimage.jpg");
        }
        
        // Draws the world image for the game
        return new OverlayImages(new FromFileImage(new Posn(this.width/2, this.height/2), "Images/background.png"),
                new OverlayImages(new RectangleImage(new Posn(this.width/2, 600), this.width, 50, Color.white),
                new OverlayImages(new TextImage(new Posn(82, 30), shotsShot, Color.white),
                new OverlayImages(new TextImage(new Posn(100, 45), penguinskilled, Color.white),
                new OverlayImages(showPlatforms(platforms),
                new OverlayImages(showTargets(targets), 
                new OverlayImages(showProjectiles(shots), this.shotty.ShowShooter())))))));
        
    }
    
    /**
     * Resets the canvas so the player may play another round.
     * Canvas is reset by distributing new targets and platforms, as well as clearing
     * the space of projectiles and resetting the shot counter.
     */
    
    public void reset() { 
        
        Random rand = new Random();
        first = false;
        done = false;
        
        // Generates the number of targets for the next round, between 3 and 7
        targetcounter = rand.nextInt(5) + 3;
        
        // Removes all targets and platforms from the previous round
        targets.removeAll(targets);
        platforms.removeAll(platforms);
        
        // Creates the new targets and corresponding platforms for the next round
        for (int i = 0; i<targetcounter;i++){
            Targets temptarget = new Targets();
            temptarget.setLoc();
            for (int j = 0; j<targets.size(); j++){
                Targets temp = targets.get(j);
                if (Math.abs(temptarget.loc.x - temp.loc.x) < 60 || Math.abs(temptarget.loc.y - temp.loc.y) < 60)
                    temptarget.setLoc();
            }
            targets.add(temptarget);
            // Adds platforms to the arraylist of platforms
            Platforms tempplat = new Platforms();
            tempplat.setLoc(temptarget.loc);
            platforms.add(tempplat);
        }
        
        // Resets the shot counter to 0
        shotcounter = 0;
        
        // Closes the popup window
        window.setVisible(false);
        
        // Clears projectiles 
        shots.removeAll(shots);
        
    }
    
}
