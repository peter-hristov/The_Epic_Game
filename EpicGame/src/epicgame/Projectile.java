
package epicgame;

import java.awt.image.BufferedImage;

/**
 *
 * @author Peter
 */
public class Projectile extends Space_Object{
    
   
    
    Projectile (int x,int y,int w,int h,int image_w,int image_h, int delay,int frames,BufferedImage image)
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
