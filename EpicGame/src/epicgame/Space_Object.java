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
    
    public int frame_w,frame_h;
    
    protected BufferedImage image;
    
    
    int frames;
    int br=0;
    
    int delay;
    int counter=0;
    
    Space_Object()
    {
    
    }
    
    Space_Object (int x,int y,int w,int h,int frame_w,int frame_h, int delay,int frames,BufferedImage image)
    {
        this.x=x;
        this.y=y;
        
        this.w=w;
        this.h=h;
        
        this.frame_w=frame_w;
        this.frame_h=frame_h;
        
        this.delay=delay;
        this.frames=frames;
        
        this.image=image;
    }
    
    public void update(int offset_x,int offset_y)
    {
        x+=offset_x;
        y+=offset_y;
            
        if(counter % delay==0 )
        {
            br++;     
            br%=frames;
        }
        
        counter++;
        counter%=delay;
    }
    
    public void paint(Graphics a)
    {
        a.drawImage(image, x, y, x + w, y + h, br*frame_w, 0, br*frame_w+frame_w, frame_h, null);
    }
    
}
