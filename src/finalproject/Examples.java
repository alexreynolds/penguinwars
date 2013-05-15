/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import javalib.worldimages.Posn;
import tester.*;
import java.util.*;
import javalib.impworld.World;
import javalib.worldimages.*;
import javalib.colors.*;
import java.lang.Math.*;

public class Examples {
    
Projectile p1 = new Projectile(1.0,1.0);
Projectile p2 = new Projectile(2.0,2.0);
Projectile p3 = new Projectile (1.0,2.0);
Projectile p4 = new Projectile(1.0,0.0);
Projectile p5 = new Projectile(0.0,1.0);


void testMoveProj(Tester t){
    
    p1.moveProj();
    p2.moveProj();
    p3.moveProj();
    
    t.checkExpect(p1.xval,440.0);
    t.checkExpect(p1.yval,190.0);
    t.checkExpect(p2.xval,265.0);
    t.checkExpect(p2.yval,365.0);
    t.checkExpect(p3.xval,370.0);
    t.checkExpect(p3.yval,400.0);
}

    
void testBounce(Tester t){
    
   t.checkExpect(p4.yvel,-700.0);
   t.checkExpect(p5.xvel,700.0);
   p4.moveProj();
   p5.moveProj();
    p4.moveProj();
   p5.moveProj();
    

   t.checkExpect(p4.yvel,-700.0);
   t.checkExpect(p5.xvel,700.0);
}

void testLine(Tester t){
    p1.moveProj();
    t.checkExpect(p1.xval, 790.0);
    t.checkExpect(p1.xvel==(p1.yvel)*-1);
          
}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
   Examples e = new Examples();
   Tester.runReport(e, false, false); 
 
    // Run the game
    Environment env;
    
    env = new Environment(new Shooter());
    env.bigBang(800, 600, 0.01);   
    }
}
