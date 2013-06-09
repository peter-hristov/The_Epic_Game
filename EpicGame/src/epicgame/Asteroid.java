/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package epicgame;

import java.awt.image.BufferedImage;

/**
 *
 * @author Peter
 */
public class Asteroid extends Space_Object{
    
    
    
    Asteroid(int x,int y,int width,int height,int radius,BufferedImage a)
    {
        image=a;
        
        this.x=x;    
        this.y=y; 
        
        this.width=width;
        this.height=height;
       
        this.radius=radius;
        
        active=true;
    }
    
}
