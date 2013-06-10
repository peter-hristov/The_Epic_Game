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
public class Space_Object {
    
    public int x,y;
    public int w,h;
    
    public int image_w,image_h;
    
    protected BufferedImage image;
    
    
    int frames;
    int br=0;
    
    int delay;
    int counter=0;
    
    Space_Object()
    {
    
    }
    
    Space_Object (int x,int y,int w,int h,int image_w,int image_h, int delay,int frames,BufferedImage image)
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
    
    public void update(int offset_x,int offset_y)
    {
        x+=offset_x;
        y+=offset_y;
            
        if(counter%delay==0)
        {
            br++;      
            br%=frames;
        }
        
        counter++;
        counter%=delay;
    }
    
    public void paint(Graphics a)
    {
        a.drawImage(image, x, y, x + w, y + h, br*image_w, 0, br*image_w+image_w, image_h, null);
    } 
}
