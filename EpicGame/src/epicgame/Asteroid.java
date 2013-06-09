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
    
    public int radius;
    
    Asteroid(int x,int y,int height,int width,int radius,BufferedImage a)
    {
        image=a;
        
        this.x=x;    
        this.y=y; 
        
        this.height=height;
        this.width=width;
        
        this.radius=radius;
        
        active=true;
    }
    
}
