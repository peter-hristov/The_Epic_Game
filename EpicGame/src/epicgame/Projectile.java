
package epicgame;

import java.awt.image.BufferedImage;

/**
 *
 * @author Peter
 */
public class Projectile extends Space_Object{
    
   
    
    Projectile(int x,int y,int width,int height,BufferedImage a)
    {  
        image=a;
        
        this.x=x;    
        this.y=y; 
        
        this.width=width;
        this.height=height;
    }
     
}
