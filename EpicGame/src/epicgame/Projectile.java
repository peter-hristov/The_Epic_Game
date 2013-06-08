/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package epicgame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Peter
 */
public class Projectile {
    
    private BufferedImage projectile_image=new BufferedImage(EpicGame.Height,EpicGame.Widht, BufferedImage.TYPE_INT_RGB);
    public boolean active;
   
    
    public int x,y;
    public int height,width;
    
    Projectile(int x,int y,int height,int width,BufferedImage a)
    {
        
        projectile_image=a;
        
        this.x=x;    
        
        this.y=y; 
        
        this.height=height;
        
        this.width=width;
        
        active=true;
    }
    
    void paint(Graphics a)
    {
        if(active)
            a.drawImage(projectile_image,x,y,height,width, null);
        
    }
    
    void advance(int offset)
    {
        if(active)
        {
            x+=offset;          
        }
    }
    
}
