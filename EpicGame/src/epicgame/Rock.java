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
public class Rock {
    private BufferedImage projectile_image=new BufferedImage(EpicGame.Height,EpicGame.Widht, BufferedImage.TYPE_INT_RGB);
    public boolean active=true;
    public boolean visible=false;
    
    public int x,y;
    public int height,width;
    
    Rock(int x,int y,int height,int width,BufferedImage a)
    {
        
        projectile_image=a;
        this.x=x;    
        this.y=y; 
        this.height=height;
        this.width=width;
    }
    
    void paint(Graphics a)
    {
        if(visible)
            a.drawImage(projectile_image,x,y,height,width, null);
        
    }
    
    void move(int offset_x,int offset_y)
    {
        if(active)
        {
            x+=offset_x;
            y+=offset_y;
        }
    }
    
}
