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
    

    Asteroid (int x,int y,int w,int h,int image_w,int image_h, int delay,int frames,BufferedImage image)
    {
        this.x=x;
        this.y=y;
        
        this.w=w;
        this.h=h;
        
        this.image_w=image_w;
        this.image_h=image_h;
        
        this.delay=delay;
        this.frames=frames;
        
        this.image=image;
    }
    
}
