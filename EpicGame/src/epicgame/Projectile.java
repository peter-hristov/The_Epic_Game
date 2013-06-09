
package epicgame;

import java.awt.image.BufferedImage;

/**
 *
 * @author Peter
 */
public class Projectile extends Space_Object{
    
  
    
    Projectile(int x,int y,int height,int width,BufferedImage a)
    {  
        image=a;
        
        this.x=x;    
        this.y=y; 
        
        this.height=height;
        this.width=width;
        
        active=true;
    }
     
}
