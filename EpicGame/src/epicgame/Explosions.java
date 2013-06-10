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
public class Explosions extends Collection_Of_Space_Objects{
    
    
    
    Explosions(int max_count,int w,int h,int image_w,int image_h, int delay,int frames,BufferedImage image)
    {
        this.max_count=max_count;
        m=new Explosion[max_count];
        
        this.w=w;
        this.h=h;
        
        this.image_w=image_w;
        this.image_h=image_h;
        
        this.delay=delay;
        this.frames=frames;
        
        this.image=image;
    }
    
    private int get_free_explosion()
    {
        int i;
        
        for( i=0;i<max_count;i++)
            if(m[i]==null)
                break;
        
        if(i==max_count)
            return -1;
        
        else return i;
        
        
    }
    
    public void spawn_explosion(int x,int y)
    {
        int i=get_free_explosion();
        if(i!=-1)
        {
            m[i]=new Explosion(x, y, w, h, image_w, image_h, delay, frames, image);
        }
    }
    
    
    public void update()
    {
        for(int i=0;i<max_count;i++)
            if(m[i]!=null)
            {
                m[i].update(-1,0);
                if(m[i].br==m[i].frames-1)
                    m[i]=null;
            }   
    }
}
